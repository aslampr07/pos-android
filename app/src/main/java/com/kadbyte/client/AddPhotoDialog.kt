package com.kadbyte.client

import android.app.ActionBar
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.DialogFragment

class AddPhotoDialog : DialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.dialog_add_photo_options, container)
        val ui = activity as FragmentCallback

        view.findViewById<ImageButton>(R.id.AddPhotoCameraButton).setOnClickListener {
            ui.onCameraClick()
        }

        view.findViewById<ImageButton>(R.id.AddPhotoGalleryButton).setOnClickListener {
            ui.onGalleryClick()
        }

        view.findViewById<ImageButton>(R.id.AddPhotoFileButton).setOnClickListener {
            ui.onFileClick()
        }

        return view
    }

    override fun onStart() {
        dialog?.window?.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setGravity(Gravity.TOP)

        super.onStart()
    }

    interface FragmentCallback {
        fun onCameraClick()
        fun onGalleryClick()
        fun onFileClick()
    }
}
