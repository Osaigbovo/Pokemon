package co.metalab.tech.interview.ui.PokemonDetail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import co.metalab.tech.interview.PokeApplication
import co.metalab.tech.interview.R
import co.metalab.tech.interview.ui.MainViewModel
import co.metalab.tech.interview.utils.ViewModelFactory
import co.metalab.tech.interview.utils.prettyPrintId
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_detail.pokeList
import kotlinx.android.synthetic.main.fragment_detail.progressBar
import kotlinx.android.synthetic.main.fragment_detail.toolbar
import javax.inject.Inject

class PokeDetailFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    // Obtain the ViewModel - use the activity as the lifecycle owner
    private val sharedHomeViewModel: MainViewModel by activityViewModels { viewModelFactory }

    private val args: PokeDetailFragmentArgs by navArgs()

    private val pokeAdapter: PokeEvolutionAdapter by lazy { PokeEvolutionAdapter(requireContext()) }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        PokeApplication
            .appComponent(requireActivity())
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.setupWithNavController(findNavController())

        pokeList.layoutManager = LinearLayoutManager(context)
        pokeList.adapter = pokeAdapter

        pokeAdapter.onItemClickListener = { pokemon ->
            findNavController()
                .navigate(PokeDetailFragmentDirections.actionPokeDetailFragmentToPokeDetailFragment(pokemon.id))
        }

        val selectedPokemon = sharedHomeViewModel.pokemonsCache.value?.get(args.pokeId)
        val identifier = selectedPokemon?.identifier?.capitalize()

        Glide.with(this).load(selectedPokemon?.image_url).into(pokePhoto)
        pokeNumber.text = selectedPokemon?.id.prettyPrintId()
        pokeName.text = identifier
        selectedPokemon?.types?.forEach { type ->
            val chip = Chip(context).apply {
                text = type.identifier.capitalize()
                setTextColor(ContextCompat.getColor(context, R.color.white))
                setChipBackgroundColorResource(type.getColor())
            }

            pokeTypesGroup.addView(chip)
        }
        getEvolutions(identifier)
    }

    private fun getEvolutions(identifier: String?) {

        sharedHomeViewModel.getEvolutions(args.pokeId)
        sharedHomeViewModel.evolutionState.observe(viewLifecycleOwner, onChanged = {
            when {
                it.isLoading -> {
                    progressBar.visibility = View.VISIBLE
                    noEvolutions.visibility = View.GONE
                    errorEvolutions.visibility = View.GONE
                }
                it.error == null && it.evolutions.isNullOrEmpty() -> {
                    pokeAdapter.items = emptyList()
                    errorEvolutions.visibility = View.GONE
                    progressBar.visibility = View.GONE
                    noEvolutions.visibility = View.VISIBLE
                    noEvolutions.text = getString(R.string.STRING_does_not_evolve, identifier)
                }
                it.error != null && it.evolutions.isNullOrEmpty() -> {
                    pokeAdapter.items = emptyList()
                    noEvolutions.visibility = View.GONE
                    progressBar.visibility = View.GONE
                    errorEvolutions.visibility = View.VISIBLE
                }
                it.error == null && it.evolutions!!.isNotEmpty() -> {
                    noEvolutions.visibility = View.GONE
                    errorEvolutions.visibility = View.GONE
                    progressBar.visibility = View.GONE
                    pokeAdapter.items = it.evolutions
                }
            }
        })
    }
}