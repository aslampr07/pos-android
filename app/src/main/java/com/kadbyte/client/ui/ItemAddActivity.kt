package com.kadbyte.client.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.kadbyte.client.AddPhotoDialog
import com.kadbyte.client.R

class ItemAddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_add)
    }

    fun tempClick(view: View){
        val dialog = AddPhotoDialog()
        dialog.show(supportFragmentManager, "Add Photo Diolog")
    }
}
