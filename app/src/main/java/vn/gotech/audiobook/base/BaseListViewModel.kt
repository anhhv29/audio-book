package vn.gotech.audiobook.base

import androidx.lifecycle.MutableLiveData
import okhttp3.Request
import vn.gotech.audiobook.base.domain.utils.Resource

abstract class BaseListViewModel<T> : BaseViewModel() {

    var dataReturned = MutableLiveData<Resource<T>>()

    abstract fun loadData(params: Request)
}