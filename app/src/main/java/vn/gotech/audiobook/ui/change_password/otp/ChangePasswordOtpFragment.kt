package vn.gotech.audiobook.ui.change_password.otp

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
import vn.gotech.audiobook.databinding.FragmentChangePasswordOtpBinding
import vn.gotech.audiobook.extensions.appViewModel
import vn.gotech.audiobook.extensions.asHtml
import java.util.concurrent.TimeUnit

class ChangePasswordOtpFragment : BaseFragment<FragmentChangePasswordOtpBinding>() {
    private val changePasswordOtpViewModel: ChangePasswordOtpViewModel by appViewModel()

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentChangePasswordOtpBinding =
        FragmentChangePasswordOtpBinding.inflate(layoutInflater)

    companion object {
        fun newInstance(params: Bundle): ChangePasswordOtpFragment {
            val f = ChangePasswordOtpFragment()
            f.arguments = params
            return f
        }
    }

    private var reSentOtp = false
    private var countDownTimer: CountDownTimer? = null
    private var phone = ""
    private var password = ""
    private var oldPassword = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        phone = arguments?.getString(Const.TransferKey.EXTRA_PHONE, "") ?: ""
        password = arguments?.getString(Const.TransferKey.EXTRA_PASSWORD, "") ?: ""
        oldPassword = arguments?.getString(Const.TransferKey.EXTRA_OLD_PASSWORD, "") ?: ""
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvResent.text =
            "<font color=\"#E3E3E3\">Nếu bạn chưa nhận được mã? </font><font color=\"#F44336\"><b>Yêu cầu gửi lại OTP</b></font>".asHtml()
        binding.tvCountDown.text =
            "<font color=\"#E3E3E3\">Mã OTP đã được gửi. Còn</font><font color=\"#FFFFFF\"><b> 0:30s</b></font>".asHtml()

        binding.btnAccept.setOnClickListener {
            val otp = binding.edtOtp.text?.trim().toString()
            if (isRequiredFieldsValid(otp)) {
                changePasswordOtpViewModel.changePasswordOtp(phone, otp, it.context)
            }
        }

        binding.tvResent.setOnClickListener {
            if (reSentOtp) {
                changePasswordOtpViewModel.changePassword(oldPassword, password, it.context)
                reSentOtp = false
                setupCountDownTimer()
            }
        }
        setupCountDownTimer()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (countDownTimer != null) {
            countDownTimer!!.cancel()
            countDownTimer = null
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
                    binding.tvCountDown.text =
                        "<font color=\"#E3E3E3\">Mã OTP đã được gửi. Còn</font><font color=\"#FFFFFF\"><b> ${time}s</b></font>".asHtml()
                }

                override fun onFinish() {
                    if (countDownTimer != null) {
                        countDownTimer!!.cancel()
                        countDownTimer = null
                    }
                    binding.tvCountDown.text =
                        "<font color=\"#E3E3E3\">Mã OTP đã được gửi</font>".asHtml()
                    reSentOtp = true
                }
            }

        if (countDownTimer != null) {
            countDownTimer!!.cancel()
            countDownTimer!!.start()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (reSentOtp) {
            reSentOtp = false
            toast("OTP đã được gửi lại.")
        } else
            toast("Đăng nhập không thành công.")

        changePasswordOtpViewModel.resultChangePasswordOtpResponse().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    showLoading()
                }
                Status.SUCCESS -> {
                    hideLoading()
                    toast("Gửi lại OTP thành công")
                }

                Status.ERROR -> {
                    hideLoading()
                    resolveError(it.code, it.message)
                }
            }
        }

        changePasswordOtpViewModel.resultChangePasswordResponse().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    showLoading()
                }
                Status.SUCCESS -> {
                    hideLoading()
                    if (it.code == BaseErrorSignal.RESPONSE_SUCCESS) {
                        UserDataManager.accessToken = it.data?.accessToken ?: ""
                        UserDataManager.currentUserId = it.data?.user?.id?.toLong() ?: -1L
                        UserDataManager.currentUserName = it.data?.user?.name ?: ""
                        UserDataManager.currentCreateAt = it.data?.user?.createdAt ?: ""
                        UserDataManager.currentUserPhone = it.data?.user?.phone ?: ""
                        UserDataManager.currentPackage = it.data?.user?.packageId ?: 0
                        toast("Đổi mật khẩu thành công")

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

    private fun isRequiredFieldsValid(otp: String): Boolean {
        if (otp.isEmpty()) {
            binding.edtOtp.error = getString(R.string.error_field_required)
            binding.edtOtp.requestFocus()
            return false
        }
        return true
    }
}