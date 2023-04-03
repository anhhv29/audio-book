package vn.gotech.audiobook.ui.forgot.otp

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import vn.gotech.audiobook.R
import vn.gotech.audiobook.base.BaseFragment
import vn.gotech.audiobook.base.common.Const
import vn.gotech.audiobook.base.common.UserDataManager
import vn.gotech.audiobook.base.domain.BaseErrorSignal
import vn.gotech.audiobook.base.domain.utils.Status
import vn.gotech.audiobook.databinding.FragmentForgetOtpBinding
import vn.gotech.audiobook.extensions.appViewModel
import vn.gotech.audiobook.ui.HomeActivity
import java.util.concurrent.TimeUnit

class ForgotOtpFragment : BaseFragment<FragmentForgetOtpBinding>() {
    private val forgotOtpViewModel: ForgotOtpViewModel by appViewModel()

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentForgetOtpBinding =
        FragmentForgetOtpBinding.inflate(layoutInflater)


    companion object {
        fun newInstance(params: Bundle): ForgotOtpFragment {
            val f = ForgotOtpFragment()
            f.arguments = params
            return f
        }
    }

    private var reSentOtp = false
    private var countDownTimer: CountDownTimer? = null
    private var phone = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        phone = arguments?.getString(Const.TransferKey.EXTRA_PHONE, "") ?: ""
        password = arguments?.getString(Const.TransferKey.EXTRA_PASSWORD, "") ?: ""
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        forgotOtpViewModel.resultOtpResponse().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    showLoading()
                }
                Status.SUCCESS -> {
                    if (it.code == BaseErrorSignal.RESPONSE_SUCCESS) {
                        hideLoading()
                        UserDataManager.accessToken = it.data?.accessToken ?: ""
                        UserDataManager.currentUserId = it.data?.user?.id?.toLong() ?: -1L
                        UserDataManager.currentUserName = it.data?.user?.name ?: ""
                        UserDataManager.currentCreateAt = it.data?.user?.createdAt ?: ""
                        UserDataManager.currentUserPhone = it.data?.user?.phone ?: ""
                        UserDataManager.currentPackage = it.data?.user?.packageId ?: 0

                        val i = Intent(activity, HomeActivity::class.java)
                        i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(i)
                    } else {
                        hideLoading()
                        resolveError(it.code, it.message)
                    }
                }

                Status.ERROR -> {
                    hideLoading()
                    if (reSentOtp) {
                        reSentOtp = false
                        toast("OTP đã được gửi lại.")
                    } else
                        toast("Đăng nhập không thành công.")
                    resolveError(it.code, it.message)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnAccept.setOnClickListener {
                val otp = edtOtp.text?.trim().toString()
                if (isRequiredFieldsValid(otp)) {
                    forgotOtpViewModel.forgotOtp(phone, otp, it.context)
                }
            }

            tvResent.setOnClickListener {
                if (reSentOtp) {
                    forgotOtpViewModel.forgot(phone, password, it.context)
                    reSentOtp = false
                    setupCountDownTimer()
                }
            }
            setupCountDownTimer()
        }
    }

    private fun setupCountDownTimer() {
        if (countDownTimer == null)
            countDownTimer = object : CountDownTimer(30000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val time = String.format(
                        "%d:%d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(
                                    TimeUnit.MILLISECONDS.toMinutes(
                                        millisUntilFinished
                                    )
                                )
                    )
                }

                override fun onFinish() {
                    if (countDownTimer != null) {
                        countDownTimer!!.cancel()
                        countDownTimer = null
                    }
                    reSentOtp = true
                }
            }

        if (countDownTimer != null) {
            countDownTimer!!.cancel()
            countDownTimer!!.start()
        }
    }

    private fun isRequiredFieldsValid(otp: String): Boolean {
        if (otp.isEmpty()) {
            binding.edtOtp.error = getString(R.string.error_field_required)
            binding.edtOtp.requestFocus()
            return false
        }
        return true
    }

}