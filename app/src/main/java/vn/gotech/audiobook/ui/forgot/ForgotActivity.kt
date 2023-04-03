package vn.gotech.audiobook.ui.forgot

import android.os.Bundle
import androidx.fragment.app.Fragment
import vn.gotech.audiobook.base.BaseSingleFragmentActivity

class ForgotActivity : BaseSingleFragmentActivity() {
    override fun createFragment(startupOption: Bundle): Fragment {
        return ForgotFragment.newInstance(startupOption)
    }

    override fun startupOptions(): Bundle {
        return intent.extras ?: Bundle()
    }
}