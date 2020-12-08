package com.kadbyte.client.ui.dialog

import android.app.ActionBar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.kadbyte.client.R
import com.kadbyte.client.viewmodel.CategoryViewModel
import kotlinx.android.synthetic.main.dialog_add_category.*
import kotlinx.android.synthetic.main.dialog_add_category.view.*

class AddCategoryDialog : DialogFragment() {

    private val model: CategoryViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_add_category, container)
        view.newCategorySaveButton.setOnClickListener {
            model.createCategory(categoryNameInput.text.toString())
        }

        model.itemInsertSuccess.observe(viewLifecycleOwner, Observer {isSuccess->
            findNavController().previousBackStackEntry?.savedStateHandle?.set("isDone", isSuccess)
            findNavController().navigateUp()
        })

        return view
    }

    override fun onStart() {
        dialog?.window?.setLayout(
            ActionBar.LayoutParams.MATCH_PARENT,
            ActionBar.LayoutParams.WRAP_CONTENT
        )

        super.onStart()
    }
}