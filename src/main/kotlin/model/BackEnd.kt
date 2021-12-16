package model

import io.ktor.application.*
import io.ktor.request.*

fun getResult(answers: MutableList<Answer>): String {
    val size = answers.size
    var countTrue = 0.0f

    answers.forEach { if (it.answerCurrent == it.correctAnswer) countTrue++ }
    val temp = if (countTrue != 0.0f) (countTrue / size).toDouble() * 100 else 0.0
    if (temp > 60 && temp < 80) return "Not bad, but you can better $temp%"
    if (temp > 80) return "Excellent, continue in this pace $temp%"

    return "You need more hard work $temp%"
}

suspend fun newAnswer(
    numberQuestion: String,
    call: ApplicationCall,
    correctAnswer: String,
    question: String
): Answer {
    return Answer(
        numberQuestion = numberQuestion,
        answerCurrent = call.receiveParameters()["answer"].toString(),
        correctAnswer = correctAnswer,
        question = question
    )
}