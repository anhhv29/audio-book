package vn.gotech.audiobook.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import vn.gotech.audiobook.base.BaseSingleFragmentActivity

class LoginActivity : BaseSingleFragmentActivity() {
    override fun createFragment(startupOption: Bundle): Fragment {
        return LoginFragment.newInstance(startupOption)
    }

    override fun startupOptions(): Bundle {
        return intent.extras ?: Bundle()
    }
}