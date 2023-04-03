package vn.gotech.audiobook.ui.change_password

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import vn.gotech.audiobook.R
import vn.gotech.audiobook.base.common.UserDataManager
import vn.gotech.audiobook.base.domain.BaseApiViewModel
import vn.gotech.audiobook.base.domain.BaseErrorSignal
import vn.gotech.audiobook.base.domain.repository.RequestNoAuthRepository
import vn.gotech.audiobook.base.domain.utils.NetworkHelper
import vn.gotech.audiobook.base.domain.utils.Resource

class ChangePasswordViewModel(
    private val requestNoAuthRepository: RequestNoAuthRepository,
    private val networkHelper: NetworkHelper
) : BaseApiViewModel() {

    private val resultChangePassword = MutableLiveData<Resource<Any>>()

    fun resultChangePasswordResponse(): LiveData<Resource<Any>> {
        return resultChangePassword
    }

    fun changePassword(oldPassword: String, password: String, context: Context) {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                resultChangePassword.postValue(Resource.onLoading())
                val builder = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("phone", UserDataManager.currentUserPhone)
                    .addFormDataPart("password_new", password)
                    .addFormDataPart("password_old", oldPassword)

                requestNoAuthRepository.changePassword(builder.build()).let {
                    if (it.isSuccessful) {
                        it.body()?.let { t ->
                            val data = t.data
                            resultChangePassword.postValue(
                                Resource.onSuccess(
                                    BaseErrorSignal.RESPONSE_SUCCESS,
                                    t.message,
                                    data
                                )
                            )
                        } ?: kotlin.run {
                            resultChangePassword.postValue(
                                Resource.onError(
                                    BaseErrorSignal.ERROR_PARSE,
                                    null
                                )
                            )
                        }
                    } else {
                        resultChangePassword.postValue(
                            Resource.onError(
                                it.code(),
                                it.message()
                            )
                        )

                    }
                }
            } else {
                resultChangePassword.postValue(
                    Resource.onError(
                        BaseErrorSignal.ERROR_NETWORK,
                        context.getString(R.string.error_network)
                    )
                )
            }
        }
    }
}