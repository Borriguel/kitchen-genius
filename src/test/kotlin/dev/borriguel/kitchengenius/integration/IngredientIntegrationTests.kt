package dev.borriguel.kitchengenius.integration

import dev.borriguel.kitchengenius.controller.RecipeController
import dev.borriguel.kitchengenius.controller.dto.IngredientRequest
import dev.borriguel.kitchengenius.controller.dto.IngredientResponse
import dev.borriguel.kitchengenius.controller.dto.RecipeRequest
import dev.borriguel.kitchengenius.controller.dto.RecipeResponse
import dev.borriguel.kitchengenius.enum.Category
import dev.borriguel.kitchengenius.enum.UnitMeasure
import dev.borriguel.kitchengenius.exception.ResourceNotFoundException
import dev.borriguel.kitchengenius.exception.ValidationErrorMessage
import org.junit.jupiter.api.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.exchange
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class IngredientIntegrationTests {
    private val log: Logger = LoggerFactory.getLogger(RecipeController::class.java)

    @LocalServerPort
    private var port: Int = 0
    private val localHost = "http://localhost:"

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @BeforeAll
    fun setup() {
        val recipeRequest = RecipeRequest("test", 5, Category.OTHER, "test")
        val urlRecipe = "$localHost$port/recipe"
        restTemplate.postForEntity(urlRecipe, recipeRequest, RecipeResponse::class.java)
        val ingredientRequest = IngredientRequest("test", UnitMeasure.UNIT, 2.0)
        val urlIngredient = "$localHost$port/ingredient/1"
        restTemplate.postForEntity(urlIngredient, ingredientRequest, IngredientResponse::class.java)
    }

    @Order(1)
    @Test
    fun `should create ingredient and add to recipe when successful`() {
        val request = IngredientRequest("test post", UnitMeasure.L, 1.0)
        val url = "$localHost$port/ingredient/1"
        val response = restTemplate.postForEntity(url, request, IngredientResponse::class.java)
        log.info("$response")
        Assertions.assertNotNull(response.body)
        Assertions.assertEquals(HttpStatus.CREATED, response.statusCode)
        Assertions.assertEquals(request.name, response.body?.name)
        Assertions.assertEquals(request.quantity, response.body?.quantity)
        Assertions.assertEquals(request.unitMeasure, response.body?.unitMeasure)
    }

    @Order(2)
    @Test
    fun `should throw ResourceNotFoundException when recipe id invalid`() {
        val request = IngredientRequest("water", UnitMeasure.L, 1.0)
        val url = "$localHost$port/ingredient/50"
        val response = restTemplate.postForEntity(url, request, ResourceNotFoundException::class.java)
        log.info("$response")
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    @Order(3)
    @Test
    fun `should throw MethodArgumentNotValidException when ingredient request not valid`() {
        val request = IngredientRequest("", UnitMeasure.L, 0.0)
        val url = "$localHost$port/ingredient/1"
        val response = restTemplate.postForEntity(url, request, ValidationErrorMessage::class.java)
        log.info("$response")
        log.info("${response.body}")
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)

    }

    @Order(4)
    @Test
    fun `should find ingredient by id when successful`() {
        val url = "$localHost$port/ingredient/1"
        val response = restTemplate.getForEntity(url, IngredientResponse::class.java)
        log.info("$response")
        Assertions.assertNotNull(response.body)
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
    }

    @Order(5)
    @Test
    fun `should delete ingredient by id when successful`() {
        val url = "$localHost$port/ingredient?idRecipe=1&idIngredient=1"
        val response: ResponseEntity<Unit> =
            restTemplate.exchange(url, HttpMethod.DELETE, null, Unit)
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
    }

    @Order(6)
    @Test
    fun `should empty ingredient list from recipe when successful`() {
        val url = "$localHost$port/ingredient/empty/1"
        val response: ResponseEntity<Unit> =
            restTemplate.exchange(url, HttpMethod.DELETE, null, Unit)
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
    }

}
