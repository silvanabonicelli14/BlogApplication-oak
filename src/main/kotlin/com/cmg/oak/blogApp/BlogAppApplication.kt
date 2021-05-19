package com.cmg.oak.blogApp

import com.cmg.oak.blogApp.domain.model.Article
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class BlogAppApplication

fun main(args: Array<String>) {
	runApplication<BlogAppApplication>(*args)
}

@RestController
class ArticlesController {

	@GetMapping("/api/articles")
	fun getAll() = listOf(
		Article(1, "title x", "body of the article x"),
		Article(2, "title y", "body of the article y"))

}