package co.metalab.tech.interview.domain

import co.metalab.tech.interview.data.Result
import co.metalab.tech.interview.data.Pokemon
import javax.inject.Inject

open class LoadPokemonUseCase @Inject constructor(private val pokemonRepository: PokemonRepository) {

    suspend operator fun invoke(): Result<List<Pokemon>> {
        return when (val result = pokemonRepository.getPokemons()) {
            is Result.Success -> {
                val pokemons = result.data
                Result.Success(pokemons)
            }
            is Result.Error -> {
                result
            }
        }
    }
}