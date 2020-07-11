package com.kadbyte.client.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kadbyte.client.R
import com.kadbyte.client.adapter.HomeItemListAdapter
import com.kadbyte.client.databinding.ActivityHomeBinding
import com.kadbyte.client.viewmodel.HomeViewModel
import com.kadbyte.client.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var adapter: HomeItemListAdapter;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityHomeBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.lifecycleOwner = this

        binding.mainItemList.layoutManager = LinearLayoutManager(this)

        adapter = HomeItemListAdapter(emptyList())

        viewModel.itemList.observe(this, Observer { list ->
            adapter = HomeItemListAdapter(list)
            binding.mainItemList.adapter = adapter
        })

        viewModel.getItemList()

    }

    fun actionButtonClick(view: View) {
        startActivity(Intent(this, ItemAddActivity::class.java))
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }
}