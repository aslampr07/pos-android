package com.kadbyte.client.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.kadbyte.client.R
import com.kadbyte.client.databinding.LayoutCategoryItemBinding
import com.kadbyte.client.model.Category

class CategoryListAdapter(private val list: List<Category>) :
    Adapter<CategoryListAdapter.ViewHolder>() {

    class ViewHolder(val binding: LayoutCategoryItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutCategoryItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.category = list[position]
    }

    override fun getItemCount() = list.size

}