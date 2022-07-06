package com.example.myapplication.ui.home.game

import android.content.SharedPreferences
import com.example.myapplication.models.data.Cocktail
import com.example.myapplication.models.data.Game

interface CocktailsRepository {

    fun saveHighScore(score: Int)

    fun getHighScore(): Int

    fun getAlcoholic(repositoryCallback: RepositoryCallback)

}

private const val HIGH_SCORE_KEY = "HIGH_SCORE_KEY"

class CocktailsRepositoryImpl(
    private val api: CocktailsApi,
    private val sharedPreferences: SharedPreferences
) : CocktailsRepository {

    override fun saveHighScore(score: Int) {
        val highScore = getHighScore()
        if (score > highScore) {
            val editor = sharedPreferences.edit()
            editor.putInt(HIGH_SCORE_KEY, score)
            editor.apply()
        }
    }

    override fun getHighScore() = sharedPreferences.getInt(HIGH_SCORE_KEY, -1)

    override fun getAlcoholic(repositoryCallback: RepositoryCallback) {
        TODO("Not yet implemented")
    }

}

interface RepositoryCallback{

    fun onSuccess(cocktailList: List<Cocktail>)

    fun onError(e: String)

}