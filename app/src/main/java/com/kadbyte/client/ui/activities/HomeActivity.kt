package com.kadbyte.client.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.kadbyte.client.R
import com.kadbyte.client.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityHomeBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_home)

        binding.lifecycleOwner = this

        setSupportActionBar(binding.mainToolBar)

        //navigation Controller
        val navController = findNavController(R.id.MainNavHost)

        //To configure the app bar
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.orderListFragment, R.id.itemListFragment, R.id.categoryListFragment, R.id.createBarcodeFragment),
            binding.mainDrawer
        )

        //Setting up the app bar and the navigation drawer, to display the name and navigate automatically
        binding.mainToolBar.setupWithNavController(navController, appBarConfiguration)
        binding.mainNavigationView.setupWithNavController(navController)

    }

}