package vn.gotech.audiobook.ui.category.detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.google.android.exoplayer2.util.Log
import vn.gotech.audiobook.R
import vn.gotech.audiobook.base.ClickableAdapter
import vn.gotech.audiobook.base.domain.ressponse.Book
import vn.gotech.audiobook.databinding.ItemBookBinding
import vn.gotech.audiobook.extensions.asThumbnailToString

class BookAdapter : ClickableAdapter<Book>() {
    internal inner class Holder(itemView: ViewBinding) : ViewHolder<Book>(itemView) {
        @SuppressLint("UseCompatLoadingForDrawables")
        override fun populate(data: Book) {
            super.populate(data)
            (binding as ItemBookBinding).apply {
                Log.d("image",data.thumbnail.toString())
                Glide.with(ivBanner.context).load(data.thumbnail?.asThumbnailToString())
                    .transform(GranularRoundedCorners(0f, 0f, 0f, 20f))
                    .placeholder(R.drawable.book_cover_placeholder)
                    .error(R.drawable.book_cover_placeholder)
                    .thumbnail(0.5f)
                    .into(ivBanner)
            }
        }
    }

    override fun getChildLayoutResource(
        layoutInflater: LayoutInflater,
        viewType: Int
    ): ViewBinding {
        return ItemBookBinding.inflate(layoutInflater)
    }

    override fun createHolder(
        v: ViewBinding,
        viewType: Int
    ): ViewHolder<Book> {
        return Holder(v)
    }
}