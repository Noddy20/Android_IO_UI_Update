package com.example.myapplication.game

import android.util.Log
import com.example.myapplication.models.data.Cocktail
import com.example.myapplication.models.data.Game
import com.example.myapplication.models.data.Question
import com.example.myapplication.ui.home.game.CocktailsGameFactory
import com.example.myapplication.ui.home.game.CocktailsGameFactoryImpl
import com.example.myapplication.ui.home.game.CocktailsRepository
import com.example.myapplication.ui.home.game.RepositoryCallback
import com.nhaarman.mockito_kotlin.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CocktailsGameFactoryUnitTests {

    private lateinit var repository: CocktailsRepository
    private lateinit var factory: CocktailsGameFactory

    private val cocktails = listOf(
        Cocktail("1", "Drink1", "image1"),
        Cocktail("2", "Drink2", "image2"),
        Cocktail("3", "Drink3", "image3"),
        Cocktail("4", "Drink4", "image4")
    )

    @Before
    fun setup() {
        repository = mock()
        factory = CocktailsGameFactoryImpl(repository)
    }

    @Test
    fun `buildGame should get Cocktails from repo`() {
        factory.buildGame(mock())
        verify(repository).getAlcoholic(any())
    }

    @Test
    fun `buildGame should call onSuccess`() {
        val callback = mock<CocktailsGameFactory.Callback>()
        setUpRepositoryWithCocktails(repository)
        factory.buildGame(callback)
        verify(callback).onSuccess(any())
    }

    private fun setUpRepositoryWithCocktails(repository: CocktailsRepository) {
        doAnswer {
            val callback: RepositoryCallback = it.getArgument(0)
            callback.onSuccess(cocktails)
        }.whenever(repository)
            .getAlcoholic(any())
    }

    @Test
    fun `buildGame should call onError`() {
        val callback = mock<CocktailsGameFactory.Callback>()
        setUpRepositoryWithError(repository)
        factory.buildGame(callback)
        verify(callback).onError()
    }

    private fun setUpRepositoryWithError(repository: CocktailsRepository) {
        doAnswer {
            val callback: RepositoryCallback = it.getArgument(0)
            callback.onError("Error")
        }.whenever(repository)
            .getAlcoholic(any())
    }



    @Test
    fun `buildGame should get high score from repo`() {
        setUpRepositoryWithCocktails(repository)
        factory.buildGame(mock())
        verify(repository).getHighScore()
    }

    @Test
    fun `buildGame should build game with high score`() {
        setUpRepositoryWithCocktails(repository)
        val highScore = 100

        whenever(repository.getHighScore()).thenReturn(highScore)

        factory.buildGame(object : CocktailsGameFactory.Callback {
            override fun onSuccess(game: Game)
                    = assertEquals(highScore, game.score.highest)
            override fun onError() = fail()
        })
    }

    @Test
    fun `buildGame should build game with questions`() {
        setUpRepositoryWithCocktails(repository)
        factory.buildGame(object : CocktailsGameFactory.Callback {
            override fun onSuccess(game: Game) {
                cocktails.forEach {
                    assertQuestion(game.nextQuestion(),
                        it.strDrink,
                        it.strDrinkThumb)
                }
            }
            override fun onError() = fail()
        })
    }

    private fun assertQuestion(question: Question?,
                               correctOption: String,
                               imageUrl: String?) {
        assertNotNull(question)
        assertEquals(imageUrl, question?.imageUrl)
        assertEquals(correctOption, question?.correctOption)
        assertNotEquals(correctOption, question?.incorrectOption)
    }

}