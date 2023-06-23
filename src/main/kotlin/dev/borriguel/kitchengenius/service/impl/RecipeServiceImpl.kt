package dev.borriguel.kitchengenius.service.impl

import dev.borriguel.kitchengenius.controller.dto.RecipeRequest
import dev.borriguel.kitchengenius.entity.Recipe
import dev.borriguel.kitchengenius.enum.Category
import dev.borriguel.kitchengenius.exception.ResourceNotFoundException
import dev.borriguel.kitchengenius.repository.RecipeRepository
import dev.borriguel.kitchengenius.service.RecipeService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.Throws

@Service
class RecipeServiceImpl(private val repository: RecipeRepository) : RecipeService {
    @Transactional
    override fun saveRecipe(recipe: Recipe): Recipe = repository.save(recipe)

    @Transactional
    override fun updateRecipe(id: Long, recipeUpdated: RecipeRequest): Recipe {
        val recipeToUpdate = findRecipeById(id)
        recipeToUpdate.apply {
            name = recipeUpdated.name
            time = recipeUpdated.time
            category = recipeUpdated.category
            instruction = recipeUpdated.instruction
        }
        return saveRecipe(recipeToUpdate)
    }

    @Transactional(readOnly = true)
    override fun findAllRecipes(page: Pageable): Page<Recipe> = repository.findAll(page)

    @Transactional(readOnly = true)
    @Throws(ResourceNotFoundException::class)
    override fun findRecipeById(id: Long): Recipe = repository.findById(id).orElseThrow { ResourceNotFoundException("Not found Recipe with id -> $id") }

    @Transactional
    override fun deleteRecipeById(id: Long) {
        repository.deleteById(id)
    }

    @Transactional(readOnly = true)
    override fun findRecipesByCategory(category: Category, page: Pageable): Page<Recipe> = repository.findRecipeByCategory(category, page)
}