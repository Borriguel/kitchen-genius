package dev.borriguel.kitchengenius.util

import dev.borriguel.kitchengenius.controller.dto.IngredientRequest
import dev.borriguel.kitchengenius.controller.dto.IngredientResponse
import dev.borriguel.kitchengenius.controller.dto.RecipeRequest
import dev.borriguel.kitchengenius.controller.dto.RecipeResponse
import dev.borriguel.kitchengenius.entity.Ingredient
import dev.borriguel.kitchengenius.entity.Recipe
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.stereotype.Component

@Component
class EntityConverter {
    companion object {
        fun convertRecipeToRecipeResponse(recipe: Recipe): RecipeResponse {
            val ingredientsResponse = recipe.ingredients.map { convertIngredientToIngredientResponse(it) }
            return RecipeResponse(recipe.name, recipe.time, recipe.category, ingredientsResponse, recipe.instruction)
        }

        fun convertRequestToRecipe(request: RecipeRequest): Recipe {
            return Recipe(null, request.name, request.time, mutableListOf(), request.category, request.instruction)
        }

        fun convertIngredientToIngredientResponse(ingredient: Ingredient): IngredientResponse {
            return IngredientResponse(ingredient.name, ingredient.unitMeasure, ingredient.quantity)
        }

        fun convertRecipePagedToResponsePaged(recipe: Page<Recipe>): Page<RecipeResponse> {
            val recipeResponseList = recipe.content.map { convertRecipeToRecipeResponse(it) }
            return PageImpl(recipeResponseList, recipe.pageable, recipe.totalElements)
        }

        fun convertRequestToIngredient(request: IngredientRequest): Ingredient {
            return Ingredient(null, request.name, request.unitMeasure, request.quantity)
        }
    }
}