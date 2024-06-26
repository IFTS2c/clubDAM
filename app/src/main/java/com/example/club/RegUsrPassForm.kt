package com.example.club

import UsuarioDB
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.club.tools.toolsVal

class RegUsrPassForm : AppCompatActivity() {

    var bbdd=BBDDusuario(this)
    var t = toolsVal()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_reg_usr_pass_form)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btnSiguiente = findViewById<AppCompatButton>(R.id.btnSiguiente)

        var usernameInput: EditText = findViewById<AppCompatEditText>(R.id.inputUser)
//        usernameInput.onFocusChangeListener = View.OnFocusChangeListener {
//                view, hasFocus -> if (hasFocus) { usernameInput.hint = "" }
//        }
        var pass1Input: EditText = findViewById<AppCompatEditText>(R.id.inputPass1)
//        pass1Input.onFocusChangeListener = View.OnFocusChangeListener {
//                view, hasFocus -> if (hasFocus) { pass1Input.hint = "" }
//        }
        var pass2Input: EditText = findViewById<AppCompatEditText>(R.id.inputPass2)
//        pass2Input.onFocusChangeListener = View.OnFocusChangeListener {
//                view, hasFocus -> if (hasFocus) { pass2Input.hint = "" }
//        }

        btnSiguiente.setOnClickListener{
            val username:String = usernameInput.text.toString()
            val pass1:String = pass1Input.text.toString()
            val pass2:String = pass2Input.text.toString()


            if (!validarRegUsrPassInputs(username,pass1,pass2).first) {
                Toast.makeText(this, validarRegUsrPassInputs(username,pass1,pass2).second, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (bbdd.existeUsrName(username)) {
                Toast.makeText(this, "Ese usuario ya existe", Toast.LENGTH_SHORT).show()
                usernameInput.text.clear()
                pass1Input.text.clear()
                pass2Input.text.clear()
                usernameInput.requestFocus()
                return@setOnClickListener
            }

            if (pass1 != pass2) {
                Log.i("Modulo1", "No coinciden ambas password")
                Toast.makeText(this, "No coinciden ambas password", Toast.LENGTH_SHORT).show()
                pass1Input.text.clear()
                pass2Input.text.clear()
                pass1Input.requestFocus()
                return@setOnClickListener
            } else {
                usernameInput.text.clear()
                pass1Input.text.clear()
                pass2Input.text.clear()
                val intent = Intent(this, RegForm::class.java)
                intent.putExtra("username", username)
                intent.putExtra("password", pass1)
                startActivity(intent)
            }
        }
    }
    /*fun leerUnDato(username:String):UsuarioDB{
        var bbdd=BBDD(this)
        var res:UsuarioDB = bbdd.leerUno(username)
        if (res == null){
            Log.i("modulo1","Elemento no encontrado")
            return res
        }
        Log.i("modulo1",res.toString())
        return res
    }*/

    fun validarRegUsrPassInputs(
        username: String,
        pass1: String,
        pass2: String
    ): Pair<Boolean, String> {
        if (username.isEmpty() || pass1.isEmpty() || pass2.isEmpty()) {
            Log.i("Modulo1", "Debe completar todos los campos")
            Toast.makeText(this, "Debe completar todos los campos", Toast.LENGTH_SHORT).show()
            return Pair<Boolean, String>(false, "Debe completar todos los campos")
        }
        if (!t.valid_not_start_num(username)) {
            Toast.makeText(this, "Username no puede empezar con un numero", Toast.LENGTH_SHORT)
                .show()
            return Pair<Boolean, String>(false, "Username no puede empezar con un numero")
        }
        if (!t.valid_have_no_whitespace(username)) {
            Toast.makeText(this, "Username no puede contener espacios", Toast.LENGTH_SHORT).show()
            return Pair<Boolean, String>(false, "Username no puede contener espacios")
        }
        if (!t.valid_len_six(username)) {
            Toast.makeText(this, "Username debe tener al menos 6 caracteres", Toast.LENGTH_SHORT)
                .show()
            return Pair<Boolean, String>(false, "Username debe tener al menos 6 caracteres")
        }
        if (!t.valid_have_no_whitespace(pass1) || !t.valid_have_no_whitespace(pass2)) {
            Toast.makeText(this, "Password no puede contener espacios", Toast.LENGTH_SHORT).show()
            return Pair<Boolean, String>(false, "Password no puede contener espacios")
        }
        if (!t.valid_len_six(pass1) || !t.valid_len_six(pass2)) {
            Toast.makeText(this, "Password debe tener al menos 6 caracteres", Toast.LENGTH_SHORT)
                .show()
            return Pair<Boolean, String>(false, "Password debe tener al menos 6 caracteres")
        }
        return Pair<Boolean, String>(true, "username y password validados OK")
    }
}