package de.bazi.kompro_backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class KomproBackendApplication

fun main(args: Array<String>) {
	runApplication<KomproBackendApplication>(*args)
}
