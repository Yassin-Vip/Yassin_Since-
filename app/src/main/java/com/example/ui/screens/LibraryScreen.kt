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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material.icons.filled.ZoomOut
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
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
import com.example.data.BookPage
import com.example.data.CurriculumDataSource
import com.example.data.PrintHelper
import com.example.data.StudyBook
import com.example.data.Term
import com.example.ui.components.GlassCard

@Composable
fun LibraryScreen() {
    val context = LocalContext.current
    val initialBooks = remember { mutableStateListOf(*CurriculumDataSource.digitalBooks.toTypedArray()) }
    var selectedBook by remember { mutableStateOf<StudyBook?>(null) }
    var showAddBookDialog by remember { mutableStateOf(false) }

    // Reader Mode state
    var currentPageIndex by remember { mutableIntStateOf(0) }
    var fontZoomScale by remember { mutableFloatStateOf(16f) }
    var isNightReadingMode by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val bookmarkedPages = remember { mutableStateListOf<Int>() }

    if (selectedBook == null) {
        // Bookshelf View
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("library_screen"),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 96.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text(
                        text = "📚 رف الكتب والمذكرات الرقمية",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "كتب ومناهج الصف الثالث الإعدادي 2026/2027 مع قارئ إلكتروني وبحث وطباعة.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                items(initialBooks, key = { it.id }) { book ->
                    GlassCard(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            selectedBook = book
                            currentPageIndex = 0
                            bookmarkedPages.clear()
                        }
                    ) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            // Book Spine / Cover Mockup
                            Box(
                                modifier = Modifier
                                    .width(85.dp)
                                    .height(115.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(
                                        Brush.verticalGradient(
                                            listOf(Color(0xFF4F46E5), Color(0xFF1E1B4B))
                                        )
                                    )
                                    .padding(8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(
                                        imageVector = Icons.Default.AutoStories,
                                        contentDescription = null,
                                        tint = Color(0xFF38BDF8),
                                        modifier = Modifier.size(28.dp)
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = book.subjectName,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp
                                    )
                                    Text(
                                        text = book.term.title,
                                        color = Color(0xFF94A3B8),
                                        fontSize = 9.sp
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = book.title,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    lineHeight = 20.sp
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = book.author,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "${book.pageCount} صفحة • ${book.chapters.size} فصول مقررة",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Button(
                                        onClick = {
                                            selectedBook = book
                                            currentPageIndex = 0
                                            bookmarkedPages.clear()
                                        },
                                        shape = RoundedCornerShape(8.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                                    ) {
                                        Text("فتح وقراءة الكتاب", fontSize = 12.sp)
                                    }

                                    IconButton(
                                        onClick = {
                                            val html = PrintHelper.generateBookPagesHtml(book)
                                            PrintHelper.printHtml(context, html, "طباعة_${book.title}")
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Print,
                                            contentDescription = "طباعة",
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // FAB for adding custom book / material
            FloatingActionButton(
                onClick = { showAddBookDialog = true },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 80.dp, end = 20.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "إضافة كتاب / مذكرة")
            }
        }
    } else {
        // Book Reader View
        val book = selectedBook!!
        val pages = book.pages
        val currentPage = pages.getOrElse(currentPageIndex) {
            BookPage(
                pageNumber = currentPageIndex + 1,
                chapterTitle = "فصل دراسي",
                content = "المحتوى الرقمي قيد القراءة والمذاكرة."
            )
        }
        val isBookmarked = bookmarkedPages.contains(currentPage.pageNumber)

        val readerBg = if (isNightReadingMode) Color(0xFF0F172A) else Color(0xFFFDFBF7)
        val readerTextColor = if (isNightReadingMode) Color(0xFFF1F5F9) else Color(0xFF1E293B)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(readerBg)
                .testTag("book_reader_view")
        ) {
            // Reader Navigation Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { selectedBook = null }) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "رجوع للمكتبة")
                }

                Text(
                    text = "${currentPageIndex + 1} / ${pages.size}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )

                Row {
                    // Zoom In / Out
                    IconButton(onClick = { if (fontZoomScale < 26f) fontZoomScale += 2f }) {
                        Icon(imageVector = Icons.Default.ZoomIn, contentDescription = "تكبير الخط")
                    }
                    IconButton(onClick = { if (fontZoomScale > 12f) fontZoomScale -= 2f }) {
                        Icon(imageVector = Icons.Default.ZoomOut, contentDescription = "تصغير الخط")
                    }

                    // Night mode toggle
                    IconButton(onClick = { isNightReadingMode = !isNightReadingMode }) {
                        Icon(
                            imageVector = if (isNightReadingMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = "الوضع الليلي"
                        )
                    }

                    // Bookmark toggle
                    IconButton(
                        onClick = {
                            if (isBookmarked) bookmarkedPages.remove(currentPage.pageNumber)
                            else bookmarkedPages.add(currentPage.pageNumber)
                        }
                    ) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "علامة مرجعية",
                            tint = if (isBookmarked) Color(0xFFF59E0B) else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    // Print page / book
                    IconButton(
                        onClick = {
                            val html = PrintHelper.generateBookPagesHtml(book, currentPage.pageNumber, currentPage.pageNumber)
                            PrintHelper.printHtml(context, html, "طباعة_${book.title}_صفحة_${currentPage.pageNumber}")
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Print, contentDescription = "طباعة الصفحة")
                    }
                }
            }

            // Search in book bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("بحث داخل الكتاب...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                singleLine = true
            )

            // Page Content Body
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            ) {
                item {
                    Text(
                        text = currentPage.chapterTitle,
                        fontWeight = FontWeight.Bold,
                        fontSize = (fontZoomScale * 1.1f).sp,
                        color = Color(0xFF4F46E5)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = currentPage.content,
                        fontSize = fontZoomScale.sp,
                        color = readerTextColor,
                        lineHeight = (fontZoomScale * 1.7f).sp
                    )

                    if (currentPage.keyTakeaway.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(18.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFFEFF6FF))
                                .padding(12.dp)
                        ) {
                            Text(
                                text = "💡 الفكرة الأساسية: ${currentPage.keyTakeaway}",
                                color = Color(0xFF1E3A8A),
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }

            // Footer Pager (Prev / Next Buttons & Jump to Page)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { if (currentPageIndex > 0) currentPageIndex-- },
                    enabled = currentPageIndex > 0
                ) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("السابقة")
                }

                Text(
                    text = "صفحة ${currentPage.pageNumber}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )

                Button(
                    onClick = { if (currentPageIndex < pages.size - 1) currentPageIndex++ },
                    enabled = currentPageIndex < pages.size - 1
                ) {
                    Text("التالية")
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
                }
            }
        }
    }

    // Add Book Dialog
    if (showAddBookDialog) {
        var newTitle by remember { mutableStateOf("") }
        var newSubject by remember { mutableStateOf("العلوم") }
        var newPagesCount by remember { mutableStateOf("80") }

        AlertDialog(
            onDismissRequest = { showAddBookDialog = false },
            title = { Text("إضافة كتاب أو مذكرة خاصة", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = newTitle,
                        onValueChange = { newTitle = it },
                        label = { Text("اسم الكتاب أو المذكرة") }
                    )
                    OutlinedTextField(
                        value = newSubject,
                        onValueChange = { newSubject = it },
                        label = { Text("المادة") }
                    )
                    OutlinedTextField(
                        value = newPagesCount,
                        onValueChange = { newPagesCount = it },
                        label = { Text("عدد الصفحات التقريبي") }
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newTitle.isNotEmpty()) {
                            initialBooks.add(
                                StudyBook(
                                    id = "custom_${System.currentTimeMillis()}",
                                    title = newTitle,
                                    subjectId = "custom",
                                    subjectName = newSubject,
                                    term = Term.TERM_1,
                                    pageCount = newPagesCount.toIntOrNull() ?: 50,
                                    author = "مذكرة مضافة بواسطة الطالب",
                                    chapters = listOf("المحتوى المضاف"),
                                    pages = listOf(
                                        BookPage(1, "المحتوى المضاف", "تمت إضافة المذكرة بنجاح في المكتبة الدراسية.")
                                    )
                                )
                            )
                            showAddBookDialog = false
                            Toast.makeText(context, "تمت إضافة الكتاب للمكتبة!", Toast.LENGTH_SHORT).show()
                        }
                    }
                ) {
                    Text("إضافة")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showAddBookDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant, contentColor = MaterialTheme.colorScheme.onSurfaceVariant)
                ) {
                    Text("إلغاء")
                }
            }
        )
    }
}
