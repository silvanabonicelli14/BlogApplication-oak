package com.cmg.oak.blogApp.doors.outbound.entities

import javax.persistence.*

@Entity(name = "Blog.articles")
@Table(schema = "Blog", name = "articles")
open class ArticleEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Int,

    @Column
    open val title: String,

    @Column
    open val body: String
)