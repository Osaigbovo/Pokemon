package co.metalab.tech.interview.ui.PokemonDetail

import co.metalab.tech.interview.data.Evolution
import co.metalab.tech.interview.data.Result

/**
 * UI Model for [PokeDetailFragment].
 */
data class EvolutionViewState(
    var isLoading: Boolean,
    val error: Result.Error?,
    val evolutions: List<Evolution>?
)