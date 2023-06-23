package dev.borriguel.kitchengenius.repository

import dev.borriguel.kitchengenius.entity.Ingredient
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IngredientRepository: JpaRepository<Ingredient, Long>