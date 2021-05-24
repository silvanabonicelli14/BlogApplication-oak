package com.cmg.oak.blogApp

import org.jetbrains.exposed.sql.Database
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationStartingEvent
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationListener
import org.springframework.context.support.beans
import javax.sql.DataSource

@SpringBootApplication
class BlogAppApplication

fun main(args: Array<String>) {
	runApplication<BlogAppApplication>(*args){
		addInitializers(
			beans {
				bean {
					val dataSource: DataSource = ref()
					Database.connect(dataSource)
				}
			}
		)
	}
}
