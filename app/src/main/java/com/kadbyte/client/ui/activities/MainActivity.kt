package com.kadbyte.client.ui.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kadbyte.client.KadByteService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

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
                        finish()
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