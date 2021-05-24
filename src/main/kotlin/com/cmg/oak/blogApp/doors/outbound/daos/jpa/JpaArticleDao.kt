package com.cmg.oak.blogApp.doors.outbound.daos.jpa

import com.cmg.oak.blogApp.domain.model.Article
import com.cmg.oak.blogApp.doors.outbound.daos.ArticlesDao
import com.cmg.oak.blogApp.doors.outbound.entities.jpa.ArticleEntity
import com.cmg.oak.blogApp.doors.outbound.repositories.ArticlesRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component("jpa")
class JpaArticleDao(private val articlesRepository: ArticlesRepository): ArticlesDao {
    override fun getAll(): List<Article> = articlesRepository
        .findAll()
        .map(this::toArticle)

    private fun toArticle(it: ArticleEntity) =
        Article(it.id, it.title, it.body)

    override fun getOne(id: Int): Article? = articlesRepository
        .findByIdOrNull(id)
        ?.let(this::toArticle)

    override fun save(article: Article): Article {
        val newArticle = articlesRepository.save(ArticleEntity(article.id, article.title, article.body))
        return toArticle(newArticle)
    }

    override fun reset() {
        articlesRepository.deleteAll()
    }
}