package com.example.club

import UsuarioDB
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.club.tools.toolsVal

class RegForm : AppCompatActivity() {
    var bbdd=BBDDusuario(this)
    val t = toolsVal()
    var usr = UsuarioDB()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        enableEdgeToEdge()
        setContentView(R.layout.activity_reg_form)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val username:String = intent.extras?.getString("username").orEmpty()
        val password:String = intent.extras?.getString("password").orEmpty()
        Log.i("Modulo1","desdePaginaAnterior = ${username}, ${password}")

        var nombreApellidoInput: EditText = findViewById<AppCompatEditText>(R.id.inputnombre)
//        nombreApellidoInput.onFocusChangeListener = View.OnFocusChangeListener {
//                view, hasFocus -> if (hasFocus) { nombreApellidoInput.hint = "" }
//        }
        var dniInput: EditText = findViewById<AppCompatEditText>(R.id.inputDni)
//        dniInput.onFocusChangeListener = View.OnFocusChangeListener {
//                view, hasFocus -> if (hasFocus) { dniInput.hint = "" }
//        }
        var emailInput: EditText = findViewById<AppCompatEditText>(R.id.inputEmail)
//        emailInput.onFocusChangeListener = View.OnFocusChangeListener {
//                view, hasFocus -> if (hasFocus) { emailInput.hint = "" }
//        }
        var chkTeammateFitness = findViewById<CheckBox>(R.id.chkCategoria)
        val chkAsociado = findViewById<CheckBox>(R.id.chkAsociarse)
        chkTeammateFitness.setOnClickListener{
            if (chkTeammateFitness.isChecked){
                chkAsociado.visibility = View.INVISIBLE
                chkAsociado.isChecked = false
            } else {
                chkAsociado.visibility = View.VISIBLE
            }
        }

        var btnRegistrarFn = findViewById<AppCompatButton>(R.id.btnRegistrarFn)
        btnRegistrarFn.setOnClickListener {
            val nombreApellidoText:String = nombreApellidoInput.text.toString()
            val dniText:String = dniInput.text.toString()
            val emailText:String = emailInput.text.toString()
            val asociado:Boolean = chkAsociado.isChecked()
            var categoria:String= ""
            if (chkTeammateFitness.isChecked){
                categoria = "e" // empleado
            } else {
                categoria = "c" // cliente
            }
            if (bbdd.existeEmail(emailText)) {
                Toast.makeText(this, "Ya existe una cuenta con ese mail, solo se permite una.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            var validInputs = validarRegNomDniEmlInputs(nombreApellidoText,dniText,emailText)
            if (!validInputs.first){
                Toast.makeText(this, validInputs.second, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            Log.i("CRUD","ANTES DE CREAR")
            var res = crearDatos(username, password, nombreApellidoText, dniText, emailText, asociado, categoria)
            Log.i("CRUD","Nuevo Usuario = username: ${res.toString()}")

            var usr:UsuarioDB? = bbdd.leerUnDato(username)
            usr?.let {
                intent = Intent(this, MainActivity::class.java)
//                intent.putExtra("username", username)
//                intent.putExtra("nombreApellido", nombreApellidoText)
                startActivity(intent)
            } ?: run {
                Toast.makeText(this, "Hubo un error al intentar registrar el usuario en la base de datos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun crearDatos(username:String, password:String, nombreApellido:String, dni:String, email:String, asociado:Boolean, categoria:String):Boolean{
        var usr = UsuarioDB(username,password,nombreApellido, dni, email, asociado, categoria)
        var res = bbdd.insertar(usr)
        Log.i("CRUD", "[[crearDatos]] ${res.toString()}")
        return res
    }

    /*fun leerUnDato(username:String):UsuarioDB{
        var bbdd=BBDD(this)
        var res:UsuarioDB = bbdd.leerUno(username)
        Log.i("modulo1",res.toString())
        return res
    }*/

    fun validarRegNomDniEmlInputs(nombreApellido: String,dni: String,email: String):Pair<Boolean,String>{
        if (nombreApellido.isEmpty() || dni.isEmpty() || email.isEmpty()){
            Log.i("Modulo1","Debe completar todos los campos")
            Toast.makeText(this, "Debe completar todos los campos", Toast.LENGTH_SHORT).show()
            return Pair<Boolean,String>(false,"Debe completar todos los campos")
        }
        if (!t.valid_not_start_num(nombreApellido)){
            Toast.makeText(this, "nombre y apellido no puede empezar con un numero", Toast.LENGTH_SHORT).show()
            return Pair<Boolean,String>(false,"nombre y apellido no puede empezar con un numero")
        }
        if (!t.valid_len_six(nombreApellido)){
            Toast.makeText(this, "nombre y apellido debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show()
            return Pair<Boolean,String>(false,"nombre y apellido debe tener al menos 6 caracteres")
        }
        if (!t.valid_len_six(dni)) {
            Toast.makeText(this, "Dni debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show()
            return Pair<Boolean,String>(false,"Dni debe tener al menos 6 caracteres")
        }
        if (!t.valid_have_Arrob_Dot(email)) {
            Toast.makeText(this, "Hay un error en el email. Email incorrecto", Toast.LENGTH_SHORT).show()
            return Pair<Boolean,String>(false,"Hay un error en el email. Email incorrecto")
        }
        if (!t.valid_not_start_num(email)){
            Toast.makeText(this, "email no puede empezar con un numero", Toast.LENGTH_SHORT).show()
            return Pair<Boolean,String>(false,"email no puede empezar con un numero")
        }
        if (!t.valid_have_no_whitespace(email)){
            Toast.makeText(this, "email no puede contener espacios", Toast.LENGTH_SHORT).show()
            return Pair<Boolean,String>(false,"email no puede contener espacios")
        }
        return Pair<Boolean,String>(true,"username y password validados OK")
    }
}