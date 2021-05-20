package com.cmg.oak.blogApp.doors.outbound.repositories

import com.cmg.oak.blogApp.doors.outbound.entities.ArticleEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ArticlesRepository: JpaRepository<ArticleEntity, Int>