package com.example.music_app

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etCorreo: EditText
    private lateinit var etPassword: EditText
    private lateinit var bntRegistrar: Button
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        // Inicializar vistas
        etNombre = findViewById(R.id.etNombre)
        etCorreo = findViewById(R.id.etCorreo)
        etPassword = findViewById(R.id.etPassword)
        bntRegistrar = findViewById(R.id.btnRegistrar) // <-- ¡Faltaba esta línea!

        dbHelper = DatabaseHelper(this)

        bntRegistrar.setOnClickListener {
            registrarUsuario()
        }
    }

    private fun registrarUsuario() {
        val nombre = etNombre.text.toString().trim()
        val correo = etCorreo.text.toString().trim()
        val pass = etPassword.text.toString().trim()

        if (nombre.isEmpty() || correo.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Complete los campos", Toast.LENGTH_LONG).show()
            return
        }

        val resultado = dbHelper.registrarUsuario(nombre, correo, pass)

        if (resultado) {
            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_LONG).show()
            finish() // Cierra la pantalla de registro para regresar al login
        } else {
            Toast.makeText(this, "Error al registrar (correo ya registrado)", Toast.LENGTH_SHORT).show()
        }
    }
}