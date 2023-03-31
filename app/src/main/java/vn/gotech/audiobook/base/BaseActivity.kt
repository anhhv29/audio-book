package vn.gotech.audiobook.base

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.viewbinding.ViewBinding
import vn.gotech.audiobook.extensions.transact
import com.kaopiz.kprogresshud.KProgressHUD
import vn.gotech.audiobook.R
import vn.gotech.audiobook.extensions.hideKeyboard

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity(), ViewModelStoreOwner {

    protected var toast: Toast? = null
    private var kProgressHUD: KProgressHUD? = null
    private val mViewModelStore = ViewModelStore()
    protected lateinit var binding: VB

    override fun onSupportNavigateUp(): Boolean {
        hideKeyboard()
        onBackPressed()
        return true
    }

    /**
     * require input of activity is a list of key in intent
     */
    protected open fun requireInput(): List<String> = listOf()

    // verify input of activity has all key
    protected open fun validateInput(intent: Intent?): Boolean {
        if (intent == null) return true

        var inputOK = true
        val requireInput = requireInput()
        for (s in requireInput) {
            if (!intent.hasExtra(s)) {
                inputOK = false
                break
            }
        }

        return inputOK
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        if (!validateInput(intent))
            throw IllegalArgumentException("Chưa truyền đủ tham số")

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val windowInsetsController =
            WindowCompat.getInsetsController(window, window.decorView) ?: return
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        windowInsetsController.hide(WindowInsetsCompat.Type.statusBars())

        Log.e("SCREEN", this.javaClass.simpleName)
        if (!validateInput(intent))
            throw IllegalArgumentException("Chưa truyền đủ tham số")
        kProgressHUD = KProgressHUD.create(this)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setBackgroundColor(Color.TRANSPARENT)
            .setCancellable(true)
            .setAnimationSpeed(2)
            .setDimAmount(0.5f)

        binding = inflateLayout(layoutInflater)
        setContentView(binding.root)
    }

    fun hideProgressDialog() {
        kProgressHUD?.dismiss()
    }

    fun showProgressDialog() {
        kProgressHUD?.show()
    }

    fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }


    fun replaceFragmentInActivity(
        fragment: Fragment,
        frameId: Int,
        isAddToBackStack: Boolean = false,
        tag: String? = null
    ) {
        supportFragmentManager.transact {
            replace(frameId, fragment, tag ?: fragment.javaClass.name)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            if (isAddToBackStack) {
                addToBackStack(fragment.javaClass.name)
            }
        }
    }

    override fun startActivity(intent: Intent) {
        super.startActivity(intent)
        overridePendingTransition(
            R.anim.nav_pop_right_to_left_enter_anim,
            R.anim.nav_slide_left_to_right_exit_anim
        );
    }

    override fun startActivityForResult(intent: Intent, requestCode: Int) {
        super.startActivityForResult(intent, requestCode)
        overridePendingTransition(
            R.anim.nav_pop_right_to_left_enter_anim,
            R.anim.nav_slide_left_to_right_exit_anim
        );
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.hold, R.anim.nav_slide_left_to_right_exit_anim);
    }


    override fun getViewModelStore(): ViewModelStore {
        return mViewModelStore
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }

    abstract fun inflateLayout(layoutInflater: LayoutInflater): VB
}