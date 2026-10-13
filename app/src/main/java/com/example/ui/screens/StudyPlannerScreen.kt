package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.ui.components.GlassCard
import kotlinx.coroutines.delay

@Composable
fun StudyPlannerScreen(repository: AppRepository) {
    val context = LocalContext.current
    val tasks by repository.studyTasks.collectAsState()
    var showAddTaskDialog by remember { mutableStateOf(false) }

    // Pomodoro Timer State (25 minutes = 1500 seconds)
    var timerSeconds by remember { mutableIntStateOf(25 * 60) }
    var isTimerRunning by remember { mutableStateOf(false) }

    LaunchedEffect(isTimerRunning) {
        while (isTimerRunning && timerSeconds > 0) {
            delay(1000)
            timerSeconds--
        }
        if (timerSeconds == 0 && isTimerRunning) {
            isTimerRunning = false
            repository.addXp(50)
            Toast.makeText(context, "أحسنت! أتممت جلسة تركيز كاملة (+50 XP) 👏", Toast.LENGTH_LONG).show()
        }
    }

    val minutes = timerSeconds / 60
    val seconds = timerSeconds % 60
    val timeFormatted = "%02d:%02d".format(minutes, seconds)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("study_planner_screen"),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "📅 خطة المذاكرة ومؤقت التركيز",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "نظم وقتك اليومي، حدد المهام، واستخدم مؤقت بومودورو للتركيز المتقطع أثناء المذاكرة.",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Pomodoro Focus Timer Card
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Timer, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "مؤقت التركيز الدراسي (Pomodoro)", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Box(
                        modifier = Modifier
                            .size(110.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = timeFormatted,
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 26.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Button(
                            onClick = { isTimerRunning = !isTimerRunning },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isTimerRunning) Color(0xFFEF4444) else Color(0xFF10B981)
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(imageVector = if (isTimerRunning) Icons.Default.Pause else Icons.Default.PlayArrow, contentDescription = null)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(if (isTimerRunning) "إيقاف مؤقت" else "بدء التركيز")
                        }

                        Button(
                            onClick = {
                                isTimerRunning = false
                                timerSeconds = 25 * 60
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant, contentColor = MaterialTheme.colorScheme.onSurfaceVariant),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("إعادة ضبط")
                        }
                    }
                }
            }
        }

        // Tasks Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "مهام وجدول المذاكرة (${tasks.count { it.isCompleted }} مكتملة)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
                Button(
                    onClick = { showAddTaskDialog = true },
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("إضافة مهمة", fontSize = 12.sp)
                }
            }
        }

        // Tasks List
        items(tasks, key = { it.id }) { task ->
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
                        contentDescription = null,
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
                            text = "${task.subjectName} • ${task.durationMinutes} دقيقة • موعدها: ${task.dateString}",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    IconButton(onClick = { repository.deleteTask(task.id) }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "حذف المهمة",
                            tint = MaterialTheme.colorScheme.error.copy(alpha = 0.7f),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }

    if (showAddTaskDialog) {
        var taskTitle by remember { mutableStateOf("") }
        var taskSubject by remember { mutableStateOf("العلوم") }
        var taskDuration by remember { mutableStateOf("30") }

        AlertDialog(
            onDismissRequest = { showAddTaskDialog = false },
            title = { Text("إضافة مهمة مذاكرة جديدة", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = taskTitle,
                        onValueChange = { taskTitle = it },
                        label = { Text("عنوان المهمة (مثال: حل مسائل السرعة)") }
                    )
                    OutlinedTextField(
                        value = taskSubject,
                        onValueChange = { taskSubject = it },
                        label = { Text("المادة") }
                    )
                    OutlinedTextField(
                        value = taskDuration,
                        onValueChange = { taskDuration = it },
                        label = { Text("المدة بالدقائق") }
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (taskTitle.isNotBlank()) {
                            val dur = taskDuration.toIntOrNull() ?: 30
                            repository.addTask(taskTitle, taskSubject, dur)
                            showAddTaskDialog = false
                        }
                    }
                ) {
                    Text("حفظ المهمة")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showAddTaskDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant, contentColor = MaterialTheme.colorScheme.onSurfaceVariant)
                ) {
                    Text("إلغاء")
                }
            }
        )
    }
}
