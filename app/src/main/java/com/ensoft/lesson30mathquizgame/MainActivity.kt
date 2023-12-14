package com.ensoft.lesson30mathquizgame

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.ensoft.lesson30mathquizgame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var A = 0
    private var B = 0

    private var score = 0

    private var questionNumber = 1

    var timer = 30000 //30seconds

    private var locationOfCorrectAnswer = 0
    private var options: ArrayList<Int> = ArrayList()


    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        startTimer()
        generateQuestions()



        binding.btnPlayAgain.setOnClickListener {
            binding.opt1.isEnabled = true
            binding.opt2.isEnabled = true
            binding.opt3.isEnabled = true
            binding.opt4.isEnabled = true

            binding.countView.text = "$score/$questionNumber"
            binding.btnPlayAgain.visibility = View.INVISIBLE
            binding.timerView.text = (timer/1000).toString()
            startTimer()
            generateQuestions()
        }


    }
    fun generateQuestions(){
        options.clear()

        A = (1 until 21).random()
        B = (1 until 21).random()

        binding.questionView.text = "$A + $B"
        locationOfCorrectAnswer = (0 until 4).random()

        var incorrectAnswer: Int

        for (i in 0 until 4){
            if (i == locationOfCorrectAnswer)
                options.add(A + B)
            else{
                incorrectAnswer = (5 until 41).random()

                while (incorrectAnswer == A + B || incorrectAnswer in options){
                    incorrectAnswer = (5 until 41).random()
                }
                options.add(incorrectAnswer)
            }
        }
        binding.opt1.text = options[0].toString()
        binding.opt2.text = options[1].toString()
        binding.opt3.text = options[2].toString()
        binding.opt4.text = options[3].toString()
    }

    fun chooseAnswer(view: View){
        binding.resultTxt.visibility = View.VISIBLE
        if (view.tag.toString() == locationOfCorrectAnswer.toString()){
            score++
            binding.resultTxt.text = "Javob to'g'ri"
        }
        else{
            binding.resultTxt.text = "Javob noto'g'ri"
            binding.resultTxt.setTextColor(Color.parseColor("#CD5C5C"))
        }
        object : CountDownTimer(1000,1000){
            override fun onTick(millisUntilFinished: Long) {  }
            override fun onFinish() {
                binding.resultTxt.setTextColor(Color.parseColor("#DDCECE"))
                binding.resultTxt.visibility = View.INVISIBLE
            }
        }.start()
        questionNumber++
        binding.countView.text = "$score/$questionNumber"
        generateQuestions()
    }

    fun startTimer(){
        object : CountDownTimer((timer + 100).toLong(), 1000){
            override fun onFinish() {
                binding.timerView.text = "0"
                val builder = AlertDialog.Builder(this@MainActivity)
                    .setTitle("O`yin tugadi")
                    .setCancelable(false)
                    .setMessage("Siz $questionNumber ta savoldan $score ta topdingiz!")
                    .setPositiveButton("Ok"){
                            dialog, which ->
                        dialog.dismiss()
                    }
                val dialog = builder.create()
                dialog.show()

                clearGame()

                binding.opt1.isEnabled = false
                binding.opt2.isEnabled = false
                binding.opt3.isEnabled = false
                binding.opt4.isEnabled = false

                binding.btnPlayAgain.visibility = View.VISIBLE
            }
            override fun onTick(millisUntilFinished: Long) {
                binding.timerView.text = (millisUntilFinished/1000).toString()
            }

        }.start()
    }
    fun clearGame(){
        binding.opt1.text = ""
        binding.opt2.text = ""
        binding.opt3.text = ""
        binding.opt4.text = ""

        binding.questionView.text = ""
        score = 0
        questionNumber = 1
    }
}