package com.tiago.desafio.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.tiago.desafio.R
import com.tiago.desafio.model.Pokemon
import kotlin.properties.Delegates

class PokeListRecyclerAdapter(private val getClick: (Pokemon) -> Unit) :
    RecyclerView.Adapter<PokeListRecyclerAdapter.ViewHolder>(), Filterable {

    var items: List<Pokemon> by Delegates.observable(emptyList()) { _, old, new ->
        if (old != new) notifyDataSetChanged()
    }

    var newsList: List<Pokemon> by Delegates.observable(emptyList()) { _, old, new ->
        if (old != new) notifyDataSetChanged()
    }

    var filterList = ArrayList<Pokemon>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.pokemon_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemon = items[position]
        holder.bind(pokemon, getClick)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(pokemon: Pokemon, getClick: (Pokemon) -> Unit) {
            val name = itemView.findViewById<TextView>(R.id.textNews)
            val click = itemView.findViewById<ConstraintLayout>(R.id.idClick)

            name.text = pokemon.name


            click.setOnClickListener {
                getClick(pokemon)
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                filterList = if (charSearch.isEmpty()) {
                    newsList as ArrayList<Pokemon>
                } else {
                    val resultList = ArrayList<Pokemon>()
                    for (row in newsList) {
                        if (row.name?.toLowerCase()
                                ?.contains(constraint.toString().toLowerCase())!!
                        ) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                items = results?.values as List<Pokemon>
                notifyDataSetChanged()
            }
        }
    }

}