package com.cmg.oak.blogApp.doors.outbound.daos.exposed

import com.cmg.oak.blogApp.domain.model.ArticleComment
import com.cmg.oak.blogApp.doors.outbound.daos.ArticleCommentDao
import com.cmg.oak.blogApp.doors.outbound.entities.exposed.CommentArticleEntity
import com.cmg.oak.blogApp.doors.outbound.entities.exposed.CommentArticlesDao
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Component

@Component("exposedComment")
class ExposedCommentArticleDao: ArticleCommentDao {
    override fun getAll(): List<ArticleComment> {
        TODO("Not yet implemented")
    }

    override fun getOne(id: Int): ArticleComment? {
        TODO("Not yet implemented")
    }

    override fun save(commentArticle: ArticleComment): ArticleComment = transaction {

       CommentArticleEntity.insert {
            it[id] = commentArticle.id
            it[comment] = commentArticle.comment
            it[author] = commentArticle.author
            it[article_id] = commentArticle.article
        }

        ArticleComment(commentArticle.id, commentArticle.comment, commentArticle.author,commentArticle.article)
    }

    override fun reset() {
        CommentArticleEntity.deleteAll()
        Unit
    }

}