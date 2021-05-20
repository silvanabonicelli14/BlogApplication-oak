package com.cmg.oak.blogApp.doors.inbound.routes

import com.cmg.oak.blogApp.domain.model.Article
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

const val articlesResource = "/articles"

@RestController
@RequestMapping("/api")
class ArticlesController {

	val articlesRepository = InMemoryArticlesRepository(listOf(
		Article(1, "title x", "body of the article x"),
		Article(2, "title y", "body of the article y")))

	@GetMapping(articlesResource)
	fun getAll(): List<Article> = articlesRepository.getAll()

	@GetMapping("$articlesResource/{id}")
	fun getOne(@PathVariable("id") id: Int): ResponseEntity<Article> =
		articlesRepository.getOne(id)
			?.run { ResponseEntity.ok(this) }
			?: ResponseEntity.notFound().build()

	@PostMapping(
		articlesResource,
		consumes = ["application/json"],
		produces = ["application/json"])
	fun save(@RequestBody article: Article): ResponseEntity<Article> =
		articlesRepository.save(article)
			.run { ResponseEntity.created(URI("/api/articles/${id}")).body(this) }
}

class InMemoryArticlesRepository(initialArticles: List<Article>) {
	private val articles = initialArticles.toMutableList()

	fun getAll(): List<Article> = articles

	fun getOne(id: Int): Article? = articles.firstOrNull { it.id == id }
	fun save(article: Article): Article {
		val newId: Int = articles.maxByOrNull { it.id }?.id ?: 1
		return article.copy(id = newId + 1)
			.apply(articles::add)
	}
}
