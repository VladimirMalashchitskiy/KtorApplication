package controller

import io.ktor.application.*
import io.ktor.http.content.*
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
    val server = embeddedServer(Netty, port = 8080) {

        val question1 = "What is tag use for new line with space"
        val question2 = "Whats tags block"
        val question3 = "What tag use for subscriber number list"
        val question4 = "Choose the correct HTML element for the largest heading"
        val question5 = "What is the correct HTML element for inserting a line break"
        val question6 = "Is width=”100” and width=”100%” the same"

        val answers: MutableList<Answer> = mutableListOf()
        var person = Person(null, null)

        routing {
            this.install(Thymeleaf) {
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
                answers.add(newAnswer("1", call, "p", question1))
                call.respond(ThymeleafContent("question2", mapOf("question" to question2)))
            }

            post("/answer2") {
                answers.add(newAnswer("2", call, "div, p, ul, ol", question2))
                call.respond(ThymeleafContent("question3", mapOf("question" to question3)))
            }

            post("/answer3") {
                answers.add(newAnswer("3", call, "ol", question3))
                call.respond(ThymeleafContent("question4", mapOf("question" to question4)))
            }

            post("/answer4") {
                answers.add(newAnswer("4", call, "h1", question4))
                call.respond(ThymeleafContent("question5", mapOf("question" to question5)))
            }

            post("/answer5") {
                answers.add(newAnswer("5", call, "br", question5))
                call.respond(ThymeleafContent("question6", mapOf("question" to question6)))
            }

            post("/answer6") {
                answers.add(newAnswer("6", call, "no", question6))
                call.respond(ThymeleafContent("result", mapOf("user" to person, "result" to getResult(answers))))
            }

            post("/result") {
                call.respond(ThymeleafContent("end", mapOf("answer" to answers)))
            }

            post("/again") {
                answers.clear()
                call.respondFile(File("./src/main/resources/pages/main.html"))
            }
        }
    }
    server.start(wait = true)
}

