package vn.gotech.audiobook.ui.forgot

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

class ForgotViewModel(
    private val requestNoAuthRepository: RequestNoAuthRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {
    private val resultForgot = MutableLiveData<Resource<Any>>()

    fun resultForgotResponse(): LiveData<Resource<Any>> {
        return resultForgot
    }

    fun forgetPassword(context: Context, phone: String, password: String) {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                resultForgot.postValue(Resource.onLoading())
                val builder = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("phone", phone)
                    .addFormDataPart("password", password)
                requestNoAuthRepository.forgetPassword(builder.build()).let {
                    if (it.isSuccessful) {
                        it.body()?.let { t ->
                            val data = t.data
                            resultForgot.postValue(
                                Resource.onSuccess(
                                    BaseErrorSignal.RESPONSE_SUCCESS,
                                    t.message,
                                    data
                                )
                            )
                        } ?: kotlin.run {
                            resultForgot.postValue(
                                Resource.onError(
                                    BaseErrorSignal.ERROR_PARSE,
                                    null
                                )
                            )
                        }
                    } else {
                       resultForgot.postValue(
                            Resource.onError(
                                it.code(),
                                it.message()
                            )
                        )

                    }
                }
            } else {
                resultForgot.postValue(
                    Resource.onError(
                        BaseErrorSignal.ERROR_NETWORK,
                        context.getString(R.string.error_network)
                    )
                )
            }
        }
    }

}