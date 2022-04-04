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

        //Botones
        val bFormulario = findViewById<ImageButton>(R.id.botonFormulario)
        val bRecomendaciones = findViewById<ImageButton>(R.id.botonRecomendaciones)
        val bAcerca = findViewById<ImageButton>(R.id.botonAcerca)
        val bAyuda = findViewById<ImageButton>(R.id.botonAyuda)

        //Botón que lleva al Formulario para realizar una grabación
        bFormulario.setOnClickListener{
            val intent = Intent(this, Formulario::class.java)
            startActivity(intent)
        }

        //Botón que lleva a las recomendaciones y protocolos
        bRecomendaciones.setOnClickListener{
            val intent = Intent(this, Recomendaciones::class.java)
            startActivity(intent)
        }

        //Botón que lleva al "Acerca de"
        bAcerca.setOnClickListener{
            val intent = Intent(this, AcercaDe::class.java)
            startActivity(intent)
        }

        //Botón que lleva a la ayuda
        bAyuda.setOnClickListener{
            val intent = Intent(this,Ayuda::class.java)
            startActivity(intent)
        }
    }

    }
