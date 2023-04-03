package vn.gotech.audiobook.ui.login

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

class LoginViewModel(
    private val requestNoAuthRepository: RequestNoAuthRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {
    private val resultLogin = MutableLiveData<Resource<Login>>()

    fun resultLoginResponse(): LiveData<Resource<Login>> {
        return resultLogin
    }

    fun login(context: Context, phone: String, password: String) {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                resultLogin.postValue(Resource.onLoading())
                val builder = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("phone", phone)
                    .addFormDataPart("password", password)
                requestNoAuthRepository.login(builder.build()).let {
                    if (it.isSuccessful) {
                        it.body()?.let { t ->
                            val data = t.data
                            resultLogin.postValue(
                                Resource.onSuccess(
                                    t.code,
                                    t.message,
                                    data
                                )
                            )
                        } ?: kotlin.run {
                            resultLogin.postValue(
                                Resource.onError(
                                    BaseErrorSignal.ERROR_PARSE,
                                    null
                                )
                            )
                        }
                    } else {
                        resultLogin.postValue(
                            Resource.onError(
                                it.code(),
                                it.message()
                            )
                        )

                    }
                }
            } else {
                resultLogin.postValue(
                    Resource.onError(
                        BaseErrorSignal.ERROR_NETWORK,
                        context.getString(R.string.error_network)
                    )
                )
            }
        }
    }
}