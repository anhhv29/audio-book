package vn.gotech.audiobook.ui.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vn.gotech.audiobook.R
import vn.gotech.audiobook.base.domain.BaseApiViewModel
import vn.gotech.audiobook.base.domain.BaseErrorSignal
import vn.gotech.audiobook.base.domain.repository.RequestNoAuthRepository
import vn.gotech.audiobook.base.domain.ressponse.Category
import vn.gotech.audiobook.base.domain.utils.NetworkHelper
import vn.gotech.audiobook.base.domain.utils.Resource

class HomeViewModel(
    private val requestNoAuthRepository: RequestNoAuthRepository,
    private val networkHelper: NetworkHelper
) : BaseApiViewModel() {

    var loadCategorySuccess = MutableLiveData<Resource<MutableList<Category>>>()
    var loadBookByCategorySuccess = MutableLiveData<Resource<Category>>()

    fun loadCategory(context: Context) {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                loadCategorySuccess.postValue(Resource.onLoading())

                requestNoAuthRepository.getCategoryList().let {
                    if (it.isSuccessful) {
                        it.body()?.let { t ->
                            val data = t.data
                            loadCategorySuccess.postValue(
                                Resource.onSuccess(
                                    t.code,
                                    t.message,
                                    data
                                )
                            )
                        } ?: kotlin.run {
                            loadCategorySuccess.postValue(
                                Resource.onError(
                                    BaseErrorSignal.ERROR_PARSE,
                                    null
                                )
                            )
                        }
                    } else {
                        loadCategorySuccess.postValue(
                            Resource.onError(
                                it.code(),
                                it.message()
                            )
                        )

                    }
                }
            } else {
                loadCategorySuccess.postValue(
                    Resource.onError(
                        BaseErrorSignal.ERROR_NETWORK,
                        context.getString(R.string.error_network)
                    )
                )
            }
        }
    }

    fun loadData(context: Context, id: Int) {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                loadBookByCategorySuccess.postValue(Resource.onLoading())
                requestNoAuthRepository.getCategoryBook(id).let {
                    if (it.isSuccessful) {
                        it.body()?.let { t ->
                            val data = t.data
                            val category = Category()
                            category.id = id
                            category.listBook = data ?: mutableListOf()
                            loadBookByCategorySuccess.postValue(
                                Resource.onSuccess(
                                    it.code(),
                                    it.message(),
                                    category
                                )
                            )
                        } ?: kotlin.run {
                            loadBookByCategorySuccess.postValue(
                                Resource.onError(
                                    BaseErrorSignal.ERROR_PARSE,
                                    null
                                )
                            )
                        }
                    } else {
                        loadBookByCategorySuccess.postValue(
                            Resource.onError(
                                it.code(),
                                it.message()
                            )
                        )

                    }
                }
            } else {
                loadBookByCategorySuccess.postValue(
                    Resource.onError(
                        BaseErrorSignal.ERROR_NETWORK,
                        context.getString(R.string.error_network)
                    )
                )
            }
        }
    }
}