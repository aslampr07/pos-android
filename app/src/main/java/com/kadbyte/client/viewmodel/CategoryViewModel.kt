package com.kadbyte.client.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kadbyte.client.model.Category
import com.kadbyte.client.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryViewModel @ViewModelInject constructor(private val repository: CategoryRepository) :
    ViewModel() {

    val itemList = mutableListOf<Category>()

    val changedEvent = MutableLiveData<Boolean>()

    fun getCategoryList() {
        viewModelScope.launch(Dispatchers.IO) {
            itemList.clear()
            itemList.addAll(repository.getCategoryList()!!)
            changedEvent.postValue(changedEvent.value)
        }
    }

}