package co.metalab.tech.interview.ui

import co.metalab.tech.interview.data.Result
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.metalab.tech.interview.data.Pokemon
import co.metalab.tech.interview.domain.LoadEvolutionUseCase
import co.metalab.tech.interview.domain.LoadPokemonUseCase
import co.metalab.tech.interview.ui.PokemonDetail.EvolutionViewState
import co.metalab.tech.interview.ui.PokemonList.PokemonsViewState
import co.metalab.tech.interview.utils.CoroutinesDispatcherProvider
import co.metalab.tech.interview.utils.getPokemonCache
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val loadPokemonUseCase: LoadPokemonUseCase,
    private val loadEvolutionUseCase: LoadEvolutionUseCase,
    private val dispatcherProvider: CoroutinesDispatcherProvider
) : ViewModel() {

    var pokemonsCache: MutableLiveData<Map<Int, Pokemon>> = MutableLiveData()

    private val _state = MutableLiveData<PokemonsViewState>()
    val state: LiveData<PokemonsViewState> get() = _state

    private val _evolutionState = MutableLiveData<EvolutionViewState>()
    val evolutionState: LiveData<EvolutionViewState> get() = _evolutionState

    init {
        getPokemons()
    }

    private fun getPokemons(): Job {
        // Set a default loading state
        _state.value?.isLoading = true
        return viewModelScope.launch(dispatcherProvider.computation) {
            when (val result = loadPokemonUseCase()) {
                is Result.Success -> withContext(dispatcherProvider.main) {
                    _state.value = PokemonsViewState(isLoading = false, error = null, pokemons = result.data)
                }
                is Result.Error -> withContext(dispatcherProvider.main) {
                    _state.value = PokemonsViewState(isLoading = false, error = result, pokemons = emptyList())
                }
            }
        }
    }

    fun pokemonsCache(pokemons: List<Pokemon>) {
        pokemonsCache.value = pokemons.associateBy { it.id }
        getPokemonCache(pokemons.associateBy { it.id })
    }

    fun retry() {
        getPokemons()
    }

    private fun getEvolutionsNow(pokeid: Int): Job {
        // Set a default loading state
        _evolutionState.value?.isLoading = true
        return viewModelScope.launch(dispatcherProvider.computation) {
            val result = loadEvolutionUseCase.invoke(pokeid)
            withContext(dispatcherProvider.main) {
                when (result) {
                    is Result.Success -> _evolutionState.value = EvolutionViewState(false, null, result.data)
                    is Result.Error -> _evolutionState.value = EvolutionViewState(false, result, emptyList())
                }
            }
        }
    }

    fun getEvolutions(pokeId: Int) {
        getEvolutionsNow(pokeId)
    }

}