package com.kadbyte.client.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.kadbyte.client.R

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_welcome)
        startActivity(Intent(this, ItemAddActivity::class.java))
    }

    fun buttonClick(view: View){
        when(view.id){
            R.id.WelcomeLoginButton -> {
                val i = Intent(this, LoginActivity::class.java)
                startActivity(i)
            }
            R.id.WelcomeSignUpButton -> {
                val i = Intent(this, SignUpActivity::class.java)
                startActivity(i)
            }
        }
    }
}

