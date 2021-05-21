package com.cmg.oak.blogApp

import com.cmg.oak.blogApp.domain.model.Article
import com.cmg.oak.blogApp.doors.outbound.daos.ArticlesDao
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.transaction.support.TransactionTemplate
import javax.persistence.EntityManager

@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
	classes = [BlogAppApplication::class])
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BlogAppApplicationTests(
	@Autowired val entityManager: EntityManager,
	@Autowired val transactionTemplate: TransactionTemplate,
	@Autowired @Qualifier("jpa") private val articlesDao: ArticlesDao,
	@Autowired private val mockMvc: MockMvc) {

	private val mapper = jacksonObjectMapper()
	val articles = listOf(
		Article(1, "title x", "body of the article x"),
		Article(2, "title y", "body of the article y"))

	fun withExpected(test: (List<Article>) -> Unit){
		articlesDao.reset()
		articles
			.map(articlesDao::save)
			.let(test)
	}

	@Test
	fun `can get all articles`() = withExpected { expectedArticles ->
		mockMvc.get("/api/articles")
			.andExpect {
				status { isOk() }
				content { contentType(MediaType.APPLICATION_JSON) }
				content { json(mapper.writeValueAsString(expectedArticles)) }
			}

	}

	@Test
	fun `can get one article`() = withExpected { expectedArticles ->
		val expected = expectedArticles.first()

		mockMvc.get("/api/articles/${expected.id}")
			.andExpect {
				status { isOk() }
				content { contentType(MediaType.APPLICATION_JSON) }
				content { json(mapper.writeValueAsString(expected)) }
			}

	}

	@Test
	fun `not found when article does not exist`() = withExpected {

		mockMvc.get("/api/articles/999999")
			.andExpect {
				status { isNotFound() }
			}

	}

	@Test
	fun `can save a new article`()  = withExpected {

		mockMvc.post("/api/articles"){
			contentType = MediaType.APPLICATION_JSON
			content = mapper.writeValueAsString(Article(0, "z", "Body of z"))
			accept = MediaType.APPLICATION_JSON
		}
		.andExpect {
			status { isCreated() }
		}.andReturn()
		.let {
			val article: Article = mapper.readValue(it.response.contentAsString)

			val location: String = it.response.getHeaderValue("location") as String
			location shouldBe "/api/articles/${article.id}"

			mockMvc.get(location)
				.andExpect { status { isOk() } }
		}

	}

}

