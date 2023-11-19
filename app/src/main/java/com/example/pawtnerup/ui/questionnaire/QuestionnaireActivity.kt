package com.example.pawtnerup.ui.questionnaire

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.pawtnerup.databinding.ActivityQuestionnaireBinding
import com.example.pawtnerup.ui.main.MainActivity

class QuestionnaireActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuestionnaireBinding
    private val questions = listOf(
        "Question 1?",
        "Question 2?"
    )
    private var currentQuestionIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionnaireBinding.inflate(layoutInflater)
        setContentView(binding.root)

        displayQuestion()

        binding.choiceButton1.setOnClickListener {
            // Handle choice 1 click
            processAnswer(0)
        }

        binding.choiceButton2.setOnClickListener {
            // Handle choice 2 click
            processAnswer(1)
        }
    }

    private fun displayQuestion() {
        if (currentQuestionIndex < questions.size) {
            binding.questionTextView.text = questions[currentQuestionIndex]
        } else {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun processAnswer(choiceIndex: Int) {
        // Handle the chosen answer here
        Toast.makeText(this, "Choice $choiceIndex", Toast.LENGTH_SHORT).show()
        // Move to the next question
        currentQuestionIndex++
        displayQuestion()
    }
}