package dev.borriguel.kitchengenius.service

import dev.borriguel.kitchengenius.controller.dto.IngredientRequest
import dev.borriguel.kitchengenius.entity.Ingredient

interface IngredientService {
    fun addIngredientToRecipe(idRecipe: Long, ingredient: Ingredient): Ingredient
    fun removeIngredient(idRecipe: Long, idIngredient: Long)
    fun emptyIngredientList(idRecipe: Long)
    fun updateIngredient(idRecipe: Long, idIngredient: Long, ingredientUpdated: IngredientRequest): Ingredient
    fun findIngredientById(id: Long): Ingredient
}