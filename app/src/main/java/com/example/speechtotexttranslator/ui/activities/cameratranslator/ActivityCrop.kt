package com.example.speechtotexttranslator.ui.activities.cameratranslator

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.example.speechtotexttranslator.annotations.AnNot
import com.example.speechtotexttranslator.databinding.ActivityCropBinding
import com.example.speechtotexttranslator.utils.Singleton.toastLong
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions


class ActivityCrop : AppCompatActivity() {
    private var _binding: ActivityCropBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCropBinding.inflate(LayoutInflater.from(this), null, false)
        setContentView(binding.root)

        val imageUri = intent.getStringExtra(AnNot.ObjIntentKeys.IMAGE_URI)

        binding.apply {
            cropImageView.setImageUriAsync(imageUri!!.toUri())
            textViewCrop.setOnClickListener {
                cropImageView.getCroppedImageAsync()
            }
            cropImageView.setOnCropImageCompleteListener { _, result ->
                val bit = result.bitmap
                if (result.isSuccessful) {
                    methTakeResult(bit)
                } else {
                    toastLong("error")
                }
            }
            textViewRotate.setOnClickListener {
                cropImageView.rotateImage(90)
            }
        }

    }

    private fun methTakeResult(bitmap: Bitmap) {
        val image = InputImage.fromBitmap(bitmap, 0)

        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        recognizer.process(image)
            .addOnSuccessListener { visionText ->
                if (visionText.text.isNotBlank()) {
                    startActivity(
                        Intent(this@ActivityCrop, ActivityCameraTranslatorResult::class.java
                        ).apply {
                            putExtra(AnNot.ObjIntentKeys.TEXT_SOURCE, visionText.text)
                        })
                } else {
                    toastLong("text Not found")
                }
                finish()

            }.addOnFailureListener { e -> toastLong(e.message.toString()) }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}