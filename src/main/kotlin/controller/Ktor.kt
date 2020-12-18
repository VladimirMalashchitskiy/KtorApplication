package controller

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.thymeleaf.*
import model.*
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver
import java.io.File

fun main() {

    val server = embeddedServer(Netty, port = 9090) {

        val question1 = "What is tag use for new line with space?"
        val question2 = "Whats tags block?"
        val question3 = "What tag use for subscriber number list?"
        val question4 = "Choose the correct HTML element for the largest heading?"
        val question5 = "What is the correct HTML element for inserting a line break?"
        val question6 = "Is width=”100” and width=”100%” the same?"

        val answers: MutableList<Answer> = mutableListOf()
        var person = Person(null, null);

        routing {

            install(Thymeleaf) {
                setTemplateResolver(
                    ClassLoaderTemplateResolver().apply {
                        prefix = "pages/"
                        suffix = ".html"
                        characterEncoding = "utf-8"
                    }
                )
            }

            get("/") {
                call.respondFile(File("./src/main/resources/pages/main.html"))
            }

            post("/main") {
                val parameters = call.receiveParameters()
                val name = parameters["name"].toString()
                val age = parameters["age"].toString()
                person = Person(name = name, age = age)
                call.respond(ThymeleafContent("question1", mapOf("question" to question1)))

            }

            post("/answer1") {

                val parameters = call.receiveParameters()
                val answer = parameters["answer"].toString()

                answers.add(
                    Answer(
                        numberQuestion = "1",
                        answerCurrent = answer,
                        correctAnswer = "p",
                        question = question1
                    )
                )

                call.respond(ThymeleafContent("question2", mapOf("question" to question2)))
            }

            post("/answer2") {

                val parameters = call.receiveParameters()
                val answer = parameters["answer"].toString()

                answers.add(
                    Answer(
                        numberQuestion = "2",
                        answerCurrent = answer,
                        correctAnswer = "div, p, ul, ol",
                        question = question2
                    )
                )
                call.respond(ThymeleafContent("question3", mapOf("question" to question3)))

            }

            post("/answer3") {
                val parameters = call.receiveParameters()
                val answer = parameters["answer3"].toString()


                answers.add(
                    Answer(
                        numberQuestion = "3",
                        answerCurrent = answer,
                        correctAnswer = "ol",
                        question = question3
                    )
                )

                call.respond(ThymeleafContent("question4", mapOf("question" to question4)))

            }

            post("/answer4") {
                val parameters = call.receiveParameters()
                val answer = parameters["answer4"].toString()

                answers.add(
                    Answer(
                        numberQuestion = "4",
                        answerCurrent = answer,
                        correctAnswer = "h1",
                        question = question4
                    )
                )

                call.respond(ThymeleafContent("question5", mapOf("question" to question5)))

            }

            post("/answer5") {
                val parameters = call.receiveParameters()
                val answer = parameters["answer5"].toString()


                answers.add(
                    Answer(
                        numberQuestion = "5",
                        answerCurrent = answer,
                        correctAnswer = "br",
                        question = question5
                    )
                )

                call.respond(ThymeleafContent("question6", mapOf("question" to question6)))

            }

            post("/answer6") {
                val parameters = call.receiveParameters()
                val answer = parameters["answer6"].toString()


                answers.add(
                    Answer(
                        numberQuestion = "6",
                        answerCurrent = answer,
                        correctAnswer = "no",
                        question = question6
                    )
                )

                call.respond(
                    ThymeleafContent(
                        "end",
                        mapOf("user" to person, "answer" to answers, "result" to getResult(answers))
                    )
                )

                answers.clear()


            }
        }
    }
    server.start(wait = true)
}

private fun getResult(answers: MutableList<Answer>): String {
    val size = answers.size
    var countTrue = 0.0f;

    answers.forEach { if (it.answerCurrent == it.correctAnswer) countTrue++ }

    val temp = if (countTrue != 0.0f) (countTrue / size).toDouble() * 100 else 0.0

    return "$temp%"
}