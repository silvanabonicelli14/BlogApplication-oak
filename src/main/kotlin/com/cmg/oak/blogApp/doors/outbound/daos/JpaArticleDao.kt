package com.cmg.oak.blogApp.doors.outbound.daos

import com.cmg.oak.blogApp.domain.model.Article
import com.cmg.oak.blogApp.doors.outbound.entities.ArticleEntity
import com.cmg.oak.blogApp.doors.outbound.repositories.ArticlesRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import javax.transaction.Transactional

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

    @Transactional
    override fun save(article: Article): Article {
        val newArticle = articlesRepository.save(ArticleEntity(article.id, article.title, article.body))
        return toArticle(newArticle)
    }

    @Transactional
    override fun reset() {
        articlesRepository.deleteAll()
    }
}