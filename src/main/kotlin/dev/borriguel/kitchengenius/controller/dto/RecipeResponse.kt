package dev.borriguel.kitchengenius.controller.dto

import dev.borriguel.kitchengenius.enum.Category

data class RecipeResponse(
    val name: String,
    val time: Int,
    val category: Category,
    val ingredients: List<IngredientResponse>,
    val instruction: String
)
