package com.example.taskassignmentpacedarshan.ui.add_address.view

import android.R.attr
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.taskassignmentpacedarshan.BR
import com.example.taskassignmentpacedarshan.R
import com.example.taskassignmentpacedarshan.common.base.BaseActivity
import com.example.taskassignmentpacedarshan.ui.add_address.viewmodel.AddAddressViewModel
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.activity_add_address.*
import kotlinx.android.synthetic.main.layout_loading.*
import org.koin.android.ext.android.inject
import java.io.File


class AddAddressActivity : BaseActivity() {
    private val viewModel: AddAddressViewModel by inject()

    companion object {
        const val GALLERY_IMAGE_REQ_CODE = 111
        const val CAMERA_IMAGE_REQ_CODE = 222
        const val MY_CAMERA_PERMISSION_CODE = 333
    }

    private var mCameraFile: File? = null
    private var mGalleryFile: File? = null

    var isFirstImageClicked = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ivBack.setOnClickListener {
            finish()
        }
        frameAddressOne.setOnClickListener {
            isFirstImageClicked = true
            viewModel.showImagePickDialog(this)
        }

        frameAddressTwo.setOnClickListener {
            isFirstImageClicked = false
            viewModel.showImagePickDialog(this)
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            Log.e("TAG", "Path:${ImagePicker.getFilePath(data)}")
            // File object will not be null for RESULT_OK
            when (requestCode) {
                GALLERY_IMAGE_REQ_CODE -> {
                    val file = ImagePicker.getFile(data)!!
                    mGalleryFile = file
                    if (isFirstImageClicked) {
                        tvSelectImage1.visibility = View.GONE
                        ivSelectImage1.setLocalImage(file)
                    } else {
                        tvSelectImage2.visibility = View.GONE
                        ivSelectImage2.setLocalImage(file)
                    }

                    isFirstImageClicked = false
                }
                CAMERA_IMAGE_REQ_CODE -> {
                    if (isFirstImageClicked) {
                        tvSelectImage1.visibility = View.GONE
                        ivSelectImage1.setImageBitmap(data?.extras?.get("data") as Bitmap?)
                    } else {
                        tvSelectImage2.visibility = View.GONE
                        ivSelectImage2.setImageBitmap(data?.extras?.get("data") as Bitmap?)
                    }

                    isFirstImageClicked = false
                }
            }
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_add_address
    }

    override fun getVm(): ViewModel {
        return viewModel
    }

    override fun getBindingVariable(): Int {
        return BR.addAddressViewModel
    }

    override fun getLoadingView(): View? {
        return loadingView
    }

    fun ImageView.setLocalImage(file: File, applyCircle: Boolean = false) {
        val glide = Glide.with(this).load(file)
        if (applyCircle) {
            glide.apply(RequestOptions.circleCropTransform()).into(this)
        } else {
            glide.into(this)
        }
    }
}