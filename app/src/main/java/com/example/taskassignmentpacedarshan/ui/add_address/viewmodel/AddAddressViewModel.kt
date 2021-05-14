package com.example.taskassignmentpacedarshan.ui.add_address.viewmodel

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.provider.MediaStore
import android.view.Gravity
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDialog
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.lifecycle.ViewModel
import com.example.taskassignmentpacedarshan.R
import com.example.taskassignmentpacedarshan.di.managers.ApiManager
import com.example.taskassignmentpacedarshan.ui.add_address.view.AddAddressActivity
import com.github.dhaval2404.imagepicker.ImagePicker


class AddAddressViewModel constructor(context: Context, val apiManager: ApiManager) : ViewModel(){


    fun pickCameraImage(activity: Activity) {
        if (checkSelfPermission(activity,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(activity,arrayOf(Manifest.permission.CAMERA), AddAddressActivity.MY_CAMERA_PERMISSION_CODE);
        }else{
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            activity.startActivityForResult(cameraIntent, AddAddressActivity.CAMERA_IMAGE_REQ_CODE)
        }
    }

    fun pickGalleryImage(activity: Activity) {
        ImagePicker.with(activity)
            // Crop Image(User can choose Aspect Ratio)
            .crop()
            // User can only select image from Gallery
            .galleryOnly()
            .galleryMimeTypes( // no gif images at all
                mimeTypes = arrayOf(
                    "image/png",
                    "image/jpg",
                    "image/jpeg"
                )
            )
            // Image resolution will be less than 1080 x 1920
            .maxResultSize(1080, 1920)
            .start(AddAddressActivity.GALLERY_IMAGE_REQ_CODE)
    }

    fun showImagePickDialog(activity: Activity) {
        val dialogPickImage = AppCompatDialog(activity)
        dialogPickImage.setContentView(R.layout.dialog_select_image)
        //Grab the window of the dialog, and change the width
        val lp: WindowManager.LayoutParams = WindowManager.LayoutParams()
        val window = dialogPickImage.window
        dialogPickImage.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        lp.copyFrom(window!!.attributes)
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp.gravity = Gravity.BOTTOM
        window.attributes = lp
        val tvGallery: AppCompatTextView = dialogPickImage.findViewById(R.id.tvGallery)!!
        val tvCamera: AppCompatTextView = dialogPickImage.findViewById(R.id.tvCamera)!!
        val tvCancel: AppCompatTextView = dialogPickImage.findViewById(R.id.tvCancel)!!
        tvGallery.setOnClickListener {
            pickGalleryImage(activity)
            dialogPickImage.dismiss()
        }
        tvCamera.setOnClickListener {
            pickCameraImage(activity)
            dialogPickImage.dismiss()
        }
        tvCancel.setOnClickListener { dialogPickImage.dismiss() }
        dialogPickImage.show()
    }


}