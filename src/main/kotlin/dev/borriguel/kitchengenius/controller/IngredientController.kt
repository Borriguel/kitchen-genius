package dev.borriguel.kitchengenius.controller

import dev.borriguel.kitchengenius.controller.dto.IngredientRequest
import dev.borriguel.kitchengenius.controller.dto.IngredientResponse
import dev.borriguel.kitchengenius.service.IngredientService
import dev.borriguel.kitchengenius.util.EntityConverter
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Ingredient Resource")
@RestController
@RequestMapping("/ingredient")
class IngredientController(private val service: IngredientService) {
    private val log: Logger = LoggerFactory.getLogger(RecipeController::class.java)

    @Operation(summary = "Create Ingredient and insert into a Recipe")
    @PostMapping("/{idRecipe}")
    fun addIngredient(
        @PathVariable idRecipe: Long,
        @RequestBody @Valid request: IngredientRequest
    ): ResponseEntity<IngredientResponse> {
        val ingredient = EntityConverter.convertRequestToIngredient(request)
        val ingredientSaved = service.addIngredientToRecipe(idRecipe, ingredient)
        log.info("$ingredientSaved")
        val ingredientResponse = EntityConverter.convertIngredientToIngredientResponse(ingredientSaved)
        return ResponseEntity.status(HttpStatus.CREATED).body(ingredientResponse)
    }

    @Operation(summary = "Get Ingredient by id")
    @GetMapping("/{id}")
    fun getIngredientById(@PathVariable id: Long): ResponseEntity<IngredientResponse> {
        val ingredient = service.findIngredientById(id)
        log.info("$ingredient")
        val ingredientResponse = EntityConverter.convertIngredientToIngredientResponse(ingredient)
        return ResponseEntity.status(HttpStatus.OK).body(ingredientResponse)
    }

    @Operation(summary = "Empty Ingredient list from a Recipe")
    @DeleteMapping("/empty/{idRecipe}")
    fun emptyIngredientList(@PathVariable idRecipe: Long): ResponseEntity<Unit> {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.emptyIngredientList(idRecipe))
    }

    @Operation(summary = "Delete a Ingredient by id from a Recipe")
    @DeleteMapping
    fun deleteIngredientById(@RequestParam idRecipe: Long, @RequestParam idIngredient: Long): ResponseEntity<Unit> {
        service.removeIngredient(idRecipe, idIngredient)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Unit)
    }
}