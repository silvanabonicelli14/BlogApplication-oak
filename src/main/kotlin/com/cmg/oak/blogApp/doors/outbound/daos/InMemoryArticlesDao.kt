package com.cmg.oak.blogApp.doors.outbound.daos

import com.cmg.oak.blogApp.domain.model.Article
import org.springframework.stereotype.Component

@Component("inmemory")
class InMemoryArticlesDao(initialArticles: List<Article>) : ArticlesDao {
	private val articles = initialArticles.toMutableList()

	override fun getAll(): List<Article> = articles
	override fun getOneWithComment(id: Int): Article? {
		TODO("Not yet implemented")
	}

	override fun getOne(id: Int): Article? = articles.firstOrNull { it.id == id }
	override fun save(article: Article): Article {
		val newId: Int = articles.maxByOrNull { it.id }?.id ?: 0
		return article.copy(id = newId + 1)
			.apply(articles::add)
	}


	override fun reset(): Unit = articles.clear()
}