package vn.gotech.audiobook.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import vn.gotech.audiobook.R
import vn.gotech.audiobook.base.BaseActivity
import vn.gotech.audiobook.databinding.ActivityHomeBinding
import vn.gotech.audiobook.extensions.hideKeyboard
import vn.gotech.audiobook.widget.CountSpecificPager

class HomeActivity : BaseActivity<ActivityHomeBinding>(), OnClickListener {
    companion object {
        const val TAB_HOME = 0
        const val TAB_ACCOUNT = 1
    }

    private lateinit var pagerAdapter: MainPagerAdapter
    private val colorActive = Color.parseColor("#FA573E")
    private val colorDefault = Color.parseColor("#808080")

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityHomeBinding =
        ActivityHomeBinding.inflate(layoutInflater)

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.apply {
            llHome.setOnClickListener(this@HomeActivity)
            llUser.setOnClickListener(this@HomeActivity)
            setUpViewPager()

            contentMainContainer.viewTreeObserver.addOnGlobalLayoutListener {
                val r = Rect()
                contentMainContainer.getWindowVisibleDisplayFrame(r)
                val heightDiff: Int = contentMainContainer.rootView.height - (r.bottom - r.top)
                if (heightDiff > 100) {
                    viewBottomNavigation.visibility = View.GONE
                } else {
                    viewBottomNavigation.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setUpViewPager() {
        binding.apply {
            viewPager.offscreenPageLimit = 2
            pagerAdapter = MainPagerAdapter(supportFragmentManager)
            viewPager.adapter = pagerAdapter
            viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    hideKeyboard()
                    setNavigationPosition(position)
                }
            })
            setNavigationPosition(0)
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setDefaultNavigation() {
        binding.apply {
            ivHome.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_home_24))
            tvHome.setTextColor(colorDefault)
            ivUser.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_account_24))
            tvUser.setTextColor(colorDefault)
        }
    }

    inner class MainPagerAdapter(fm: FragmentManager) : CountSpecificPager(fm, 4) {

        override fun getItem(position: Int): Fragment {
            return when (position) {

//                TAB_HOME -> {
//                    CategoryNewFragment.newInstance(Bundle())
//                }
//
//                TAB_ACCOUNT -> {
//                    ProfilesFragment.newInstance(Bundle())
//                }

                else -> {
                    Fragment()
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onClick(v: View?) {
        binding.apply {
            setDefaultNavigation()
            when (v?.id) {
                R.id.llHome -> {
                    Glide.with(ivHome.context).load(R.drawable.ic_baseline_home_24_active)
                        .into(ivHome)
                    tvHome.setTextColor(colorActive)
                    viewPager.currentItem = TAB_HOME
                }

                R.id.llUser -> {
                    Glide.with(ivHome.context).load(R.drawable.ic_baseline_account_24_active)
                        .into(ivUser)
                    tvUser.setTextColor(colorActive)
                    viewPager.currentItem = TAB_ACCOUNT
                }
            }
        }
    }

    private fun setNavigationPosition(position: Int) {
        binding.apply {
            setDefaultNavigation()
            when (position) {
                TAB_HOME -> {
                    Glide.with(ivHome.context).load(R.drawable.ic_baseline_home_24_active)
                        .into(ivHome)
                    tvHome.setTextColor(colorActive)
                }

                TAB_ACCOUNT -> {
                    Glide.with(ivHome.context).load(R.drawable.ic_baseline_account_24_active)
                        .into(ivUser)
                    tvUser.setTextColor(colorActive)
                }
            }
            viewPager.currentItem = position
        }
    }
}