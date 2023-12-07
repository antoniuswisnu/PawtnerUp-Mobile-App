package com.example.pawtnerup.ui.questionnaire

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.pawtnerup.data.model.QuestionnaireModel
import com.example.pawtnerup.databinding.ActivityQuestionnaireTextBinding

class QuestionnaireTextActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuestionnaireTextBinding
    private val questions = listOf(
        "Tell Me About Yourself",
        "What personality in \n" +
                "pet do you want?"
    )
    private val listAnswer = mutableListOf("", "")
    private var currentQuestionIndex = 0
    private var questionnaireModel = QuestionnaireModel("", "")
    private var answer1 = ""
    private var answer2 = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionnaireTextBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNext.setOnClickListener {
            if(currentQuestionIndex == 0){
                answer1 = binding.answerEditText.text.toString()
                listAnswer[0] = answer1
            } else {
                answer2 = binding.answerEditText.text.toString()
                listAnswer[1] = answer2
            }
            processAnswer()
        }
    }

    private fun displayQuestion(){
        if(currentQuestionIndex < questions.size){
            binding.questionTextView.text = questions[currentQuestionIndex]
        } else {
            questionnaireModel = QuestionnaireModel(listAnswer[0], listAnswer[1])

            val editor = getSharedPreferences("questionnaireData", MODE_PRIVATE).edit()
            editor.putString("bio", listAnswer[0])
            editor.putString("petPersonality", listAnswer[1])
            editor.apply()

            val intent = Intent(this, QuestionnaireActivity::class.java)
            intent.putExtra("questionnaireData", questionnaireModel)
            startActivity(intent)
        }
    }

    private fun processAnswer(){
        binding.answerEditText.text.clear()
        currentQuestionIndex++
        Toast.makeText(this, "$listAnswer", Toast.LENGTH_SHORT).show()
        displayQuestion()

        Log.d(TAG, "DATA Model : $questionnaireModel")
        Log.d(TAG, "answer1: $answer1")
        Log.d(TAG, "answer2: $answer2")
        Log.d(TAG, "List Answerrrr: $listAnswer")
    }
    companion object {
        private const val TAG = "QuestionnaireTextActivityWisnu"
    }
}