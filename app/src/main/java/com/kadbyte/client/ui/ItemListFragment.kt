package com.kadbyte.client.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kadbyte.client.adapter.HomeItemListAdapter
import com.kadbyte.client.databinding.FragmentItemListBinding
import com.kadbyte.client.viewmodel.HomeViewModel

class ItemListFragment : Fragment() {

    private val model: HomeViewModel by activityViewModels()

    private var _binding: FragmentItemListBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemListBinding.inflate(inflater, container, false)

        binding.mainItemList.layoutManager = LinearLayoutManager(context);

        model.itemList.observe( viewLifecycleOwner, Observer { list->
            binding.mainItemList.adapter = HomeItemListAdapter(list)
        })

        model.getItemList()

        val view = binding.root;
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null;
    }
}