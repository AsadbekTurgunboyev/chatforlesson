package com.example.chatapp.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.chatapp.MainActivity
import com.example.chatapp.databinding.ActivitySplashBinding
import com.example.chatapp.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {

    lateinit var viewBinding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.materialButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        if (!getData().isNullOrEmpty()){
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }



     fun getData(): String {
        val pref = getSharedPreferences("MyPref", MODE_PRIVATE)
        val key = pref.getString("key", "")
        return key.toString()
    }
}