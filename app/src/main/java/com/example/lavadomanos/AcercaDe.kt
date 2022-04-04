package com.example.lavadomanos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class AcercaDe : AppCompatActivity() {
    lateinit var bVolver : Button
    lateinit var bPrueba : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acerca_de)

        //Botones
        bVolver = findViewById(R.id.botonVolverAcerca)
        bPrueba = findViewById(R.id.bPrueba)

        bVolver.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        bPrueba.setOnClickListener{
            val intent = Intent(this, Prueba::class.java)
            startActivity(intent)
        }
    }
}