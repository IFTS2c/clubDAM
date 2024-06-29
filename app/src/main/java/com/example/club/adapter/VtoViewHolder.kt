package com.example.club.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.club.Pagar
import com.example.club.R
import com.example.club.datos.ActividadDB
import com.example.club.datos.Deuda

class VtoViewHolder (val view: View) : RecyclerView.ViewHolder(view){
    var tvNombre = view.findViewById<TextView>(R.id.tvNombre)
    val tvVto = view.findViewById<TextView>(R.id.tvVto)
    val tvmonto = view.findViewById<TextView>(R.id.tvmonto)
    val tvDni = view.findViewById<TextView>(R.id.tvDni)
    //val ivEmail = view.findViewById<ImageView>(R.id.ivEmail)

    fun render(deuda: Deuda) {
        val id = deuda.userid
        tvNombre.text = deuda.nombreApellido
        tvVto.text = "Vto. ${deuda.fechaVto}"
        tvmonto.text = "$ ${deuda.deuda.toString()}0"
        tvDni.text = "Dni: ${deuda.dni}"


        itemView.setOnClickListener {
            dialogDeuda(view.context, deuda.userid, deuda.deuda)
        }
//        ivEmail.setOnClickListener {
//
//        }
    }

    fun dialogDeuda(context: Context, userId:Int, deuda:Double){
        val builder = AlertDialog.Builder(context)
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_deuda, null)
        builder.setView(view)
        val tvMontoDeuda = view.findViewById<TextView>(R.id.tvMontoDeuda)
        tvMontoDeuda.text = "Monto: $ ${deuda}0"
        val dialog = builder.create()
        dialog.show()

        var btnPagarDueda = view.findViewById<TextView>(R.id.btnPagarDeuda)
        btnPagarDueda.setOnClickListener {
            val intent = Intent(context, Pagar::class.java)
            intent.putExtra("userId",userId)
            intent.putExtra("codAct", 0)
            context.startActivity(intent)
            dialog.dismiss()
        }
    }

}