package com.kadbyte.client.ui

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.kadbyte.client.AddPhotoDialog
import com.kadbyte.client.R
import com.kadbyte.client.adapter.AddItemImageAdapter
import com.kadbyte.client.databinding.ActivityItemAddBinding
import com.kadbyte.client.model.StagedImage
import com.kadbyte.client.viewmodel.ItemAddViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ItemAddActivity : AppCompatActivity(), AddPhotoDialog.FragmentCallback {

    private lateinit var dialog: AddPhotoDialog
    private lateinit var list: ArrayList<StagedImage>
    private lateinit var imageListview: RecyclerView
    private lateinit var adapter: AddItemImageAdapter

    private val viewModel: ItemAddViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityItemAddBinding>(this, R.layout.activity_item_add)

        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

    }

    fun tempClick(view: View) {
        dialog = AddPhotoDialog()
        dialog.show(supportFragmentManager, "Add Photo Dialog")
    }

    override fun onCameraClick() {

        dialog.dismiss()

        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                val photoFile = createImageFile()
                photoFile.also {
                    val photoUri = FileProvider.getUriForFile(
                        this, "com.example.android.fileprovider", it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
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

    lateinit var currentPhotoPath: String

    private fun createImageFile(): File {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storage = externalCacheDir

        return File.createTempFile("JPEG_${timestamp}", ".jpg", storage).apply {
            currentPhotoPath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (Activity.RESULT_OK == resultCode) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    val bitmap = BitmapFactory.decodeFile(currentPhotoPath)
                    list.add(StagedImage(bitmap))
                    adapter.notifyDataSetChanged()
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data)

    }

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1244
    }

}
