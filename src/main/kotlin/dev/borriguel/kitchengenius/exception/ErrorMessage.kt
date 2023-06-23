package dev.borriguel.kitchengenius.exception

import java.time.Instant

data class ErrorMessage(
    val statusCode: Int,
    val timeStamp: Instant,
    val message: String?,
    val description: String
)
