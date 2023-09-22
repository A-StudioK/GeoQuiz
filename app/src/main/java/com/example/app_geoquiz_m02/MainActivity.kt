package com.example.app_geoquiz_m02

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private val question = arrayOfNulls<String>(5)
    private var score = 0
    private var life = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        arrayQuestions()
        setupViews(0)
    }

    private fun arrayQuestions() {
        question[0] = "¿Is Messi the best player in the world?"
        question[1] = "¿Is CR7 better than Lionel Messi?"
        question[2] = "¿Does Japan play better than Germany?"
        question[3] = "¿Didn't Argentina win the America Cup?"
        question[4] = "¿Does is Argentina the best team in the world?"
    }

    @SuppressLint("SetTextI18n")
    private fun checkTrueOrFalse(isCorrect: Boolean) {
        val message = if (isCorrect) "Correct" else "Incorrect"
        if (message == "Correct") {
            score += 10
            val tvScore = findViewById<TextView>(R.id.tvScore)
            tvScore.text = "Score: " + score.toString()
        }
        else {
            life--
            val tvLife = findViewById<TextView>(R.id.tvLife)
            tvLife.text = "Life: " + life.toString()
        }

        val toast = Toast.makeText(this, message, Toast.LENGTH_LONG)
        val greenColor = Color.GREEN
        val redColor = Color.RED
        val backgroundColor = if (isCorrect) greenColor else redColor
        val toastLayout = toast.view as View
        toastLayout.backgroundTintList = ColorStateList.valueOf(backgroundColor)
        toast.show()
    }

    private fun setupViews(size: Int) {
        if (size >= 5) {
            Toast.makeText(this, "End Of Questions", Toast.LENGTH_LONG).show()
            val btRestart = findViewById<Button>(R.id.btRestart)
            val btNext = findViewById<Button>(R.id.btNext)
            btRestart.visibility = View.VISIBLE
            btNext.visibility = View.GONE
            return
        }
        val btYes = findViewById<Button>(R.id.btYes)
        val btNo = findViewById<Button>(R.id.btNo)
        val tvQuestion = findViewById<TextView>(R.id.tvQuestion)

        tvQuestion.text = question[size]

        btYes.setOnClickListener {
            val isCorrect = (size % 2 == 0)
            checkTrueOrFalse(isCorrect)
        }
        btNo.setOnClickListener {
            val isCorrect = (size % 2 != 0)
            checkTrueOrFalse(isCorrect)
        }

        val btNext = findViewById<Button>(R.id.btNext)
        btNext.setOnClickListener {
            if (life > 0) setupViews(size + 1)
            else {
                Toast.makeText(this, "Your lives are over", Toast.LENGTH_LONG).show()
                setupViews(5)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun restartQuestions(view: View) {
        val btRestart = findViewById<Button>(R.id.btRestart)
        val btNext = findViewById<Button>(R.id.btNext)
        val tvScore = findViewById<TextView>(R.id.tvScore)
        val tvLife = findViewById<TextView>(R.id.tvLife)

        tvScore.text = "Score: 0"
        tvLife.text = "Life: 3"
        btRestart.visibility = View.GONE
        btNext.visibility = View.VISIBLE
        setupViews(0)
    }
}