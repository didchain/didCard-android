package com.didchain.android.lib.viewadapter.image

import android.widget.ImageView
import androidx.databinding.BindingAdapter


@BindingAdapter(value = ["url", "placeholderRes","errRes","circle","corner"], requireAll = false)
fun setImageUri(imageView: ImageView, url: String, placeholderRes: Int,errRes: Int,circle: Boolean,corner: Float) {
//    if (!TextUtils.isEmpty(url)) { //使用coil框架加载图片
//        imageView.load(url)
//    }

}

@BindingAdapter("imageId")
fun setBackground(imageView: ImageView, imageId:Int) {
    imageView.setBackgroundResource(imageId)
}



