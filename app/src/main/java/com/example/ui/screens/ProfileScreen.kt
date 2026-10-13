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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppRepository
import com.example.ui.components.GlassCard

@Composable
fun ProfileScreen(repository: AppRepository) {
    val profile by repository.studentProfile.collectAsState()
    val achievements by repository.achievements.collectAsState()
    var showEditProfileDialog by remember { mutableStateOf(false) }

    val nextLevelXp = profile.level * 150
    val currentLevelBase = (profile.level - 1) * 150
    val progressInLevel = (profile.xp - currentLevelBase).toFloat() / (nextLevelXp - currentLevelBase)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("profile_screen"),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Student Info Card
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                borderBrush = Brush.linearGradient(
                    listOf(Color(0xFF6366F1), Color(0xFF38BDF8), Color(0xFFA855F7))
                )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(76.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
                            .clickable { showEditProfileDialog = true },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = profile.avatarEmoji, fontSize = 42.sp)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = profile.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "تعديل الاسم",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .size(18.dp)
                                .clickable { showEditProfileDialog = true }
                        )
                    }

                    Text(
                        text = profile.grade,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // XP and Level Bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "المستوى ${profile.level}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "${profile.xp} / $nextLevelXp XP",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    LinearProgressIndicator(
                        progress = { progressInLevel.coerceIn(0f, 1f) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
            }
        }

        // Student Stats Matrix
        item {
            Text(
                text = "📊 إحصائيات الإنجاز التعليمي",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))

            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                        ProfileStatBox("الدروس المكتملة", "${profile.completedLessons.size}")
                        ProfileStatBox("الامتحانات المحلولة", "${profile.totalExamsCount}")
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                        ProfileStatBox("متوسط الدرجات", "${profile.averageScore}%")
                        ProfileStatBox("أيام الحماس المتتالية", "🔥 ${profile.streakDays} أيام")
                    }
                }
            }
        }

        // Achievements Section
        item {
            Text(
                text = "🏆 الأوسمة والشارات التعليمية",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        items(achievements, key = { it.id }) { ach ->
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = if (ach.isUnlocked) Color(0xFF10B981).copy(alpha = 0.08f) else null
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = ach.iconEmoji,
                        fontSize = 32.sp,
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(
                                if (ach.isUnlocked) Color(0xFFD1FAE5) else MaterialTheme.colorScheme.surfaceVariant
                            )
                            .padding(8.dp)
                    )

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = ach.title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            Text(
                                text = if (ach.isUnlocked) "✓ مكتمل" else "${ach.progress} / ${ach.maxProgress}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (ach.isUnlocked) Color(0xFF059669) else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Text(
                            text = ach.description,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }

    if (showEditProfileDialog) {
        var editName by remember { mutableStateOf(profile.name) }
        var selectedAvatar by remember { mutableStateOf(profile.avatarEmoji) }
        val avatars = listOf("🎓", "🚀", "🔬", "👨‍🎓", "👩‍🎓", "⭐", "🧠", "🦁")

        AlertDialog(
            onDismissRequest = { showEditProfileDialog = false },
            title = { Text("تعديل بيانات الطالب", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = editName,
                        onValueChange = { editName = it },
                        label = { Text("اسم الطالب") },
                        singleLine = true
                    )

                    Text(text = "اختر الأيقونة الشخصية:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        avatars.forEach { av ->
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(if (selectedAvatar == av) MaterialTheme.colorScheme.primary.copy(alpha = 0.25f) else Color.Transparent)
                                    .clickable { selectedAvatar = av },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = av, fontSize = 20.sp)
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (editName.isNotBlank()) {
                            repository.updateStudentName(editName, selectedAvatar)
                            showEditProfileDialog = false
                        }
                    }
                ) {
                    Text("حفظ")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showEditProfileDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant, contentColor = MaterialTheme.colorScheme.onSurfaceVariant)
                ) {
                    Text("إلغاء")
                }
            }
        )
    }
}

@Composable
fun ProfileStatBox(title: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, fontWeight = FontWeight.ExtraBold, fontSize = 17.sp, color = MaterialTheme.colorScheme.primary)
        Text(text = title, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
