package com.singhsoft.newsapp.binding

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import java.text.SimpleDateFormat
import java.util.*


@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}

@BindingAdapter("utcToIstDate")
fun bindUtcDateToIst(view: TextView, utcDate: String) {
    val utcFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val utcTimeInstance: Date = utcFormatter.parse(utcDate)
    val indianFormatter = SimpleDateFormat("yyyy-MM-dd")
    indianFormatter.timeZone = TimeZone.getTimeZone("Asia/Kolkata")
    val indianTime: String = indianFormatter.format(utcTimeInstance)
    view.text = indianTime
}



