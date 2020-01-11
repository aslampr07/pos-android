package com.kadbyte.client

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
    }

    fun buttonClick(view: View){
        when(view.id){
            R.id.WelcomeLoginbutton -> {
                val i = Intent(this, LoginActivity::class.java)
                startActivity(i)
            }
        }
    }
}
