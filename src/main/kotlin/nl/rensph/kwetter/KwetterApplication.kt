package nl.rensph.kwetter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KwetterApplication

fun main(args: Array<String>) {
    runApplication<KwetterApplication>(*args)
}
