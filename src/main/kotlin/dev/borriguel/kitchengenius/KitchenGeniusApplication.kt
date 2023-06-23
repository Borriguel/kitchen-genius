package dev.borriguel.kitchengenius

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@OpenAPIDefinition(
    info = Info(
        title = "Kitchen Genius Rest API Documentation",
        description = "Spring Boot Rest API Documentation",
        contact = Contact(
            name = "Rodolpho Henrique",
            email = "rodolpho.omedio@gmail.com",
            url = "https://github.com/Borriguel"
        )
    )
)
class KitchenGeniusApplication

fun main(args: Array<String>) {
    runApplication<KitchenGeniusApplication>(*args)
}
