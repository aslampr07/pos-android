package com.kadbyte.client.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.kadbyte.client.KadByteService
import com.kadbyte.client.model.ServiceError
import com.kadbyte.client.model.SignUp
import com.squareup.moshi.JsonAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    private val service: KadByteService,
    private val deserialize: JsonAdapter<List<ServiceError>>
) {


    val username: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    val phone: MutableLiveData<String> = MutableLiveData()
    val email: MutableLiveData<String> = MutableLiveData()

    val usernameError: MutableLiveData<String> = MutableLiveData()
    val passwordError: MutableLiveData<String> = MutableLiveData()
    val phoneError: MutableLiveData<String> = MutableLiveData()
    val emailError: MutableLiveData<String> = MutableLiveData()


    fun signUp() {

        val username = this.username.value
        val password = this.password.value
        val phone = this.phone.value
        val email = this.email.value

        var status = true

        if (username.isNullOrBlank()) {
            usernameError.postValue("Username cannot be empty")
            status = false
        }
        if (password.isNullOrBlank()) {
            passwordError.postValue("Password cannot be empty")
            status = false
        }
        if (phone.isNullOrBlank()) {
            phoneError.postValue("Phone number cannot be empty")
            status = false
        }
        if (email.isNullOrBlank()) {
            emailError.postValue("Email cannot be empty")
            status = false
        }

        if (status) {
            service.signUp(
                SignUp(
                    username!!,
                    password!!,
                    phone!!,
                    email!!
                )
            ).enqueue(object :
                Callback<Boolean> {
                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Log.v("This", "This is an error")
                }

                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    if (response.isSuccessful) {
                        Log.v("Viewmoodel", "Success")
                    } else {
                        val x = deserialize.fromJson(response.errorBody()?.string())
                        if (x != null) {
                            for(item in x){
                                Log.v("this", item.message)
                            }
                        }
                    }
                }
            })
        }
    }
}