package com.example.data

enum class Term(val title: String) {
    TERM_1("الترم الأول"),
    TERM_2("الترم الثاني")
}

enum class QuestionType(val title: String) {
    MCQ("اختيار من متعدد"),
    TRUE_FALSE("صح أو خطأ"),
    COMPLETE("أكمل العبارات"),
    ESSAY("سؤال مقالي / مسألة")
}

enum class Difficulty(val title: String) {
    EASY("سهل"),
    MEDIUM("متوسط"),
    HARD("مستويات عليا / متفوقين")
}

data class DefinitionItem(
    val term: String,
    val meaning: String,
    val note: String = ""
)

data class FormulaItem(
    val name: String,
    val formula: String,
    val explanation: String,
    val unit: String = ""
)

data class ExampleItem(
    val problem: String,
    val steps: List<String>,
    val finalAnswer: String
)

data class ExerciseQuestion(
    val question: String,
    val answer: String,
    val tip: String = ""
)

data class QuizQuestion(
    val id: String,
    val questionText: String,
    val type: QuestionType,
    val options: List<String> = emptyList(),
    val correctOptionIndex: Int = -1,
    val correctAnswerText: String = "",
    val explanation: String = ""
)

data class Lesson(
    val id: String,
    val unitId: String,
    val subjectId: String,
    val term: Term,
    val title: String,
    val order: Int,
    val simpleSummary: String,
    val detailedExplanation: String,
    val keyPoints: List<String>,
    val definitions: List<DefinitionItem> = emptyList(),
    val formulas: List<FormulaItem> = emptyList(),
    val workedExamples: List<ExampleItem> = emptyList(),
    val practiceQuestions: List<ExerciseQuestion> = emptyList(),
    val quizQuestions: List<QuizQuestion> = emptyList(),
    val homework: String = ""
)

data class UnitItem(
    val id: String,
    val subjectId: String,
    val term: Term,
    val title: String,
    val order: Int,
    val lessons: List<Lesson>
)

data class Subject(
    val id: String,
    val name: String,
    val englishName: String,
    val iconName: String,
    val colorHex: Long,
    val description: String,
    val unitsTerm1: List<UnitItem>,
    val unitsTerm2: List<UnitItem>
)

data class BankQuestion(
    val id: String,
    val subjectId: String,
    val subjectName: String,
    val term: Term,
    val unitTitle: String,
    val lessonTitle: String,
    val type: QuestionType,
    val difficulty: Difficulty,
    val question: String,
    val options: List<String> = emptyList(),
    val correctIndex: Int = -1,
    val answer: String,
    val explanation: String
)

enum class ExperimentType {
    SPEED_MOTION,
    OPTICS_MIRRORS,
    OHMS_LAW,
    MENDEL_GENETICS
}

data class ScienceExperiment(
    val id: String,
    val title: String,
    val target: String,
    val tools: List<String>,
    val steps: List<String>,
    val observation: String,
    val scientificExplanation: String,
    val questions: List<QuizQuestion>,
    val type: ExperimentType
)

data class StudyBook(
    val id: String,
    val title: String,
    val subjectId: String,
    val subjectName: String,
    val term: Term,
    val pageCount: Int,
    val author: String,
    val chapters: List<String>,
    val pages: List<BookPage>
)

data class BookPage(
    val pageNumber: Int,
    val chapterTitle: String,
    val content: String,
    val keyTakeaway: String = ""
)

data class StudyTask(
    val id: String,
    val title: String,
    val subjectName: String,
    val durationMinutes: Int,
    val isCompleted: Boolean = false,
    val dateString: String = ""
)

data class ExamResult(
    val id: String,
    val examTitle: String,
    val subjectName: String,
    val timestamp: Long,
    val totalQuestions: Int,
    val correctAnswers: Int,
    val scorePercentage: Int,
    val wrongQuestions: List<String> = emptyList(),
    val reviewRecommendations: List<String> = emptyList()
)

data class StudentProfile(
    val name: String = "ياسين وائل",
    val grade: String = "الصف الثالث الإعدادي (2026/2027)",
    val avatarEmoji: String = "🎓",
    val xp: Int = 380,
    val level: Int = 3,
    val streakDays: Int = 5,
    val completedLessons: Set<String> = emptySet(),
    val bookmarkedLessons: Set<String> = emptySet(),
    val totalExamsCount: Int = 6,
    val averageScore: Int = 92
)

data class Achievement(
    val id: String,
    val title: String,
    val description: String,
    val iconEmoji: String,
    val isUnlocked: Boolean,
    val progress: Int,
    val maxProgress: Int
)

data class SubjectRevisionSummary(
    val subjectId: String,
    val subjectName: String,
    val term: Term,
    val capsuleSummary: String,
    val goldenRules: List<String>,
    val commonMistakes: List<String>,
    val nightBeforeExamTips: List<String>
)
