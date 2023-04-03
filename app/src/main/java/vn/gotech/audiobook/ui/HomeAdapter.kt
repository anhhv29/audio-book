package vn.gotech.audiobook.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import vn.gotech.audiobook.base.ClickableAdapter
import vn.gotech.audiobook.base.domain.ressponse.Book
import vn.gotech.audiobook.base.domain.ressponse.Category
import vn.gotech.audiobook.databinding.ItemHomeCategoryBinding
import vn.gotech.audiobook.ui.category.detail.BookAdapter
import vn.gotech.audiobook.ui.category.CategoryHomeAdapter
import java.util.*

@Suppress("CAST_NEVER_SUCCEEDS", "DEPRECATION")
class HomeAdapter : ClickableAdapter<Category>() {

    companion object {
        const val BOOK = 0
        const val CATEGORY = 1
        const val BOOK_CATEGORY_PAGE = 2
    }

    var isShowCategoryBackground = false

    internal inner class HomeHolder(itemView: ViewBinding) : ViewHolder<Category>(itemView) {
        @SuppressLint("UseCompatLoadingForDrawables")
        override fun populate(data: Category) {
            super.populate(data)
            (binding as ItemHomeCategoryBinding).apply {

                val adapter = when (data.type) {

                    CATEGORY -> {
                        CategoryHomeAdapter()
                    }

                    BOOK_CATEGORY_PAGE -> {
                        BookAdapter()
                    }

                    else -> {
                        BookAdapter()
                    }
                }

                when (data.type) {

                    CATEGORY -> {
                        (adapter as CategoryHomeAdapter).replaceAll(data.listCategoryHome)
                        adapter.listener = object : BaseAdapterAction<Category> {
                            override fun click(position: Int, data: Category, code: Int) {
                                val adapterPosition = adapterPosition
                                listener?.click(position, getItem(adapterPosition), CATEGORY)
                            }
                        }
                    }

                    BOOK_CATEGORY_PAGE -> {
                        (adapter as BookAdapter).replaceAll(data.listBook)
                        adapter.listener = object : BaseAdapterAction<Book> {
                            override fun click(position: Int, data: Book, code: Int) {
                                val adapterPosition = adapterPosition
                                listener?.click(
                                    position,
                                    getItem(adapterPosition),
                                    code
                                )
                            }
                        }
                    }

                    else -> {
                        (adapter as BookAdapter).replaceAll(data.listBook)
                        adapter.listener = object : BaseAdapterAction<Book> {
                            override fun click(position: Int, data: Book, code: Int) {
                                val adapterPosition = adapterPosition
                                listener?.click(position, getItem(adapterPosition), BOOK)
                            }
                        }
                    }

                }

                if (data.type == CATEGORY) {
                    tvHomeCategory.text = data.name.ifEmpty { data.title }
                    rvHomeContent.setHasFixedSize(true)
                    rvHomeContent.isNestedScrollingEnabled = false
                    rvHomeContent.adapter = adapter
                    val layoutManager =
                        LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
                    rvHomeContent.layoutManager = layoutManager
                } else {
                    if (adapter.itemCount == 0) {
                        itemView.visibility = View.GONE
                    } else itemView.visibility = View.VISIBLE
                    tvHomeCategory.visibility = View.VISIBLE
                    tvHomeCategory.text = data.name.ifEmpty { data.title }
                    rvHomeContent.adapter = adapter
                    rvHomeContent.layoutManager =
                        LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).type
    }

    override fun getChildLayoutResource(
        layoutInflater: LayoutInflater,
        viewType: Int
    ): ViewBinding {
        return ItemHomeCategoryBinding.inflate(layoutInflater)
    }

    override fun createHolder(v: ViewBinding, viewType: Int): ViewHolder<Category> {
        return HomeHolder(v)
    }
}