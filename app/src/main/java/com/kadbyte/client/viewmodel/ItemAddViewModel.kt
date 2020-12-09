package com.kadbyte.client.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kadbyte.client.KadByteService
import com.kadbyte.client.model.ImageUploadResponse
import com.kadbyte.client.model.Item
import com.kadbyte.client.model.ItemImage
import com.kadbyte.client.repository.ItemRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class ItemAddViewModel @ViewModelInject constructor(
    private val repository: ItemRepository,
    private val service: KadByteService
) :
    ViewModel() {


    val itemName = MutableLiveData<String>()
    val itemAlias = MutableLiveData<String>()
    val itemPrice = MutableLiveData<String>()

    fun saveClick(isSaveNew: Boolean) {
        val item = itemPrice.value?.toFloat()?.let {
            Item(
                "", itemName.value.toString(), itemAlias.value.toString(),
                it, emptyList(), "", "FFFFFF"
            )
        }
        viewModelScope.launch(IO) {
            if (item != null) {
                repository.insertItem(item)
            }
        }
    }

    suspend fun uploadImage(file: File, position: Int): ImageUploadResponse? {
        val requestBody = file.asRequestBody()
        val part = MultipartBody.Part.createFormData("picture", file.name, requestBody)
        val response = service.uploadImage(part)
        if (response.isSuccessful) {
            return response.body()
        }
        return null
    }
}