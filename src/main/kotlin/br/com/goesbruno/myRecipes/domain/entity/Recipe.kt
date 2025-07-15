package br.com.goesbruno.myRecipes.domain.entity

import br.com.goesbruno.myRecipes.domain.extensions.formatInstantToBrazilian
import kotlinx.datetime.Clock
import org.bson.BsonType
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonRepresentation
import org.bson.types.ObjectId

data class Recipe(
    val name: String,
    val userId: String,
    val category: CategoryEnum,
    val preparationMode: String,
    val preparationTime: String,
    val ingredients: List<Ingredient> = listOf(),
    @BsonId
    @BsonRepresentation(BsonType.OBJECT_ID)
    override val id: String = ObjectId().toString(),
    override val createdAt: String = Clock.System.now().formatInstantToBrazilian()
): Basic()