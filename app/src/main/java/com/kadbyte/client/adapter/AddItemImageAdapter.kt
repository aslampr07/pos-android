package com.kadbyte.client.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.kadbyte.client.R
import com.kadbyte.client.databinding.ImageCardItemBinding
import com.kadbyte.client.model.ItemImage
import com.squareup.picasso.Picasso

class AddItemImageAdapter(
    private val ui: AddItemClickListener,
    private val list: MutableList<ItemImage>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class AddImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.findViewById<ImageButton>(R.id.addImageButton).setOnClickListener {
                ui.onAddClick()
            }
        }
    }

    inner class ImageViewHolder(val binding: ImageCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            binding.image = list[position]
            binding.removeImage.setOnClickListener {
                list.removeAt(layoutPosition)
                notifyItemRemoved(layoutPosition)
            }
            if (list[position].imageUrl != "") {
                val picasso = Picasso.get()
                picasso.setIndicatorsEnabled(true)
                picasso.load(list[position].imageUrl).placeholder(R.drawable.banana).into(binding.AddImageView)
            }

            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            ADD_BUTTON -> AddImageViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.layout_add_image_button,
                    parent,
                    false
                )
            )
            else -> return ImageViewHolder(
                ImageCardItemBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemViewType(position: Int) =
        if (position == list.size) ADD_BUTTON else IMAGE_LAYOUT

    override fun getItemCount() = list.size + 1

    companion object {
        const val IMAGE_LAYOUT = 0
        const val ADD_BUTTON = 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == IMAGE_LAYOUT) {
            (holder as ImageViewHolder).bind(position)

        }
    }

    interface AddItemClickListener {
        fun onAddClick()
    }
}
