package com.ui.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@androidx.databinding.BindingAdapter("app:error")
fun setError(textInputLayout: TextInputLayout, error:String){
    textInputLayout.error=error
}
@BindingAdapter("app:imageSrc")
fun setImage(imageView: ImageView, imageId:Int){
    imageView.setImageResource(imageId)
}