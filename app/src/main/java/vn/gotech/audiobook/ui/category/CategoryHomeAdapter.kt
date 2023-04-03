package vn.gotech.audiobook.ui.category

import android.graphics.Color
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import vn.gotech.audiobook.R
import vn.gotech.audiobook.base.ClickableAdapter
import vn.gotech.audiobook.base.domain.ressponse.Category
import vn.gotech.audiobook.databinding.ItemCategoryBinding

@Suppress("CAST_NEVER_SUCCEEDS")
class CategoryHomeAdapter : ClickableAdapter<Category>() {
    var positionColor = 0

    override fun getItemViewType(position: Int): Int {
        return getItem(position).type
    }

    override fun onBindViewHolder(
        holder: ViewHolder<Category>,
        position: Int
    ) {
        super.onBindViewHolder(holder, position)
        holder.apply {
            itemView.setOnClickListener {
                val adapterPosition = holder.adapterPosition
                if (adapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener

                listener?.click(adapterPosition, getItem(adapterPosition))
            }
        }
    }

    internal inner class Holder(binding: ViewBinding) : ViewHolder<Category>(binding) {
        override fun populate(data: Category) {
            super.populate(data)
            (binding as ItemCategoryBinding).apply {
                when (positionColor) {
                    0 -> {
                        ctRoot.setBackgroundResource(R.drawable.bg_category_root_0)
                        tvCategory.setTextColor(Color.parseColor("#FF9359"))
                    }

                    1 -> {
                        ctRoot.setBackgroundResource(R.drawable.bg_category_root_1)
                        tvCategory.setTextColor(Color.parseColor("#5847C6"))
                    }

                    2 -> {
                        ctRoot.setBackgroundResource(R.drawable.bg_category_root_2)
                        tvCategory.setTextColor(Color.parseColor("#FFA724"))
                    }

                    3 -> {
                        ctRoot.setBackgroundResource(R.drawable.bg_category_root_3)
                        tvCategory.setTextColor(Color.parseColor("#DA579D"))
                    }

                    4 -> {
                        ctRoot.setBackgroundResource(R.drawable.bg_category_root_4)
                        tvCategory.setTextColor(Color.parseColor("#006DD0"))
                    }
                }

                Glide.with(ivCategoryIcon.context).load(data.image).placeholder(R.drawable.ic_logo)
                    .error(R.drawable.ic_logo).into(ivCategoryIcon)

                tvCategory.text = data.name

                if (positionColor == 4) positionColor = 0
                else
                    positionColor++
            }
        }
    }

    override fun getChildLayoutResource(
        layoutInflater: LayoutInflater,
        viewType: Int
    ): ViewBinding {
        return ItemCategoryBinding.inflate(layoutInflater)
    }

    override fun createHolder(
        v: ViewBinding,
        viewType: Int
    ): ViewHolder<Category> {
        return Holder(v)
    }
}