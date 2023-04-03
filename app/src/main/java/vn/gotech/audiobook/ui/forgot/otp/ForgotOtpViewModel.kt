package vn.gotech.audiobook.ui.forgot.otp

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import vn.gotech.audiobook.R
import vn.gotech.audiobook.base.BaseViewModel
import vn.gotech.audiobook.base.domain.BaseErrorSignal
import vn.gotech.audiobook.base.domain.repository.RequestNoAuthRepository
import vn.gotech.audiobook.base.domain.ressponse.Login
import vn.gotech.audiobook.base.domain.utils.NetworkHelper
import vn.gotech.audiobook.base.domain.utils.Resource

class ForgotOtpViewModel(
    private val requestNoAuthRepository: RequestNoAuthRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {
    private val resultForgotOtp = MutableLiveData<Resource<Login>>()

    fun resultOtpResponse(): LiveData<Resource<Login>> {
        return resultForgotOtp
    }

    fun forgotOtp(phone: String, otp: String, context: Context) {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                resultForgotOtp.postValue(Resource.onLoading())
                val builder = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("phone", phone)
                    .addFormDataPart("otp", otp)
                requestNoAuthRepository.forgetOtp(builder.build()).let {
                    if (it.isSuccessful) {
                        it.body()?.let { t ->
                            val data = t.data
                            resultForgotOtp.postValue(
                                Resource.onSuccess(
                                    BaseErrorSignal.RESPONSE_SUCCESS,
                                    t.message,
                                    data
                                )
                            )
                        } ?: kotlin.run {
                            resultForgotOtp.postValue(
                                Resource.onError(
                                    BaseErrorSignal.ERROR_PARSE,
                                    null
                                )
                            )
                        }
                    } else {
                        resultForgotOtp.postValue(
                            Resource.onError(
                                it.code(),
                                it.message()
                            )
                        )

                    }
                }
            } else {
                resultForgotOtp.postValue(
                    Resource.onError(
                        BaseErrorSignal.ERROR_NETWORK,
                        context.getString(R.string.error_network)
                    )
                )
            }
        }
    }

    fun forgot(phone: String, password: String, context: Context) {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                resultForgotOtp.postValue(Resource.onLoading())
                val builder = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("phone", phone)
                    .addFormDataPart("password", password)
                requestNoAuthRepository.login(builder.build()).let {
                    if (it.isSuccessful) {
                        it.body()?.let { t ->
                            val data = t.data
                            resultForgotOtp.postValue(
                                Resource.onSuccess(
                                    BaseErrorSignal.RESPONSE_SUCCESS,
                                    t.message,
                                    data
                                )
                            )
                        } ?: kotlin.run {
                            resultForgotOtp.postValue(
                                Resource.onError(
                                    BaseErrorSignal.ERROR_PARSE,
                                    null
                                )
                            )
                        }
                    } else {
                        resultForgotOtp.postValue(
                            Resource.onError(
                                it.code(),
                                it.message()
                            )
                        )

                    }
                }
            } else {
                resultForgotOtp.postValue(
                    Resource.onError(
                        BaseErrorSignal.ERROR_NETWORK,
                        context.getString(R.string.error_network)
                    )
                )
            }
        }
    }
}