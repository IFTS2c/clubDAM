package com.example.club.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.club.R
import com.example.club.datos.Deuda

class VtoAdapter(private val listaVto:List<Deuda>) : RecyclerView.Adapter<VtoViewHolder> () {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VtoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return VtoViewHolder(layoutInflater.inflate(R.layout.item_vencimiento, parent, false))
    }

    override fun getItemCount(): Int = listaVto.size

    override fun onBindViewHolder(holder: VtoViewHolder, position: Int) {
        val item = listaVto[position]
        holder.render(item)
    }


}