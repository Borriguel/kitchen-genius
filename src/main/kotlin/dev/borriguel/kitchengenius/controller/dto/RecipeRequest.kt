package dev.borriguel.kitchengenius.controller.dto

import dev.borriguel.kitchengenius.enum.Category
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class RecipeRequest(
    @field:NotBlank(message = "Recipe name can't be null or blank") val name: String,
    @field:Min(value = 1, message = "Time minimum value is 1 minute") val time: Int,
    val category: Category,
    @field:Size(max = 700, message = "Instruction must have min 10 and max 700 characters") val instruction: String
)
