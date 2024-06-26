package com.example.club.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.club.R
import com.example.club.datos.ActividadDB

class ActViewHolder(val view: View) : ViewHolder(view) {

    var nombreActividad = view.findViewById<TextView>(R.id.tvActividad)
    val photo = view.findViewById<ImageView>(R.id.ivActividad)
    val contenedor = view.findViewById<ConstraintLayout>(R.id.constraintCard)


    fun render( actividad: ActividadDB) {
        nombreActividad.text = actividad.nombre

        var colorItem = colorAndPicture(view.context, actividad)[0]
        var fotoItem = colorAndPicture(view.context, actividad)[1]
        colorItem.also { contenedor.background = it }
        fotoItem.also { photo.background = it}

        itemView.setOnClickListener {
            Toast.makeText(view.context,"${actividad.nombre}: ${actividad.cupo} cupo/s libre.", Toast.LENGTH_SHORT).show()
        }
    }

    fun colorAndPicture(context: Context, actividad:ActividadDB): List<Drawable> {
        var codAct = actividad.cod_act
        var color:Drawable? = null
        var foto:Drawable? = null
        when (codAct) {
            2 -> color = ContextCompat.getDrawable(context, R.color.c_funcional)
            3 -> color = ContextCompat.getDrawable(context, R.color.c_yoga)
            4 -> color = ContextCompat.getDrawable(context, R.color.c_pilates)
            5 -> color = ContextCompat.getDrawable(context, R.color.c_spinning)
            6 -> color = ContextCompat.getDrawable(context, R.color.c_dance)
            7 -> color = ContextCompat.getDrawable(context, R.color.c_stretching)
            8 -> color = ContextCompat.getDrawable(context, R.color.c_boulder)
            9 -> color = ContextCompat.getDrawable(context, R.color.c_aparatos)
        }
        when (codAct) {
            2 -> foto = ContextCompat.getDrawable(context, R.drawable.funcional_image)
            3 -> foto = ContextCompat.getDrawable(context, R.drawable.yoga_image)
            4 -> foto = ContextCompat.getDrawable(context, R.drawable.pilates_image)
            5 -> foto = ContextCompat.getDrawable(context, R.drawable.spinning_image)
            6 -> foto = ContextCompat.getDrawable(context, R.drawable.dance_image)
            7 -> foto = ContextCompat.getDrawable(context, R.drawable.stretching_image)
            8 -> foto = ContextCompat.getDrawable(context, R.drawable.boulder_image)
            9 -> foto = ContextCompat.getDrawable(context, R.drawable.aparatos_image)
        }
        return  listOfNotNull(color, foto)
    }
}
