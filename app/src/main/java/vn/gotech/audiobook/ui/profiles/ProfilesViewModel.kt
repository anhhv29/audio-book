package vn.gotech.audiobook.ui.profiles

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vn.gotech.audiobook.base.BaseViewModel
import vn.gotech.audiobook.base.common.UserDataManager
import vn.gotech.audiobook.base.domain.BaseErrorSignal
import vn.gotech.audiobook.base.domain.repository.RequestAuthRepository
import vn.gotech.audiobook.base.domain.ressponse.User
import vn.gotech.audiobook.base.domain.utils.NetworkHelper
import vn.gotech.audiobook.base.domain.utils.Resource

class ProfilesViewModel(
    private val requestAuthRepository: RequestAuthRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {
    private val resultProfiles = MutableLiveData<Resource<User>>()
    private val resultUpdateProfiles = MutableLiveData<Resource<Any>>()

    fun resultProfilesResponse(): LiveData<Resource<User>> {
        return resultProfiles
    }

    fun resultUpdateProfilesResponse(): LiveData<Resource<Any>> {
        return resultUpdateProfiles
    }

    fun getProfiles(isLoading: Boolean = true) {
        if (isLoading)
            resultProfiles.postValue(Resource.onLoading())
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                val params = mutableMapOf<String, Any>()
                params["user_id"] = UserDataManager.currentUserId
                params["phone"] = UserDataManager.currentUserPhone
                requestAuthRepository.getProfiles(params).let {
                    if (it.isSuccessful) {
                        it.body()?.let { t ->
                            val data = t.data
                            resultProfiles.postValue(
                                Resource.onSuccess(
                                    BaseErrorSignal.RESPONSE_SUCCESS,
                                    "",
                                    data
                                )
                            )

                        } ?: kotlin.run {
                            resultProfiles.postValue(
                                Resource.onError(
                                    BaseErrorSignal.ERROR_PARSE,
                                    null
                                )
                            )
                        }
                    } else {
                        resultProfiles.postValue(
                            Resource.onError(
                                BaseErrorSignal.ERROR_SERVER,
                                null
                            )
                        )
                    }
                }
            } else {
                resultProfiles.postValue(
                    Resource.onError(
                        BaseErrorSignal.ERROR_NETWORK,
                        null
                    )
                )
            }
        }
    }

    fun updateUserInfo(userName: String) {
        resultUpdateProfiles.postValue(Resource.onLoading())
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                val params = mutableMapOf<String, Any>()
                params["user_id"] = UserDataManager.currentUserId
                params["phone"] = UserDataManager.currentUserPhone
                params["name"] = userName
                requestAuthRepository.updateUserInfo(params).let {
                    if (it.isSuccessful) {
                        it.body()?.let { t ->
                            val data = t.data
                            resultUpdateProfiles.postValue(
                                Resource.onSuccess(
                                    t.code,
                                    t.message,
                                    null
                                )
                            )

                        } ?: kotlin.run {
                            resultUpdateProfiles.postValue(
                                Resource.onError(
                                    BaseErrorSignal.ERROR_PARSE,
                                    null
                                )
                            )
                        }
                    } else {
                        resultUpdateProfiles.postValue(
                            Resource.onError(
                                BaseErrorSignal.ERROR_SERVER,
                                null
                            )
                        )
                    }
                }
            } else {
                resultUpdateProfiles.postValue(
                    Resource.onError(
                        BaseErrorSignal.ERROR_NETWORK,
                        null
                    )
                )
            }
        }
    }
}