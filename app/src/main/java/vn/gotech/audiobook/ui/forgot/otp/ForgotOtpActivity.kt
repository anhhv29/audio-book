package vn.gotech.audiobook.ui.forgot.otp

import android.os.Bundle
import androidx.fragment.app.Fragment
import vn.gotech.audiobook.base.BaseSingleFragmentActivity

class ForgotOtpActivity : BaseSingleFragmentActivity() {
    override fun createFragment(startupOption: Bundle): Fragment {
        return ForgotOtpFragment.newInstance(startupOption)
    }

    override fun startupOptions(): Bundle {
        return intent.extras ?: Bundle()
    }
}