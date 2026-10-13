package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.AppRepository
import com.example.data.CurriculumDataSource
import com.example.ui.components.GlassCard

@Composable
fun DashboardScreen(
    repository: AppRepository,
    onNavigateToCurriculum: () -> Unit,
    onNavigateToLab: () -> Unit,
    onNavigateToLibrary: () -> Unit,
    onNavigateToExams: () -> Unit,
    onNavigateToMath: () -> Unit,
    onNavigateToPrint: () -> Unit,
    onNavigateToQuestionBank: () -> Unit,
    onNavigateToRevision: () -> Unit,
    onNavigateToPlanner: () -> Unit,
    onNavigateToFavorites: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onSelectLesson: (String) -> Unit
) {
    val profile by repository.studentProfile.collectAsState()
    val tasks by repository.studyTasks.collectAsState()
    val examResults by repository.examResults.collectAsState()

    val totalCurriculumLessons = CurriculumDataSource.subjects.sumOf { s ->
        s.unitsTerm1.sumOf { it.lessons.size } + s.unitsTerm2.sumOf { it.lessons.size }
    }
    val completedCount = profile.completedLessons.size
    val progressPercent = if (totalCurriculumLessons > 0) {
        ((completedCount.toFloat() / totalCurriculumLessons) * 100).toInt()
    } else 0

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("dashboard_screen"),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Greeting Card with 3D Banner
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = Color.Transparent,
                borderBrush = Brush.linearGradient(
                    listOf(Color(0xFF6366F1), Color(0xFF38BDF8), Color(0xFF8B5CF6))
                )
            ) {
                Column {
                    // Image Banner
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp)
                            .clip(RoundedCornerShape(12.dp))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.hero_study_banner),
                            contentDescription = "غلاف المذاكرة",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        listOf(Color.Transparent, Color(0xCC0F172A))
                                    )
                                )
                        )
                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = profile.avatarEmoji,
                                fontSize = 28.sp,
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(Color(0x801E293B))
                                    .padding(4.dp)
                                    .clickable { onNavigateToProfile() }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "مرحبًا بك في Yassin Study 2027",
                                    color = Color.White,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "الطالب: ${profile.name} | ${profile.grade}",
                                    color = Color(0xFF94A3B8),
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Streak and Level Pills
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color(0xFFFEF3C7))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocalFireDepartment,
                                contentDescription = "حماس المذاكرة",
                                tint = Color(0xFFD97706),
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${profile.streakDays} أيام حماس متتالية",
                                color = Color(0xFF92400E),
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color(0xFFEEF2FF))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                                .clickable { onNavigateToProfile() }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "المستوى",
                                tint = Color(0xFF4F46E5),
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "المستوى ${profile.level} (${profile.xp} XP)",
                                color = Color(0xFF3730A3),
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }

        // Live Progress & Stats Overview
        item {
            Text(
                text = "📊 مؤشرات التحصيل الدراسي",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))

            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "نسبة التقدم العام بالمنهج",
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )
                        Text(
                            text = "$progressPercent%",
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = { progressPercent / 100f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        StatItem(title = "الدروس المكتملة", value = "$completedCount من $totalCurriculumLessons")
                        StatItem(title = "الامتحانات المحلولة", value = "${profile.totalExamsCount}")
                        StatItem(title = "متوسط الدرجات", value = "${profile.averageScore}%")
                    }
                }
            }
        }

        // Quick Primary Navigation Shortcuts (Lab, Library, Math, Print, Question Bank)
        item {
            Text(
                text = "⚡ بوابات المنصة السريعة",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    QuickActionPill(
                        title = "المختبر التفاعلي",
                        subtitle = "تجارب علمية حية",
                        icon = Icons.Default.Science,
                        accentColor = Color(0xFF9333EA),
                        onClick = onNavigateToLab
                    )
                }
                item {
                    QuickActionPill(
                        title = "المكتبة والكتب",
                        subtitle = "رف كتب إلكتروني",
                        icon = Icons.Default.AutoStories,
                        accentColor = Color(0xFF0284C7),
                        onClick = onNavigateToLibrary
                    )
                }
                item {
                    QuickActionPill(
                        title = "الرياضيات التفاعلية",
                        subtitle = "آلة حاسبة وخطوات",
                        icon = Icons.Default.Calculate,
                        accentColor = Color(0xFF6366F1),
                        onClick = onNavigateToMath
                    )
                }
                item {
                    QuickActionPill(
                        title = "مركز الطباعة",
                        subtitle = "طباعة كتب وامتحانات",
                        icon = Icons.Default.Print,
                        accentColor = Color(0xFF10B981),
                        onClick = onNavigateToPrint
                    )
                }
                item {
                    QuickActionPill(
                        title = "بنك الأسئلة",
                        subtitle = "تدريب واختبارات",
                        icon = Icons.Default.Psychology,
                        accentColor = Color(0xFFD97706),
                        onClick = onNavigateToQuestionBank
                    )
                }
                item {
                    QuickActionPill(
                        title = "المراجعة النهائية",
                        subtitle = "كبسولة الامتحان",
                        icon = Icons.Default.Flag,
                        accentColor = Color(0xFFEC4899),
                        onClick = onNavigateToRevision
                    )
                }
            }
        }

        // Last Studied Lesson Recommendation
        item {
            Text(
                text = "📖 مواصلة المذاكرة",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))

            val firstLesson = CurriculumDataSource.subjects.first().unitsTerm1.first().lessons.first()
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onSelectLesson(firstLesson.id) },
                borderBrush = Brush.horizontalGradient(listOf(Color(0xFF9333EA), Color(0xFF6366F1)))
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "مادة العلوم • الترم الأول",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = firstLesson.title,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = firstLesson.simpleSummary,
                            fontSize = 12.sp,
                            maxLines = 2,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Button(
                        onClick = { onSelectLesson(firstLesson.id) },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("متابعة", fontSize = 12.sp)
                    }
                }
            }
        }

        // Today's Study Tasks
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "📅 مهام المذاكرة اليومية",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "عرض الكل",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onNavigateToPlanner() }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                tasks.take(3).forEach { task ->
                    GlassCard(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { repository.toggleTask(task.id) }
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = if (task.isCompleted) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                                contentDescription = if (task.isCompleted) "مكتمل" else "غير مكتمل",
                                tint = if (task.isCompleted) Color(0xFF10B981) else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = task.title,
                                    fontSize = 14.sp,
                                    fontWeight = if (task.isCompleted) FontWeight.Normal else FontWeight.SemiBold,
                                    color = if (task.isCompleted) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "${task.subjectName} • ${task.durationMinutes} دقيقة",
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
}

@Composable
fun StatItem(title: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = title,
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun QuickActionPill(
    title: String,
    subtitle: String,
    icon: ImageVector,
    accentColor: Color,
    onClick: () -> Unit
) {
    GlassCard(
        modifier = Modifier
            .width(150.dp)
            .clickable { onClick() },
        backgroundColor = accentColor.copy(alpha = 0.12f),
        borderBrush = Brush.linearGradient(listOf(accentColor.copy(alpha = 0.5f), Color.Transparent))
    ) {
        Column {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(accentColor.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = accentColor,
                    modifier = Modifier.size(22.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = subtitle,
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
