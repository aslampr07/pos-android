package com.kadbyte.client.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kadbyte.client.R
import com.kadbyte.client.adapter.ItemImageListAdapter
import com.kadbyte.client.databinding.FragmentItemAddBinding
import com.kadbyte.client.viewmodel.ItemAddViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ItemAddFragment : Fragment() {

    private val viewModel by viewModels<ItemAddViewModel>()
    private lateinit var file: File
    private var uploadQueueCount = 0;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentItemAddBinding.inflate(inflater)

        binding.viewmodel = viewModel

        binding.StoreDropDown.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            arrayOf("Select Inventory", "Amazon", "FlipKart", "+ Add Item")
        )

        binding.itemImageList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val adapter = ItemImageListAdapter()
        binding.itemImageList.adapter = adapter

        binding.StoreDropDown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 == 3) {
                    findNavController().navigate(R.id.addVendorDialog)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        val getContent =
            registerForActivityResult(ActivityResultContracts.TakePicture()) { isPhotoSaved ->
                if (isPhotoSaved) {
                    adapter.addItem("")
                    lifecycleScope.launch {
                        val result = viewModel.uploadImage(file)
                        if (result != null) {
                            Toast.makeText(requireContext(), result.assetId, Toast.LENGTH_SHORT)
                                .show()
                            viewModel.imageList.add(result.assetId)
                            adapter.urlList[adapter.urlList.size - 1] = result.assetId
                            adapter.notifyItemChanged(adapter.urlList.size - 1)
                        }
                    }
                }
            }

        binding.attachImageButton.setOnClickListener {
            // Replace this with new random string generator.
            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
            file = File.createTempFile(timeStamp, ".jpg", requireContext().externalCacheDir)
            val photoUri = FileProvider.getUriForFile(
                requireContext(),
                "com.kadbyte.client.fileprovider",
                file
            )
            getContent.launch(photoUri)
        }

        viewModel.aliasError.observe(viewLifecycleOwner, { errorMessage ->
            binding.aliasInput.error = errorMessage
        })

        return binding.root
    }
}