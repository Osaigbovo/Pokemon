package co.metalab.tech.interview.data

import androidx.annotation.ColorRes
import co.metalab.tech.interview.R
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeAPIService {

    @GET("/pokemon")
    suspend fun getPokemons(): Response<List<Pokemon>>

    @GET("/pokemon/{id}/evolutions")
    suspend fun getEvolutions(@Path("id") id: Int): Response<List<Evolution>>

    companion object {
        const val ENDPOINT = "https://hiring-test-api.herokuapp.com"
    }
}

data class Pokemon(
    val id: Int,
    val identifier: String,
    val image_url: String,
    val types: List<Type>
)

data class Type(val id: Int, val identifier: String) {
    @ColorRes
    fun getColor(): Int = when (identifier) {
        "normal" -> R.color.colorNormal
        "fire" -> R.color.colorFire
        "water" -> R.color.colorWater
        "electric" -> R.color.colorElectric
        "grass" -> R.color.colorGrass
        "ice" -> R.color.colorIce
        "fighting" -> R.color.colorFighting
        "poison" -> R.color.colorPoison
        "ground" -> R.color.colorGround
        "flying" -> R.color.colorFlying
        "psychic" -> R.color.colorPsychic
        "bug" -> R.color.colorBug
        "rock" -> R.color.colorRock
        "ghost" -> R.color.colorGhost
        "dragon" -> R.color.colorDragon
        "dark" -> R.color.colorDark
        "steel" -> R.color.colorSteel
        "fairy" -> R.color.colorFairy
        else -> R.color.colorNormal
    }
}

data class Evolution(
    val evolves_from: Int?,
    val evolves_into: Int?,
    val trigger: String
)