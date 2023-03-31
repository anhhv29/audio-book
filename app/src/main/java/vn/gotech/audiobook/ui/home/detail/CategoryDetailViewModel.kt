package vn.gotech.audiobook.ui.home.detail

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.exoplayer2.util.Log
import kotlinx.coroutines.launch
import vn.gotech.audiobook.R
import vn.gotech.audiobook.base.domain.BaseApiViewModel
import vn.gotech.audiobook.base.domain.BaseErrorSignal
import vn.gotech.audiobook.base.domain.repository.RequestNoAuthRepository
import vn.gotech.audiobook.base.domain.ressponse.Book
import vn.gotech.audiobook.base.domain.utils.NetworkHelper
import vn.gotech.audiobook.base.domain.utils.Resource

class CategoryDetailViewModel(
    private val requestNoAuthRepository: RequestNoAuthRepository,
    private val networkHelper: NetworkHelper
) : BaseApiViewModel() {

    var loadBookSuccess = MutableLiveData<Resource<MutableList<Book>>>()

    fun loadData(context: Context, id: Int) {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                loadBookSuccess.postValue(Resource.onLoading())
                requestNoAuthRepository.getCategoryBook(id).let {
                    if (it.isSuccessful) {
                        it.body()?.let { t ->
                            val data = t.data
                            Log.d("check", "1")
                            loadBookSuccess.postValue(
                                Resource.onSuccess(
                                    BaseErrorSignal.RESPONSE_SUCCESS,
                                    it.message(),
                                    data
                                )
                            )
                        } ?: kotlin.run {
                            Log.d("check", "2")
                            loadBookSuccess.postValue(
                                Resource.onError(
                                    BaseErrorSignal.ERROR_PARSE,
                                    null
                                )
                            )
                        }
                    } else {
                        Log.d("check", "3")
                        loadBookSuccess.postValue(
                            Resource.onError(
                                it.code(),
                                it.message()
                            )
                        )

                    }
                }
            } else {
                Log.d("check", "4")
                loadBookSuccess.postValue(
                    Resource.onError(
                        BaseErrorSignal.ERROR_NETWORK,
                        context.getString(R.string.error_network)
                    )
                )
            }
        }
    }

}