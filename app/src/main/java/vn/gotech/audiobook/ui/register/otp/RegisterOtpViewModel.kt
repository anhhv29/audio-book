package vn.gotech.audiobook.ui.register.otp

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import vn.gotech.audiobook.R
import vn.gotech.audiobook.base.domain.BaseApiViewModel
import vn.gotech.audiobook.base.domain.BaseErrorSignal
import vn.gotech.audiobook.base.domain.repository.RequestNoAuthRepository
import vn.gotech.audiobook.base.domain.ressponse.Login
import vn.gotech.audiobook.base.domain.utils.NetworkHelper
import vn.gotech.audiobook.base.domain.utils.Resource

class RegisterOtpViewModel(
    private val requestNoAuthRepository: RequestNoAuthRepository,
    private val networkHelper: NetworkHelper
) : BaseApiViewModel() {

    var registerOtpResult = MutableLiveData<Resource<Login>>()
    var registerForPassResult = MutableLiveData<Resource<Any>>()
    var loading = MutableLiveData<Boolean>()

    fun registerOtpResultResponse(): LiveData<Resource<Login>> {
        return registerOtpResult
    }

    fun registerForPassResultResponse(): LiveData<Resource<Any>> {
        return registerForPassResult
    }

    fun registerOtp(phone: String, otp: String, context: Context) {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                registerOtpResult.postValue(Resource.onLoading())
                val builder = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("phone", phone)
                    .addFormDataPart("otp", otp)

                requestNoAuthRepository.registerOtp(builder.build()).let {
                    if (it.isSuccessful) {
                        it.body()?.let { t ->
                            val data = t.data
                            registerOtpResult.postValue(
                                Resource.onSuccess(
                                    t.code,
                                    t.message,
                                    data
                                )
                            )
                        } ?: kotlin.run {
                            registerOtpResult.postValue(
                                Resource.onError(
                                    BaseErrorSignal.ERROR_PARSE,
                                    context.getString(R.string.error_server)
                                )
                            )
                        }
                    } else {
                        registerOtpResult.postValue(
                            Resource.onError(
                                it.code(),
                                it.message()
                            )
                        )
                    }
                }
            } else {
                registerOtpResult.postValue(
                    Resource.onError(
                        BaseErrorSignal.ERROR_NETWORK,
                        context.getString(R.string.error_network)
                    )
                )
            }
        }
    }

    fun register(phone: String, password: String, context: Context) {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                registerForPassResult.postValue(Resource.onLoading())
                val builder = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("phone", phone)
                    .addFormDataPart("password", password)
                    .addFormDataPart("name", "user_$phone")

                requestNoAuthRepository.registerForPass(builder.build()).let {
                    if (it.isSuccessful) {
                        it.body()?.let { t ->
                            val data = t.data
                            registerForPassResult.postValue(
                                Resource.onSuccess(
                                    t.code,
                                    t.message,
                                    data
                                )
                            )
                        } ?: kotlin.run {
                            registerForPassResult.postValue(
                                Resource.onError(
                                    BaseErrorSignal.ERROR_PARSE,
                                    context.getString(R.string.error_server)
                                )
                            )
                        }
                    } else {
                        registerForPassResult.postValue(
                            Resource.onError(
                                it.code(),
                                it.message()
                            )
                        )
                    }
                }
            } else {
                registerForPassResult.postValue(
                    Resource.onError(
                        BaseErrorSignal.ERROR_NETWORK,
                        context.getString(R.string.error_network))
                )
            }
        }
    }
}