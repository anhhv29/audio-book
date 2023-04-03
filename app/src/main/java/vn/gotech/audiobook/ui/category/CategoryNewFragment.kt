package vn.gotech.audiobook.ui.category

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import vn.gotech.audiobook.base.BaseFragment
import vn.gotech.audiobook.base.ClickableAdapter
import vn.gotech.audiobook.base.common.Const
import vn.gotech.audiobook.base.domain.ressponse.Category
import vn.gotech.audiobook.databinding.ContentSwipableRecyclerviewBinding
import vn.gotech.audiobook.extensions.appViewModel
import vn.gotech.audiobook.ui.HomeAdapter
import vn.gotech.audiobook.ui.category.detail.CategoryDetailActivity

class CategoryNewFragment : BaseFragment<ContentSwipableRecyclerviewBinding>(),
    SwipeRefreshLayout.OnRefreshListener {
    private val viewModel: CategoryNewViewModel by appViewModel()
    override fun inflateLayout(layoutInflater: LayoutInflater): ContentSwipableRecyclerviewBinding =
        ContentSwipableRecyclerviewBinding.inflate(layoutInflater)

    companion object {
        fun newInstance(params: Bundle): CategoryNewFragment {
            val f = CategoryNewFragment()
            f.arguments = params
            return f
        }
    }

    private var listCategory = mutableListOf<Category>()

    private val adapter = HomeAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            swipe.setOnRefreshListener(this@CategoryNewFragment)
            viewRecyclerview.setHasFixedSize(true)
            viewRecyclerview.isNestedScrollingEnabled = true
            val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            viewRecyclerview.layoutManager = layoutManager
            viewRecyclerview.adapter = adapter
        }

        adapter.isShowCategoryBackground = true
        adapter.listener = object : ClickableAdapter.BaseAdapterAction<Category> {
            override fun click(position: Int, data: Category, code: Int) {
                when (code) {
                    HomeAdapter.CATEGORY -> {
                        val intent = Intent(context, CategoryDetailActivity::class.java)
                        intent.putExtra(
                            Const.TransferKey.EXTRA_ID,
                            data.listCategoryHome[position].id
                        )
                        intent.putExtra(
                            Const.TransferKey.EXTRA_TITLE,
                            data.listCategoryHome[position].name
                        )
                        startActivity(intent)
                    }
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.errorSignal.observe(viewLifecycleOwner, Observer {
            hideProgressDialog()
        })

        viewModel.loadCategorySuccess.observe(viewLifecycleOwner, Observer {
            if (binding.swipe.isRefreshing)
                binding.swipe.isRefreshing = false
            listCategory.clear()
            hideProgressDialog()
            val category = Category()
            category.type = HomeAdapter.CATEGORY
            category.title = "Danh mục"
            category.listCategoryHome = it.data ?: mutableListOf()
            listCategory.add(0, category)
            for (cate in it.data ?: mutableListOf()) {
                listCategory.add(cate)
                loadBookByCategory(cate.id)
            }
        })

        viewModel.loadBookByCategorySuccess.observe(viewLifecycleOwner, Observer {
            for ((i, cate) in listCategory.withIndex()) {
                if (cate.id == it.data?.id) {
                    hideProgressDialog()
                    listCategory[i].title = cate.name
                    listCategory[i].type = HomeAdapter.BOOK_CATEGORY_PAGE
                    listCategory[i].listBook = it.data.listBook
                    adapter.replaceAll(listCategory)
                    break
                }
            }
        })
        firstLoad()
    }

    private fun firstLoad() {
        context?.let { viewModel.loadCategory(it) }
    }

    private fun loadBookByCategory(id: Int) {
        context?.let { viewModel.loadData(it, id) }
    }

    override fun onRefresh() {
        firstLoad()
    }
}