package bg.qponer.android.ui

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import bg.qponer.android.R
import bg.qponer.android.data.model.BusinessType
import com.bumptech.glide.Glide

@BindingAdapter("android:imageUrl")
fun ImageView.imageUrl(imageUrl: String) =
    Glide.with(this)
        .load(imageUrl)
        .into(this)

@BindingAdapter("android:businessType")
fun TextView.setDrawableForBusinessType(type: BusinessType) {
    val imageResId = when (type) {
        BusinessType.BAR -> R.drawable.ic_local_bar_black_24dp
        BusinessType.RESTAURANT -> R.drawable.ic_restaurant_black_24dp
        BusinessType.DISCO -> R.drawable.ic_music_note_black_24dp
    }
    setCompoundDrawablesRelativeWithIntrinsicBounds(imageResId, 0, 0, 0)
}