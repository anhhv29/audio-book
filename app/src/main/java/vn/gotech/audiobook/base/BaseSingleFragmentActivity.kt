package vn.gotech.audiobook.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import vn.gotech.audiobook.R
import vn.gotech.audiobook.databinding.ActivityEmptyBinding

abstract class BaseSingleFragmentActivity : vn.gotech.audiobook.base.BaseActivity<ActivityEmptyBinding>() {

    private var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            currentFragment = createFragment(startupOptions())
            replaceFragmentInActivity(currentFragment!!, R.id.fragment_container)
        }
    }

    open fun startupOptions(): Bundle = Bundle()

    abstract fun createFragment(startupOption: Bundle): Fragment

    override fun inflateLayout(layoutInflater: LayoutInflater)  = ActivityEmptyBinding.inflate(layoutInflater)
}