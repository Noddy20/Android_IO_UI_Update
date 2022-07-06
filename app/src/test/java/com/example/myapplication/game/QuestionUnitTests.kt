package com.example.myapplication.game

import com.example.myapplication.models.data.Question
import com.example.myapplication.models.data.QuestionImp
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test


class QuestionUnitTests {

    companion object{
        const val CORRECT_OPTION = "A"
        const val INCORRECT_OPTION = "B"
    }

    private lateinit var question: Question

    @Before
    fun setup(){
        question = QuestionImp(CORRECT_OPTION, INCORRECT_OPTION, null)
    }
    
    @Test
    fun `when creating question, should not have answered question`(){
        assertNull(question.answeredOption)
    }

    @Test
    fun `when answered, should have answered option`(){
        question.answer(CORRECT_OPTION)

        assertEquals("Answer should be $CORRECT_OPTION", CORRECT_OPTION, question.answeredOption)
    }

    @Test
    fun `when answering correct option, should return true`(){
        val result = question.answer(CORRECT_OPTION)

        assertTrue("result should be true", result)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `when answering invalid option, should throw exception`(){
        val invalidOption = "C"

        question.answer(invalidOption)
    }
    
}