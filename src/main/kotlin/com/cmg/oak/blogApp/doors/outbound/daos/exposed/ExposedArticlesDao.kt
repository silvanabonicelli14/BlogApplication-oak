package com.cmg.oak.blogApp.doors.outbound.daos.exposed

import com.cmg.oak.blogApp.domain.model.Article
import com.cmg.oak.blogApp.domain.model.ArticleComment
import com.cmg.oak.blogApp.doors.outbound.daos.ArticlesDao
import com.cmg.oak.blogApp.doors.outbound.entities.exposed.ArticleDao
import com.cmg.oak.blogApp.doors.outbound.entities.exposed.ArticleEntity
import com.cmg.oak.blogApp.doors.outbound.entities.exposed.CommentArticleEntity
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Component

@Component("exposed")
class ExposedArticlesDao: ArticlesDao{
    override fun getAll(): List<Article> = transaction {
        ArticleDao.all()
            .map(::toArticle)
    }

    override fun getOneWithComment(id: Int): Article? = transaction {
        ArticleEntity
            .innerJoin(CommentArticleEntity)
            .select { ArticleEntity.id eq id }
            .map {
                Article(
                    it[ArticleEntity.id].value, it[ArticleEntity.title], it[ArticleEntity.body], listOf(
                        ArticleComment(
                            it[CommentArticleEntity.id].value,
                            it[CommentArticleEntity.author],
                            it[CommentArticleEntity.comment],
                            it[ArticleEntity.id].value
                        )
                    )
                )
            }
            .firstOrNull()
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

    private fun toArticle(art: ArticleDao) =
        Article(art.id.value, art.title, art.body, art.comments.map { ArticleComment(it.id.value,it.author,it.comment,art.id.value) })
}