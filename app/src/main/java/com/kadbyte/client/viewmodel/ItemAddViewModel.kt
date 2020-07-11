package com.kadbyte.client.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kadbyte.client.model.Item
import com.kadbyte.client.repository.ItemRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class ItemAddViewModel @ViewModelInject constructor(val repository: ItemRepository) : ViewModel() {

    private val TAG = "ItemAddViewModel"

    val itemName = MutableLiveData<String>()
    val itemAlias = MutableLiveData<String>()
    val itemPrice = MutableLiveData<String>()

    fun saveClick() {
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
}