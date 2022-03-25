package com.example.lavadomanos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class recomLM : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recom_lm)

        //Botones
        val bVolverRecomLM = findViewById<Button>(R.id.bVolverRecomLM)
        bVolverRecomLM.setOnClickListener{
            val intent = Intent(this,Recomendaciones::class.java)
            startActivity(intent)
        }
    }
}