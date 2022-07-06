package com.example.myapplication.game

import com.example.myapplication.models.data.Game
import com.example.myapplication.models.data.GameImp
import com.example.myapplication.models.data.Question
import com.example.myapplication.models.data.Score
import com.nhaarman.mockito_kotlin.*
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString

class GameUnitTests {

    @Test
    fun `when answering, should delegate to question`(){
        val question = mock<Question>()

        val game: Game = GameImp(listOf(question))

        game.answer(question, QuestionUnitTests.CORRECT_OPTION)

        verify(question, times(1)).answer(eq(QuestionUnitTests.CORRECT_OPTION))
    }
    
    @Test
    fun `when answering correctly, should increment currentScore`(){
        val question = mock<Question>()
        whenever(question.answer(anyString())).thenReturn(true)

        val score = mock<Score>()

        val game: Game = GameImp(listOf(question), score)

        game.answer(question, QuestionUnitTests.CORRECT_OPTION)

        verify(score, times(1)).increment()
    }

    @Test
    fun `when answering incorrectly, should not increment currentScore`(){
        val question = mock<Question>()
        whenever(question.answer(anyString())).thenReturn(false)

        val score = mock<Score>()

        val game: Game = GameImp(listOf(question), score)

        game.answer(question, QuestionUnitTests.CORRECT_OPTION)

        //Verify increment() function never called on score
        verify(score, never()).increment()
    }

}