package com.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CurriculumDataSource
import com.example.ui.components.GlassCard

enum class SearchResultCategory(val title: String) {
    LESSON("درس تعليمي"),
    QUESTION("سؤال في بنك الأسئلة"),
    EXPERIMENT("تجربة في المختبر"),
    BOOK("كتاب دراسي")
}

data class UnifiedSearchResult(
    val id: String,
    val title: String,
    val snippet: String,
    val category: SearchResultCategory,
    val targetId: String
)

@Composable
fun GlobalSearchScreen(
    onSelectLesson: (String) -> Unit,
    onNavigateToLab: () -> Unit,
    onNavigateToLibrary: () -> Unit
) {
    var query by remember { mutableStateOf("") }

    val allLessons = CurriculumDataSource.subjects.flatMap { s ->
        s.unitsTerm1.flatMap { it.lessons } + s.unitsTerm2.flatMap { it.lessons }
    }
    val allQuestions = CurriculumDataSource.questionBank
    val allExperiments = CurriculumDataSource.scienceExperiments
    val allBooks = CurriculumDataSource.digitalBooks

    val results = if (query.trim().length >= 2) {
        val q = query.trim()
        val list = mutableListOf<UnifiedSearchResult>()

        allLessons.filter { it.title.contains(q) || it.simpleSummary.contains(q) || it.detailedExplanation.contains(q) }
            .take(5).forEach {
                list.add(UnifiedSearchResult("les_${it.id}", it.title, it.simpleSummary, SearchResultCategory.LESSON, it.id))
            }

        allQuestions.filter { it.question.contains(q) || it.answer.contains(q) }
            .take(5).forEach {
                list.add(UnifiedSearchResult("q_${it.id}", it.question, "الإجابة: ${it.answer}", SearchResultCategory.QUESTION, it.id))
            }

        allExperiments.filter { it.title.contains(q) || it.target.contains(q) }
            .take(3).forEach {
                list.add(UnifiedSearchResult("exp_${it.id}", it.title, it.target, SearchResultCategory.EXPERIMENT, it.id))
            }

        allBooks.filter { it.title.contains(q) || it.author.contains(q) }
            .take(3).forEach {
                list.add(UnifiedSearchResult("bk_${it.id}", it.title, it.author, SearchResultCategory.BOOK, it.id))
            }

        list
    } else emptyList()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("global_search_screen"),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = "🔎 البحث الشامل في المنصة",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "ابحث في محتوى الدروس، القوانين، بنك الأسئلة، الكتب، وتجارب المختبر بكلمة واحدة.",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("اكتب مصطلح، قانون، درس، أو سؤال...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }

        if (query.trim().length >= 2 && results.isEmpty()) {
            item {
                Text(
                    text = "لم يتم العثور على نتائج تطابق '$query'. جرب كتابة كلمة أخرى مثل 'سرعة'، 'قانون'، 'منادى'.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 13.sp
                )
            }
        }

        items(results, key = { it.id }) { item ->
            val icon = when (item.category) {
                SearchResultCategory.LESSON -> Icons.Default.MenuBook
                SearchResultCategory.QUESTION -> Icons.Default.Quiz
                SearchResultCategory.EXPERIMENT -> Icons.Default.Science
                SearchResultCategory.BOOK -> Icons.Default.AutoStories
            }

            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    when (item.category) {
                        SearchResultCategory.LESSON -> onSelectLesson(item.targetId)
                        SearchResultCategory.EXPERIMENT -> onNavigateToLab()
                        SearchResultCategory.BOOK -> onNavigateToLibrary()
                        SearchResultCategory.QUESTION -> {}
                    }
                }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = item.title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            Text(
                                text = item.category.title,
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.secondary,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = item.snippet,
                            fontSize = 12.sp,
                            maxLines = 2,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}
