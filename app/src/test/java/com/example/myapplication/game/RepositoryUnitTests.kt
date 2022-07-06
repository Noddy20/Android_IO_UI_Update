package com.example.myapplication.game

import android.content.SharedPreferences
import com.example.myapplication.ui.home.game.CocktailsApi
import com.example.myapplication.ui.home.game.CocktailsRepository
import com.example.myapplication.ui.home.game.CocktailsRepositoryImpl
import com.nhaarman.mockito_kotlin.*
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test

class RepositoryUnitTests {

    private lateinit var repository: CocktailsRepository
    private lateinit var api: CocktailsApi
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferencesEditor: SharedPreferences.Editor
    @Before
    fun setup() {
        api = mock()
        sharedPreferences = mock()
        sharedPreferencesEditor = mock()

        whenever(sharedPreferences.edit()).thenReturn(sharedPreferencesEditor)
        repository = CocktailsRepositoryImpl(api, sharedPreferences)
    }

    @Test
    fun `saveHighScore should save to SharedPreferences`() {
        val score = 100
        repository.saveHighScore(score)

        inOrder(sharedPreferencesEditor) {
            verify(sharedPreferencesEditor).putInt(any(), eq(score))
            verify(sharedPreferencesEditor).apply()
        }
    }
    
    @Test
    fun `getHighScore should get score from SharedPreferences`(){
        repository.getHighScore()

        verify(sharedPreferences).getInt(any(), any())
    }

    @Test
    fun `saveHighScore should not save, if lower than previous score`(){
        val prevScore = 100
        val newScore = 10

        //Using a spy will let you call the methods of a real object,
        // while also tracking every interaction, just as you would do with a mock.
        val spyRepository = spy(repository)

        doReturn(prevScore)
            .whenever(spyRepository)
            .getHighScore()

        spyRepository.saveHighScore(newScore)

        verify(sharedPreferencesEditor, never()).putInt(any(), eq(newScore))
    }

}