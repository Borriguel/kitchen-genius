package dev.borriguel.kitchengenius.exception

import java.time.Instant

data class ValidationErrorMessage(
    val statusCode: Int,
    val timeStamp: Instant,
    val messages: MutableList<String>,
    val description: String
)
