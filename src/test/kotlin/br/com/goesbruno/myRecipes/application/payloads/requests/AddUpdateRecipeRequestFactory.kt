package br.com.goesbruno.myRecipes.application.payloads.requests

class AddUpdateRecipeRequestFactory {

    fun create() = AddUpdateRecipeRequest(
    name = "Lasanha",
    category = 1,
    preparationTime = "120",
    preparationMode = "Cozinhe a massa da lasanha em aproximadamente em 2 litros de água por 5 minutos." +
            "Em uma panela cozinhe a carne moída, depois de cozida coloque molho de tomate, o sal e temperos a gosto." +
            "Comece montando com uma camada de molho, a massa da lasanha, o presunto e o queijo" +
            "Aqueça o forno a 180º C durante 5 minutos." +
            "Coloque a lasanha no forno de 20 a 30 minutos.",
    ingredients = listOf(
        IngredientRequest(
            name = "massa de lasanha (pronta)",
            quantity = "1"
        ),
        IngredientRequest(
            name = "queijo mussarela",
            quantity = "500g"
        ),
        IngredientRequest(
            name = "massa de tomate pronta",
            quantity = "1"
        ),
    )
)

    fun update() = AddUpdateRecipeRequest(
        name = "Lasanha",
        category = 4,
        preparationTime = "60",
        preparationMode = "Cozinhe a massa da lasanha em aproximadamente em 2 litros de água por 5 minutos." +
                "Em uma panela cozinhe a carne moída, depois de cozida coloque molho de tomate, o sal e temperos a gosto." +
                "Comece montando com uma camada de molho, a massa da lasanha, o presunto e o queijo" +
                "Aqueça o forno a 180º C durante 5 minutos." +
                "Coloque a lasanha no forno de 20 a 30 minutos.",
        ingredients = listOf(
            IngredientRequest(
                name = "massa de lasanha (pronta)",
                quantity = "1"
            ),
            IngredientRequest(
                name = "queijo mussarela",
                quantity = "600g"
            ),
            IngredientRequest(
                name = "massa de tomate pronta",
                quantity = "1"
            ),
            IngredientRequest(
                name = "Carne moida",
                quantity = "400g"
            ),
        )
    )
}