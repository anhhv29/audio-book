package vn.gotech.audiobook.base.domain

import vn.gotech.audiobook.base.BaseViewModel
import vn.gotech.audiobook.base.domain.api.ApiService
import javax.inject.Inject

open class BaseApiViewModel : BaseViewModel() {
    companion object {
        private val TAG = "BaseAppViewModel"
    }

    @Inject
    lateinit var authService: ApiService.Auth
    @Inject
    lateinit var noAuthService: ApiService.NoAuth
}