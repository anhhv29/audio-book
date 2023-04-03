package vn.gotech.audiobook.ui.register

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
import vn.gotech.audiobook.base.domain.utils.NetworkHelper
import vn.gotech.audiobook.base.domain.utils.Resource

class RegisterViewModel(
    private val requestNoAuthRepository: RequestNoAuthRepository,
    private val networkHelper: NetworkHelper
) : BaseApiViewModel() {
    var registerResult = MutableLiveData<Resource<Any>>()
    var loading = MutableLiveData<Boolean>()

    fun registerResultResponse(): LiveData<Resource<Any>> {
        return registerResult
    }

    fun register(context: Context, phone: String, password: String) {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                registerResult.postValue(Resource.onLoading())
                val builder = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("phone", phone)
                    .addFormDataPart("password", password)
                    .addFormDataPart("name", "user_$phone")

                requestNoAuthRepository.registerForPass(builder.build()).let {
                    if (it.isSuccessful) {
                        it.body()?.let { t ->
                            val data = t.data
                            registerResult.postValue(
                                Resource.onSuccess(
                                    t.code,
                                    t.message,
                                    data
                                )
                            )
                        } ?: kotlin.run {
                            registerResult.postValue(
                                Resource.onError(
                                    BaseErrorSignal.ERROR_PARSE,
                                    context.getString(R.string.error_server)
                                )
                            )
                        }
                    } else {
                        registerResult.postValue(
                            Resource.onError(
                                it.code(),
                                it.message()
                            )
                        )
                    }
                }
            } else {
                registerResult.postValue(
                    Resource.onError(
                        BaseErrorSignal.ERROR_NETWORK,
                        context.getString(R.string.error_network)
                    )
                )
            }
        }
    }
}
