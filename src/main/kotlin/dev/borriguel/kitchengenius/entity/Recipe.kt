package dev.borriguel.kitchengenius.entity

import dev.borriguel.kitchengenius.enum.Category
import jakarta.persistence.*

@Entity
data class Recipe(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long?,
    @Column(nullable = false, length = 30) var name: String,
    @Column(nullable = false) var time: Int,
    @OneToMany(cascade = [CascadeType.ALL]) var ingredients: MutableList<Ingredient>,
    @Enumerated(EnumType.STRING) var category: Category,
    var instruction: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Recipe

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "Recipe(id=$id, name='$name', time=$time)"
    }

}
