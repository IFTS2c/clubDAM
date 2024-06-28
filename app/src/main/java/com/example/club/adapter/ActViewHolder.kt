package com.example.club.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.club.Pagar
import com.example.club.R
import com.example.club.datos.ActividadDB

class ActViewHolder(val view: View, val userId: Int) : ViewHolder(view) {

    var nombreActividad = view.findViewById<TextView>(R.id.tvActividad)
    val photo = view.findViewById<ImageView>(R.id.ivActividad)
    val contenedor = view.findViewById<ConstraintLayout>(R.id.constraintCard)


    fun render( actividad: ActividadDB, userId:Int) {
        nombreActividad.text = actividad.nombre

        var colorItem = colorAndPicture(view.context, actividad)[0]
        var fotoItem = colorAndPicture(view.context, actividad)[1]
        colorItem.also { contenedor.background = it }
        fotoItem.also { photo.background = it}

        itemView.setOnClickListener {
            Toast.makeText(view.context,"${actividad.nombre}: ${actividad.cupo} cupo/s libre.", Toast.LENGTH_SHORT).show()
            DialogInputName(view.context, actividad, userId)
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

    fun DialogInputName(context: Context, actividad: ActividadDB, userId:Int) {
        val builder = AlertDialog.Builder(context, R.style.MiDialogoTema)
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.dialog_actividad_seleccionada, null)

        var imagen = view.findViewById<ImageView>(R.id.ivDialogActSelect)
        var titleBox = view.findViewById<TextView>(R.id.TitleActSelec)
        val btnAtras = view.findViewById<TextView>(R.id.btnAtras)
        val btnAceptar = view.findViewById<TextView>(R.id.btnAceptar)
        var dias = view.findViewById<TextView>(R.id.actDias)
        var horario = view.findViewById<TextView>(R.id.actHorario)
        var precioSocio = view.findViewById<TextView>(R.id.actPrecioSocio)
        var precioNoSOcio = view.findViewById<TextView>(R.id.actPrecioNoSocio)
        var cupo = view.findViewById<TextView>(R.id.actCupo)

        var colorItem = colorAndPicture(view.context, actividad)[0]
        var fotoItem = colorAndPicture(view.context, actividad)[1]
        colorItem.also { btnAtras.background = it }
        colorItem.also { btnAceptar.background = it }
        fotoItem.also {
            imagen.setImageDrawable(it)
            imagen.scaleType = ImageView.ScaleType.FIT_XY
        }
        imagen.setColorFilter(Color.argb(200,255,255,255), PorterDuff.Mode.SRC_ATOP)


        titleBox.text = actividad.nombre
        dias.text = actividad.dia
        horario.text = "${actividad.horario} hs."
        precioSocio.text = "Socios: $ ${actividad.precio_socio}0"
        precioNoSOcio.text = "No Socios: $ ${actividad.precio_no_socio}0"
        cupo.text = "Cupos libres: ${actividad.cupo} ud/s."

        val dialog = builder.setView(view).create()
        dialog.show()

        btnAceptar.setOnClickListener {
            var intent = Intent(context, Pagar::class.java)
            intent.putExtra("userId", userId)
            intent.putExtra("codAct", actividad.cod_act)
            context.startActivity(intent)
        }

        btnAtras.setOnClickListener {
            dialog.dismiss()
        }
    }
}
