package com.cmg.oak.blogApp.domain.model

data class ArticleComment (
    val id: Int,
    val author: String,
    val comment: String,
    val article: Int
)

