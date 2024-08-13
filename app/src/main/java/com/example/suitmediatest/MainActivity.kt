package com.example.suitmediatest

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var name: EditText
    private lateinit var palindrome: EditText
    private lateinit var btnNext : Button
    private lateinit var btnCheck: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        name = findViewById(R.id.Name)
        palindrome = findViewById(R.id.Polindrome)
        btnCheck = findViewById(R.id.btnCheck)
        btnNext = findViewById(R.id.btnNext)

        btnCheck.setOnClickListener(this)
        btnNext.setOnClickListener(this)
    }

    private fun isPalindrome(input: String): Boolean {
        val cleanedInput = input.replace(" ", "").lowercase()
        return cleanedInput == cleanedInput.reversed()
    }
    private fun showAlert(message: String) {
        AlertDialog.Builder(this).setTitle("Result")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btnCheck -> run{
                val sentence = palindrome.text.toString()
                if (isPalindrome(sentence)) {
                    showAlert("is Palindrome")
                } else {
                    showAlert("not Palindrome")
                }
            }
            R.id.btnNext -> run{
                val intent = Intent(this@MainActivity, SecondScreen::class.java)
                intent.putExtra(SecondScreen.nama, name.text.toString())
                startActivity(intent)
            }
        }
    }
}