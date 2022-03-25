package com.example.lavadomanos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class recomFM : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recom_fm)

        //Botones
        val bVolverRecomFM = findViewById<Button>(R.id.bVolverRecomFM)
        bVolverRecomFM.setOnClickListener{
            val intent = Intent(this,Recomendaciones::class.java)
            startActivity(intent)
        }
    }
}