package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppRepository
import com.example.data.Lesson
import com.example.data.PrintHelper
import com.example.data.QuestionType
import com.example.data.QuizQuestion
import com.example.ui.components.GlassCard

enum class LessonTab(val title: String) {
    SUMMARY("شرح مبسط"),
    DETAILED("شرح تفصيلي"),
    KEY_POINTS("أهم النقاط"),
    DEFINITIONS("التعريفات"),
    FORMULAS("القوانين"),
    EXAMPLES("أمثلة محلولة"),
    EXERCISES("تدريبات"),
    QUIZ("اختبار وواجب"),
    NOTES("ملاحظاتي ✍️")
}

@Composable
fun LessonDetailScreen(
    lesson: Lesson,
    subjectName: String,
    repository: AppRepository,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    var selectedTab by remember { mutableStateOf(LessonTab.SUMMARY) }
    val profile by repository.studentProfile.collectAsState()
    val notesMap by repository.notesMap.collectAsState()

    val isBookmarked = profile.bookmarkedLessons.contains(lesson.id)
    val isCompleted = profile.completedLessons.contains(lesson.id)

    // Quiz user answers state
    val selectedOptions = remember { mutableStateMapOf<String, Int>() }
    val textAnswers = remember { mutableStateMapOf<String, String>() }
    var quizSubmitted by remember { mutableStateOf(false) }

    // Notes local text state
    var currentNoteText by remember { mutableStateOf(notesMap[lesson.id] ?: "") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("lesson_detail_screen")
    ) {
        // Quick Action Bar (Bookmark, Mark Completed, Print)
        GlassCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Bookmark toggle
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable {
                            repository.toggleLessonBookmark(lesson.id)
                            Toast.makeText(
                                context,
                                if (!isBookmarked) "تمت إضافة الدرس إلى المحفوظات ⭐" else "تمت إزالة الدرس من المحفوظات",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        .background(
                            if (isBookmarked) Color(0xFFFEF3C7) else MaterialTheme.colorScheme.surfaceVariant
                        )
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "حفظ للمراجعة",
                        tint = if (isBookmarked) Color(0xFFD97706) else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (isBookmarked) "محفوظ للمراجعة" else "حفظ للمراجعة",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isBookmarked) Color(0xFF92400E) else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Completed toggle
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable {
                            repository.markLessonCompleted(lesson.id, !isCompleted)
                        }
                        .background(
                            if (isCompleted) Color(0xFFD1FAE5) else MaterialTheme.colorScheme.surfaceVariant
                        )
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (isCompleted) Icons.Default.CheckCircle else Icons.Default.Check,
                        contentDescription = "تم الإكمال",
                        tint = if (isCompleted) Color(0xFF059669) else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (isCompleted) "مكتمل ✓" else "إكمال الدرس",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isCompleted) Color(0xFF065F46) else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Print Lesson Button
                IconButton(
                    onClick = {
                        val html = PrintHelper.generateLessonHtml(lesson, subjectName)
                        PrintHelper.printHtml(context, html, "طباعة_${lesson.title}")
                    },
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
                ) {
                    Icon(
                        imageVector = Icons.Default.Print,
                        contentDescription = "طباعة الدرس",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        // Horizontal Scrollable Tabs
        ScrollableTabRow(
            selectedTabIndex = selectedTab.ordinal,
            edgePadding = 16.dp,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            LessonTab.values().forEach { tab ->
                Tab(
                    selected = selectedTab == tab,
                    onClick = { selectedTab = tab },
                    text = {
                        Text(
                            text = tab.title,
                            fontSize = 12.sp,
                            fontWeight = if (selectedTab == tab) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
            }
        }

        // Tab Content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(bottom = 96.dp)
        ) {
            when (selectedTab) {
                LessonTab.SUMMARY -> {
                    item {
                        GlassCard(modifier = Modifier.fillMaxWidth()) {
                            Column {
                                Text(
                                    text = "💡 الشرح المبسط للدرس",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = lesson.simpleSummary,
                                    fontSize = 14.sp,
                                    lineHeight = 24.sp
                                )
                            }
                        }
                    }
                }

                LessonTab.DETAILED -> {
                    item {
                        GlassCard(modifier = Modifier.fillMaxWidth()) {
                            Column {
                                Text(
                                    text = "🔍 الشرح التفصيلي وعناصر الدرس",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = lesson.detailedExplanation,
                                    fontSize = 14.sp,
                                    lineHeight = 24.sp
                                )
                            }
                        }
                    }
                }

                LessonTab.KEY_POINTS -> {
                    item {
                        Text(
                            text = "⭐ أهم النقاط الواجب حفظها",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                    items(lesson.keyPoints) { point ->
                        GlassCard(modifier = Modifier.fillMaxWidth()) {
                            Row(verticalAlignment = Alignment.Top) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.primary)
                                        .padding(top = 8.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = point,
                                    fontSize = 14.sp,
                                    lineHeight = 22.sp
                                )
                            }
                        }
                    }
                }

                LessonTab.DEFINITIONS -> {
                    if (lesson.definitions.isEmpty()) {
                        item {
                            Text(
                                text = "لا توجد مصطلحات خاصة بهذا الدرس، راجع الشرح التفصيلي.",
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    } else {
                        items(lesson.definitions) { def ->
                            GlassCard(modifier = Modifier.fillMaxWidth()) {
                                Column {
                                    Text(
                                        text = def.term,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = def.meaning,
                                        fontSize = 13.sp,
                                        lineHeight = 22.sp
                                    )
                                    if (def.note.isNotEmpty()) {
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text(
                                            text = "ملاحظة: ${def.note}",
                                            fontSize = 12.sp,
                                            color = MaterialTheme.colorScheme.tertiary
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                LessonTab.FORMULAS -> {
                    if (lesson.formulas.isEmpty()) {
                        item {
                            Text(
                                text = "هذا الدرس لا يتضمن قوانين رياضية أو فيزيائية مباشرة.",
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    } else {
                        items(lesson.formulas) { formulaItem ->
                            GlassCard(
                                modifier = Modifier.fillMaxWidth(),
                                borderBrush = Brush.horizontalGradient(
                                    listOf(Color(0xFF6366F1), Color(0xFF38BDF8))
                                )
                            ) {
                                Column {
                                    Text(
                                        text = formulaItem.name,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(MaterialTheme.colorScheme.surfaceVariant)
                                            .padding(10.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = formulaItem.formula,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = formulaItem.explanation,
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    if (formulaItem.unit.isNotEmpty()) {
                                        Text(
                                            text = "وحدة القياس: ${formulaItem.unit}",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = MaterialTheme.colorScheme.secondary
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                LessonTab.EXAMPLES -> {
                    if (lesson.workedExamples.isEmpty()) {
                        item {
                            Text(
                                text = "لا توجد مسائل وأمثلة حسابية في هذا الدرس.",
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    } else {
                        items(lesson.workedExamples) { example ->
                            GlassCard(modifier = Modifier.fillMaxWidth()) {
                                Column {
                                    Text(
                                        text = "📌 المسألة:",
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary,
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        text = example.problem,
                                        fontSize = 14.sp,
                                        lineHeight = 22.sp
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Text(
                                        text = "📝 خطوات الحل النموذجية:",
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.secondary,
                                        fontSize = 13.sp
                                    )
                                    example.steps.forEach { step ->
                                        Text(
                                            text = "• $step",
                                            fontSize = 13.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(Color(0xFFD1FAE5))
                                            .padding(horizontal = 8.dp, vertical = 4.dp)
                                    ) {
                                        Text(
                                            text = "✓ الناتج النهائي: ${example.finalAnswer}",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp,
                                            color = Color(0xFF065F46)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                LessonTab.EXERCISES -> {
                    if (lesson.practiceQuestions.isEmpty()) {
                        item {
                            Text(
                                text = "راجع بنك الأسئلة والامتحانات لحل مئات الأسئلة المخصصة.",
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    } else {
                        items(lesson.practiceQuestions) { exercise ->
                            var showAnswer by remember { mutableStateOf(false) }
                            GlassCard(modifier = Modifier.fillMaxWidth()) {
                                Column {
                                    Text(
                                        text = "❓ ${exercise.question}",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp
                                    )
                                    if (exercise.tip.isNotEmpty()) {
                                        Text(
                                            text = "إرشاد: ${exercise.tip}",
                                            fontSize = 12.sp,
                                            color = MaterialTheme.colorScheme.tertiary
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Button(
                                        onClick = { showAnswer = !showAnswer },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                                        ),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text(if (showAnswer) "إخفاء الإجابة" else "إظهار الإجابة", fontSize = 12.sp)
                                    }
                                    if (showAnswer) {
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text(
                                            text = "✓ ${exercise.answer}",
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF10B981),
                                            fontSize = 13.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                LessonTab.QUIZ -> {
                    item {
                        if (lesson.quizQuestions.isEmpty()) {
                            Text(
                                text = "لا يوجد اختبار قصير مدرج لهذا الدرس حالياً.",
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        } else {
                            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                Text(
                                    text = "🎯 اختبار قصير تفاعلي على الدرس",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )

                                lesson.quizQuestions.forEachIndexed { index, q ->
                                    val userOption = selectedOptions[q.id]
                                    val isCorrect = userOption != null && userOption == q.correctOptionIndex

                                    GlassCard(modifier = Modifier.fillMaxWidth()) {
                                        Column {
                                            Text(
                                                text = "${index + 1}. ${q.questionText}",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 14.sp
                                            )
                                            Spacer(modifier = Modifier.height(8.dp))

                                            if (q.type == QuestionType.MCQ || q.type == QuestionType.TRUE_FALSE) {
                                                q.options.forEachIndexed { optIndex, optionText ->
                                                    val isSelected = userOption == optIndex
                                                    Row(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .clip(RoundedCornerShape(8.dp))
                                                            .background(
                                                                if (quizSubmitted) {
                                                                    if (optIndex == q.correctOptionIndex) Color(0xFFD1FAE5)
                                                                    else if (isSelected) Color(0xFFFEE2E2)
                                                                    else MaterialTheme.colorScheme.surfaceVariant
                                                                } else if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                                                                else MaterialTheme.colorScheme.surfaceVariant
                                                            )
                                                            .clickable(enabled = !quizSubmitted) {
                                                                selectedOptions[q.id] = optIndex
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
                                                        Text(
                                                            text = optionText,
                                                            fontSize = 13.sp,
                                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                                        )
                                                    }
                                                    Spacer(modifier = Modifier.height(6.dp))
                                                }
                                            } else if (q.type == QuestionType.COMPLETE) {
                                                OutlinedTextField(
                                                    value = textAnswers[q.id] ?: "",
                                                    onValueChange = { if (!quizSubmitted) textAnswers[q.id] = it },
                                                    label = { Text("اكتب إجابتك هنا") },
                                                    modifier = Modifier.fillMaxWidth(),
                                                    enabled = !quizSubmitted
                                                )
                                            }

                                            if (quizSubmitted) {
                                                Spacer(modifier = Modifier.height(6.dp))
                                                Text(
                                                    text = "تفسير الإجابة: ${q.explanation}",
                                                    fontSize = 12.sp,
                                                    color = Color(0xFF047857),
                                                    fontWeight = FontWeight.Medium
                                                )
                                            }
                                        }
                                    }
                                }

                                if (!quizSubmitted) {
                                    Button(
                                        onClick = {
                                            quizSubmitted = true
                                            var score = 0
                                            lesson.quizQuestions.forEach { q ->
                                                if (selectedOptions[q.id] == q.correctOptionIndex) score++
                                            }
                                            repository.addXp(score * 15)
                                            Toast.makeText(
                                                context,
                                                "أحسنت! حققت $score من ${lesson.quizQuestions.size}",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        },
                                        modifier = Modifier.fillMaxWidth(),
                                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                                    ) {
                                        Text("تصحيح الاختبار وعرض النتيجة", fontWeight = FontWeight.Bold)
                                    }
                                }

                                if (lesson.homework.isNotEmpty()) {
                                    Spacer(modifier = Modifier.height(14.dp))
                                    GlassCard(
                                        modifier = Modifier.fillMaxWidth(),
                                        backgroundColor = Color(0xFFFFFBEB)
                                    ) {
                                        Column {
                                            Text(
                                                text = "📚 واجب الدرس:",
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFFB45309),
                                                fontSize = 14.sp
                                            )
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                text = lesson.homework,
                                                color = Color(0xFF78350F),
                                                fontSize = 13.sp
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                LessonTab.NOTES -> {
                    item {
                        GlassCard(modifier = Modifier.fillMaxWidth()) {
                            Column {
                                Text(
                                    text = "✍️ مفكرة ملاحظاتك الخاصة على هذا الدرس",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "اكتب هنا التلخيص، الأسئلة التي تريد سؤال المعلم عنها، أو الملاحظات الهامة. تحفظ محلياً على جهازك.",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                OutlinedTextField(
                                    value = currentNoteText,
                                    onValueChange = { currentNoteText = it },
                                    label = { Text("اكتب ملاحظاتك...") },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(150.dp),
                                    maxLines = 8
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Button(
                                    onClick = {
                                        repository.saveLessonNote(lesson.id, currentNoteText)
                                        Toast.makeText(context, "تم حفظ الملاحظات بنجاح!", Toast.LENGTH_SHORT).show()
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Icon(imageVector = Icons.Default.Save, contentDescription = null, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("حفظ الملاحظة محلياً")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
