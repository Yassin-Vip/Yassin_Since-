package com.example.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CurriculumDataSource
import com.example.data.Difficulty
import com.example.ui.components.GlassCard

@Composable
fun QuestionBankScreen() {
    val allQuestions = CurriculumDataSource.questionBank
    var searchQuery by remember { mutableStateOf("") }
    var selectedSubjectId by remember { mutableStateOf<String?>(null) }
    var selectedDifficulty by remember { mutableStateOf<Difficulty?>(null) }

    val revealedAnswers = remember { mutableStateMapOf<String, Boolean>() }

    val filteredQuestions = allQuestions.filter { q ->
        (selectedSubjectId == null || q.subjectId == selectedSubjectId) &&
                (selectedDifficulty == null || q.difficulty == selectedDifficulty) &&
                (searchQuery.isBlank() || q.question.contains(searchQuery) || q.answer.contains(searchQuery))
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("question_bank_screen"),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = "🧠 بنك الأسئلة الشامل",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "مجموعة أسئلة تدريبية متنوعة (اختيار من متعدد، صح وخطأ، أكمل ومسائل) مع الإجابات النموذجية والشرح.",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("ابحث في الأسئلة والتدريبات...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }

        // Subject Filter chips
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                item {
                    FilterChip(
                        selected = selectedSubjectId == null,
                        onClick = { selectedSubjectId = null },
                        label = { Text("الكل") }
                    )
                }
                items(CurriculumDataSource.subjects) { sub ->
                    FilterChip(
                        selected = selectedSubjectId == sub.id,
                        onClick = { selectedSubjectId = sub.id },
                        label = { Text(sub.name) }
                    )
                }
            }
        }

        // Questions List
        if (filteredQuestions.isEmpty()) {
            item {
                Text(
                    text = "لم يتم العثور على أسئلة تطابق معايير البحث.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }
        } else {
            items(filteredQuestions, key = { it.id }) { q ->
                val isRevealed = revealedAnswers[q.id] == true

                GlassCard(modifier = Modifier.fillMaxWidth()) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "${q.subjectName} • ${q.unitTitle}",
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${q.type.title} (${q.difficulty.title})",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 11.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = q.question,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            lineHeight = 22.sp
                        )

                        if (q.options.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            q.options.forEachIndexed { i, opt ->
                                Text(
                                    text = "${listOf("أ", "ب", "جـ", "د").getOrElse(i) { "${i+1}" }}) $opt",
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Button(
                            onClick = { revealedAnswers[q.id] = !isRevealed },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isRevealed) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                                contentColor = if (isRevealed) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.primary
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(if (isRevealed) "إخفاء الإجابة والشرح" else "إظهار الإجابة والشرح 💡", fontSize = 12.sp)
                        }

                        if (isRevealed) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "✓ الإجابة الصحيحة: ${q.answer}",
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF10B981),
                                fontSize = 13.sp
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "تفسير: ${q.explanation}",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}
