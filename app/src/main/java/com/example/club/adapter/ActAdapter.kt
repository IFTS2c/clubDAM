package com.example.club.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.club.R
import com.example.club.datos.ActividadDB

class ActAdapter(private val actividadesList:List<ActividadDB>, val userId:Int) : RecyclerView.Adapter<ActViewHolder> () {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ActViewHolder(layoutInflater.inflate(R.layout.item_actividad, parent, false), userId)
    }


    override fun onBindViewHolder(holder: ActViewHolder, position: Int) {
        val item = actividadesList[position]
        holder.render(item, userId)
    }

    override fun getItemCount(): Int = actividadesList.size

}