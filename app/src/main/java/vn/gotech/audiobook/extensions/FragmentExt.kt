package vn.gotech.audiobook.extensions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import org.koin.androidx.viewmodel.ViewModelOwner
import org.koin.androidx.viewmodel.koin.getViewModel
import org.koin.androidx.viewmodel.scope.BundleDefinition
import org.koin.core.context.GlobalContext
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import vn.gotech.audiobook.base.BaseActivity

/**
 * Runs a FragmentTransaction, then calls commit().
 */
fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commitAllowingStateLoss()
}

inline fun <reified T : ViewModel> appViewModel(
    activity: BaseActivity<*>,
    qualifier: Qualifier? = null,
    noinline state: BundleDefinition? = null,
    noinline parameters: ParametersDefinition? = null
): Lazy<T> {
    return lazy(LazyThreadSafetyMode.NONE) {
        GlobalContext.get().getViewModel(
            qualifier,
            state,
            { ViewModelOwner.from(activity, null) },
            T::class,
            parameters
        )
    }
}

inline fun <reified T : ViewModel> Fragment.appViewModel(
    qualifier: Qualifier? = null,
    noinline state: BundleDefinition? = null,
    noinline parameters: ParametersDefinition? = null
): Lazy<T> {
    return lazy(LazyThreadSafetyMode.NONE) {
        GlobalContext.get().getViewModel(
            qualifier,
            state,
            { ViewModelOwner.from(activity as BaseActivity<*>, null) },
            T::class,
            parameters
        )
    }
}
