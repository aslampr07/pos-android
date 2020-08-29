package com.kadbyte.client.repository

import android.util.Log
import com.kadbyte.client.KadByteService
import com.kadbyte.client.model.Item
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemRepository @Inject constructor(private val service: KadByteService) {


    private var itemList: MutableList<Item>? = null

    suspend fun getItemList(): List<Item>? {
        if (itemList == null) {
            val response = service.getItemList()
            if (response.isSuccessful) {
                itemList = response.body()?.toMutableList()
            }
        }
        return itemList;
    }

    suspend fun insertItem(item: Item): Boolean {
        val response = service.insertItem(item)
        if(response.isSuccessful){
            response.body()?.let { itemList?.add(it) }
        }
        return false
    }
}