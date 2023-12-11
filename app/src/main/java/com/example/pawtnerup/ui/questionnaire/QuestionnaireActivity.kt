package com.example.pawtnerup.ui.questionnaire

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.pawtnerup.data.model.QuestionnaireModel2
import com.example.pawtnerup.databinding.ActivityQuestionnaireBinding

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
    private var answer1 = ""
    private var answer2 = ""
    private var answer3 = ""
    private var answer4 = ""
    private var listAnswer1 = ArrayList<String>()
    private var listAnswer2 = ArrayList<String>()
    private var listAnswer3 = ArrayList<String>()
    private var listAnswer4 = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionnaireBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (currentQuestionIndex < questions.size){
            binding.rb1.setOnCheckedChangeListener{ _, isChecked ->
                answer1 = if(isChecked){
                    binding.rb1.text.toString()
                } else {
                    ""
                }
            }
            binding.rb2.setOnCheckedChangeListener{ _, isChecked ->
                answer2 = if(isChecked){
                    binding.rb2.text.toString()
                } else {
                    ""
                }
            }
            binding.rb3.setOnCheckedChangeListener{ _, isChecked ->
                answer3 = if(isChecked){
                    binding.rb3.text.toString()
                } else {
                    ""
                }
            }
            binding.rb4.setOnCheckedChangeListener{ _, isChecked ->
                answer4 = if(isChecked){
                    binding.rb4.text.toString()
                } else {
                    ""
                }
            }
        }

        playAnimation()

        binding.btnNext.setOnClickListener {
            processAnswer()
            Log.d(TAG, "processAnswer: $listAnswer1 $listAnswer2 $listAnswer3")
        }
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
            val questionnaireModel2 = QuestionnaireModel2(
                listAnswer1,
                listAnswer2,
                listAnswer3,
                listAnswer4
            )

            val editor = getSharedPreferences("questionnaireData2", MODE_PRIVATE).edit()
            editor.putString("petSizes", listAnswer1.toString())
            editor.putString("petAges", listAnswer2.toString())
            editor.putString("petGenders", listAnswer3.toString())
            editor.apply()

            val intent = Intent(this, BreedQuestionnaireActivity::class.java)
            intent.putExtra("questionnaireData2", questionnaireModel2)
            startActivity(intent)
        }
    }

    private fun processAnswer() {
        if (currentQuestionIndex == 0){
            if(answer1.isNotEmpty()){
                listAnswer1.add(answer1)
            }
            if (answer2.isNotEmpty()){
                listAnswer1.add(answer2)
            }
            if (answer3.isNotEmpty()){
                listAnswer1.add(answer3)
            }
            if (answer4.isNotEmpty()){
                listAnswer1.add(answer4)
            }
        } else if (currentQuestionIndex == 1){
            if(answer1.isNotEmpty()){
                listAnswer2.add(answer1)
            }
            if (answer2.isNotEmpty()){
                listAnswer2.add(answer2)
            }
            if (answer3.isNotEmpty()){
                listAnswer2.add(answer3)
            }
            if (answer4.isNotEmpty()){
                listAnswer2.add(answer4)
            }
        } else {
            if(answer1.isNotEmpty()){
                listAnswer3.add(answer1)
            }
            if (answer2.isNotEmpty()){
                listAnswer3.add(answer2)
            }
            if (answer3.isNotEmpty()){
                listAnswer3.add(answer3)
            }
            if (answer4.isNotEmpty()){
                listAnswer3.add(answer4)
            }
        }

        binding.rb1.isChecked = false
        binding.rb2.isChecked = false
        binding.rb3.isChecked = false
        binding.rb4.isChecked = false

        currentQuestionIndex++
        displayQuestion()
    }

    fun playAnimation(){
        val tvTitle = ObjectAnimator.ofFloat(binding.tvTitle, android.view.View.ALPHA, 1f).setDuration(400)
        val tvQuestion = ObjectAnimator.ofFloat(binding.questionTextView, android.view.View.ALPHA, 1f).setDuration(400)
        val rbGroup = ObjectAnimator.ofFloat(binding.radioGroup, android.view.View.ALPHA, 1f).setDuration(400)
        val btnNext = ObjectAnimator.ofFloat(binding.btnNext, android.view.View.ALPHA, 1f).setDuration(400)

        android.animation.AnimatorSet().apply {
            playSequentially(
                tvTitle,
                tvQuestion,
                rbGroup,
                btnNext
            )
            startDelay = 500
        }.start()
    }

    companion object {
        private const val TAG = "QuestionnaireActivityWisnu"
    }
}