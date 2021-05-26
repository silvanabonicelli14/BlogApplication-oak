package com.cmg.oak.blogApp.doors.outbound.entities.exposed

import com.cmg.oak.blogApp.domain.model.Article
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object Users: IntIdTable("Blog.users") {
    val name = varchar("name", 50)
}

object CommentArticleEntity : IntIdTable("Blog.articlecomments") {

    val author: Column<String> = varchar("author", 2000)
    val comment: Column<String> = varchar("comment", 2000)
    val article_id = reference("article_id",ArticleEntity.id)
}

class CommentArticlesDao(id: EntityID<Int>) : IntEntity(id){
    companion object : IntEntityClass<CommentArticlesDao>(CommentArticleEntity)


    var comment by CommentArticleEntity.comment
    var article by ArticleDao referencedOn CommentArticleEntity.article_id
    var author  by CommentArticleEntity.author

}