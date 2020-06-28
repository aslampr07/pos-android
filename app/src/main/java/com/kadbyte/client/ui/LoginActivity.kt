package com.kadbyte.client.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.kadbyte.client.R
import com.kadbyte.client.databinding.ActivityLoginBinding
import com.kadbyte.client.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.login = viewModel

        viewModel.usernameError.observe(this, Observer { error ->
            binding.LoginUsernameInput.error = error
        })

        viewModel.passwordError.observe(this, Observer { error ->
            binding.LoginPasswordInput.error = error
        })

        viewModel.isSignInSuccess.observe(this, Observer { isSuccess ->
            if(isSuccess){
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
            }
        })
    }
}
