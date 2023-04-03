package vn.gotech.audiobook.ui.forgot

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import vn.gotech.audiobook.base.BaseFragment
import vn.gotech.audiobook.base.common.Const
import vn.gotech.audiobook.base.domain.BaseErrorSignal
import vn.gotech.audiobook.base.domain.utils.Status
import vn.gotech.audiobook.databinding.FragmentForgotBinding
import vn.gotech.audiobook.extensions.appViewModel
import vn.gotech.audiobook.extensions.hideKeyboardForce
import vn.gotech.audiobook.ui.forgot.otp.ForgotOtpActivity

class ForgotFragment : BaseFragment<FragmentForgotBinding>() {
    private val forgotViewModel: ForgotViewModel by appViewModel()
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentForgotBinding =
        FragmentForgotBinding.inflate(layoutInflater)

    companion object {
        fun newInstance(params: Bundle): ForgotFragment {
            val f = ForgotFragment()
            f.arguments = params
            return f
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        forgotViewModel.resultForgotResponse().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    showLoading()
                }
                Status.SUCCESS -> {
                    hideLoading()
                    if (it.code == BaseErrorSignal.RESPONSE_SUCCESS) {
                        val phone = binding.edtPhone.text.toString().trim()
                        val password = binding.edtPassword.text.toString().trim()

                        val i = Intent(activity, ForgotOtpActivity::class.java)
                        i.putExtra(Const.TransferKey.EXTRA_PHONE, phone)
                        i.putExtra(Const.TransferKey.EXTRA_PASSWORD, password)
                        startActivity(i)

                        toast(it.message ?: "")
                        Handler().postDelayed({
                            activity?.finish()
                        }, 1000)
                    }
                    else{
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnNext.setOnClickListener {
                val phone = edtPhone.text.toString().trim()
                val password = edtPassword.text.toString().trim()
                if (isRequiredFieldsValid(phone, password)) {
                    forgotViewModel.forgetPassword(it.context, phone, password)
                }
            }

            btnBack.setOnClickListener {
                activity?.finish()
            }

            edtPhone.setOnEditorActionListener(object : OnEditorActionListener {
                override fun onEditorAction(
                    v: TextView?,
                    actionId: Int,
                    event: KeyEvent?
                ): Boolean {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        btnNext.performClick()
                        hideKeyboardForce()
                        return true
                    }
                    hideKeyboardForce()
                    return false
                }

            })
        }
    }

    private fun isRequiredFieldsValid(phone: String, password: String): Boolean {
        if (phone.trim().isEmpty()) {
            setAlarmError("Số điện thoại không hợp lệ")
            binding.edtPhone.requestFocus()
            return false
        }

        if (phone.trim().length > 12 || phone.trim().length < 10) {
            setAlarmError("Số điện thoại không hợp lệ")
            binding.edtPhone.requestFocus()
            return false
        }
        if (password.trim().isEmpty()) {
            setAlarmError("Mật khẩu không được để trống")
            binding.edtPassword.requestFocus()
            return false
        }

        if (password.trim().length < 6) {
            setAlarmError("Mật khẩu từ 6 ký tự trở lên")
            binding.edtPassword.requestFocus()
            return false
        }
        return true
    }

    private fun setAlarmError(message: String) {
        binding.tvPhoneError.text = message
        binding.tvPhoneError.visibility = View.VISIBLE
        Handler().postDelayed({ binding.tvPhoneError.visibility = View.INVISIBLE }, 3000)
    }
}