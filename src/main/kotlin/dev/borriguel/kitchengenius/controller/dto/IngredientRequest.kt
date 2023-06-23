package dev.borriguel.kitchengenius.controller.dto

import dev.borriguel.kitchengenius.enum.UnitMeasure
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

data class IngredientRequest(
    @field:NotBlank(message = "Ingredient name can't be null or blank") val name: String,
    val unitMeasure: UnitMeasure,
    @field:Min(value = 0, message = "Quantity minimum value is 0") val quantity: Double
)
