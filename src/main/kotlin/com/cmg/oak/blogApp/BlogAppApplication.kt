package com.cmg.oak.blogApp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BlogAppApplication

fun main(args: Array<String>) {
	runApplication<BlogAppApplication>(*args)
}
