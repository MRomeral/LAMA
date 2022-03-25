package com.example.lavadomanos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Recomendaciones : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recomendaciones)
        //Botones
        val bVolver = findViewById<Button>(R.id.botonVolverRecom)
        val bFM = findViewById<Button>(R.id.bFM)
        val bLM = findViewById<Button>(R.id.bLM)

        //Botón volver a la pantalla de inicio
        bVolver.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        //Botón con la recomendación de FM
        bFM.setOnClickListener{
            val intent = Intent(this, recomFM::class.java)
            startActivity(intent)
        }

        //Botón con la recomendación de LM
        bLM.setOnClickListener{
            val intent = Intent(this, recomLM::class.java)
            startActivity(intent)
        }
    }
}