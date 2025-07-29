package br.com.goesbruno.myRecipes.domain.model

import br.com.goesbruno.myRecipes.domain.entity.CategoryEnum
import br.com.goesbruno.myRecipes.domain.entity.Ingredient
import br.com.goesbruno.myRecipes.domain.entity.Recipe

class RecipesFactory {

    fun create(
        userId: String,
        recipeId: String,
        recipesFactoryFake: RecipesFactoryFake
    ) = when (recipesFactoryFake) {
        RecipesFactoryFake.Ana -> {
            Recipe(
                id = recipeId,
                name = "Lasanha",
                userId = userId,
                category = CategoryEnum.Lunch,
                preparationTime = "120",
                preparationMode = "Cozinhe a massa da lasanha em aproximadamente em 2 litros de água por 5 minutos." +
                        "Em uma panela cozinhe a carne moída, depois de cozida coloque molho de tomate, o sal e temperos a gosto." +
                        "Comece montando com uma camada de molho, a massa da lasanha, o presunto e o queijo" +
                        "Aqueça o forno a 180º C durante 5 minutos." +
                        "Coloque a lasanha no forno de 20 a 30 minutos.",

                ingredients = listOf(
                    Ingredient(
                        name = "massa de lasanha (pronta)",
                        quantity = "1",
                        recipeId = recipeId
                    ),
                    Ingredient(
                        name = "queijo mussarela",
                        quantity = "500g",
                        recipeId = recipeId
                    ),
                    Ingredient(
                        name = "massa de tomate pronta",
                        quantity = "1",
                        recipeId = recipeId
                    )
                )
            )
        }

        RecipesFactoryFake.Alex -> {
            Recipe(
                id = recipeId,
                name = "Strogonoff de frango",
                userId = userId,
                category = CategoryEnum.Dinner,
                preparationTime = "60",
                preparationMode = "Em uma panela, misture o frango, o alho, a maionese, o sal e a pimenta." +
                        "Em uma frigideira grande, derreta a manteiga e doure a cebola." +
                        "Junte o frango temperado até que esteja dourado." +
                        "Adicione os cogumelos, o ketchup e a mostarda." +
                        "Sirva com arroz branco e batata palha.",

                ingredients = listOf(
                    Ingredient(
                        name = "peitos de frango cortados em cubos",
                        quantity = "3",
                        recipeId = recipeId
                    ),
                    Ingredient(
                        name = "cebola picada",
                        quantity = "1",
                        recipeId = recipeId
                    ),
                    Ingredient(
                        name = "dente de alho picado",
                        quantity = "1",
                        recipeId = recipeId
                    )
                )
            )
        }
    }

    sealed class RecipesFactoryFake {
        data object Ana : RecipesFactoryFake()
        data object Alex : RecipesFactoryFake()
    }
}