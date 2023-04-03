package vn.gotech.audiobook.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import vn.gotech.audiobook.ui.register.RegisterActivity
import vn.gotech.audiobook.base.BaseFragment
import vn.gotech.audiobook.base.common.UserDataManager
import vn.gotech.audiobook.base.domain.BaseErrorSignal
import vn.gotech.audiobook.base.domain.utils.Status
import vn.gotech.audiobook.databinding.FragmentLoginBinding
import vn.gotech.audiobook.extensions.appViewModel
import vn.gotech.audiobook.extensions.hideKeyboardForce
import vn.gotech.audiobook.ui.forgot.ForgotActivity
import vn.gotech.audiobook.ui.profiles.ProfilesViewModel

class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    private val loginViewModel: LoginViewModel by appViewModel()
    private val profilesViewModel: ProfilesViewModel by appViewModel()

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentLoginBinding =
        FragmentLoginBinding.inflate(layoutInflater)

    companion object {
        fun newInstance(params: Bundle): LoginFragment {
            val f = LoginFragment()
            f.arguments = params
            return f
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loginViewModel.resultLoginResponse().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    showLoading()
                }
                Status.SUCCESS -> {
                    hideLoading()
                    if (it.code == BaseErrorSignal.RESPONSE_SUCCESS){
                        UserDataManager.accessToken = it.data?.accessToken ?: ""
                        UserDataManager.currentUserId = it.data?.user?.id?.toLong() ?: -1L
                        UserDataManager.currentUserName = it.data?.user?.name ?: ""
                        UserDataManager.currentCreateAt = it.data?.user?.createdAt ?: ""
                        UserDataManager.currentUserPhone = it.data?.user?.phone ?: ""
                        UserDataManager.currentPackage = it.data?.user?.packageId ?: 0

                        profilesViewModel.getProfiles()
                    } else {
                        binding.tvPhoneError.text = it.message
                        binding.tvPhoneError.visibility = View.VISIBLE
                        Handler().postDelayed(
                            { binding.tvPhoneError.visibility = View.INVISIBLE },
                            3000
                        )
                    }
                }

                Status.ERROR -> {
                    hideLoading()
                    binding.tvPhoneError.text = it.message
                    binding.tvPhoneError.visibility = View.VISIBLE
                    Handler().postDelayed(
                        { binding.tvPhoneError.visibility = View.INVISIBLE },
                        3000
                    )
                }
            }
        }

        profilesViewModel.resultProfilesResponse().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    showLoading()
                }
                Status.SUCCESS -> {
                    hideLoading()
                    if (it.code == BaseErrorSignal.RESPONSE_SUCCESS) {
                        it.data?.let {
                            activity?.setResult(Activity.RESULT_OK)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNext.setOnClickListener {
            val phone = binding.edtPhone.text.toString()
            val password = binding.edtPassword.text.toString()
            if (isRequiredFieldsValid(phone, password)) {
                loginViewModel.login(it.context,phone, password)
            }
        }

        binding.edtPassword.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.btnNext.performClick()
                hideKeyboardForce()
                return@OnEditorActionListener true
            }
            hideKeyboardForce()
            return@OnEditorActionListener false
        })

        binding.tvForgetPassword.setOnClickListener {
            val i = Intent(activity, ForgotActivity::class.java)
            startActivity(i)
        }

        binding.tvRegister.setOnClickListener {
            val i = Intent(activity, RegisterActivity::class.java)
            startActivity(i)
        }

        binding.btnBack.setOnClickListener {
            activity?.finish()
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