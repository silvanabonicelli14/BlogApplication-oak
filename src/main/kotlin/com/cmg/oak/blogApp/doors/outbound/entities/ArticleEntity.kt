package com.cmg.oak.blogApp.doors.outbound.entities

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
open class ArticleEntity(
    @Id
    @GeneratedValue
    var id: Int,

    @Column
    val title: String,

    @Column
    val body: String,
)