package com.example.speechtotexttranslator.ui.activities.cameratranslator

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.example.speechtotexttranslator.R
import com.example.speechtotexttranslator.annotations.AnNot
import com.example.speechtotexttranslator.annotations.AnNot.ObjIntentKeys.IMAGE_URI
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.SOURCE_RECENT_LANGUAGES_CODE_CAMERA_TRANSLATOR
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.TARGET_RECENT_LANGUAGES_CODE_CAMERA_TRANSLATOR
import com.example.speechtotexttranslator.databinding.ActivityCameraTranslatorBinding
import com.example.speechtotexttranslator.ui.activities.ActivityLanguages
import com.example.speechtotexttranslator.utils.AppPreferences.funGetString
import com.example.speechtotexttranslator.utils.Singleton.funLaunchLanguagesActivity
import com.example.speechtotexttranslator.utils.Singleton.initTTS
import com.example.speechtotexttranslator.utils.Singleton.toastLong
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class ActivityCameraTranslator : AppCompatActivity(), LifecycleOwner {
    var binding: ActivityCameraTranslatorBinding? = null
    private var imageCapture: ImageCapture? = null
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService

    companion object {
        private const val TAG = "CameraXBasic"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraTranslatorBinding.inflate(LayoutInflater.from(this), null, false)
        setContentView(binding?.root)


        // Request camera permissions
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }
        outputDirectory = getOutputDirectory()

        cameraExecutor = Executors.newSingleThreadExecutor()
        // Set up the listener for take photo button

        binding?.apply {
            textViewCapture.setOnClickListener { takePhoto() }

            textViewSourceLang.setOnClickListener {
                funLaunchLanguagesActivity(
                    AnNot.ObjIntentKeys.LANGUAGE_CAMERA_SUPPORTED,
                    SOURCE_RECENT_LANGUAGES_CODE_CAMERA_TRANSLATOR,
                    AnNot.ObjPreferencesKeys.SOURCE_RECENT_LANGUAGES_CAMERA_TRANSLATOR,
                    AnNot.ObjPreferencesKeys.SOURCE_RECENT_LANGUAGE_SELECTED_CAMERA_TRANSLATOR,
                    AnNot.ObjPreferencesKeys.SOURCE_LANGUAGE_SELECTED_CODE_CAMERA_TRANSLATOR,
                    AnNot.ObjPreferencesKeys.SOURCE_LANGUAGE_SELECTED_NAME_CAMERA_TRANSLATOR,
                    ActivityLanguages()
                )
            }
            textViewTargetLang.setOnClickListener {
                funLaunchLanguagesActivity(
                    AnNot.ObjIntentKeys.LANGUAGE_ONLINE,
                    TARGET_RECENT_LANGUAGES_CODE_CAMERA_TRANSLATOR,
                    AnNot.ObjPreferencesKeys.TARGET_RECENT_LANGUAGES_CAMERA_TRANSLATOR,
                    AnNot.ObjPreferencesKeys.TARGET_RECENT_LANGUAGE_SELECTED_CAMERA_TRANSLATOR,
                    AnNot.ObjPreferencesKeys.TARGET_LANGUAGE_SELECTED_CODE_CAMERA_TRANSLATOR,
                    AnNot.ObjPreferencesKeys.TARGET_LANGUAGE_SELECTED_NAME_CAMERA_TRANSLATOR,
                    ActivityLanguages()
                )
            }
            textViewOpenGallery.setOnClickListener {
                val photoPickerIntent = Intent(Intent.ACTION_PICK)
                photoPickerIntent.type = "image/*"
                intentLauncher.launch(photoPickerIntent)
            }
        }


    }

    private val intentLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            if (result.resultCode == RESULT_OK && result.data!!.data != null) {
                val chosenImage = result.data!!.data.toString()
                startActivity(Intent(
                    this@ActivityCameraTranslator,
                    ActivityCrop::class.java
                ).apply {
                    putExtra(IMAGE_URI, chosenImage)
                })
            } else {
                toastLong("mo chosen image")
            }

        }

    private fun takePhoto() {
        // Get a stable reference of the modifiable image capture use case

        // Create time-stamped output file to hold the image
        val photoFile = File(
            outputDirectory, SimpleDateFormat(FILENAME_FORMAT, Locale.US)
                .format(System.currentTimeMillis()) + ".jpg"
        )

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Set up image capture listener, which is triggered after photo has
        // been taken

        imageCapture!!.takePicture(outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    val msg = "Photo capture succeeded: $savedUri"
                    startActivity(Intent(
                        this@ActivityCameraTranslator,
                        ActivityCrop::class.java
                    ).apply {
                        putExtra(IMAGE_URI, savedUri.toString())
                    })

                    toastLong(msg)
                    Log.d(TAG, msg)
                }

            })
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        imageCapture = ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            // We request aspect ratio but no resolution to match preview config, but letting
            // CameraX optimize for whatever specific resolution best fits our use cases
            // Set initial target rotation, we will have to call this again if rotation changes
            // during the lifecycle of this use case
            .build()
        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding?.viewFinder?.surfaceProvider)
                }

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(this, cameraSelector, imageCapture, preview)

            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults:
        IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                toastLong("Permissions not granted by the user.")
                finish()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        initTTS()
        binding?.textViewSourceLang!!.text = funGetString(
            AnNot.ObjPreferencesKeys.SOURCE_LANGUAGE_SELECTED_NAME_CAMERA_TRANSLATOR,
            "English"
        )
        binding?.textViewTargetLang!!.text = funGetString(
            AnNot.ObjPreferencesKeys.TARGET_LANGUAGE_SELECTED_NAME_CAMERA_TRANSLATOR,
            "English"
        )
    }


    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

}