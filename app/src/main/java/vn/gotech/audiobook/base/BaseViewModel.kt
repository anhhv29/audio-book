package vn.gotech.audiobook.base

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import vn.gotech.audiobook.base.domain.BaseErrorSignal
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * @author Steve
 * @since 10/22/17
 */
open class BaseViewModel : ViewModel() {

    @SuppressLint("StaticFieldLeak")
    @Inject
    lateinit var appContext: Application

    private val disposables = CompositeDisposable()

    var errorSignal: MutableLiveData<BaseErrorSignal> = MutableLiveData()

    fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    override fun onCleared() {
        disposables.clear()
    }

    fun resolveError(statusCode: Int, message: String): String {
        Log.e("Check_Error", "$statusCode")
        return when (statusCode) {
            BaseErrorSignal.ERROR_1 -> { // has message
                message
            }

            BaseErrorSignal.ERROR_100 -> { // has message
                message
            }

            BaseErrorSignal.ERROR_HAS_MESSAGE -> { // has message
                 message
            }
            BaseErrorSignal.ERROR_AUTH -> { // has message
                "Hết phiên làm việc, vui lòng đăng nhập lại"
            }
            BaseErrorSignal.ERROR_SERVER -> {
                "Không thể kết nối tới server."
            }
            BaseErrorSignal.ERROR_NETWORK -> {
                "Không có kết nối mạng"
            }
            else -> {
                message
            }
        }
    }
}