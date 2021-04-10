package com.kadbyte.client.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kadbyte.client.R

class ItemImageListAdapter : RecyclerView.Adapter<ItemImageListAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.AddImageView)
        val removeButton: ImageButton = view.findViewById(R.id.removeImage)
    }

    val urlList: MutableList<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.image_card_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (urlList[position] != "") {
            Glide.with(holder.imageView)
                .load("https://api.kadbyte.com/assets/${urlList[position]}")
                .into(holder.imageView)
            holder.removeButton.visibility = View.VISIBLE
        }
    }

    override fun getItemCount() = urlList.size;

    fun addItem(url: String) {
        urlList.add(url)
        notifyItemInserted(urlList.size - 1);
    }

}