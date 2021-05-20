package com.cmg.oak.blogApp.doors.inbound.routes

import com.cmg.oak.blogApp.domain.model.Article
import com.cmg.oak.blogApp.doors.outbound.daos.ArticlesDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

const val articlesResource = "/articles"

@RestController
@RequestMapping("/api")
class ArticlesController(
	@Autowired private val articlesDao: ArticlesDao){

	@GetMapping(articlesResource)
	fun getAll(): List<Article> = articlesDao.getAll()

	@GetMapping("$articlesResource/{id}")
	fun getOne(@PathVariable("id") id: Int): ResponseEntity<Article> =
		articlesDao.getOne(id)
			?.run { ResponseEntity.ok(this) }
			?: ResponseEntity.notFound().build()

	@PostMapping(
		articlesResource,
		consumes = ["application/json"],
		produces = ["application/json"])
	fun save(@RequestBody article: Article): ResponseEntity<Article> =
		articlesDao.save(article)
			.run { ResponseEntity.created(URI("/api/articles/${id}")).body(this) }
}

