package com.example.lavadomanos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bFormulario = findViewById<ImageButton>(R.id.botonFormulario)
        bFormulario.setOnClickListener{
            val intent = Intent(this, Formulario::class.java)
            startActivity(intent)
        }

        val bRecomendaciones = findViewById<ImageButton>(R.id.botonRecomendaciones)
        bRecomendaciones.setOnClickListener{
            val intent = Intent(this, Recomendaciones::class.java)
            startActivity(intent)
        }

        val bAcerca = findViewById<ImageButton>(R.id.botonAcerca)
        bAcerca.setOnClickListener{
            val intent = Intent(this, AcercaDe::class.java)
            startActivity(intent)
        }
    }

    }
