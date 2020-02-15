package com.kadbyte.client.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.kadbyte.client.DaggerViewModelComponent
import com.kadbyte.client.R
import com.kadbyte.client.databinding.ActivitySignUpBinding
import com.kadbyte.client.viewmodel.SignUpViewModel

class SignUpActivity : AppCompatActivity() {

    private lateinit var viewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivitySignUpBinding =
            DataBindingUtil.setContentView(this,
                R.layout.activity_sign_up
            )


        viewModel = DaggerViewModelComponent.create().getSignUpViewModel()

        binding.signup = viewModel
        binding.lifecycleOwner = this

        viewModel.usernameError.observe(this, Observer { error ->
            binding.SignUpUsernameInput.error = error
        })

        viewModel.passwordError.observe(this, Observer { error ->
            binding.SignUpPasswordInput.error = error
        })

        viewModel.emailError.observe(this, Observer { error ->
            binding.SignUpEmailInput.error = error
        })

        viewModel.phoneError.observe(this, Observer { error ->
            binding.SignUpPhoneInput.error = error
        })
    }

}
