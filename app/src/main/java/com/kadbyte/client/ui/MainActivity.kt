package com.kadbyte.client.ui

import android.content.Intent
import android.content.SharedPreferences
import android.media.RingtoneManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.kadbyte.client.KadByteService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var service: KadByteService

    @Inject
    lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (preferences.contains("refreshToken")) {
            CoroutineScope(IO).launch {
                val response = service.profile()
                val x = service.getItemList()
                if (response.isSuccessful) {
                    if (response.body()?.stores?.isNotEmpty()!!) {
                        startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                    } else {
                        startActivity(Intent(this@MainActivity, NoStoreActivity::class.java))
                        finish()
                    }

                }
            }

        } else {
            startActivity(Intent(this, WelcomeActivity::class.java))
        }
    }
}