package com.kadbyte.client.repository

import com.kadbyte.client.KadByteService
import com.kadbyte.client.model.Category
import javax.inject.Inject

class CategoryRepository @Inject constructor(private val service: KadByteService) {

    private var categoryList: List<Category>? = null

    suspend fun getCategoryList(): List<Category>? {
        if (categoryList == null) {
            val response = service.getCategoryList()
            if (response.isSuccessful) {
                categoryList = response.body()?.toMutableList()
            }
        }
        return categoryList;
    }

}