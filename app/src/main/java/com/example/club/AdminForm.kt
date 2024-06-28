package com.example.club

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.club.tools.toolsVal

class AdminForm : AppCompatActivity() {

    val bdUsr = BBDDusuario(this)
    val t = toolsVal()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_admin_form)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val adminname:String = intent.extras?.getString("username").orEmpty()
        val nombreApellido:String = intent.extras?.getString("nombreApellido").orEmpty()
        val userSelected = bdUsr.leerUnDato(adminname)
        val userNameText = findViewById<TextView>(R.id.userName)
        val categoriaUser = findViewById<TextView>(R.id.categoria)

        categoriaUser.text = "Administrador"

        userNameText.text = "Bienvenido " + nombreApellido

        val btnActividad = findViewById<TextView>(R.id.btnActividad)
        val btnPagar = findViewById<TextView>(R.id.btnPagar)
        val btnCarnet = findViewById<TextView>(R.id.btnCarnet)
        val btnRegistrar = findViewById<TextView>(R.id.btnRegistrar)
        val btnListaVtos = findViewById<TextView>(R.id.btnListaVtos)


        btnActividad.setOnClickListener{
            DialogInputName("actividades",userSelected.categoria)
        }

        btnPagar.setOnClickListener{
            DialogInputName("pagar",userSelected.categoria)
        }

        btnCarnet.setOnClickListener{
            DialogInputName("carnet",userSelected.categoria)
//            var username = ""
//            val builder = AlertDialog.Builder(this)
//            val view = layoutInflater.inflate(R.layout.dialog_solicitud_username, null)
//            builder.setView(view)
//                .setPositiveButton("Aceptar") { dialog, _ ->
//                    username = view.findViewById<EditText>(R.id.inputUsername).text.toString()
//                    var userSelected = bdUsr.leerUnDato(username)
//                    if (userSelected.id == 0){
//                        Toast.makeText(this,"No se encontro el usuario", Toast.LENGTH_SHORT).show()
//                        Log.i("DIALOG", userSelected.id.toString())
//                    } else {
//                        Log.i("DIALOG", userSelected.id.toString())
//                        val intent = Intent(this,Carnet::class.java)
//                        intent.putExtra("username",username)
//                        startActivity(intent)
//                    }
//
//                }
//                .setNegativeButton("Cancelar", null)
//                .show()
//            Log.i("DIALOG", "username: ${username}")

        }

        btnRegistrar.setOnClickListener{
            val intent = Intent(this, RegUsrPassForm::class.java)
            startActivity(intent)
        }

        btnListaVtos.setOnClickListener{

        }


    }
    fun DialogInputName(act : String, categoria:String) {
        var username = ""
        val builder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.dialog_solicitud_username, null)
        builder.setView(view)
            .setPositiveButton("Aceptar") { dialog, _ ->
                username = view.findViewById<EditText>(R.id.inputUsername).text.toString()
                var userSelected = bdUsr.leerUnDato(username)
                if (userSelected.id == 0){
                    Toast.makeText(this,"No se encontro el usuario", Toast.LENGTH_SHORT).show()
                    Log.i("DIALOG", userSelected.id.toString())
                } else {
                    Log.i("DIALOG", userSelected.id.toString())
//                    var intent = Intent()
//                    when (act){
//                        "pagar" -> intent = Intent(this,Pagar::class.java)
//                        "carnet" -> intent = Intent(this,Carnet::class.java)
//                    }
                    val intent: Intent = when (act) {
                        "actividades" -> Intent(this, Actividades::class.java)
                        "pagar" -> Intent(this, Pagar::class.java)
                        "carnet" -> Intent(this, Carnet::class.java)
                        else  -> Intent(this, UserForm::class.java)

                    }
                    intent.putExtra("username",username)
                    //intent.putExtra("categoria",categoria)
                    startActivity(intent)
                }

            }
            .setNegativeButton("Cancelar", null)
            .show()
        Log.i("DIALOG", "username: ${username}")
    }
}