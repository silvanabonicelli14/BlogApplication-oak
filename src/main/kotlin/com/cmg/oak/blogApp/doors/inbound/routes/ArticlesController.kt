package com.cmg.oak.blogApp.doors.inbound.routes

import com.cmg.oak.blogApp.domain.model.Article
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class ArticlesController {

	private val articles = listOf(
        Article(1, "title x", "body of the article x"),
        Article(2, "title y", "body of the article y")
    )

	@GetMapping("/articles")
	fun getAll(): List<Article> = articles

	@GetMapping("/articles/{id}")
	fun getOne(@PathVariable("id") id: Int): ResponseEntity<Article> = articles
		.firstOrNull { it.id == id }
		?.run { ResponseEntity.ok(this) }
		?: ResponseEntity.notFound().build()
}