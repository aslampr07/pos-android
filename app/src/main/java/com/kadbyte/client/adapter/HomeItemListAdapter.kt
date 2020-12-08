package com.kadbyte.client.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kadbyte.client.databinding.LayoutHomeItemListBinding
import com.kadbyte.client.model.Item

class HomeItemListAdapter(private val itemList: List<Item>) :
    RecyclerView.Adapter<HomeItemListAdapter.ViewHolder>() {

    class ViewHolder(val binding: LayoutHomeItemListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutHomeItemListBinding.inflate(layoutInflater, parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.item = itemList[position]
    }
}