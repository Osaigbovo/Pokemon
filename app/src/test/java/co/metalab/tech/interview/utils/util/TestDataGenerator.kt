package co.metalab.tech.interview.utils.util

import co.metalab.tech.interview.data.Pokemon

class TestDataGenerator {

    companion object {
        @JvmStatic
        fun generatePokemons(): List<Pokemon> {
            val pokemon1 = Pokemon(
                id = 1,
                identifier = "Pokemon 1",
                image_url = "",
                types = emptyList()
            )
            val pokemon2 = Pokemon(
                id = 2,
                identifier = "Pokemon 2",
                image_url = "",
                types = emptyList()
            )
            val pokemon3 = Pokemon(
                id = 2,
                identifier = "Pokemon 3",
                image_url = "",
                types = emptyList()
            )
            return listOf(pokemon1, pokemon2, pokemon3)
        }
    }
}
