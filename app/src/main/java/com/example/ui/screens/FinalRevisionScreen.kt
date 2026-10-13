package com.example.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.data.SubjectRevisionSummary
import com.example.ui.components.GlassCard

@Composable
fun FinalRevisionScreen() {
    val summaries = CurriculumDataSource.revisionSummaries
    var selectedSubjectId by remember { mutableStateOf(summaries.first().subjectId) }

    val currentSummary = summaries.find { it.subjectId == selectedSubjectId } ?: summaries.first()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("final_revision_screen"),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "🌟 المراجعة النهائية وكبسولة ليلة الامتحان",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "خلاصة المنهج، القواعد الذهبية، وتنبيهات الأخطاء الشائعة لتثبيت المعلومات قبل الامتحان.",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(10.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(summaries) { s ->
                    FilterChip(
                        selected = selectedSubjectId == s.subjectId,
                        onClick = { selectedSubjectId = s.subjectId },
                        label = { Text(s.subjectName) }
                    )
                }
            }
        }

        // Capsule Summary
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.10f)
            ) {
                Column {
                    Text(
                        text = "💊 كبسولة المنهج السريعة:",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = currentSummary.capsuleSummary,
                        fontSize = 14.sp,
                        lineHeight = 22.sp
                    )
                }
            }
        }

        // Golden Rules
        item {
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column {
                    Text(
                        text = "⭐ القواعد الذهبية التي لا يخلو منها امتحان:",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFD97706),
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    currentSummary.goldenRules.forEach { rule ->
                        Text(
                            text = "• $rule",
                            fontSize = 13.sp,
                            lineHeight = 22.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }

        // Common Mistakes
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = Color(0xFFFEF2F2)
            ) {
                Column {
                    Text(
                        text = "⚠️ أخطاء شائعة يقع فيها طلاب 3 إعدادي (احذرها!):",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFDC2626),
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    currentSummary.commonMistakes.forEach { mistake ->
                        Text(
                            text = "❌ $mistake",
                            fontSize = 13.sp,
                            lineHeight = 22.sp,
                            color = Color(0xFF991B1B)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }

        // Night Before Exam Tips
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = Color(0xFFECFDF5)
            ) {
                Column {
                    Text(
                        text = "🌙 نصائح ليلة الامتحان:",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF059669),
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    currentSummary.nightBeforeExamTips.forEach { tip ->
                        Text(
                            text = "✓ $tip",
                            fontSize = 13.sp,
                            lineHeight = 22.sp,
                            color = Color(0xFF065F46)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }
    }
}
