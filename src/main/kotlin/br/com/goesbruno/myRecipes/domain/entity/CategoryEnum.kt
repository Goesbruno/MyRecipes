package br.com.goesbruno.myRecipes.domain.entity

enum class CategoryEnum(val value: Int, val description: String) {

    Breakfast(0, "Café da manhã"),
    Lunch(1, "Almoço"),
    Dessert(2, "Sobremesa"),
    Snack(3, "Lanche"),
    Dinner(4, "Jantar");

    companion object {
        fun fromInt(value: Int) = entries.first{it.value == value}
    }

}