package com.kadbyte.client.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kadbyte.client.R
import com.kadbyte.client.adapter.HomeItemListAdapter
import com.kadbyte.client.databinding.ActivityHomeBinding
import com.kadbyte.client.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

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
        binding.mainItemList.setHasFixedSize(true)
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