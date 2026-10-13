package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.example.data.AppRepository
import com.example.data.CurriculumDataSource
import com.example.ui.components.AppBottomNavBar
import com.example.ui.components.AppHeader
import com.example.ui.components.MainNavTab
import com.example.ui.screens.CurriculumScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.ExamCenterScreen
import com.example.ui.screens.FavoritesNotesScreen
import com.example.ui.screens.FinalRevisionScreen
import com.example.ui.screens.GlobalSearchScreen
import com.example.ui.screens.LessonDetailScreen
import com.example.ui.screens.LibraryScreen
import com.example.ui.screens.MathToolsScreen
import com.example.ui.screens.PrintCenterScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.QuestionBankScreen
import com.example.ui.screens.ScienceLabScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.screens.StudyPlannerScreen
import com.example.ui.theme.YassinStudyTheme

enum class AppScreen {
    DASHBOARD,
    CURRICULUM,
    EXAMS,
    LAB,
    LIBRARY,
    MATH_TOOLS,
    PRINT_CENTER,
    QUESTION_BANK,
    FINAL_REVISION,
    STUDY_PLANNER,
    FAVORITES_NOTES,
    PROFILE,
    SETTINGS,
    GLOBAL_SEARCH,
    LESSON_DETAIL
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val repository = remember { AppRepository(context) }
            val isDarkMode by repository.isDarkMode.collectAsState()

            YassinStudyTheme(darkTheme = isDarkMode) {
                // RTL Layout for Arabic 3rd Preparatory Platform
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    var showSplash by remember { mutableStateOf(true) }

                    if (showSplash) {
                        SplashScreen(onSplashFinished = { showSplash = false })
                    } else {
                        MainStudyApp(repository = repository)
                    }
                }
            }
        }
    }
}

@Composable
fun MainStudyApp(repository: AppRepository) {
    var currentTab by remember { mutableStateOf(MainNavTab.HOME) }
    var currentScreen by remember { mutableStateOf(AppScreen.DASHBOARD) }
    var selectedLessonId by remember { mutableStateOf<String?>(null) }
    val screenStack = remember { mutableStateListOf(AppScreen.DASHBOARD) }

    val profile by repository.studentProfile.collectAsState()

    fun navigateTo(screen: AppScreen) {
        if (currentScreen != screen) {
            screenStack.add(screen)
            currentScreen = screen
        }
    }

    fun navigateBack() {
        if (screenStack.size > 1) {
            screenStack.removeAt(screenStack.lastIndex)
            currentScreen = screenStack.last()
        } else if (currentScreen != AppScreen.DASHBOARD) {
            currentScreen = AppScreen.DASHBOARD
            currentTab = MainNavTab.HOME
        }
    }

    // Predictive / System Back Handling
    BackHandler(enabled = screenStack.size > 1 || currentScreen != AppScreen.DASHBOARD) {
        navigateBack()
    }

    val (headerTitle, headerSubtitle) = when (currentScreen) {
        AppScreen.DASHBOARD -> Pair("Yassin Study 2027", "الصف الثالث الإعدادي")
        AppScreen.CURRICULUM -> Pair("المناهج والمقررات", "7 مواد دراسية مقررة")
        AppScreen.EXAMS -> Pair("مركز الامتحانات", "نماذج وتقييم فوري")
        AppScreen.LAB -> Pair("المختبر التفاعلي", "محاكاة التجارب العلمية")
        AppScreen.LIBRARY -> Pair("المكتبة والكتب", "رف الكتب والمذكرات")
        AppScreen.MATH_TOOLS -> Pair("الرياضيات التفاعلية", "آلة حاسبة وخطوات الحل")
        AppScreen.PRINT_CENTER -> Pair("مركز الطباعة", "طباعة الكتب والامتحانات")
        AppScreen.QUESTION_BANK -> Pair("بنك الأسئلة", "تدريبات وإجابات نموذجية")
        AppScreen.FINAL_REVISION -> Pair("المراجعة النهائية", "كبسولة ليلة الامتحان")
        AppScreen.STUDY_PLANNER -> Pair("خطة المذاكرة", "المهام اليومية ومؤقت التركيز")
        AppScreen.FAVORITES_NOTES -> Pair("المحفوظات والملاحظات", "سجلك الدراسي الخاص")
        AppScreen.PROFILE -> Pair("الملف الشخصي", "مستوى الطالب والإنجازات")
        AppScreen.SETTINGS -> Pair("الإعدادات", "المظهر وحول التطبيق")
        AppScreen.GLOBAL_SEARCH -> Pair("البحث الشامل", "بحث في كل أقسام المنصة")
        AppScreen.LESSON_DETAIL -> {
            val allLessons = CurriculumDataSource.subjects.flatMap { s ->
                s.unitsTerm1.flatMap { it.lessons } + s.unitsTerm2.flatMap { it.lessons }
            }
            val l = allLessons.find { it.id == selectedLessonId }
            Pair(l?.title ?: "تفاصيل الدرس", "شرح، أسئلة، وملاحظات")
        }
    }

    val showBottomBar = currentScreen in listOf(
        AppScreen.DASHBOARD,
        AppScreen.CURRICULUM,
        AppScreen.EXAMS,
        AppScreen.LAB,
        AppScreen.LIBRARY
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            AppHeader(
                title = headerTitle,
                subtitle = headerSubtitle,
                onBackClick = if (currentScreen != AppScreen.DASHBOARD) { { navigateBack() } } else null,
                onSearchClick = { navigateTo(AppScreen.GLOBAL_SEARCH) },
                onBookmarksClick = { navigateTo(AppScreen.FAVORITES_NOTES) },
                onSettingsClick = { navigateTo(AppScreen.SETTINGS) },
                bookmarksCount = profile.bookmarkedLessons.size
            )
        },
        bottomBar = {
            if (showBottomBar) {
                AppBottomNavBar(
                    currentTab = currentTab,
                    onTabSelected = { tab ->
                        currentTab = tab
                        when (tab) {
                            MainNavTab.HOME -> {
                                currentScreen = AppScreen.DASHBOARD
                                screenStack.clear()
                                screenStack.add(AppScreen.DASHBOARD)
                            }
                            MainNavTab.CURRICULUM -> navigateTo(AppScreen.CURRICULUM)
                            MainNavTab.EXAMS -> navigateTo(AppScreen.EXAMS)
                            MainNavTab.LAB -> navigateTo(AppScreen.LAB)
                            MainNavTab.LIBRARY -> navigateTo(AppScreen.LIBRARY)
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AnimatedContent(
                targetState = currentScreen,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "screen_transition"
            ) { targetScreen ->
                when (targetScreen) {
                    AppScreen.DASHBOARD -> DashboardScreen(
                        repository = repository,
                        onNavigateToCurriculum = {
                            currentTab = MainNavTab.CURRICULUM
                            navigateTo(AppScreen.CURRICULUM)
                        },
                        onNavigateToLab = {
                            currentTab = MainNavTab.LAB
                            navigateTo(AppScreen.LAB)
                        },
                        onNavigateToLibrary = {
                            currentTab = MainNavTab.LIBRARY
                            navigateTo(AppScreen.LIBRARY)
                        },
                        onNavigateToExams = {
                            currentTab = MainNavTab.EXAMS
                            navigateTo(AppScreen.EXAMS)
                        },
                        onNavigateToMath = { navigateTo(AppScreen.MATH_TOOLS) },
                        onNavigateToPrint = { navigateTo(AppScreen.PRINT_CENTER) },
                        onNavigateToQuestionBank = { navigateTo(AppScreen.QUESTION_BANK) },
                        onNavigateToRevision = { navigateTo(AppScreen.FINAL_REVISION) },
                        onNavigateToPlanner = { navigateTo(AppScreen.STUDY_PLANNER) },
                        onNavigateToFavorites = { navigateTo(AppScreen.FAVORITES_NOTES) },
                        onNavigateToProfile = { navigateTo(AppScreen.PROFILE) },
                        onSelectLesson = { lessonId ->
                            selectedLessonId = lessonId
                            navigateTo(AppScreen.LESSON_DETAIL)
                        }
                    )

                    AppScreen.CURRICULUM -> CurriculumScreen(
                        repository = repository,
                        onSelectLesson = { lessonId ->
                            selectedLessonId = lessonId
                            navigateTo(AppScreen.LESSON_DETAIL)
                        }
                    )

                    AppScreen.EXAMS -> ExamCenterScreen(repository = repository)

                    AppScreen.LAB -> ScienceLabScreen()

                    AppScreen.LIBRARY -> LibraryScreen()

                    AppScreen.MATH_TOOLS -> MathToolsScreen()

                    AppScreen.PRINT_CENTER -> PrintCenterScreen()

                    AppScreen.QUESTION_BANK -> QuestionBankScreen()

                    AppScreen.FINAL_REVISION -> FinalRevisionScreen()

                    AppScreen.STUDY_PLANNER -> StudyPlannerScreen(repository = repository)

                    AppScreen.FAVORITES_NOTES -> FavoritesNotesScreen(
                        repository = repository,
                        onSelectLesson = { lessonId ->
                            selectedLessonId = lessonId
                            navigateTo(AppScreen.LESSON_DETAIL)
                        }
                    )

                    AppScreen.PROFILE -> ProfileScreen(repository = repository)

                    AppScreen.SETTINGS -> SettingsScreen(repository = repository)

                    AppScreen.GLOBAL_SEARCH -> GlobalSearchScreen(
                        onSelectLesson = { lessonId ->
                            selectedLessonId = lessonId
                            navigateTo(AppScreen.LESSON_DETAIL)
                        },
                        onNavigateToLab = {
                            currentTab = MainNavTab.LAB
                            navigateTo(AppScreen.LAB)
                        },
                        onNavigateToLibrary = {
                            currentTab = MainNavTab.LIBRARY
                            navigateTo(AppScreen.LIBRARY)
                        }
                    )

                    AppScreen.LESSON_DETAIL -> {
                        val allLessons = CurriculumDataSource.subjects.flatMap { s ->
                            s.unitsTerm1.flatMap { it.lessons } + s.unitsTerm2.flatMap { it.lessons }
                        }
                        val lesson = allLessons.find { it.id == selectedLessonId }
                            ?: allLessons.first()
                        val sub = CurriculumDataSource.subjects.find { it.id == lesson.subjectId }

                        LessonDetailScreen(
                            lesson = lesson,
                            subjectName = sub?.name ?: "المادة",
                            repository = repository,
                            onBack = { navigateBack() }
                        )
                    }
                }
            }
        }
    }
}
