package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppRepository
import com.example.data.BankQuestion
import com.example.data.CurriculumDataSource
import com.example.data.Difficulty
import com.example.data.ExamResult
import com.example.data.PrintHelper
import com.example.ui.components.GlassCard
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamCenterScreen(repository: AppRepository) {
    val context = LocalContext.current
    val subjects = CurriculumDataSource.subjects
    val allQuestions = CurriculumDataSource.questionBank

    // Generator parameters
    var selectedSubject by remember { mutableStateOf(subjects.first()) }
    var selectedDifficulty by remember { mutableStateOf<Difficulty?>(null) }
    var questionsCount by remember { mutableIntStateOf(5) }

    // Active Exam state
    var activeExamQuestions by remember { mutableStateOf<List<BankQuestion>?>(null) }
    val userAnswers = remember { mutableStateMapOf<String, Int>() }
    var examCompleted by remember { mutableStateOf(false) }
    var lastExamResult by remember { mutableStateOf<ExamResult?>(null) }

    // Dropdown state
    var subjectExpanded by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("exam_center_screen"),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Mode 1: Exam Generator View (if no exam is currently taking place)
        if (activeExamQuestions == null) {
            item {
                Text(
                    text = "🎯 مولد الامتحانات الذكي",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "حدد المادة ومستوى الصعوبة لتوليد امتحان مطابق لمواصفات الورقة الامتحانية.",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))

                GlassCard(modifier = Modifier.fillMaxWidth()) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        // Subject selector
                        Text(text = "اختر المادة:", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        ExposedDropdownMenuBox(
                            expanded = subjectExpanded,
                            onExpandedChange = { subjectExpanded = it }
                        ) {
                            OutlinedTextField(
                                value = selectedSubject.name,
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = subjectExpanded) },
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth()
                            )
                            ExposedDropdownMenu(
                                expanded = subjectExpanded,
                                onDismissRequest = { subjectExpanded = false }
                            ) {
                                subjects.forEach { sub ->
                                    DropdownMenuItem(
                                        text = { Text(sub.name) },
                                        onClick = {
                                            selectedSubject = sub
                                            subjectExpanded = false
                                        }
                                    )
                                }
                            }
                        }

                        // Difficulty filter
                        Text(text = "مستوى الصعوبة:", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            FilterChip(
                                selected = selectedDifficulty == null,
                                onClick = { selectedDifficulty = null },
                                label = { Text("شامل (كل المستويات)") }
                            )
                            Difficulty.values().forEach { diff ->
                                FilterChip(
                                    selected = selectedDifficulty == diff,
                                    onClick = { selectedDifficulty = diff },
                                    label = { Text(diff.title) }
                                )
                            }
                        }

                        // Questions count slider
                        Text(
                            text = "عدد الأسئلة: $questionsCount أسئلة",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                        Slider(
                            value = questionsCount.toFloat(),
                            onValueChange = { questionsCount = it.roundToInt() },
                            valueRange = 3f..10f,
                            steps = 6
                        )

                        // Start Exam button
                        Button(
                            onClick = {
                                val filtered = allQuestions.filter {
                                    (it.subjectId == selectedSubject.id || allQuestions.size < 5) &&
                                            (selectedDifficulty == null || it.difficulty == selectedDifficulty)
                                }.ifEmpty { allQuestions }
                                activeExamQuestions = filtered.shuffled().take(questionsCount)
                                userAnswers.clear()
                                examCompleted = false
                                lastExamResult = null
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Quiz, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("توليد وبدء الامتحان الآن", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Quick Ready-to-Take Exams
            item {
                Text(
                    text = "📝 امتحانات جاهزة للوحدات والدروس",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(8.dp))

                listOf(
                    Pair("امتحان شامل: علوم (الوحدة الأولى - القوى والحركة)", "science"),
                    Pair("امتحان جبر وهندسة: حاصل الضرب الديكارتي وحساب المثلثات", "math"),
                    Pair("امتحان لغة عربية: النحو (المنادى والبدل)", "arabic")
                ).forEach { (examTitle, subId) ->
                    GlassCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        onClick = {
                            activeExamQuestions = allQuestions.shuffled().take(5)
                            userAnswers.clear()
                            examCompleted = false
                            lastExamResult = null
                        }
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = examTitle, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Text(text = "5 أسئلة • المدة المقترحة: 20 دقيقة", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            Button(
                                onClick = {
                                    activeExamQuestions = allQuestions.shuffled().take(5)
                                    userAnswers.clear()
                                    examCompleted = false
                                    lastExamResult = null
                                },
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("بدء")
                            }
                        }
                    }
                }
            }
        } else {
            // Mode 2: Active Exam or Completed Result Screen
            val questions = activeExamQuestions!!

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (examCompleted) "📊 نتيجة الامتحان وتحليل الأداء" else "📝 جاري الامتحان: ${selectedSubject.name}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Button(
                        onClick = {
                            activeExamQuestions = null
                            examCompleted = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant, contentColor = MaterialTheme.colorScheme.onSurfaceVariant)
                    ) {
                        Text(if (examCompleted) "امتحان جديد" else "إنهاء والعودة", fontSize = 11.sp)
                    }
                }
            }

            // If exam was completed, show score summary and print actions
            if (examCompleted && lastExamResult != null) {
                val res = lastExamResult!!
                item {
                    GlassCard(
                        modifier = Modifier.fillMaxWidth(),
                        borderBrush = androidx.compose.ui.graphics.Brush.horizontalGradient(
                            listOf(Color(0xFF10B981), Color(0xFF6366F1))
                        )
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "الدرجة: ${res.correctAnswers} من ${res.totalQuestions}",
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 18.sp,
                                        color = if (res.scorePercentage >= 80) Color(0xFF10B981) else Color(0xFFD97706)
                                    )
                                    Text(
                                        text = "النسبة المئوية: ${res.scorePercentage}% ${if (res.scorePercentage >= 85) "🌟 ممتاز" else "جيد جداً"}",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }

                            if (res.reviewRecommendations.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "📌 دروس يُنصح بمراجعتها بناءً على أخطائك:",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = Color(0xFFEF4444)
                                )
                                res.reviewRecommendations.forEach {
                                    Text(text = "• $it", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            // Print Buttons for Exam and Model Answers
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Button(
                                    onClick = {
                                        val html = PrintHelper.generateExamHtml(
                                            examTitle = res.examTitle,
                                            subjectName = res.subjectName,
                                            questions = questions,
                                            includeAnswers = false
                                        )
                                        PrintHelper.printHtml(context, html, "ورقة_امتحان_${res.subjectName}")
                                    },
                                    modifier = Modifier.weight(1f),
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Icon(imageVector = Icons.Default.Print, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("طباعة الأسئلة", fontSize = 11.sp)
                                }

                                Button(
                                    onClick = {
                                        val html = PrintHelper.generateExamHtml(
                                            examTitle = res.examTitle,
                                            subjectName = res.subjectName,
                                            questions = questions,
                                            includeAnswers = true
                                        )
                                        PrintHelper.printHtml(context, html, "نموذج_إجابة_${res.subjectName}")
                                    },
                                    modifier = Modifier.weight(1f),
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Icon(imageVector = Icons.Default.Print, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("طباعة نموذج الإجابة", fontSize = 11.sp)
                                }
                            }
                        }
                    }
                }
            }

            // Questions List
            items(questions.mapIndexed { i, q -> Pair(i, q) }, key = { it.second.id }) { (index, q) ->
                val chosenIndex = userAnswers[q.id]
                val isCorrect = chosenIndex == q.correctIndex

                GlassCard(modifier = Modifier.fillMaxWidth()) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "السؤال ${index + 1}: ${q.question}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                modifier = Modifier.weight(1f)
                            )
                            if (examCompleted) {
                                Icon(
                                    imageVector = if (isCorrect) Icons.Default.CheckCircle else Icons.Default.Close,
                                    contentDescription = null,
                                    tint = if (isCorrect) Color(0xFF10B981) else Color(0xFFEF4444)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        if (q.options.isNotEmpty()) {
                            q.options.forEachIndexed { optIdx, optText ->
                                val isSelected = chosenIndex == optIdx
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(
                                            if (examCompleted) {
                                                if (optIdx == q.correctIndex) Color(0xFFD1FAE5)
                                                else if (isSelected) Color(0xFFFEE2E2)
                                                else MaterialTheme.colorScheme.surfaceVariant
                                            } else if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                                            else MaterialTheme.colorScheme.surfaceVariant
                                        )
                                        .clickable(enabled = !examCompleted) {
                                            userAnswers[q.id] = optIdx
                                        }
                                        .padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = if (isSelected) Icons.Default.RadioButtonChecked else Icons.Default.RadioButtonUnchecked,
                                        contentDescription = null,
                                        tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(text = optText, fontSize = 13.sp)
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                            }
                        }

                        if (examCompleted) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "✓ الإجابة الصحيحة: ${q.answer}",
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF047857),
                                fontSize = 12.sp
                            )
                            Text(
                                text = "تفسير: ${q.explanation}",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }

            // Submit Exam Button
            if (!examCompleted) {
                item {
                    Button(
                        onClick = {
                            examCompleted = true
                            var correct = 0
                            val wrongs = mutableListOf<String>()
                            val recs = mutableListOf<String>()

                            questions.forEach { q ->
                                if (userAnswers[q.id] == q.correctIndex) {
                                    correct++
                                } else {
                                    wrongs.add(q.question)
                                    recs.add("${q.unitTitle}: ${q.lessonTitle}")
                                }
                            }

                            val pct = if (questions.isNotEmpty()) (correct * 100) / questions.size else 0
                            val result = ExamResult(
                                id = "res_${System.currentTimeMillis()}",
                                examTitle = "امتحان ${selectedSubject.name}",
                                subjectName = selectedSubject.name,
                                timestamp = System.currentTimeMillis(),
                                totalQuestions = questions.size,
                                correctAnswers = correct,
                                scorePercentage = pct,
                                wrongQuestions = wrongs,
                                reviewRecommendations = recs.distinct()
                            )
                            repository.saveExamResult(result)
                            lastExamResult = result
                            Toast.makeText(context, "تم تصحيح الامتحان وحفظ النتيجة في سجلك!", Toast.LENGTH_LONG).show()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("تسليم الإجابات وإنهاء الامتحان", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
