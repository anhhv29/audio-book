package vn.gotech.audiobook.ui.change_password

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import vn.gotech.audiobook.R
import vn.gotech.audiobook.base.BaseFragment
import vn.gotech.audiobook.base.common.Const
import vn.gotech.audiobook.base.common.UserDataManager
import vn.gotech.audiobook.base.domain.BaseErrorSignal
import vn.gotech.audiobook.base.domain.utils.Status
import vn.gotech.audiobook.databinding.FragmentChangePasswordBinding
import vn.gotech.audiobook.extensions.appViewModel
import vn.gotech.audiobook.extensions.hideKeyboardForce
import vn.gotech.audiobook.ui.change_password.otp.ChangePasswordOtpActivity

class ChangePasswordFragment : BaseFragment<FragmentChangePasswordBinding>() {
    private val changePasswordViewModel: ChangePasswordViewModel by appViewModel()

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentChangePasswordBinding =
        FragmentChangePasswordBinding.inflate(layoutInflater)

    companion object {
        fun newInstance(params: Bundle): ChangePasswordFragment {
            val f = ChangePasswordFragment()
            f.arguments = params
            return f
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnNext.setOnClickListener {
                val oldPassword = edtOldPassword.text.toString().trim()
                val password = edtNewPassword.text.toString().trim()
                val rePassword = edtRePassword.text.toString().trim()
                if (isRequiredFieldsValid(oldPassword, password, rePassword)) {
                    changePasswordViewModel.changePassword(oldPassword, password, it.context)
                }
            }

            edtNewPassword.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    btnNext.performClick()
                    hideKeyboardForce()
                    return@OnEditorActionListener true
                }
                hideKeyboardForce()
                return@OnEditorActionListener false
            })
        }
    }

    private fun isRequiredFieldsValid(
        oldPassword: String,
        password: String,
        rePassword: String
    ): Boolean {

        if (oldPassword.trim().isEmpty()) {
            setAlarmError("Mật khẩu hiện tại không được để trống")
            binding.edtOldPassword.requestFocus()
            return false
        }

        if (oldPassword.trim().length < 6) {
            setAlarmError("Mật khẩu từ 6 ký tự trở lên")
            binding.edtOldPassword.requestFocus()
            return false
        }

        if (password.trim().isEmpty()) {
            setAlarmError("Mật khẩu mới không được để trống")
            binding.edtNewPassword.requestFocus()
            return false
        }

        if (password.trim().length < 6) {
            setAlarmError("Mật khẩu mới từ 6 ký tự trở lên")
            binding.edtNewPassword.requestFocus()
            return false
        }

        if (rePassword.trim().isEmpty()) {
            setAlarmError("Nhập lại mật khẩu không được để trống")
            binding.edtRePassword.requestFocus()
            return false
        }

        if (rePassword.trim().length < 6) {
            setAlarmError("Nhập lại mật khẩu từ 6 ký tự trở lên")
            binding.edtRePassword.requestFocus()
            return false
        }

        if (password.trim() != rePassword.trim()) {
            setAlarmError(getString(R.string.error_equal))
            binding.edtRePassword.requestFocus()
            return false
        }
        return true
    }

    private fun setAlarmError(message: String) {
        binding.apply {
            tvPhoneError.text = message
            tvPhoneError.visibility = View.VISIBLE
            Handler().postDelayed({ tvPhoneError.visibility = View.INVISIBLE }, 3000)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        changePasswordViewModel.resultChangePasswordResponse().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    showLoading()
                }
                Status.SUCCESS -> {
                    hideLoading()
                    if (it.code == BaseErrorSignal.RESPONSE_SUCCESS) {
                        binding.apply {
                            val password = edtNewPassword.text.toString().trim()
                            val oldPassword = edtOldPassword.text.toString().trim()
                            val i = Intent(activity, ChangePasswordOtpActivity::class.java)
                            i.putExtra(
                                Const.TransferKey.EXTRA_PHONE,
                                UserDataManager.currentUserPhone
                            )
                            i.putExtra(Const.TransferKey.EXTRA_PASSWORD, password)
                            i.putExtra(Const.TransferKey.EXTRA_OLD_PASSWORD, oldPassword)
                            startActivity(i)
                            activity?.finish()
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
    }
}