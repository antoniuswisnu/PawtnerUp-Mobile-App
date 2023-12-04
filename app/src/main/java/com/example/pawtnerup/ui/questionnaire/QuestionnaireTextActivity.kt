package com.example.pawtnerup.ui.questionnaire

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pawtnerup.data.pref.UserModel
import com.example.pawtnerup.databinding.ActivityQuestionnaireTextBinding

class QuestionnaireTextActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuestionnaireTextBinding
    private val questions = listOf(
        "Tell Me About Yourself",
        "What personality in \n" +
                "pet do you want?"
    )
    private val answer = mutableListOf("", "")
    private var currentQuestionIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionnaireTextBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNext.setOnClickListener {
            processAnswer()
            currentQuestionIndex++
            if(currentQuestionIndex == questions.size){
                val userModel = if(Build.VERSION.SDK_INT >= 33){
                    intent.getParcelableExtra("userData", UserModel::class.java)
                } else {
                    @Suppress
                    intent.getParcelableExtra<UserModel>("userData")
                }
                val intent = Intent(this, QuestionnaireActivity::class.java)
                intent.putExtra("userData", userModel)
                startActivity(intent)
            }
        }
    }
    private fun processAnswer(){
        if(currentQuestionIndex < questions.size){
            binding.questionTextView.text = questions[currentQuestionIndex]

            if(currentQuestionIndex == 0){
                val answer1 = binding.answerEditText.text.toString()
                answer[0] = answer1
            } else {
                val answer2 = binding.answerEditText.text.toString()
                answer[1] = answer2
            }
        }
    }
    companion object {
        private const val TAG = "QuestionnaireTextActivityWisnu"
    }
}