package com.kadbyte.client.viewmodel

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kadbyte.client.AuthenticationService
import com.kadbyte.client.KadByteService
import com.kadbyte.client.model.ServiceError
import com.kadbyte.client.model.SignIn
import com.squareup.moshi.JsonAdapter
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class LoginViewModel @ViewModelInject constructor(
    private val authService: AuthenticationService,
    private val service: KadByteService,
    private val preferences: SharedPreferences,
    private val errorAdapter: JsonAdapter<List<ServiceError>>
    //@Assisted private val savedStateHandle: SavedStateHandle
) :
    ViewModel() {

    private val TAG = "LoginViewModel"

    val username: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()

    val usernameError: MutableLiveData<String> = MutableLiveData()
    val passwordError: MutableLiveData<String> = MutableLiveData()

    val isSignInSuccess: MutableLiveData<Boolean> = MutableLiveData()
    fun login() {
        viewModelScope.launch(IO) {
            val response = authService.logIn(
                SignIn(
                    username.value.toString(),
                    password.value.toString()
                )
            )
            if (response.isSuccessful) {
                val result = response.body()
                if (result != null) {
                    preferences.edit { putString("refreshToken", result.token).commit() }
                    isSignInSuccess.postValue(true)
                }

            } else {
                val errors = errorAdapter.fromJson(response.errorBody()!!.string())
                if (errors != null) {
                    for (error in errors) {
                        when (error.code) {
                            10006 -> usernameError.postValue("Account not found")
                            10007 -> passwordError.postValue("Password incorrect")
                        }
                    }
                }
            }
        }
    }
}