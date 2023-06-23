package dev.borriguel.kitchengenius.integration

import dev.borriguel.kitchengenius.controller.RecipeController
import dev.borriguel.kitchengenius.controller.dto.RecipeRequest
import dev.borriguel.kitchengenius.controller.dto.RecipeResponse
import dev.borriguel.kitchengenius.enum.Category
import dev.borriguel.kitchengenius.exception.ResourceNotFoundException
import dev.borriguel.kitchengenius.exception.ValidationErrorMessage
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.exchange
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RecipeIntegrationTests {
    private val log: Logger = LoggerFactory.getLogger(RecipeController::class.java)

    @LocalServerPort
    private var port: Int = 0
    private val localHost = "http://localhost:"

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @BeforeAll
    fun setup() {
        val request = RecipeRequest("test", 5, Category.OTHER, "test")
        val url = "$localHost$port/recipe"
        restTemplate.postForEntity(url, request, RecipeResponse::class.java)
    }

    @Test
    fun `should create a recipe when successful`() {
        val request = RecipeRequest(
            "test 2",
            12,
            Category.FITNESS,
            "test create recipe"
        )
        val url = "$localHost$port/recipe"
        val response = restTemplate.postForEntity(url, request, RecipeResponse::class.java)
        log.info("$response")
        Assertions.assertNotNull(response.body)
        Assertions.assertEquals(HttpStatus.CREATED, response.statusCode)
        Assertions.assertEquals(request.name, response.body?.name)
        Assertions.assertEquals(request.time, response.body?.time)
        Assertions.assertEquals(request.category, response.body?.category)
        Assertions.assertEquals(request.instruction, response.body?.instruction)
    }

    @Test
    fun `should throw MethodArgumentNotValidException when recipe request fields are invalid`() {
        val request = RecipeRequest("", 0, Category.OTHER, "")
        val url = "$localHost$port/recipe"
        val exception = restTemplate.postForEntity(url, request, ValidationErrorMessage::class.java)
        log.info("$exception")
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.statusCode)
    }

    @Test
    fun `should update a recipe when successful`() {
        val request = RecipeRequest("update test", 10, Category.CANDY, "update test")
        val url = "$localHost$port/recipe/1"
        val entity = HttpEntity(request)
        val response: ResponseEntity<RecipeResponse> =
            restTemplate.exchange(url, HttpMethod.PUT, entity, RecipeResponse::class)
        log.info("$response")
        Assertions.assertNotNull(response.body)
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(request.name, response.body?.name)
        Assertions.assertEquals(request.time, response.body?.time)
        Assertions.assertEquals(request.category, response.body?.category)
        Assertions.assertEquals(request.instruction, response.body?.instruction)
    }

    @Test
    fun `should throw ResourceNotFound when update recipe id not exists`() {
        val request = RecipeRequest("update test", 10, Category.CANDY, "update test")
        val url = "$localHost$port/recipe/50"
        val entity = HttpEntity(request)
        val response: ResponseEntity<ResourceNotFoundException> =
            restTemplate.exchange(url, HttpMethod.PUT, entity, ResourceNotFoundException::class)
        log.info("$response")
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    @Test
    fun `should throw MethodArgumentNotValidException when update recipe request fields are invalid`() {
        val invalidRecipeRequest = RecipeRequest("", 0, Category.OTHER, "")
        val url = "$localHost$port/recipe/1"
        val entity = HttpEntity(invalidRecipeRequest)
        val response: ResponseEntity<ValidationErrorMessage> =
            restTemplate.exchange(url, HttpMethod.PUT, entity, ValidationErrorMessage::class)
        log.info("$response")
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
    }

    @Test
    fun `should find recipe by id when successful`() {
        val url = "$localHost$port/recipe/1"
        val response = restTemplate.getForEntity(url, RecipeResponse::class.java)
        Assertions.assertNotNull(response.body)
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        log.info("$response")
    }

    @Test
    fun `should throw ResourceNotFound when recipe id not exists`() {
        val url = "$localHost$port/recipe/50"
        val exception = restTemplate.getForEntity(url, ResourceNotFoundException::class.java)
        log.info("$exception")
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.statusCode)
    }

    @Test
    fun `should delete recipe by id when successful`() {
        val url = "$localHost$port/recipe/1"
        val response: ResponseEntity<Unit> =
            restTemplate.exchange(url, HttpMethod.DELETE, null, Unit)
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
    }

}