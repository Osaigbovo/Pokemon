package co.metalab.tech.interview.ui.PokemonDetail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.metalab.tech.interview.data.Evolution
import co.metalab.tech.interview.data.Pokemon
import co.metalab.tech.interview.R
import co.metalab.tech.interview.utils.pokemonsCache
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.poke_evolution_item.view.*

class PokeEvolutionAdapter(private val context: Context) : RecyclerView.Adapter<PokeEvolutionVH>() {

    var onItemClickListener: ((Pokemon) -> Unit)? = null

    var items: List<Evolution> = emptyList()
        set(items) {
            field = items
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeEvolutionVH {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.poke_evolution_item, parent, false)
        return PokeEvolutionVH(itemView)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: PokeEvolutionVH, position: Int) {
        holder.bind(context, items[position], onItemClickListener)
    }
}

class PokeEvolutionVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(context: Context, evolution: Evolution, onItemClickListener: ((Pokemon) -> Unit)?) = with(itemView) {
        val pokemon: Pokemon? = when {
            evolution.evolves_from != null -> pokemonsCache[evolution.evolves_from]
            evolution.evolves_into != null -> pokemonsCache[evolution.evolves_into]
            else -> null
        }

        evolutionTitle.text = when {
            evolution.evolves_from != null -> context.getString(R.string.evolves_from)
            evolution.evolves_into != null -> context.getString(R.string.evolves_into)
            else -> null
        }

        val name = pokemon?.identifier?.capitalize() ?: ""
        val description = "$name ${evolution.trigger}"
        evolutionDescription.text = description

        Glide.with(this)
            .load(pokemon?.image_url)
            .into(evolutionImage)

        if (pokemon != null) {
            setOnClickListener { onItemClickListener?.invoke(pokemon) }
        } else {
            setOnClickListener(null)
        }
    }
}
