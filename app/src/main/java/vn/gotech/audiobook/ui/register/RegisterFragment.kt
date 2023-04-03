package vn.gotech.audiobook.ui.register

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.Observer
import vn.gotech.audiobook.R
import vn.gotech.audiobook.base.BaseFragment
import vn.gotech.audiobook.base.common.Const
import vn.gotech.audiobook.base.domain.BaseErrorSignal
import vn.gotech.audiobook.base.domain.utils.Status
import vn.gotech.audiobook.databinding.FragmentRegisterBinding
import vn.gotech.audiobook.extensions.appViewModel
import vn.gotech.audiobook.extensions.hideKeyboardForce
import vn.gotech.audiobook.ui.register.otp.RegisterOtpActivity

class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {
    private val registerViewModel: RegisterViewModel by appViewModel()

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentRegisterBinding =
        FragmentRegisterBinding.inflate(layoutInflater)

    companion object {
        fun newInstance(params: Bundle): RegisterFragment {
            val f = RegisterFragment()
            f.arguments = params
            return f
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
//            textView.text = "Chào mừng bạn tham gia cùng <b>GoBook</b>".asHtml()

            btnNext.setOnClickListener {
                val phone = edtPhone.text.toString()
                val passWord = edtPassword.text.toString()
                val rePassWord = edtRePassword.text.toString()
                if (isRequiredFieldsValid(phone, passWord, rePassWord)) {
                    registerViewModel.register(it.context, phone, passWord)
                }
            }

            edtPassword.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
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
        phone: String,
        password: String,
        rePassword: String
    ): Boolean {
        binding.apply {
            if (phone.trim().isEmpty()) {
                setAlarmError("Số điện thoại không được để trống")
                edtPhone.requestFocus()
                return false
            }

            if (phone.trim().length > 12 || phone.trim().length < 10) {
                setAlarmError("Số điện thoại không đúng định dạng")
                edtPhone.requestFocus()
                return false
            }

            if (password.trim().isEmpty()) {
                setAlarmError("Mật khẩu không được để trống")
                edtPassword.requestFocus()
                return false
            }

            if (password.trim().length < 6) {
                setAlarmError("Mật khẩu từ 6 ký tự trở lên")
                edtPassword.requestFocus()
                return false
            }

            if (rePassword.trim().isEmpty()) {
                setAlarmError("Mật khẩu nhập lại không được để trống")
                edtRePassword.requestFocus()
                return false
            }

            if (rePassword.trim().length < 6) {
                setAlarmError("Mật khẩu nhập lại từ 6 ký tự trở lên")
                edtRePassword.requestFocus()
                return false
            }

            if (password.trim() != rePassword.trim()) {
                setAlarmError(getString(R.string.error_equal))
                edtRePassword.requestFocus()
                return false
            }
            return true
        }
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
        registerViewModel.registerResultResponse().observe(viewLifecycleOwner, Observer {
            binding.apply {
                hideProgressDialog()
                when (it.status) {
                    Status.LOADING -> {
                        showLoading()
                    }
                    Status.SUCCESS -> {
                        hideLoading()
                        if (it.code == BaseErrorSignal.RESPONSE_SUCCESS) {
                            val i = Intent(activity, RegisterOtpActivity::class.java)
                            val phone = binding.edtPhone.text.toString()
                            i.putExtra(
                                Const.TransferKey.EXTRA_PHONE, phone
                            )
                            i.putExtra(
                                Const.TransferKey.EXTRA_PASSWORD,
                                binding.edtPassword.text.toString()
                            )
                            startActivity(i)
                        } else {
                            tvPhoneError.text = it.message
                            tvPhoneError.visibility = View.VISIBLE
                            Handler().postDelayed(
                                { tvPhoneError.visibility = View.INVISIBLE },
                                3000
                            )
                        }
                    }

                    Status.ERROR -> {
                        hideLoading()
                        tvPhoneError.text = it.message
                        tvPhoneError.visibility = View.VISIBLE
                        Handler().postDelayed({ tvPhoneError.visibility = View.INVISIBLE }, 3000)
                    }
                }
            }
        })
    }
}
