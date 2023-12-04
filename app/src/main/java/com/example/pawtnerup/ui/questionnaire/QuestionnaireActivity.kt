package com.example.pawtnerup.ui.questionnaire

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.pawtnerup.data.pref.UserModel
import com.example.pawtnerup.databinding.ActivityQuestionnaireBinding
import com.example.pawtnerup.ui.main.MainActivity

class QuestionnaireActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuestionnaireBinding
    private val questions = listOf(
        "Which dog's size \n do you preferred?",
        "How old do you want \n your dog to be??",
        "Which dog's gender \n do you preferred?"
    )

    private val answerChoice1 = listOf(
         "Giant", "Large", "Medium", "Small"
    )
    private val answerChoice2 = listOf(
        "Puppy (0 - 6 Months)", "Young  (6 - 12 Months)", "Adult (1 - 7 Years) ", "Senior (> 7 Years)"
    )
    private val answerChoice3 = listOf(
        "Male", "Female"
    )
    private var currentQuestionIndex = 0
    private var answer = mutableListOf("", "", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionnaireBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            if (currentQuestionIndex < questions.size && answer[currentQuestionIndex].isEmpty()){
                when (checkedId) {
                    binding.rb1.id -> {
                        val temp = binding.rb1.text
                        answer[currentQuestionIndex] += "$temp, "
                        showToastAndClearCheck("Choice 1")
                    }
                    binding.rb2.id -> {
                        val temp = binding.rb2.text
                        answer[currentQuestionIndex] += "$temp, "
                        showToastAndClearCheck("choice 2")
                    }
                    binding.rb3.id -> {
                        val temp = binding.rb3.text
                        answer[currentQuestionIndex] += "$temp, "
                        showToastAndClearCheck("choice 3")
                    }
                    binding.rb4.id -> {
                        val temp = binding.rb4.text
                        answer[currentQuestionIndex] += "$temp, "
                        showToastAndClearCheck("choice 4")
                    }
                }
            }
        }

        binding.btnNext.setOnClickListener {
            processAnswer()
            Log.d(TAG, "processAnswer: $answer")
        }
    }

    private fun showToastAndClearCheck(choice: String) {
        Toast.makeText(this, choice, Toast.LENGTH_SHORT).show()
        binding.radioGroup.clearCheck()
    }

    private fun displayQuestion() {
        if (currentQuestionIndex < questions.size) {
            binding.questionTextView.text = questions[currentQuestionIndex]

            if (currentQuestionIndex == 1){
                binding.rb1.text = answerChoice2[0]
                binding.rb2.text = answerChoice2[1]
                binding.rb3.text = answerChoice2[2]
                binding.rb4.text = answerChoice2[3]
            } else if (currentQuestionIndex == 2){
                binding.rb1.text = answerChoice3[0]
                binding.rb2.text = answerChoice3[1]
                binding.rb3.visibility = android.view.View.INVISIBLE
                binding.rb4.visibility = android.view.View.INVISIBLE
            } else {
                binding.rb1.text = answerChoice1[0]
                binding.rb2.text = answerChoice1[1]
                binding.rb3.text = answerChoice1[2]
                binding.rb4.text = answerChoice1[3]
            }

        } else {
            val userModel = if(Build.VERSION.SDK_INT >= 33){
                intent.getParcelableExtra("userData", UserModel::class.java)
            } else {
                @Suppress
                intent.getParcelableExtra<UserModel>("userData")
            }
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("userData", userModel)
            startActivity(intent)
        }
    }

    private fun processAnswer() {
        Toast.makeText(this, "Answer: $answer", Toast.LENGTH_SHORT).show()
        binding.radioGroup.clearCheck()
        currentQuestionIndex++
        displayQuestion()
        Log.d(TAG, "processAnswer: $answer")
    }

    companion object {
        private const val TAG = "QuestionnaireActivityWisnu"
    }
}