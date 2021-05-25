package com.cmg.oak.blogApp.doors.outbound.entities.exposed

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object CommentArticleEntity : IntIdTable("Blog.articlecomments") {

    val author: Column<String> = varchar("author", 150)
    val comment: Column<String> = varchar("comment", 2000)
    val article_id: Column<EntityID<Int>> = reference("sequel_id", ArticleEntity.id).uniqueIndex()
}

class CommentArticleDao(id: EntityID<Int>) : IntEntity(id){
    companion object : IntEntityClass<CommentArticleDao>(CommentArticleEntity)

    var author by CommentArticleEntity.author
    var comment by CommentArticleEntity.comment
    var article_id by CommentArticleEntity.article_id
}