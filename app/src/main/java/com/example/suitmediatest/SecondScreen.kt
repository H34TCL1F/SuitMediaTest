package com.example.suitmediatest

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecondScreen : AppCompatActivity(), View.OnClickListener {

    private lateinit var username: TextView
    private lateinit var next: Button

    companion object{
        const val nama = "Username"
        const val REQUEST_CODE_SELECT_USER = 1

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        username = findViewById(R.id.Username)
        val Username = intent.getStringExtra(nama)
        username.setText(Username)
        next = findViewById(R.id.chsUser)
        next.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.chsUser) {
            val intent = Intent(this@SecondScreen, ThirdActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_SELECT_USER)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SELECT_USER && resultCode == RESULT_OK) {
            val selectedUserName = data?.getStringExtra("SELECTED_USER_NAME")
            val selectedUserTextView: TextView = findViewById(R.id.selectedUser)
            selectedUserTextView.text = selectedUserName
        }
    }



}