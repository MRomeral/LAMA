package com.example.lavadomanos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.MediaStore
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.*
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat

class FM : AppCompatActivity() {
    private lateinit var bGrabar : ImageButton
    private lateinit var videoView : VideoView
    private lateinit var tContadorFM : TextView
    private lateinit var tTiempoFM : TextView//Tiempo de lavado de manos (PRUEBA) (OCULTO AL PRINCIPIO)
    private lateinit var bVolverFM : Button
    private lateinit var bSiguienteFM : Button//Botón oculto hasta que no se realice la limpieza
    private var ourRequestCode : Int = 123 //Cualquier número
    private var contador = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fm)

        tContadorFM = findViewById(R.id.tContadorFM)//Inicialmente el texto está oculto
        tTiempoFM = findViewById(R.id.tTiempoFM)//TEXTO DEL TIEMPO DE LAVADO (PRUEBA)

        videoView = findViewById(R.id.videoView)

        //Botones
        bGrabar = findViewById(R.id.botonGrabar)
        bVolverFM = findViewById(R.id.bVolverFM)
        bSiguienteFM = findViewById(R.id.bSiguienteFM)//Botón oculto
        bSiguienteFM.isEnabled = false//Botón deshabilitado
        bSiguienteFM.isClickable = false//Botón deshabilitado


        //Funcionalidad del botón de Grabar vídeo
        bGrabar.setOnClickListener{
            val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            if(intent.resolveActivity(packageManager) != null){
                startActivityForResult(intent,ourRequestCode)
            }
            object : CountDownTimer(40000,1000){
                override fun onTick(millisUntilFinished: Long) {
                    tContadorFM.alpha = 1.0f
                    //Hay que investigar la opción de que empiece en color rojo y cuando queden 10 segundos pasarlo a verde
                    bGrabar.setImageResource(R.mipmap.ic_parargrabar256)
                    tContadorFM.setText(""+millisUntilFinished/1000 + " segundos.")
                    contador++//Cada instante se le suma 1 segundo
                    if(contador > 29){

                    }
                }

                override fun onFinish() {
                    tContadorFM.setText("Hecho!")
                    tTiempoFM.setText(""+contador)
                    tTiempoFM.alpha = 1.0f//Se revela el texto del tiempo
                }

            }.start()
            bSiguienteFM.alpha = 1.0f//Activa el botón de siguiente que está oculto
            bSiguienteFM.isEnabled = true//Botón habilitado
            bSiguienteFM.isClickable = true//Botón habilitado
            Toast.makeText(this,"Se ha iniciado la grabación",Toast.LENGTH_SHORT)
        }

        //Funcionalidad del botón de Volver atrás
        bVolverFM.setOnClickListener{
            val intent = Intent(this,Grabar::class.java)
            startActivity(intent)
        }

        //Funcionalidad del botón siguiente
        bSiguienteFM.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        //Botones de controlar vídeo
        val mediaCollection = MediaController(this)
        mediaCollection.setAnchorView(videoView)
        videoView.setMediaController(mediaCollection)

    }

    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == ourRequestCode && resultCode == RESULT_OK){
            //Obtiene datos de la URI
            val videoURI = data?.data
            videoView.setVideoURI(videoURI)
            videoView.start()
        }
    }
}