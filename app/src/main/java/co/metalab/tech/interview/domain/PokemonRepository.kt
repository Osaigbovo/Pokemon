package co.metalab.tech.interview.domain

import co.metalab.tech.interview.data.Evolution
import co.metalab.tech.interview.data.Pokemon
import co.metalab.tech.interview.data.Result

interface PokemonRepository {
    suspend fun getPokemons(): Result<List<Pokemon>>
    suspend fun getEvolutions(pokeId: Int): Result<List<Evolution>>
}