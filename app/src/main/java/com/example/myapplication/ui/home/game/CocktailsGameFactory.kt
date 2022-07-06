package com.example.myapplication.ui.home.game

import com.example.myapplication.models.data.*

interface CocktailsGameFactory {

    fun buildGame(callback: Callback)

    interface Callback {
        fun onSuccess(game: Game)
        fun onError()
    }

}

class CocktailsGameFactoryImpl(private val repository: CocktailsRepository)
    : CocktailsGameFactory {

    override fun buildGame(callback: CocktailsGameFactory.Callback) {
        repository.getAlcoholic(
            object : RepositoryCallback{
                override fun onSuccess(cocktailList: List<Cocktail>) {
                    val questions = buildQuestions(cocktailList)
                    val score: Score = ScoreImp(repository.getHighScore())
                    val game: Game = GameImp(questions, score)
                    callback.onSuccess(game)
                }

                override fun onError(e: String) {
                    callback.onError()
                }
            })
    }

    private fun buildQuestions(cocktailList: List<Cocktail>)
            = cocktailList.map { cocktail ->
        val otherCocktail = cocktailList.shuffled().first { it != cocktail }
        QuestionImp(cocktail.strDrink,
            otherCocktail.strDrink,
            cocktail.strDrinkThumb)
    }

}