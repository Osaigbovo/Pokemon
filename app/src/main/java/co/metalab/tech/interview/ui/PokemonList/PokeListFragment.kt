package co.metalab.tech.interview.ui.PokemonList

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.metalab.tech.interview.PokeApplication
import co.metalab.tech.interview.R
import co.metalab.tech.interview.ui.MainViewModel
import co.metalab.tech.interview.utils.ViewModelFactory
import co.metalab.tech.interview.utils.hideKeyboard
import kotlinx.android.synthetic.main.fragment_list.*
import javax.inject.Inject

class PokeListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    // Obtain the ViewModel - use the activity as the lifecycle owner
    private val sharedHomeViewModel: MainViewModel by activityViewModels { viewModelFactory }

    private lateinit var pokeAdapter: PokeAdapter
    private lateinit var searchView: SearchView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        PokeApplication.appComponent(requireActivity()).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenu()

        pokeList.layoutManager = GridLayoutManager(context, 3)
        getPokemon()
    }

    private fun setupMenu() {
        toolbar.inflateMenu(R.menu.menu_pokelist)

        searchView = toolbar.menu.findItem(R.id.action_search).actionView as SearchView
        searchView.queryHint = "Search by id or name"
        searchView.maxWidth = Integer.MAX_VALUE
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // filter recycler view when query submitted
                pokeAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                // filter recycler view when text is changed
                pokeAdapter.filter.filter(query)
                return false
            }
        })

        toolbar.menu.findItem(R.id.action_search).setOnMenuItemClickListener {
            return@setOnMenuItemClickListener true
        }
    }

    private fun getPokemon() {
        errorLoadingText.visibility = View.GONE

        sharedHomeViewModel.state.observe(viewLifecycleOwner, onChanged = {
            when {
                it.isLoading -> {
                    bindLoading(it.isLoading)
                }
                it.pokemons!!.isNotEmpty() -> {
                    progressBar.visibility = View.GONE
                    errorLoadingText.visibility = View.GONE
                    pokeList.visibility = View.VISIBLE
                    pokeAdapter = PokeAdapter(it.pokemons)
                    pokeList.adapter = pokeAdapter

                    pokeAdapter.onItemClickListener = { pokemon ->
                        findNavController()
                            .navigate(PokeListFragmentDirections
                                .actionPokeListFragmentToPokeDetailFragment(pokemon.id))
                    }

                    pokeAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
                        override fun onChanged() {
                            super.onChanged()
                            if (pokeAdapter.getItemCount() <= 0) {
                                noResult.setVisibility(View.VISIBLE)
                                noResult.text = getString(R.string.no_result, searchView.query)
                            } else noResult.setVisibility(View.GONE)
                        }
                    })

                    sharedHomeViewModel.pokemonsCache(it.pokemons)
                }
                it.error != null -> {
                    progressBar.visibility = View.GONE
                    pokeList.visibility = View.GONE
                    bindError()
                }
            }
        })
    }

    private fun bindLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun bindError() {
        errorLoadingText.visibility = View.VISIBLE
        errorLoadingText.setOnClickListener {
            Log.e("ERROR", "GO")
            errorLoadingText.visibility = View.GONE
            sharedHomeViewModel.retry()
            getPokemon()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideKeyboard(requireActivity())
    }
}