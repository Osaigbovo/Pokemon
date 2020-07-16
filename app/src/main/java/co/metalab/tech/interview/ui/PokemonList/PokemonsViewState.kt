package co.metalab.tech.interview.ui.PokemonList

import co.metalab.tech.interview.data.Result
import co.metalab.tech.interview.data.Pokemon

/**
 * UI Model for [PokeListFragment].
 */
data class PokemonsViewState(
    var isLoading: Boolean,
    val error: Result.Error?,
    val pokemons: List<Pokemon>?
)
