package com.example.lavadomanos


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.lavadomanos.databinding.ActivityLmBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class LM : AppCompatActivity(){
    lateinit var bGrabarLM: ImageButton
    lateinit var lm0: ImageButton
    lateinit var lm1: ImageButton
    lateinit var lm2: ImageButton
    lateinit var lm3: ImageButton
    lateinit var lm4: ImageButton
    lateinit var lm5: ImageButton
    lateinit var lm6: ImageButton
    lateinit var lm7: ImageButton
    lateinit var lm8: ImageButton
    lateinit var lm9: ImageButton
    lateinit var lm10: ImageButton
    lateinit var lm11: ImageButton

    lateinit var bSiguienteLM: Button
    lateinit var bVolverLM: Button

    lateinit var tempLM: TextView


    //Hora para saber a que hora se inicia la labor
    private val c = Calendar.getInstance()
    private val hora = c.get(Calendar.HOUR)
    private val minuto = c.get(Calendar.MINUTE)
    private val segundo = c.get(Calendar.SECOND)
    private val aux = hora*3600 + minuto*60 + segundo
    private var horaComienzo = ""

    private lateinit var binding: ActivityLmBinding
    private lateinit var outputDirectory : File
    private lateinit var cameraExecutor : ExecutorService
    private var imageCapture: ImageCapture?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Botones básicos
        bGrabarLM = findViewById<ImageButton>(R.id.bGrabarLM)
        bVolverLM = findViewById<Button>(R.id.bVolverLM)
        bSiguienteLM = findViewById<Button>(R.id.bSiguienteLM)
        bSiguienteLM.isEnabled = false//Botón deshabilitado
        bSiguienteLM.isClickable = false//Botón deshabilitado

        //Botones de acciones
        lm0 = findViewById<ImageButton>(R.id.lm0)
        lm1 = findViewById<ImageButton>(R.id.lm1)
        lm2 = findViewById<ImageButton>(R.id.lm2)
        lm3 = findViewById<ImageButton>(R.id.lm3)
        lm4 = findViewById<ImageButton>(R.id.lm4)
        lm5 = findViewById<ImageButton>(R.id.lm5)
        lm6 = findViewById<ImageButton>(R.id.lm6)
        lm7 = findViewById<ImageButton>(R.id.lm7)
        lm8 = findViewById<ImageButton>(R.id.lm8)
        lm9 = findViewById<ImageButton>(R.id.lm9)
        lm10 = findViewById<ImageButton>(R.id.lm10)
        lm11 = findViewById<ImageButton>(R.id.lm11)

        //Los botones de acción hasta que no se le da a iniciar el proceso están deshabilitados
        lm0.isEnabled = false
        lm0.isClickable = false
        lm1.isEnabled = false
        lm1.isClickable = false
        lm2.isEnabled = false
        lm2.isClickable = false
        lm3.isEnabled = false
        lm3.isClickable = false
        lm4.isEnabled = false
        lm4.isClickable = false
        lm5.isEnabled = false
        lm5.isClickable = false
        lm6.isEnabled = false
        lm6.isClickable = false
        lm7.isEnabled = false
        lm7.isClickable = false
        lm8.isEnabled = false
        lm8.isClickable = false
        lm9.isEnabled = false
        lm9.isClickable = false
        lm10.isEnabled = false
        lm10.isClickable = false
        lm11.isEnabled = false
        lm11.isClickable = false


        //Textos
        tempLM = findViewById<TextView>(R.id.tempLM)

        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()

        if(allPermissionGranted()){
            startCamera()
        }else{
            ActivityCompat.requestPermissions(
                this, Constants.REQUIRED_PERMISSIONS,
                Constants.REQUEST_CODE_PERMISSIONS
            )
        }

        binding.bGrabarLM.setOnClickListener {
            contarTiempo()
            //takePhoto()
        }

        binding.bVolverLM.setOnClickListener {
            val intent = Intent(this, Grabar::class.java)
            startActivity(intent)
        }

        binding.bSiguienteLM.setOnClickListener {
            val intent = Intent(this, resumenLM::class.java)
            startActivity(intent)
            bSiguienteLM.alpha = 0f
        }

        //Funcionalidad de los botones de acción
        binding.lm0.setOnClickListener {
            Toast.makeText(this, "Mojado de manos", Toast.LENGTH_SHORT).show()
        }
        binding.lm1.setOnClickListener {

        }
        binding.lm2.setOnClickListener {

        }
        binding.lm3.setOnClickListener {

        }
        binding.lm4.setOnClickListener {

        }
        binding.lm5.setOnClickListener {

        }
        binding.lm6.setOnClickListener {

        }
        binding.lm7.setOnClickListener {

        }
        binding.lm8.setOnClickListener {

        }
        binding.lm9.setOnClickListener {

        }
        binding.lm10.setOnClickListener {

        }
        binding.lm11.setOnClickListener {

        }
    }

    private fun contarTiempo() {
        tempLM.text = "0"
        tempLM.alpha = 1.0f
        bSiguienteLM.alpha = 1.0f
        //Activa los botones de acción al iniciar el proceso
        lm0.isEnabled = true
        lm0.isClickable = true
        lm1.isEnabled = true
        lm1.isClickable = true
        lm2.isEnabled = true
        lm2.isClickable = true
        lm3.isEnabled = true
        lm4.isClickable = true
        lm5.isEnabled = true
        lm5.isClickable = true
        lm6.isEnabled = true
        lm6.isClickable = true
        lm7.isEnabled = true
        lm7.isClickable = true
        lm8.isEnabled = true
        lm8.isClickable = true
        lm9.isEnabled = true
        lm9.isClickable = true
        lm10.isEnabled = true
        lm10.isClickable = true
        lm11.isEnabled = true
        lm11.isClickable = true


        horaComienzo = "$hora:$minuto:$segundo"
        Toast.makeText(this, "Ha comenzado a las $horaComienzo", Toast.LENGTH_SHORT).show()
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let{
            mFile -> File(mFile, resources.getString(R.string.app_name)).apply {
                mkdirs()
            }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir

    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(Constants.FILE_NAME_FORMAT, Locale.getDefault()).format(System.currentTimeMillis())+".jpg")

        val outputOption = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(
            outputOption, ContextCompat.getMainExecutor(this), object : ImageCapture.OnImageSavedCallback{
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    val msg = "Foto guardada."

                    Toast.makeText(this@LM,"$msg $savedUri", Toast.LENGTH_SHORT).show()
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e(Constants.TAG, "Error al tomar foto.", exception)
                }

            }
        )
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if(requestCode == Constants.REQUEST_CODE_PERMISSIONS){
            if(allPermissionGranted()){
                startCamera()
            }else{
                Toast.makeText(this,"No se ha autorizado a la aplicación.",Toast.LENGTH_SHORT).show()
                finish()
            }
        }
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

    private fun allPermissionGranted() =
        Constants.REQUIRED_PERMISSIONS.all {
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


