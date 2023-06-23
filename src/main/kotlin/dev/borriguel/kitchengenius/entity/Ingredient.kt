package dev.borriguel.kitchengenius.entity

import dev.borriguel.kitchengenius.enum.UnitMeasure
import jakarta.persistence.*

@Entity
data class Ingredient(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long?,
    @Column(nullable = false) var name: String,
    @Enumerated(EnumType.STRING) var unitMeasure: UnitMeasure,
    @Column(nullable = false) var quantity: Double
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Ingredient

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "Ingredient(id=$id, name='$name', unitMeasure='$unitMeasure', quantity=$quantity)"
    }
}
