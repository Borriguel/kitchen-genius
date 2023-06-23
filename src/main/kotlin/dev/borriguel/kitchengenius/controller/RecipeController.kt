package dev.borriguel.kitchengenius.controller

import dev.borriguel.kitchengenius.controller.dto.RecipeRequest
import dev.borriguel.kitchengenius.controller.dto.RecipeResponse
import dev.borriguel.kitchengenius.entity.Recipe
import dev.borriguel.kitchengenius.enum.Category
import dev.borriguel.kitchengenius.service.RecipeService
import dev.borriguel.kitchengenius.util.EntityConverter
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Recipe Resource")
@RestController
@RequestMapping("/recipe")
class RecipeController(private val service: RecipeService) {
    private val log: Logger = LoggerFactory.getLogger(RecipeController::class.java)

    @Operation(
        summary = "Create Recipe",
        description = "Categories for recipe = OTHER, DRINK, PASTA, SEAFOOD, CANDY, SALTED, FAST_FOOD, SOUP, BREAKFAST, SNACK, DESSERT, SALAD, VEGETARIAN, FITNESS, REGIONAL, ESPECIAL, SANDWICH, CARBOHYDRATE, MEAT"
    )
    @PostMapping
    fun saveRecipe(@Valid @RequestBody request: RecipeRequest): ResponseEntity<RecipeResponse> {
        val recipe = EntityConverter.convertRequestToRecipe(request)
        val recipeSaved: Recipe = service.saveRecipe(recipe)
        log.info("$recipeSaved")
        val recipeResponse: RecipeResponse = EntityConverter.convertRecipeToRecipeResponse(recipeSaved)
        return ResponseEntity.status(HttpStatus.CREATED).body(recipeResponse)
    }

    @Operation(
        summary = "Update Recipe",
        description = "Categories for recipe = OTHER, DRINK, PASTA, SEAFOOD, CANDY, SALTED, FAST_FOOD, SOUP, BREAKFAST, SNACK, DESSERT, SALAD, VEGETARIAN, FITNESS, REGIONAL, ESPECIAL, SANDWICH, CARBOHYDRATE, MEAT"
    )
    @PutMapping("/{id}")
    fun updateRecipe(
        @PathVariable id: Long,
        @Valid @RequestBody recipeRequest: RecipeRequest
    ): ResponseEntity<RecipeResponse> {
        val recipe = service.updateRecipe(id, recipeRequest)
        log.info("Recipe with id -> $id updated")
        val recipeResponse = EntityConverter.convertRecipeToRecipeResponse(recipe)
        return ResponseEntity.status(HttpStatus.OK).body(recipeResponse)
    }

    @Operation(summary = "Get Recipe by id")
    @GetMapping("/{id}")
    fun getRecipeById(@PathVariable id: Long): ResponseEntity<RecipeResponse> {
        val recipe = service.findRecipeById(id)
        log.info("$recipe")
        val recipeResponse = EntityConverter.convertRecipeToRecipeResponse(recipe)
        return ResponseEntity.status(HttpStatus.OK).body(recipeResponse)
    }

    @Operation(summary = "Get Recipes by category")
    @GetMapping("/category/{category}")
    fun getRecipesByCategory(
        @PathVariable category: Category,
        @ParameterObject page: Pageable
    ): ResponseEntity<Page<RecipeResponse>> {
        val recipes = service.findRecipesByCategory(category, page)
        val recipesResponse = EntityConverter.convertRecipePagedToResponsePaged(recipes)
        return ResponseEntity.status(HttpStatus.OK).body(recipesResponse)
    }

    @Operation(summary = "Get All Recipe")
    @GetMapping
    fun getAllRecipes(@ParameterObject page: Pageable): ResponseEntity<Page<RecipeResponse>> {
        val recipes = service.findAllRecipes(page)
        val recipeResponse = EntityConverter.convertRecipePagedToResponsePaged(recipes)
        return ResponseEntity.status(HttpStatus.OK).body(recipeResponse)
    }

    @Operation(summary = "Delete Recipe by id")
    @DeleteMapping("/{id}")
    fun deleteRecipe(@PathVariable id: Long): ResponseEntity<Unit> {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Unit)
    }
}