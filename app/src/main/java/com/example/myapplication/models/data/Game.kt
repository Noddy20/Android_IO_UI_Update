package com.example.myapplication.models.data

interface Game{

    val score: Score

    fun nextQuestion(): Question?
    fun answer(question: Question, option: String)

}

class GameImp(val questions: List<Question> = emptyList(), override val score: Score = ScoreImp(0)): Game{

    private var questionIndex = -1

    override fun nextQuestion(): Question? {
        if (questionIndex + 1 < questions.size) {
            questionIndex++
            return questions[questionIndex]
        }
        return null
    }

    override fun answer(question: Question, option: String){
        if(question.answer(option)){
            score.increment()
        }
    }

}

interface Score{

    val current: Int
    val highest: Int

    fun increment()

}

class ScoreImp(highestScore: Int = 0): Score{

    override var current = 0
        private set
    override var highest = highestScore
        private set

    override fun increment() {
        current++
        if (current > highest) {
            highest = current
        }
    }
}

interface Question{

    val answeredOption: String?
    val isAnsweredCorrectly: Boolean

    val correctOption: String
    val incorrectOption: String
    val imageUrl: String?

    fun answer(option: String): Boolean

}

class QuestionImp(override val correctOption: String,
                  override val incorrectOption: String,
                  override val imageUrl: String?): Question {

    override var answeredOption: String? = null
    private set

    override val isAnsweredCorrectly: Boolean
        get() = correctOption == answeredOption

    override fun answer(option: String): Boolean{
        if (option != correctOption && option != incorrectOption){
            throw IllegalArgumentException("$option is an invalid option!")
        }
        answeredOption = option
        return answeredOption == correctOption
    }

}


data class Cocktail(val id: String, val strDrink: String, val strDrinkThumb: String)