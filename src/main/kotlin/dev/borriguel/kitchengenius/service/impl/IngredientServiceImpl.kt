package dev.borriguel.kitchengenius.service.impl

import dev.borriguel.kitchengenius.controller.dto.IngredientRequest
import dev.borriguel.kitchengenius.entity.Ingredient
import dev.borriguel.kitchengenius.exception.ResourceNotFoundException
import dev.borriguel.kitchengenius.repository.IngredientRepository
import dev.borriguel.kitchengenius.service.IngredientService
import dev.borriguel.kitchengenius.service.RecipeService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.Throws

@Service
class IngredientServiceImpl(private val repository: IngredientRepository, private val recipeService: RecipeService) :
    IngredientService {

    @Transactional
    override fun addIngredientToRecipe(idRecipe: Long, ingredient: Ingredient): Ingredient {
        val recipe = recipeService.findRecipeById(idRecipe)
        recipe.ingredients.add(ingredient)
        val ingredientSaved = repository.save(ingredient)
        recipeService.saveRecipe(recipe)
        return ingredientSaved
    }

    @Transactional
    override fun removeIngredient(idRecipe: Long, idIngredient: Long) {
        val recipe = recipeService.findRecipeById(idRecipe)
        val ingredient = findIngredientById(idIngredient)
        recipe.ingredients.remove(ingredient)
        recipeService.saveRecipe(recipe)
        repository.delete(ingredient)
    }

    @Transactional
    override fun emptyIngredientList(idRecipe: Long) {
        val recipe = recipeService.findRecipeById(idRecipe)
        val ingredients = recipe.ingredients.toMutableList()
        recipe.ingredients.clear()
        recipeService.saveRecipe(recipe)
        repository.deleteAll(ingredients)
    }

    @Transactional
    override fun updateIngredient(
        idRecipe: Long,
        idIngredient: Long,
        ingredientUpdated: IngredientRequest
    ): Ingredient {
        val recipe = recipeService.findRecipeById(idRecipe)
        val ingredientToUpdate = findIngredientById(idIngredient)
        if (recipe.ingredients.contains(ingredientToUpdate)) {
            ingredientToUpdate.apply {
                name = ingredientUpdated.name
                unitMeasure = ingredientUpdated.unitMeasure
                quantity = ingredientUpdated.quantity
            }
        }
        return repository.save(ingredientToUpdate)
    }

    @Transactional(readOnly = true)
    @Throws(ResourceNotFoundException::class)
    override fun findIngredientById(id: Long): Ingredient =
        repository.findById(id).orElseThrow { ResourceNotFoundException("Not found Ingredient with id -> $id") }
}