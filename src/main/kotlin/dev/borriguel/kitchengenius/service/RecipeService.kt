package dev.borriguel.kitchengenius.service

import dev.borriguel.kitchengenius.controller.dto.RecipeRequest
import dev.borriguel.kitchengenius.entity.Recipe
import dev.borriguel.kitchengenius.enum.Category
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface RecipeService {
    fun saveRecipe(recipe: Recipe): Recipe
    fun updateRecipe(id: Long, recipeUpdated: RecipeRequest): Recipe
    fun findAllRecipes(page: Pageable): Page<Recipe>
    fun findRecipeById(id: Long): Recipe
    fun deleteRecipeById(id: Long)
    fun findRecipesByCategory(category: Category, page: Pageable): Page<Recipe>
}