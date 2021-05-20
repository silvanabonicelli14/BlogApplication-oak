package com.cmg.oak.blogApp.doors.outbound.repositories

import com.cmg.oak.blogApp.domain.model.Article
import org.springframework.stereotype.Component

@Component
class InMemoryArticlesRepository(initialArticles: List<Article>) : ArticlesRepository {
	private val articles = initialArticles.toMutableList()

	override fun getAll(): List<Article> = articles

	override fun getOne(id: Int): Article? = articles.firstOrNull { it.id == id }
	override fun save(article: Article): Article {
		val newId: Int = articles.maxByOrNull { it.id }?.id ?: 0
		return article.copy(id = newId + 1)
			.apply(articles::add)
	}


	fun reset(): Unit = articles.clear()
}