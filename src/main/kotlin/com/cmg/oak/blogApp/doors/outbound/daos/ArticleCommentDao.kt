package com.cmg.oak.blogApp.doors.outbound.daos

import com.cmg.oak.blogApp.domain.model.ArticleComment

interface ArticleCommentDao {
    fun getAll(): List<ArticleComment>
    fun getOne(id: Int): ArticleComment?
    fun save(commentArticle: ArticleComment): ArticleComment
    fun reset(): Unit
}