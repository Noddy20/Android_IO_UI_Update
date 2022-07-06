package com.example.myapplication.game

import com.example.myapplication.models.data.Score
import com.example.myapplication.models.data.ScoreImp
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test

class ScoreUnitTests {

    @Test
    fun `when incrementing score, should increment currentScore`() {

        val score: Score = ScoreImp()

        score.increment()

        assertEquals("Current score should have been 1", 1, score.current)
    }

    @Test
    fun `when incrementing score above highScore, should also increment highScore`() {
        val score: Score = ScoreImp()

        score.increment()

        assertEquals(1, score.highest)
    }

    @Test
    fun `when incrementing score below highScore should not increment highScore`() {
        val score: Score = ScoreImp(10)

        score.increment()

        assertEquals(10, score.highest)
    }

}