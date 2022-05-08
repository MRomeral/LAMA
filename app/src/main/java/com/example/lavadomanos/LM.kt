package com.example.lavadomanos


import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE

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
    private var tiempoTotalFM = 0

    //Hora para saber a que hora se inicia la labor
    private val c = Calendar.getInstance()
    private val hora = c.get(Calendar.HOUR)
    private val minuto = c.get(Calendar.MINUTE)
    private val segundo = c.get(Calendar.SECOND)
    private var auxLM = 0
    private var horaComienzoLM = ""
    private var terminadoLM = false
    private var categoria = ""
    private var subcat = ""
    private var indicacion = ""

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

        //Datos que pasamos por el bundle
        var rCentro = ""
        var rServicio = ""
        var rPabellon = ""
        var rDepartamento = ""
        var per = ""
        var ses = ""
        var observ = ""
        var rFecha = ""
        var rHoraIni = ""
        var rHoraFin = ""

        val bundle = intent.extras//Obtiene todos los datos del bundle
        if(bundle != null){
            rCentro = "${bundle.getString("centro")}"//Datos del centro
            rServicio = "${bundle.getString("servicio")}"//Datos del servicio
            rPabellon = "${bundle.getString("pabellon")}"//Datos del servicio
            rDepartamento = "${bundle.getString("departamento")}"//Datos del servicio
            rFecha = "${bundle.getString("fecha")}"//Datos de la fecha
            rHoraIni = "${bundle.getString("horaInicio")}"//Datos de la hora de inicio
            rHoraFin = "${bundle.getString("horaFin")}"//Datos de la hora de fin
            per = "${bundle.getString("periodo")}"//Datos del periodo
            ses = "${bundle.getString("sesion")}"//Datos de la sesión
            observ = "${bundle.getString("observados")}"//Datos de los observados
        }


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
        //Funciones asociadas a los botones básicos
        //Botón que inicia el proceso de grabación
        binding.bGrabarLM.setOnClickListener {
            contarTiempoLM()

            //takePhoto()
        }


        //Vuelve a la pantalla anterior
        binding.bVolverLM.setOnClickListener {
            val intent = Intent(this, Grabar::class.java)
            //Envío de datos a la pantalla siguiente
            val bundle = Bundle()
            bundle.putString("centro", rCentro)
            bundle.putString("servicio", rServicio)
            bundle.putString("pabellon", rPabellon)
            bundle.putString("departamento", rDepartamento)
            bundle.putString("periodo",per)
            bundle.putString("sesion",ses)
            bundle.putString("observados",observ)
            bundle.putString("fecha", rFecha)
            bundle.putString("horaInicio",rHoraIni)
            bundle.putString("horaFin",rHoraFin)
            bundle.putString("categoria",categoria)
            bundle.putString("subcategoria",subcat)
            bundle.putString("indicacion",indicacion)
            intent.putExtras(bundle)
            startActivity(intent)
        }

        //Este botón se revela una vez que el usuario ha pulsado grabar
        binding.bSiguienteLM.setOnClickListener {
            if(terminadoLM == true){

            }
            val intent = Intent(this, resumenLM::class.java)
            //Envío de datos a la pantalla siguiente
            val bundle = Bundle()
            bundle.putString("centro", rCentro)
            bundle.putString("servicio", rServicio)
            bundle.putString("pabellon", rPabellon)
            bundle.putString("departamento", rDepartamento)
            bundle.putString("periodo",per)
            bundle.putString("sesion",ses)
            bundle.putString("observados",observ)
            bundle.putString("fecha", rFecha)
            bundle.putString("horaInicio",rHoraIni)
            bundle.putString("horaFin",rHoraFin)
            bundle.putString("categoria",categoria)
            bundle.putString("subcategoria",subcat)
            bundle.putString("indicacion",indicacion)
            intent.putExtras(bundle)
            startActivity(intent)
            bSiguienteLM.alpha = 0f
        }

        //Funcionalidad de los botones de acción
        binding.lm0.setOnClickListener {
            takePhoto()

            lm0.isClickable = false
            lm0.isEnabled = false
            //Toast.makeText(this, "lm0", Toast.LENGTH_SHORT).show()
            mostrarHoraPulsadaLM("Paso 0")
        }
        binding.lm1.setOnClickListener {
            takePhoto()
            lm1.isClickable = false
            lm1.isEnabled = false
            //Toast.makeText(this, "lm1", Toast.LENGTH_SHORT).show()
            mostrarHoraPulsadaLM("Paso 1")
        }
        binding.lm2.setOnClickListener {
            lm2.isClickable = false
            lm2.isEnabled = false
            mostrarHoraPulsadaLM("Paso 2")
        }
        binding.lm3.setOnClickListener {
            lm3.isClickable = false
            lm3.isEnabled = false
            mostrarHoraPulsadaLM("Paso 3")
        }
        binding.lm4.setOnClickListener {
            lm4.isClickable = false
            lm4.isEnabled = false
            mostrarHoraPulsadaLM("Paso 4")
        }
        binding.lm5.setOnClickListener {
            lm5.isClickable = false
            lm5.isEnabled = false
            mostrarHoraPulsadaLM("Paso 5")
        }
        binding.lm6.setOnClickListener {
            lm6.isClickable = false
            lm6.isEnabled = false
            mostrarHoraPulsadaLM("Paso 6")
        }
        binding.lm7.setOnClickListener {
            lm7.isClickable = false
            lm7.isEnabled = false
            mostrarHoraPulsadaLM("Paso 7")
        }
        binding.lm8.setOnClickListener {
            lm8.isClickable = false
            lm8.isEnabled = false
            mostrarHoraPulsadaLM("Paso 8")
        }
        binding.lm9.setOnClickListener {
            lm9.isClickable = false
            lm9.isEnabled = false
            mostrarHoraPulsadaLM("Paso 9")
        }
        binding.lm10.setOnClickListener {
            lm10.isClickable = false
            lm10.isEnabled = false
            mostrarHoraPulsadaLM("Paso 10")
        }
        binding.lm11.setOnClickListener {
            lm11.isClickable = false
            lm11.isEnabled = false
            mostrarHoraPulsadaLM("Paso 11")
            mostrarTiempoHigieneLM()
        }
    }

    private fun mostrarTiempoHigieneLM() {
        if(tiempoTotalFM < 40){
            tempLM.setTextColor(Color.parseColor("#FF0000"))
        }else if(tiempoTotalFM > 40){
            tempLM.setTextColor(Color.parseColor("#FF00FF0A"))
        }
        tempLM.text = tiempoTotalFM.toString()
        tempLM.alpha = 1.0f
        bSiguienteLM.alpha = 1.0f
    }

    private fun contarTiempoLM() {
        bSiguienteLM.alpha = 1.0f
        bSiguienteLM.isEnabled = true
        bSiguienteLM.isClickable = true
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

        //Cuando empieza el proceso de higiene LM
        horaComienzoLM = "$hora:$minuto:$segundo"
        auxLM = hora*3600 + minuto*60 + segundo
        Toast.makeText(this, "Ha comenzado a las $horaComienzoLM", Toast.LENGTH_SHORT).show()
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

    private fun mostrarHoraPulsadaLM(paso: String) {

        val c = Calendar.getInstance()
        val dia = c.get(Calendar.DAY_OF_MONTH)
        val mes = c.get(Calendar.MONTH)
        val anno = c.get(Calendar.YEAR)
        val hora = c.get(Calendar.HOUR)
        val minuto = c.get(Calendar.MINUTE)
        val segundo = c.get(Calendar.SECOND)
        val auxPaso = hora*3600 + minuto*60 + segundo

        Toast.makeText(this,"Se ha realizado el $paso, en el segundo: ${difHoraLM(auxLM, auxPaso)}", Toast.LENGTH_LONG).show()
        if(paso == "Paso 11"){
            tiempoTotalFM = difHoraLM(auxLM, auxPaso).toInt()
            terminadoLM = true
        }
    }

    private fun difHoraLM(horaComienzo: Int, horaPaso: Int): String {
        return horaPaso.minus(horaComienzo).toString()
    }

}


