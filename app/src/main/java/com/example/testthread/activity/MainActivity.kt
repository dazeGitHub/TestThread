package com.example.testthread.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.testthread.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.btn_jump_1).setOnClickListener{
            this.startActivity(Intent(this, HandlerThreadActivity::class.java))
        }

        findViewById<View>(R.id.btn_jump_2).setOnClickListener {
            this.startActivity(Intent(this, HandlerThreadActivity2::class.java))
        }
    }
}