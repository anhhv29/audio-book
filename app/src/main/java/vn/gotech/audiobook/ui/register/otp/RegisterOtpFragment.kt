package vn.gotech.audiobook.ui.register.otp

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
import vn.gotech.audiobook.databinding.FragmentRegisterOtpBinding
import vn.gotech.audiobook.extensions.appViewModel
import vn.gotech.audiobook.extensions.asHtml
import java.util.concurrent.TimeUnit

class RegisterOtpFragment : BaseFragment<FragmentRegisterOtpBinding>() {

    private val registerOtpViewModel: RegisterOtpViewModel by appViewModel()

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentRegisterOtpBinding =
        FragmentRegisterOtpBinding.inflate(layoutInflater)

    companion object {
        fun newInstance(params: Bundle): RegisterOtpFragment {
            val f = RegisterOtpFragment()
            f.arguments = params
            return f
        }
    }

    private var phone = ""
    private var password = ""
    private var reSentOtp = false
    private var countDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        phone = arguments?.getString(Const.TransferKey.EXTRA_PHONE, "") ?: ""
        password = arguments?.getString(Const.TransferKey.EXTRA_PASSWORD, "") ?: ""
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            tvResent.text =
                "<font color=\"#E3E3E3\">Nếu bạn chưa nhận được mã? </font><font color=\"#F44336\"><b>Yêu cầu gửi lại OTP</b></font>".asHtml()
            tvCountDown.text =
                "<font color=\"#E3E3E3\">Mã OTP đã được gửi. Còn</font><font color=\"#FFFFFF\"><b> 0:30s</b></font>".asHtml()

            btnAccept.setOnClickListener {
                val otp = edtOTP.text?.trim().toString()
                if (isRequiredFieldsValid(otp)) {
                    registerOtpViewModel.registerOtp(phone, otp, it.context)
                }
            }

            tvResent.setOnClickListener {
                if (reSentOtp) {
                    registerOtpViewModel.register(phone, password, it.context)
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

    private fun isRequiredFieldsValid(otp: String): Boolean {
        if (otp.isEmpty()) {
            binding.edtOTP.error = getString(R.string.error_field_required)
            binding.edtOTP.requestFocus()
            return false
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        if (countDownTimer != null) {
            countDownTimer!!.cancel()
            countDownTimer = null
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        registerOtpViewModel.registerForPassResultResponse().observe(viewLifecycleOwner) {
            hideProgressDialog()
            when (it.status) {
                Status.LOADING -> {
                    showLoading()
                }
                Status.SUCCESS -> {
                    hideLoading()
                    if (it.code == BaseErrorSignal.RESPONSE_SUCCESS)
                        toast("Gửi lại OTP thành công")
                    else
                        toast(it.message ?: "")
                }
                Status.ERROR -> {
                    hideLoading()
                    resolveError(it.code, it.message)
                }
            }
        }

        registerOtpViewModel.registerOtpResultResponse().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    showLoading()
                }
                Status.SUCCESS -> {
                    hideLoading()
                    if (it.code == BaseErrorSignal.RESPONSE_SUCCESS) {
                        it?.let {
                            UserDataManager.accessToken = it.data?.accessToken ?: ""
                            UserDataManager.currentUserId = it.data?.user?.id?.toLong() ?: -1L
                            UserDataManager.currentUserName = it.data?.user?.name ?: ""
                            UserDataManager.currentCreateAt = it.data?.user?.createdAt ?: ""
                            UserDataManager.currentUserPhone = it.data?.user?.phone ?: ""
                            UserDataManager.currentPackage = it.data?.user?.packageId ?: 0


//                    val i = Intent(activity, HomeActivity::class.java)
//                    i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//                    startActivity(i)

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
