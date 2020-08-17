package com.kadbyte.client.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.kadbyte.client.AddPhotoDialog
import com.kadbyte.client.R
import com.kadbyte.client.adapter.AddItemImageAdapter
import com.kadbyte.client.databinding.ActivityItemAddBinding
import com.kadbyte.client.model.ItemImage
import com.kadbyte.client.viewmodel.ItemAddViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.*
import kotlin.coroutines.coroutineContext

@AndroidEntryPoint
class ItemAddActivity : AppCompatActivity(), AddPhotoDialog.FragmentCallback,
    AddItemImageAdapter.AddItemClickListener {

    private val viewModel: ItemAddViewModel by viewModels()

    private lateinit var dialog: AddPhotoDialog
    private lateinit var binding: ActivityItemAddBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView<ActivityItemAddBinding>(this, R.layout.activity_item_add)

        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        binding.ItemAddImageList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.ItemAddImageList.adapter = AddItemImageAdapter(this, viewModel.imageList)

        Log.v("Test", "TEST")
    }

    fun backButtonClick(view: View) {
        super.onBackPressed()
    }

    private lateinit var photoFile: File

    override fun onCameraClick() {
        dialog.dismiss()

        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {

                photoFile = createImageFile()
                val photoUri = FileProvider.getUriForFile(
                    this, "com.example.android.fileprovider", photoFile
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)

            }
        }
    }

    override fun onGalleryClick() {
        val i = Intent()
        i.type = ("images/*")
        i.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(i, "Select Image"), 2342)
    }

    override fun onFileClick() {
        val i = Intent()
        i.type = ("images/*")
        i.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(i, "Select Image"), 2342)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (Activity.RESULT_OK == resultCode) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    val newImagePosition = viewModel.imageList.size
                    viewModel.imageList.add(ItemImage(true))
                    binding.ItemAddImageList.adapter?.notifyItemInserted(newImagePosition)
                    CoroutineScope(IO).launch {
                        val result = viewModel.uploadImage(photoFile, viewModel.imageList.size)
                        if (result != null) {
                            viewModel.imageList[newImagePosition].progressBarVisibility = false
                            viewModel.imageList[newImagePosition].imageUrl =
                                "http://api.kadbyte.com/assets/${result.assetId}"
                            withContext(Main) {
                                binding.ItemAddImageList.adapter?.notifyDataSetChanged()
                            }
                        }
                    }
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data)

    }

    //Creating an empty file for writing to the file
    private fun createImageFile(): File {
        val name = UUID.randomUUID().toString()
        return File.createTempFile(name, ".jpg", externalCacheDir)
    }

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1244
    }

    override fun onAddClick() {
        dialog = AddPhotoDialog()
        dialog.show(supportFragmentManager, "Add Photo Dialog")
    }

}
