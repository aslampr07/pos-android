package com.kadbyte.client.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kadbyte.client.R
import com.kadbyte.client.adapter.CategoryListAdapter
import com.kadbyte.client.viewmodel.CategoryViewModel
import com.kadbyte.client.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_category_list.*
import kotlinx.android.synthetic.main.fragment_category_list.view.*
import okhttp3.internal.notify

class CategoryListFragment : Fragment() {

    private val model: CategoryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_category_list, container, false)

        view.categoryList.layoutManager = LinearLayoutManager(context)
        val adapter = CategoryListAdapter(model.itemList)
        view.categoryList.adapter = adapter

        model.changedEvent.observe(viewLifecycleOwner, Observer {
            adapter.notifyDataSetChanged()
        })

        model.getCategoryList()

        return view
    }

}