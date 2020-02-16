package com.kadbyte.client

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.DialogFragment

class AddPhotoDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val build = AlertDialog.Builder(activity)
        build.setView(R.layout.dialog_add_photo_options)
        return build.create()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val cameraButton = view.findViewById<ImageButton>(R.id.AddPhotoCameraButton)
        cameraButton.setOnClickListener {
            Toast.makeText(context, "Test", Toast.LENGTH_LONG).show()
        }
    }
}