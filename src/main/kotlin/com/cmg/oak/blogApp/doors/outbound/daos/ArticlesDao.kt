package com.cmg.oak.blogApp.doors.outbound.daos

import com.cmg.oak.blogApp.domain.model.Article

interface ArticlesDao {
    fun getAll(): List<Article>
    fun getOne(id: Int): Article?
    fun save(article: Article): Article
    fun reset(): Unit
}