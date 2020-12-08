package com.kadbyte.client.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kadbyte.client.R
import com.kadbyte.client.adapter.CategoryListAdapter
import com.kadbyte.client.viewmodel.CategoryViewModel
import kotlinx.android.synthetic.main.fragment_category_list.view.*

class CategoryListFragment : Fragment() {

    private val model: CategoryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_category_list, container, false)

        view.addCategoryFab.setOnClickListener {
            findNavController().navigate(R.id.addCategoryDialog)
        }

        view.categoryList.layoutManager = LinearLayoutManager(context)
        val adapter = CategoryListAdapter(model.itemList)
        view.categoryList.adapter = adapter
        model.changedEvent.observe(viewLifecycleOwner, Observer {
            adapter.notifyDataSetChanged()
        })
        model.getCategoryList()

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Boolean>("isDone")
            ?.observe(viewLifecycleOwner, Observer {isSuccess ->
                if(isSuccess){
                    adapter.notifyDataSetChanged()
                }
            })

        
        return view
    }

    override fun onResume() {
        super.onResume()
        Log.v("RESUME", "Resume called")
    }

}