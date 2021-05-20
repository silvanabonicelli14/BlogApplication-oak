package com.cmg.oak.blogApp.doors.outbound.repositories

import com.cmg.oak.blogApp.domain.model.Article

interface ArticlesRepository {
    fun getAll(): List<Article>
    fun getOne(id: Int): Article?
    fun save(article: Article): Article
}