package com.github.mduesterhoeft.exposed

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<com.github.mduesterhoeft.exposed.Application>(*args)
}
