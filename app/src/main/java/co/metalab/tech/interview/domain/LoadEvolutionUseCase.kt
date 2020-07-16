package co.metalab.tech.interview.domain

import co.metalab.tech.interview.data.Evolution
import co.metalab.tech.interview.data.Result
import javax.inject.Inject

open class LoadEvolutionUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {

    suspend operator fun invoke(pokeId: Int): Result<List<Evolution>> {
        val result = pokemonRepository.getEvolutions(pokeId)
        return when (result) {
            is Result.Success -> {
                val evolutions = result.data
                Result.Success(evolutions)
            }
            is Result.Error -> {
                result
            }
        }
    }
}