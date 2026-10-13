package com.example.ui.screens

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
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppRepository
import com.example.data.CurriculumDataSource
import com.example.data.Subject
import com.example.data.Term
import com.example.ui.components.GlassCard

@Composable
fun CurriculumScreen(
    repository: AppRepository,
    onSelectLesson: (String) -> Unit
) {
    var selectedTerm by remember { mutableStateOf(Term.TERM_1) }
    var expandedSubjectId by remember { mutableStateOf<String?>("science") }
    val profile by repository.studentProfile.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("curriculum_screen"),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Term Switcher (الترم الأول / الترم الثاني)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                FilterChip(
                    selected = selectedTerm == Term.TERM_1,
                    onClick = { selectedTerm = Term.TERM_1 },
                    label = { Text("الترم الأول 🍁", fontWeight = FontWeight.Bold) },
                    modifier = Modifier.padding(end = 8.dp),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = Color.White
                    )
                )
                FilterChip(
                    selected = selectedTerm == Term.TERM_2,
                    onClick = { selectedTerm = Term.TERM_2 },
                    label = { Text("الترم الثاني 🌸", fontWeight = FontWeight.Bold) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = Color.White
                    )
                )
            }
        }

        // Subjects List
        items(CurriculumDataSource.subjects, key = { it.id }) { subject ->
            val units = if (selectedTerm == Term.TERM_1) subject.unitsTerm1 else subject.unitsTerm2
            val isExpanded = expandedSubjectId == subject.id
            val subjectColor = Color(subject.colorHex)

            val totalLessonsInSub = units.sumOf { it.lessons.size }
            val completedInSub = units.sumOf { u ->
                u.lessons.count { profile.completedLessons.contains(it.id) }
            }

            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = if (isExpanded) subjectColor.copy(alpha = 0.08f) else null,
                onClick = {
                    expandedSubjectId = if (isExpanded) null else subject.id
                }
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(46.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(subjectColor.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.School,
                                contentDescription = subject.name,
                                tint = subjectColor,
                                modifier = Modifier.size(26.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = subject.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            Text(
                                text = "${subject.englishName} • ${units.size} وحدات • $totalLessonsInSub دروس",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Icon(
                            imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = if (isExpanded) "طي" else "توسيع",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    if (totalLessonsInSub > 0) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "مكتمل: $completedInSub من $totalLessonsInSub",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "${if (totalLessonsInSub > 0) (completedInSub * 100 / totalLessonsInSub) else 0}%",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = subjectColor
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        LinearProgressIndicator(
                            progress = { if (totalLessonsInSub > 0) completedInSub.toFloat() / totalLessonsInSub else 0f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = subjectColor,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    }

                    // Expanded Units & Lessons
                    if (isExpanded) {
                        Spacer(modifier = Modifier.height(14.dp))
                        if (units.isEmpty()) {
                            Text(
                                text = "محتوى ${selectedTerm.title} متاح للإضافة والتعديل التلقائي وفق خطة المنهج.",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        } else {
                            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                units.forEach { unitItem ->
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.6f))
                                            .padding(10.dp)
                                    ) {
                                        Text(
                                            text = unitItem.title,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp,
                                            color = subjectColor
                                        )

                                        Spacer(modifier = Modifier.height(8.dp))

                                        unitItem.lessons.forEach { lesson ->
                                            val isLessonCompleted = profile.completedLessons.contains(lesson.id)
                                            val isBookmarked = profile.bookmarkedLessons.contains(lesson.id)

                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .clip(RoundedCornerShape(8.dp))
                                                    .clickable { onSelectLesson(lesson.id) }
                                                    .padding(vertical = 8.dp, horizontal = 6.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Icon(
                                                    imageVector = if (isLessonCompleted) Icons.Default.CheckCircle else Icons.Default.MenuBook,
                                                    contentDescription = null,
                                                    tint = if (isLessonCompleted) Color(0xFF10B981) else MaterialTheme.colorScheme.primary,
                                                    modifier = Modifier.size(18.dp)
                                                )
                                                Spacer(modifier = Modifier.width(8.dp))
                                                Text(
                                                    text = lesson.title,
                                                    fontSize = 13.sp,
                                                    fontWeight = if (isLessonCompleted) FontWeight.Normal else FontWeight.Medium,
                                                    modifier = Modifier.weight(1f)
                                                )
                                                if (isBookmarked) {
                                                    Icon(
                                                        imageVector = Icons.Default.Bookmark,
                                                        contentDescription = "محفوظ للمراجعة",
                                                        tint = Color(0xFFF59E0B),
                                                        modifier = Modifier.size(16.dp)
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
