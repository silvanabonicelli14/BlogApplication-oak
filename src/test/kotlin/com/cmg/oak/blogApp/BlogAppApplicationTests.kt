package com.cmg.oak.blogApp

import com.cmg.oak.blogApp.domain.model.Article
import com.cmg.oak.blogApp.domain.model.ArticleComment
import com.cmg.oak.blogApp.doors.outbound.daos.ArticleCommentDao
import com.cmg.oak.blogApp.doors.outbound.daos.ArticlesDao
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.kotest.matchers.shouldBe
import org.jetbrains.exposed.sql.transactions.transaction
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

@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
	classes = [BlogAppApplication::class])
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BlogAppApplicationTests(
	@Autowired @Qualifier("exposed") private val articlesDao: ArticlesDao,
	@Autowired @Qualifier("exposedComment") private val commentArticlesDao: ArticleCommentDao,
	@Autowired private val mockMvc: MockMvc) {

	private val mapper = jacksonObjectMapper()
	val comments = listOf<ArticleComment>(
		ArticleComment(1, "Silvana", "comment of the article x", 1),
		ArticleComment(2, "Andrea", "comment of the article x", 1)
	)

	val articles = listOf(
		Article(1, "title x", "body of the article x", comments),
		Article(2, "title y", "body of the article y",mutableListOf<ArticleComment>()))

	fun withExpected(test: (List<Article>) -> Unit){
		transaction{
			commentArticlesDao.reset()
			articlesDao.reset()
			articles
				.map(articlesDao::save)

			comments.map(commentArticlesDao::save)
		}
		articles.let(test)
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
			content = mapper.writeValueAsString(Article(0, "z", "Body of z",mutableListOf<ArticleComment>()))
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

	@Test
	fun `can get one article and his comments`() = withExpected { expectedArticles ->
		val expected = expectedArticles.first()

		mockMvc.get("/api/articles/comments/${expected.id}")
			.andExpect {
				status { isOk() }
				content { contentType(MediaType.APPLICATION_JSON) }
				content { json(mapper.writeValueAsString(expected)) }
			}

	}

}

