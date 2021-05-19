package com.cmg.oak.blogApp

import com.cmg.oak.blogApp.domain.model.Article
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
	classes = [BlogAppApplication::class])
@AutoConfigureMockMvc
class BlogAppApplicationTests(
	@Autowired private val mockMvc: MockMvc) {

	private val mapper = jacksonObjectMapper()
	val articles = listOf(
		Article(1, "title x", "body of the article x"),
		Article(2, "title y", "body of the article y"))

	@Test
	fun `can get all articles`() {

		mockMvc.get("/api/articles")
			.andExpect {
				status { isOk() }
				content { contentType(MediaType.APPLICATION_JSON) }
				content { json(mapper.writeValueAsString(articles)) }
			}

	}

	@Test
	fun `can get one article`() {
		val id = 1
		val expected = articles.first { it.id == id }

		mockMvc.get("/api/articles/$id")
			.andExpect {
				status { isOk() }
				content { contentType(MediaType.APPLICATION_JSON) }
				content { json(mapper.writeValueAsString(expected)) }
			}

	}

	@Test
	fun `not found when article does not exist`() {
		mockMvc.get("/api/articles/999999")
			.andExpect {
				status { isNotFound() }
			}

	}

}

