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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Visibility
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
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CurriculumDataSource
import com.example.data.PrintHelper
import com.example.ui.components.GlassCard

enum class PrintDocumentType(val title: String) {
    LESSON("درس تعليمي كامل"),
    EXAM_STUDENT("ورقة امتحان بدون إجابات"),
    EXAM_ANSWER_KEY("نموذج الإجابة الرسمي مع الشرح"),
    REVISION_SUMMARY("ملخص وكبسولة المادة"),
    BOOK_RANGE("كتاب / نطاق صفحات محددة")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrintCenterScreen() {
    val context = LocalContext.current
    var selectedType by remember { mutableStateOf(PrintDocumentType.LESSON) }

    val subjects = CurriculumDataSource.subjects
    val books = CurriculumDataSource.digitalBooks
    val questions = CurriculumDataSource.questionBank
    val summaries = CurriculumDataSource.revisionSummaries

    var selectedSubject by remember { mutableStateOf(subjects.first()) }
    var selectedBook by remember { mutableStateOf(books.first()) }
    var startPageStr by remember { mutableStateOf("1") }
    var endPageStr by remember { mutableStateOf("4") }

    var subjectDropdownExpanded by remember { mutableStateOf(false) }

    val firstLesson = selectedSubject.unitsTerm1.firstOrNull()?.lessons?.firstOrNull()
        ?: selectedSubject.unitsTerm2.firstOrNull()?.lessons?.first()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("print_center_screen"),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "🖨️ مركز الطباعة المعتمد",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "اطبع الدروس، نماذج الامتحانات، المذكرات، ونماذج الإجابة مباشرة عبر طابعة نظام أندرويد أو تصديرها كملفات PDF عالية الجودة.",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Document Type Radio Selection
        item {
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column {
                    Text(
                        text = "اختر نوع المستند المراد طباعته:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    PrintDocumentType.values().forEach { type ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (selectedType == type) MaterialTheme.colorScheme.primary.copy(alpha = 0.12f) else Color.Transparent)
                                .clickable { selectedType = type }
                                .padding(vertical = 4.dp, horizontal = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedType == type,
                                onClick = { selectedType = type }
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = type.title, fontSize = 13.sp, fontWeight = if (selectedType == type) FontWeight.Bold else FontWeight.Normal)
                        }
                    }
                }
            }
        }

        // Context Parameters for chosen type
        item {
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(text = "إعدادات المستند:", fontWeight = FontWeight.Bold, fontSize = 14.sp)

                    // Subject Selector
                    ExposedDropdownMenuBox(
                        expanded = subjectDropdownExpanded,
                        onExpandedChange = { subjectDropdownExpanded = it }
                    ) {
                        OutlinedTextField(
                            value = selectedSubject.name,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("المادة") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = subjectDropdownExpanded) },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                        )
                        ExposedDropdownMenu(
                            expanded = subjectDropdownExpanded,
                            onDismissRequest = { subjectDropdownExpanded = false }
                        ) {
                            subjects.forEach { s ->
                                DropdownMenuItem(
                                    text = { Text(s.name) },
                                    onClick = {
                                        selectedSubject = s
                                        subjectDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    if (selectedType == PrintDocumentType.BOOK_RANGE) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(
                                value = startPageStr,
                                onValueChange = { startPageStr = it },
                                label = { Text("من صفحة") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.weight(1f)
                            )
                            OutlinedTextField(
                                value = endPageStr,
                                onValueChange = { endPageStr = it },
                                label = { Text("إلى صفحة") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }

        // Live Print Preview Box
        item {
            Text(
                text = "👁️ معاينة قبل الطباعة:",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(6.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF8FAFC))
                    .border(1.dp, Color(0xFFCBD5E1), RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "منصة Yassin Study 2027",
                            color = Color(0xFF4F46E5),
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                        Text(
                            text = "مقاس الورق: A4 قياسي",
                            color = Color(0xFF64748B),
                            fontSize = 11.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = when (selectedType) {
                            PrintDocumentType.LESSON -> "مستند درس: ${firstLesson?.title ?: "الدرس الأول"}"
                            PrintDocumentType.EXAM_STUDENT -> "ورقة امتحان ${selectedSubject.name} (بدون حلول)"
                            PrintDocumentType.EXAM_ANSWER_KEY -> "نموذج إجابة امتحان ${selectedSubject.name}"
                            PrintDocumentType.REVISION_SUMMARY -> "كبسولة ومراجعة ليلة الامتحان: ${selectedSubject.name}"
                            PrintDocumentType.BOOK_RANGE -> "كتاب ${selectedBook.title} (صفحات $startPageStr - $endPageStr)"
                        },
                        color = Color(0xFF0F172A),
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "جاهز للإرسال إلى نظام الطباعة المدمج في جهازك. يتضمن الترويسة الرسمية، التنسيق المتجاوب RTL، وحقوق المطور ياسين وائل نجيب.",
                        color = Color(0xFF475569),
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )
                }
            }
        }

        // Native Print Action Button
        item {
            Button(
                onClick = {
                    val htmlContent = when (selectedType) {
                        PrintDocumentType.LESSON -> {
                            if (firstLesson != null) {
                                PrintHelper.generateLessonHtml(firstLesson, selectedSubject.name)
                            } else "<h1>لا يوجد درس متاح</h1>"
                        }
                        PrintDocumentType.EXAM_STUDENT -> {
                            val subQuestions = questions.filter { it.subjectId == selectedSubject.id }.ifEmpty { questions }
                            PrintHelper.generateExamHtml("امتحان ${selectedSubject.name}", selectedSubject.name, subQuestions, false)
                        }
                        PrintDocumentType.EXAM_ANSWER_KEY -> {
                            val subQuestions = questions.filter { it.subjectId == selectedSubject.id }.ifEmpty { questions }
                            PrintHelper.generateExamHtml("نموذج إجابة ${selectedSubject.name}", selectedSubject.name, subQuestions, true)
                        }
                        PrintDocumentType.REVISION_SUMMARY -> {
                            val summary = summaries.find { it.subjectId == selectedSubject.id }
                            """
                            <!DOCTYPE html>
                            <html dir="rtl" lang="ar"><head><meta charset="UTF-8"><title>ملخص ${selectedSubject.name}</title></head>
                            <body style="font-family: Arial; padding: 24px;">
                                <h1 style="color: #4f46e5;">كبسولة ليلة الامتحان: ${selectedSubject.name}</h1>
                                <p style="font-size: 16px;">${summary?.capsuleSummary ?: "مراجعة شاملة"}</p>
                                <h2>القواعد الذهبية:</h2>
                                <ul>${summary?.goldenRules?.joinToString("") { "<li>$it</li>" } ?: ""}</ul>
                                <h2>أخطاء شائعة تجنبها:</h2>
                                <ul>${summary?.commonMistakes?.joinToString("") { "<li>$it</li>" } ?: ""}</ul>
                            </body></html>
                            """.trimIndent()
                        }
                        PrintDocumentType.BOOK_RANGE -> {
                            val s = startPageStr.toIntOrNull() ?: 1
                            val e = endPageStr.toIntOrNull() ?: selectedBook.pageCount
                            PrintHelper.generateBookPagesHtml(selectedBook, s, e)
                        }
                    }

                    PrintHelper.printHtml(context, htmlContent, "طباعة_${selectedType.name}")
                    Toast.makeText(context, "جاري فتح نافذة الطباعة / حفظ PDF في الجهاز...", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(imageVector = Icons.Default.Print, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("بدء الطباعة أو حفظ PDF الآن 🖨️", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
        }
    }
}
