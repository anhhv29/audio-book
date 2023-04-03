package vn.gotech.audiobook.ui.category.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.GridLayoutManager
import vn.gotech.audiobook.R
import vn.gotech.audiobook.base.BaseFragment
import vn.gotech.audiobook.base.ClickableAdapter
import vn.gotech.audiobook.base.common.Const
import vn.gotech.audiobook.base.domain.BaseErrorSignal
import vn.gotech.audiobook.base.domain.ressponse.Book
import vn.gotech.audiobook.base.domain.utils.Status
import vn.gotech.audiobook.databinding.FragmentCategoryDetailBinding
import vn.gotech.audiobook.extensions.appViewModel

class CategoryDetailFragment : BaseFragment<FragmentCategoryDetailBinding>() {
    private val viewModel: CategoryDetailViewModel by appViewModel()
    private val adapter = BookAdapter()
    private var categoryId = -1
    private var categoryTitle = ""
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentCategoryDetailBinding =
        FragmentCategoryDetailBinding.inflate(layoutInflater)

    companion object {
        fun newInstance(params: Bundle): CategoryDetailFragment {
            val f = CategoryDetailFragment()
            f.arguments = params
            return f
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryId = arguments?.getInt(Const.TransferKey.EXTRA_ID, -1) ?: -1
        categoryTitle = arguments?.getString(Const.TransferKey.EXTRA_TITLE) ?: ""
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.loadBookSuccess.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    showLoading()
                }
                Status.SUCCESS -> {
                    hideLoading()
                    if (it.code == BaseErrorSignal.RESPONSE_SUCCESS) {
                        it.data?.let { data ->
                            adapter.replaceAll(data)
                        }
                    } else {
                        hideLoading()
                        resolveError(it.code, it.message)
                    }
                }

                Status.ERROR -> {
                    hideLoading()
                    resolveError(it.code, it.message)
                }
            }
        }
        context?.let { firstLoadByCategory(it, categoryId) }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvCategoryName.text = categoryTitle
        binding.rvCategory.scheduleLayoutAnimation()
        binding.tvCategoryName.setOnClickListener {
            it.context?.sendBroadcast(Intent(Const.ActionKey.ACTION_CLOSE_CATEGORY_DETAIL))
        }

        val layoutManager = GridLayoutManager(
            context,
            2,
            GridLayoutManager.VERTICAL,
            false
        )

        binding.apply {
            rvCategory.adapter = adapter
            rvCategory.setHasFixedSize(true)
            rvCategory.layoutManager = layoutManager

            rvCategory.layoutAnimation = AnimationUtils.loadLayoutAnimation(
                view.context,
                R.anim.grid_layout_animation_from_bottom
            )
        }

        adapter.listener = object : ClickableAdapter.BaseAdapterAction<Book> {
            override fun click(position: Int, data: Book, code: Int) {
            }
        }

    }

    private fun firstLoadByCategory(context: Context, id: Int) {
        reloadData = true
        adapter.clear()
        viewModel.loadData(context, id)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        try {
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}