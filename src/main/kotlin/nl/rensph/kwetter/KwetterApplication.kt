package nl.rensph.kwetter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class KwetterApplication

fun main(args: Array<String>) {
    runApplication<KwetterApplication>(*args)
}
