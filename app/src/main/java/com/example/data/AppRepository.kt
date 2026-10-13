package com.example.data

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.json.JSONArray
import org.json.JSONObject

class AppRepository(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("yassin_study_2027_prefs", Context.MODE_PRIVATE)

    // Reactive StateFlows
    private val _studentProfile = MutableStateFlow(loadProfile())
    val studentProfile: StateFlow<StudentProfile> = _studentProfile.asStateFlow()

    private val _studyTasks = MutableStateFlow(loadStudyTasks())
    val studyTasks: StateFlow<List<StudyTask>> = _studyTasks.asStateFlow()

    private val _examResults = MutableStateFlow(loadExamResults())
    val examResults: StateFlow<List<ExamResult>> = _examResults.asStateFlow()

    private val _notesMap = MutableStateFlow(loadNotes())
    val notesMap: StateFlow<Map<String, String>> = _notesMap.asStateFlow()

    private val _achievements = MutableStateFlow(loadAchievements())
    val achievements: StateFlow<List<Achievement>> = _achievements.asStateFlow()

    private val _isDarkMode = MutableStateFlow(prefs.getBoolean("key_dark_mode", true))
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    private val _fontScale = MutableStateFlow(prefs.getFloat("key_font_scale", 1.0f))
    val fontScale: StateFlow<Float> = _fontScale.asStateFlow()

    // Profile Management
    private fun loadProfile(): StudentProfile {
        val completedSet = prefs.getStringSet("key_completed_lessons", emptySet()) ?: emptySet()
        val bookmarkedSet = prefs.getStringSet("key_bookmarked_lessons", emptySet()) ?: emptySet()
        return StudentProfile(
            name = prefs.getString("key_student_name", "ياسين وائل") ?: "ياسين وائل",
            grade = prefs.getString("key_student_grade", "الصف الثالث الإعدادي (2026/2027)") ?: "الصف الثالث الإعدادي (2026/2027)",
            avatarEmoji = prefs.getString("key_student_avatar", "🎓") ?: "🎓",
            xp = prefs.getInt("key_student_xp", 450),
            level = prefs.getInt("key_student_level", 4),
            streakDays = prefs.getInt("key_student_streak", 5),
            completedLessons = completedSet,
            bookmarkedLessons = bookmarkedSet,
            totalExamsCount = prefs.getInt("key_exams_count", 6),
            averageScore = prefs.getInt("key_avg_score", 92)
        )
    }

    fun updateStudentName(newName: String, newAvatar: String) {
        prefs.edit()
            .putString("key_student_name", newName)
            .putString("key_student_avatar", newAvatar)
            .apply()
        _studentProfile.value = _studentProfile.value.copy(name = newName, avatarEmoji = newAvatar)
    }

    fun toggleLessonBookmark(lessonId: String) {
        val currentSet = _studentProfile.value.bookmarkedLessons.toMutableSet()
        val wasBookmarked = currentSet.contains(lessonId)
        if (wasBookmarked) {
            currentSet.remove(lessonId)
        } else {
            currentSet.add(lessonId)
        }
        prefs.edit().putStringSet("key_bookmarked_lessons", currentSet).apply()
        _studentProfile.value = _studentProfile.value.copy(bookmarkedLessons = currentSet)
    }

    fun markLessonCompleted(lessonId: String, completed: Boolean) {
        val currentSet = _studentProfile.value.completedLessons.toMutableSet()
        if (completed) {
            currentSet.add(lessonId)
            addXp(30)
        } else {
            currentSet.remove(lessonId)
        }
        prefs.edit().putStringSet("key_completed_lessons", currentSet).apply()
        _studentProfile.value = _studentProfile.value.copy(completedLessons = currentSet)
    }

    fun addXp(amount: Int) {
        val current = _studentProfile.value
        val newXp = current.xp + amount
        val newLevel = (newXp / 150) + 1
        prefs.edit()
            .putInt("key_student_xp", newXp)
            .putInt("key_student_level", newLevel)
            .apply()
        _studentProfile.value = current.copy(xp = newXp, level = newLevel)
    }

    // Notes
    private fun loadNotes(): Map<String, String> {
        val jsonStr = prefs.getString("key_lesson_notes", null) ?: return emptyMap()
        val map = mutableMapOf<String, String>()
        try {
            val json = JSONObject(jsonStr)
            val keys = json.keys()
            while (keys.hasNext()) {
                val key = keys.next()
                map[key] = json.getString(key)
            }
        } catch (_: Exception) {}
        return map
    }

    fun saveLessonNote(lessonId: String, note: String) {
        val current = _notesMap.value.toMutableMap()
        if (note.trim().isEmpty()) {
            current.remove(lessonId)
        } else {
            current[lessonId] = note
        }
        val json = JSONObject()
        current.forEach { (k, v) -> json.put(k, v) }
        prefs.edit().putString("key_lesson_notes", json.toString()).apply()
        _notesMap.value = current
    }

    // Study Tasks
    private fun loadStudyTasks(): List<StudyTask> {
        val jsonStr = prefs.getString("key_study_tasks", null)
        if (jsonStr == null) {
            return listOf(
                StudyTask("t1", "مذاكرة درس الحركة في اتجاه واحد وحل مسائله", "العلوم", 40, true, "اليوم"),
                StudyTask("t2", "حل تمارين حاصل الضرب الديكارتي بكتاب المعاصر", "الرياضيات", 35, false, "اليوم"),
                StudyTask("t3", "حفظ كلمات وقواعد الوحدة الأولى New Hello", "اللغة الإنجليزية", 25, false, "غداً"),
                StudyTask("t4", "مراجعة خريطة قارات العالم والموقع الفلكي", "الدراسات", 30, false, "الخميس")
            )
        }
        val list = mutableListOf<StudyTask>()
        try {
            val arr = JSONArray(jsonStr)
            for (i in 0 until arr.length()) {
                val obj = arr.getJSONObject(i)
                list.add(
                    StudyTask(
                        id = obj.getString("id"),
                        title = obj.getString("title"),
                        subjectName = obj.getString("subjectName"),
                        durationMinutes = obj.getInt("durationMinutes"),
                        isCompleted = obj.getBoolean("isCompleted"),
                        dateString = obj.optString("dateString", "اليوم")
                    )
                )
            }
        } catch (_: Exception) {}
        return list
    }

    private fun persistTasks(list: List<StudyTask>) {
        val arr = JSONArray()
        list.forEach { task ->
            val obj = JSONObject()
            obj.put("id", task.id)
            obj.put("title", task.title)
            obj.put("subjectName", task.subjectName)
            obj.put("durationMinutes", task.durationMinutes)
            obj.put("isCompleted", task.isCompleted)
            obj.put("dateString", task.dateString)
            arr.put(obj)
        }
        prefs.edit().putString("key_study_tasks", arr.toString()).apply()
        _studyTasks.value = list
    }

    fun addTask(title: String, subjectName: String, duration: Int) {
        val newTask = StudyTask(
            id = "t_${System.currentTimeMillis()}",
            title = title,
            subjectName = subjectName,
            durationMinutes = duration,
            isCompleted = false,
            dateString = "اليوم"
        )
        val updated = _studyTasks.value + newTask
        persistTasks(updated)
        addXp(15)
    }

    fun toggleTask(taskId: String) {
        val updated = _studyTasks.value.map {
            if (it.id == taskId) {
                val nextState = !it.isCompleted
                if (nextState) addXp(25)
                it.copy(isCompleted = nextState)
            } else it
        }
        persistTasks(updated)
    }

    fun deleteTask(taskId: String) {
        val updated = _studyTasks.value.filter { it.id != taskId }
        persistTasks(updated)
    }

    // Exam Results
    private fun loadExamResults(): List<ExamResult> {
        val jsonStr = prefs.getString("key_exam_results", null)
        if (jsonStr == null) {
            return listOf(
                ExamResult(
                    id = "res_1",
                    examTitle = "اختبار شامل: الوحدة الأولى علوم (الحركة والسرعة)",
                    subjectName = "العلوم",
                    timestamp = System.currentTimeMillis() - 86400000L,
                    totalQuestions = 10,
                    correctAnswers = 9,
                    scorePercentage = 90,
                    wrongQuestions = listOf("السرعة النسبية في نفس الاتجاه"),
                    reviewRecommendations = listOf("الدرس الأول: الحركة والسرعة النسبية")
                ),
                ExamResult(
                    id = "res_2",
                    examTitle = "امتحان قصير: حاصل الضرب الديكارتي",
                    subjectName = "الرياضيات",
                    timestamp = System.currentTimeMillis() - 172800000L,
                    totalQuestions = 8,
                    correctAnswers = 8,
                    scorePercentage = 100,
                    wrongQuestions = emptyList(),
                    reviewRecommendations = emptyList()
                )
            )
        }
        val list = mutableListOf<ExamResult>()
        try {
            val arr = JSONArray(jsonStr)
            for (i in 0 until arr.length()) {
                val obj = arr.getJSONObject(i)
                val wrongs = mutableListOf<String>()
                val wArr = obj.optJSONArray("wrongQuestions")
                if (wArr != null) {
                    for (j in 0 until wArr.length()) wrongs.add(wArr.getString(j))
                }
                val recs = mutableListOf<String>()
                val rArr = obj.optJSONArray("reviewRecommendations")
                if (rArr != null) {
                    for (j in 0 until rArr.length()) recs.add(rArr.getString(j))
                }
                list.add(
                    ExamResult(
                        id = obj.getString("id"),
                        examTitle = obj.getString("examTitle"),
                        subjectName = obj.getString("subjectName"),
                        timestamp = obj.getLong("timestamp"),
                        totalQuestions = obj.getInt("totalQuestions"),
                        correctAnswers = obj.getInt("correctAnswers"),
                        scorePercentage = obj.getInt("scorePercentage"),
                        wrongQuestions = wrongs,
                        reviewRecommendations = recs
                    )
                )
            }
        } catch (_: Exception) {}
        return list
    }

    fun saveExamResult(result: ExamResult) {
        val updated = listOf(result) + _examResults.value
        val arr = JSONArray()
        updated.forEach { r ->
            val obj = JSONObject()
            obj.put("id", r.id)
            obj.put("examTitle", r.examTitle)
            obj.put("subjectName", r.subjectName)
            obj.put("timestamp", r.timestamp)
            obj.put("totalQuestions", r.totalQuestions)
            obj.put("correctAnswers", r.correctAnswers)
            obj.put("scorePercentage", r.scorePercentage)
            val wArr = JSONArray()
            r.wrongQuestions.forEach { wArr.put(it) }
            obj.put("wrongQuestions", wArr)
            val rArr = JSONArray()
            r.reviewRecommendations.forEach { rArr.put(it) }
            obj.put("reviewRecommendations", rArr)
            arr.put(obj)
        }
        prefs.edit().putString("key_exam_results", arr.toString()).apply()
        _examResults.value = updated

        // Award XP and calculate average
        addXp(50 + (result.scorePercentage / 2))
        val totalExams = updated.size
        val avg = updated.map { it.scorePercentage }.average().toInt()
        prefs.edit()
            .putInt("key_exams_count", totalExams)
            .putInt("key_avg_score", avg)
            .apply()
        _studentProfile.value = _studentProfile.value.copy(
            totalExamsCount = totalExams,
            averageScore = avg
        )
    }

    // Achievements
    private fun loadAchievements(): List<Achievement> {
        return CurriculumDataSource.initialAchievements
    }

    // Settings
    fun setDarkMode(enabled: Boolean) {
        prefs.edit().putBoolean("key_dark_mode", enabled).apply()
        _isDarkMode.value = enabled
    }

    fun setFontScale(scale: Float) {
        prefs.edit().putFloat("key_font_scale", scale).apply()
        _fontScale.value = scale
    }

    fun resetAllProgress() {
        prefs.edit().clear().apply()
        _studentProfile.value = loadProfile()
        _studyTasks.value = loadStudyTasks()
        _examResults.value = emptyList()
        _notesMap.value = emptyMap()
    }
}
