package com.kadbyte.client.repository

import com.kadbyte.client.KadByteService
import com.kadbyte.client.model.Category
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepository @Inject constructor(private val service: KadByteService) {

    private var categoryList: MutableList<Category>? = null

    suspend fun getCategoryList(): List<Category>? {
        if (categoryList == null) {
            val response = service.getCategoryList()
            if (response.isSuccessful) {
                categoryList = response.body()?.toMutableList()
            }
        }
        return categoryList;
    }

    suspend fun insertItem(category: Category): Boolean {
        val response = service.createCategory(category)
        if(response.isSuccessful){
            response.body()?.let { categoryList?.add(it) }
            return true
        }
        return false
    }

}