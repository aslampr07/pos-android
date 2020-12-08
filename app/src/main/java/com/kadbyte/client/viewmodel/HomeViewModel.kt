package com.kadbyte.client.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kadbyte.client.model.Item
import com.kadbyte.client.repository.ItemRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(private val itemRepository: ItemRepository) :
    ViewModel() {

    val itemList: MutableLiveData<List<Item>> = MutableLiveData()

    fun getItemList() {
        viewModelScope.launch(IO) {
            val items = itemRepository.getItemList()
            if (items != null) {
                itemList.postValue(items)
            }
        }
    }
}