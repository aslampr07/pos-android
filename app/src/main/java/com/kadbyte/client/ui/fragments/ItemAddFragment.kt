package com.kadbyte.client.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kadbyte.client.R
import com.kadbyte.client.databinding.FragmentItemAddBinding
import com.kadbyte.client.viewmodel.ItemAddViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_item_add.view.*

@AndroidEntryPoint
class ItemAddFragment : Fragment() {

    private val viewModel by viewModels<ItemAddViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentItemAddBinding.inflate(inflater)
        binding.viewmodel = viewModel
        binding.StoreDropDown.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            arrayOf("Select Inventory","Amazon", "FlipKart", "+ Add Item")
        )
        binding.StoreDropDown.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(context, "Selected $p2", Toast.LENGTH_LONG).show()
                if(p2 == 3){
                    findNavController().navigate(R.id.addVendorDialog)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        return binding.root
    }
}