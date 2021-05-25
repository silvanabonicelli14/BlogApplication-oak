package com.cmg.oak.blogApp.doors.outbound.entities.exposed

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
    val article_id: Column<EntityID<Int>> = reference("article_id", ArticleEntity.id).uniqueIndex()
}

class CommentArticleDao(id: EntityID<Int>) : IntEntity(id){
    companion object : IntEntityClass<CommentArticleDao>(CommentArticleEntity)


    var comment by CommentArticleEntity.comment
    var article_id by CommentArticleEntity.article_id
    var author  by CommentArticleEntity.author

}