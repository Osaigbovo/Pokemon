package co.metalab.tech.interview.ui.PokemonList

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import co.metalab.tech.interview.R
import co.metalab.tech.interview.data.Pokemon
import co.metalab.tech.interview.utils.prettyPrintId
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.poke_list_item.view.*
import java.util.*
import kotlin.collections.ArrayList

class PokeAdapter(pokemons: List<Pokemon>)
    : RecyclerView.Adapter<PokeVH>(), Filterable {

    var onItemClickListener: ((Pokemon) -> Unit)? = null

    private var pokemonList: ArrayList<Pokemon> = ArrayList()
    private var pokemonListAll = ArrayList<Pokemon>()

    init {
        this.pokemonList.addAll(pokemons)
        this.pokemonListAll.addAll(pokemons)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeVH {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.poke_list_item, parent, false)
        return PokeVH(itemView)
    }

    override fun getItemCount(): Int = pokemonList.size

    override fun onBindViewHolder(holder: PokeVH, position: Int) {
        holder.bind(pokemonList.get(position), onItemClickListener)
    }

    override fun getFilter(): Filter {
        return myFilter
    }

    private var myFilter: Filter = object : Filter() {
        // automatic on background thread
        override fun performFiltering(charSequence: CharSequence): FilterResults {
            val filteredList: MutableList<Pokemon> = ArrayList()
            if (charSequence.length == 0) {
                filteredList.addAll(pokemonListAll)
            } else {
                for (pokemon in pokemonListAll) {
                    if (pokemon
                            .identifier
                            .toLowerCase(Locale.getDefault())
                            .contains(charSequence.toString().toLowerCase(Locale.getDefault()))
                        ||
                        pokemon.id.toString().contains(charSequence.toString())) {
                        filteredList.add(pokemon)
                    }
                }
            }
            val filterResults = FilterResults()
            filterResults.values = filteredList
            return filterResults
        }

        // automatic on UI thread
        override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
            pokemonList.clear()
            pokemonList.addAll(filterResults.values as Collection<Pokemon>)
            notifyDataSetChanged()
        }
    }
}

class PokeVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    @SuppressLint("DefaultLocale")
    fun bind(pokemon: Pokemon, onItemClickListener: ((Pokemon) -> Unit)?) = with(itemView) {
        pokeNumber.text = pokemon.id.prettyPrintId()
        pokeName.text = pokemon.identifier.capitalize()
        setOnClickListener { onItemClickListener?.invoke(pokemon) }

        Glide.with(this)
            .load(pokemon.image_url)
            .into(pokePhoto)
    }
}
