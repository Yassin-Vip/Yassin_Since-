package com.example

import com.example.data.CurriculumDataSource
import com.example.data.ExperimentType
import com.example.data.Term
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun testCurriculumSubjectsCoverage() {
        val subjects = CurriculumDataSource.subjects
        // Verifies 7 subjects for Egypt 3rd prep
        assertTrue(subjects.size >= 7)
        val subjectIds = subjects.map { it.id }
        assertTrue(subjectIds.contains("science"))
        assertTrue(subjectIds.contains("math"))
        assertTrue(subjectIds.contains("arabic"))
        assertTrue(subjectIds.contains("english"))
        assertTrue(subjectIds.contains("social"))
        assertTrue(subjectIds.contains("religion"))
        assertTrue(subjectIds.contains("computer"))
    }

    @Test
    fun testScienceExperimentsCoverage() {
        val exps = CurriculumDataSource.scienceExperiments
        assertEquals(4, exps.size)
        assertTrue(exps.any { it.type == ExperimentType.SPEED_MOTION })
        assertTrue(exps.any { it.type == ExperimentType.OPTICS_MIRRORS })
        assertTrue(exps.any { it.type == ExperimentType.OHMS_LAW })
        assertTrue(exps.any { it.type == ExperimentType.MENDEL_GENETICS })
    }

    @Test
    fun testBooksAndQuestionBank() {
        assertTrue(CurriculumDataSource.digitalBooks.isNotEmpty())
        assertTrue(CurriculumDataSource.questionBank.size >= 7)
        assertTrue(CurriculumDataSource.revisionSummaries.isNotEmpty())
    }
}
