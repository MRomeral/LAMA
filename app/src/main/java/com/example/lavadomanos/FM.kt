package com.example.lavadomanos


import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.example.lavadomanos.databinding.ActivityFmBinding
import com.example.lavadomanos.databinding.ActivityLmBinding
import java.io.File
import java.util.*
import java.util.concurrent.ExecutorService

class FM : AppCompatActivity(), LifecycleOwner {
    //Variables de los botones básicos
    private lateinit var bGrabarFM : ImageButton
    private lateinit var bVolverFM : Button
    private lateinit var bSiguienteFM : Button//Botón oculto hasta que no se realice la limpieza

    //Botones de acción
    lateinit var fm1a: ImageButton
    lateinit var fm1b: ImageButton
    lateinit var fm2: ImageButton
    lateinit var fm3: ImageButton
    lateinit var fm4: ImageButton
    lateinit var fm5: ImageButton
    lateinit var fm6: ImageButton
    lateinit var fm7: ImageButton
    lateinit var fm8: ImageButton

    //Variables de los textos
    private lateinit var tContadorFM : TextView
    private lateinit var tTiempoFM : TextView//Tiempo de lavado de manos (PRUEBA) (OCULTO AL PRINCIPIO)
    //Variables para el vídeo
    lateinit var binding: ActivityFmBinding
    private lateinit var outputDirectory : File
    private lateinit var cameraExecutor : ExecutorService
    private var imageCapture: ImageCapture?=null
    private var contador = 0

    //Hora para saber a que hora se inicia la labor
    private val c = Calendar.getInstance()
    private val hora = c.get(Calendar.HOUR)
    private val minuto = c.get(Calendar.MINUTE)
    private val segundo = c.get(Calendar.SECOND)
    private val aux = hora*3600 + minuto*60 + segundo
    private var horaComienzo = ""
    private var horaAux = 0
    private var horaPulsacion = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Textos sobreimpresionados
        //tContadorFM = findViewById(R.id.tContadorFM)//Inicialmente el texto está oculto
        //tTiempoFM = findViewById(R.id.tTiempoFM)//TEXTO DEL TIEMPO DE LAVADO (PRUEBA)

        //Permisos para la cámara
        if(allPermissionGranted()){
            startCamera()
        }else{
            ActivityCompat.requestPermissions(
                this, Constants.REQUIRED_PERMISSIONS,
                Constants.REQUEST_CODE_PERMISSIONS
            )
        }


        //Botones básicos
        bGrabarFM = findViewById(R.id.bGrabarFM)
        bVolverFM = findViewById(R.id.bVolverFM)
        bSiguienteFM = findViewById(R.id.bSiguienteFM)//Botón oculto
        bSiguienteFM.isEnabled = false//Botón deshabilitado
        bSiguienteFM.isClickable = false//Botón deshabilitado

        //Botones de acción
        fm1a = findViewById<ImageButton>(R.id.fm1a)
        fm1b = findViewById<ImageButton>(R.id.fm1b)
        fm2 = findViewById<ImageButton>(R.id.fm2)
        fm3 = findViewById<ImageButton>(R.id.fm3)
        fm4 = findViewById<ImageButton>(R.id.fm4)
        fm5 = findViewById<ImageButton>(R.id.fm5)
        fm6 = findViewById<ImageButton>(R.id.fm6)
        fm7 = findViewById<ImageButton>(R.id.fm7)
        fm8 = findViewById<ImageButton>(R.id.fm8)
        //Los botones de acción hasta que no se le da a iniciar el proceso están deshabilitados
        fm1a.isEnabled = false
        fm1a.isClickable = false
        fm1b.isEnabled = false
        fm1b.isClickable = false
        fm2.isEnabled = false
        fm2.isClickable = false
        fm3.isEnabled = false
        fm3.isClickable = false
        fm4.isEnabled = false
        fm4.isClickable = false
        fm5.isEnabled = false
        fm5.isClickable = false
        fm6.isEnabled = false
        fm6.isClickable = false
        fm7.isEnabled = false
        fm7.isClickable = false
        fm8.isEnabled = false
        fm8.isClickable = false

        //Funcionalidad de los botones de acción
        binding.fm1a.setOnClickListener {
            Toast.makeText(this, "Mojado de manos", Toast.LENGTH_SHORT).show()
            fm1a.isClickable = false
            fm1a.isEnabled = false
            //horaPulsacion = "$hora:$minuto:$segundo"
            horaAux = hora*3600 + minuto*60 + segundo
        }
        binding.fm1b.setOnClickListener {
            Toast.makeText(this, "fm1b", Toast.LENGTH_SHORT).show()
            fm1b.isClickable = false
            fm1b.isEnabled = false
        }
        binding.fm2.setOnClickListener {
            Toast.makeText(this, "fm2", Toast.LENGTH_SHORT).show()
            fm2.isClickable = false
            fm2.isEnabled = false
        }
        binding.fm3.setOnClickListener {
            Toast.makeText(this, "Frotarse los dedos", Toast.LENGTH_SHORT).show()
            fm3.isClickable = false
            fm3.isEnabled = false
        }
        binding.fm4.setOnClickListener {
            Toast.makeText(this, "fm4", Toast.LENGTH_SHORT).show()
            fm4.isClickable = false
            fm4.isEnabled = false
        }
        binding.fm5.setOnClickListener {
            Toast.makeText(this, "fm5", Toast.LENGTH_SHORT).show()
            fm5.isClickable = false
            fm5.isEnabled = false
        }
        binding.fm6.setOnClickListener {
            Toast.makeText(this, "fm6", Toast.LENGTH_SHORT).show()
            fm6.isClickable = false
            fm6.isEnabled = false
        }
        binding.fm7.setOnClickListener {
            Toast.makeText(this, "fm7", Toast.LENGTH_SHORT).show()
            fm7.isClickable = false
            fm7.isEnabled = false
        }
        binding.fm8.setOnClickListener {
            Toast.makeText(this, "fm8", Toast.LENGTH_SHORT).show()
            fm8.isClickable = false
            fm8.isEnabled = false
        }

        //Funcionalidad del botón de Grabar vídeo
        bGrabarFM.setOnClickListener {
            //Los botones de acción se activan al iniciar el proceso están deshabilitados
            fm1a.isEnabled = true
            fm1a.isClickable = true
            fm1b.isEnabled = true
            fm1b.isClickable = true
            fm2.isEnabled = true
            fm2.isClickable = true
            fm3.isEnabled = true
            fm3.isClickable = true
            fm4.isEnabled = true
            fm4.isClickable = true
            fm5.isEnabled = true
            fm5.isClickable = true
            fm6.isEnabled = true
            fm6.isClickable = true
            fm7.isEnabled = true
            fm7.isClickable = true
            fm8.isEnabled = true
            fm8.isClickable = true
            //grabarVideoFM()
            /*
            object : CountDownTimer(40000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    tContadorFM.alpha = 1.0f
                    //Hay que investigar la opción de que empiece en color rojo y cuando queden 10 segundos pasarlo a verde
                    bGrabarFM.setImageResource(R.mipmap.ic_parargrabar256)
                    tContadorFM.setText("" + millisUntilFinished / 1000 + " segundos.")
                    contador++//Cada instante se le suma 1 segundo
                    if (contador > 29) {

                    }
                }

                override fun onFinish() {
                    tContadorFM.setText("Hecho!")
                    tTiempoFM.setText("" + contador)
                    tTiempoFM.alpha = 1.0f//Se revela el texto del tiempo
                }

            }.start()*/
            bSiguienteFM.alpha = 1.0f//Activa el botón de siguiente que está oculto
            bSiguienteFM.isEnabled = true//Botón habilitado
            bSiguienteFM.isClickable = true//Botón habilitado
            horaComienzo = "$hora:$minuto:$segundo"
            horaAux = hora*3600 + minuto*60 + segundo
            Toast.makeText(this, "Se ha iniciado la grabación, $horaComienzo", Toast.LENGTH_SHORT)
        }

        //Funcionalidad del botón de Volver atrás
        bVolverFM.setOnClickListener {
            val intent = Intent(this, Grabar::class.java)
            startActivity(intent)
        }

        //Funcionalidad del botón siguiente
        bSiguienteFM.setOnClickListener {
            val intent = Intent(this, resumenFM::class.java)
            startActivity(intent)
        }

        //viewFinderFM = findViewById(R.id.viewFinderFM)
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({

            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().also {
                    mPreview -> mPreview.setSurfaceProvider(
                binding.viewFinder.surfaceProvider
            )
            }

            imageCapture = ImageCapture.Builder().build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()

                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            }catch (e: Exception){
                Log.d(Constants.TAG, "startCamera ha fallado.", e)
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun allPermissionGranted() = Constants.REQUIRED_PERMISSIONS.all {
        val b = ContextCompat.checkSelfPermission(
            baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
        b
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}


