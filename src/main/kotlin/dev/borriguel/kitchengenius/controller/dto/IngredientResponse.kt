package dev.borriguel.kitchengenius.controller.dto

import dev.borriguel.kitchengenius.enum.UnitMeasure

data class IngredientResponse(
    val name: String,
    val unitMeasure: UnitMeasure,
    val quantity: Double
)
