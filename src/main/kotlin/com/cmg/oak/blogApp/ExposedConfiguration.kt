package com.cmg.oak.blogApp

import org.jetbrains.exposed.sql.Database
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringBootConfiguration
import org.springframework.context.annotation.Bean
import javax.sql.DataSource

@SpringBootConfiguration
class ExposedConfiguration(
    @Autowired private val dataSource: DataSource) {

    @Bean
    fun db() = Database.connect(dataSource)

}