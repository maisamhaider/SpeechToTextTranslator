package com.example.speechtotexttranslator.ui.activities.dictionary

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.speechtotexttranslator.databinding.ActivityQuizBinding
import com.example.speechtotexttranslator.models.ModelQuizOfTheDay
import com.example.speechtotexttranslator.models.Option
import com.example.speechtotexttranslator.utils.Singleton.funTextToSpeech
import com.example.speechtotexttranslator.utils.Singleton.getQuizOfTheDay
import com.example.speechtotexttranslator.utils.Singleton.initTTS
import com.example.speechtotexttranslator.utils.Singleton.ttsShutdown

class ActivityQuiz : AppCompatActivity() {
    var _binding: ActivityQuizBinding? = null
    val binding get() = _binding!!
    private var anwser = ""
    lateinit var modelQuizOfTheDay: ModelQuizOfTheDay
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityQuizBinding.inflate(LayoutInflater.from(this), null,
            false)
        setContentView(binding.root)

        modelQuizOfTheDay = getQuizOfTheDay()

        binding.apply {
            textViewText.text = modelQuizOfTheDay.word
            imageButtonSpeak.setOnClickListener {
                funTextToSpeech(modelQuizOfTheDay.word, "en")
            }
            //set everything
            set(radioOption1, radioOption2, radioOption3, radioOption4, clTryAgain)
            radioOption1.setOnCheckedChangeListener { _, b ->
                if (b) {
                    handleQuiz(
                        radioOption1.text.toString(),
                        radioOption1,
                        radioOption1,
                        radioOption2,
                        radioOption3,
                        radioOption4,
                        clTryAgain
                    )
                }
            }
            radioOption2.setOnCheckedChangeListener { _, b ->
                if (b) {
                    handleQuiz(
                        radioOption2.text.toString(),
                        radioOption2,
                        radioOption1,
                        radioOption2,
                        radioOption3,
                        radioOption4,
                        clTryAgain
                    )
                }
            }
            radioOption3.setOnCheckedChangeListener { _, b ->
                if (b) {
                    handleQuiz(
                        radioOption3.text.toString(),
                        radioOption3,
                        radioOption1,
                        radioOption2,
                        radioOption3,
                        radioOption4,
                        clTryAgain
                    )
                }
            }
            radioOption4.setOnCheckedChangeListener { _, b ->
                if (b) {
                    handleQuiz(
                        radioOption4.text.toString(),
                        radioOption4,
                        radioOption1,
                        radioOption2,
                        radioOption3,
                        radioOption4,
                        clTryAgain
                    )
                }
            }

        }


    }

    private fun handleQuiz(
        text: String,
        selected: RadioButton,
        radioOption1: RadioButton,
        radioOption2: RadioButton,
        radioOption3: RadioButton,
        radioOption4: RadioButton,
        clTryAgain: ConstraintLayout,
    ) {
        radioOption1.isChecked = false
        radioOption2.isEnabled = false
        radioOption3.isEnabled = false
        radioOption4.isEnabled = false
        if (text.trim() == anwser.trim()) {
            clTryAgain.visibility = View.VISIBLE
            binding.appCompatButton.visibility = View.GONE
            binding.textViewResult.text = "Correct answer"
            selected.setBackgroundColor(Color.GREEN)
        } else {
            clTryAgain.visibility = View.VISIBLE
            binding.textViewResult.text = "Incorrect answer"
            binding.appCompatButton.visibility = View.VISIBLE
            selected.setBackgroundColor(Color.RED)
        }
        binding.appCompatButton.setOnClickListener {
            set(radioOption1, radioOption2, radioOption3, radioOption4, clTryAgain)
        }

    }

    fun set(
        radioOption1: RadioButton,
        radioOption2: RadioButton,
        radioOption3: RadioButton,
        radioOption4: RadioButton,
        cl_tryAgain: ConstraintLayout,
    ) {
        cl_tryAgain.visibility = View.GONE
        anwser = modelQuizOfTheDay.answer
        val option: MutableList<Option> = ArrayList(modelQuizOfTheDay.options)
        val array = arrayOf(
            option[0].option_1,
            option[0].option_2,
            option[0].option_3,
            option[0].option_4
        )
        array.shuffle()
        radioOption1.setBackgroundColor(Color.WHITE)
        radioOption2.setBackgroundColor(Color.WHITE)
        radioOption3.setBackgroundColor(Color.WHITE)
        radioOption4.setBackgroundColor(Color.WHITE)

        radioOption1.isChecked = false
        radioOption2.isChecked = false
        radioOption3.isChecked = false
        radioOption4.isChecked = false

        radioOption1.isEnabled = true
        radioOption2.isEnabled = true
        radioOption3.isEnabled = true
        radioOption4.isEnabled = true
        radioOption1.text = array[0]
        radioOption2.text = array[1]
        radioOption3.text = array[2]
        radioOption4.text = array[3]
    }

    override fun onResume() {
        super.onResume()
        initTTS()
    }

    override fun onDestroy() {
        super.onDestroy()
        ttsShutdown()
        _binding = null
    }
}