package com.kadbyte.client

import com.kadbyte.client.viewmodel.SignUpViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface ViewModelComponent{
    fun getSignUpViewModel(): SignUpViewModel
}