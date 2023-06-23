package dev.borriguel.kitchengenius.repository

import dev.borriguel.kitchengenius.entity.Recipe
import dev.borriguel.kitchengenius.enum.Category
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RecipeRepository: JpaRepository<Recipe, Long> {
    fun findRecipeByCategory(category: Category, page: Pageable): Page<Recipe>
}