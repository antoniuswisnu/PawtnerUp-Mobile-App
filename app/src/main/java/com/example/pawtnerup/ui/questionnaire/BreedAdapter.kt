package com.example.pawtnerup.ui.questionnaire

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pawtnerup.R
import com.example.pawtnerup.data.model.BreedModel

class BreedAdapter(private val items: MutableList<BreedModel>,
                   private var onDeleteClick: (Int) -> Unit = {}) : RecyclerView.Adapter<BreedAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemName: TextView = itemView.findViewById(R.id.tvBreedName)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteBreed)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.itemName.text = item.name
        holder.deleteButton.setOnClickListener {
            onDeleteClick.invoke(position)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BreedAdapter.ViewHolder {
        val view = View.inflate(parent.context, R.layout.item_breed, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}