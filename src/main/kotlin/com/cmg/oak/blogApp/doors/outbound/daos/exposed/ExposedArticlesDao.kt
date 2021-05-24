package com.cmg.oak.blogApp.doors.outbound.daos.exposed

import com.cmg.oak.blogApp.domain.model.Article
import com.cmg.oak.blogApp.doors.outbound.daos.ArticlesDao
import com.cmg.oak.blogApp.doors.outbound.entities.exposed.ArticleDao
import com.cmg.oak.blogApp.doors.outbound.entities.exposed.ArticleEntity
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Component

@Component("exposed")
class ExposedArticlesDao: ArticlesDao{
    override fun getAll(): List<Article> = transaction {
        ArticleDao.all()
            .map(::toArticle)
    }

    override fun getOne(id: Int): Article? = transaction {
        ArticleDao
            .findById(id)
            ?.let(::toArticle)
    }

    override fun save(article: Article): Article = transaction {
        ArticleDao.new {
            title = article.title
            body = article.body
        }.let(::toArticle)
    }

    override fun reset() = transaction {
        ArticleEntity.deleteAll()
        Unit
    }

    private fun toArticle(it: ArticleDao) =
        Article(it.id.value, it.title, it.body)
}