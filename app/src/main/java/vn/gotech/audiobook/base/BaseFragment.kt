package vn.gotech.audiobook.base

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.viewbinding.ViewBinding
import com.afollestad.materialdialogs.MaterialDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.kaopiz.kprogresshud.KProgressHUD
import jp.wasabeef.glide.transformations.BlurTransformation
import vn.gotech.audiobook.R
import vn.gotech.audiobook.base.domain.BaseErrorSignal

/**
 * @author Steve
 * @since 10/22/17
 */
open abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    /**
     * require input of activity is a list of key in intent
     */
    protected open fun requireInput(): List<String> = listOf()

    // verify input of activity has all key
    protected open fun inputVerified(argument: Bundle?): Boolean {
        if (argument == null) return true

        var inputOK = true
        val requireInput = requireInput()
        for (s in requireInput) {
            if (!argument.containsKey(s)) {
                inputOK = false
                break
            }
        }

        return inputOK
    }

    protected var reloadData = false
    protected var toast: Toast? = null
    private var kProgressHUD: KProgressHUD? = null
    protected lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        kProgressHUD = KProgressHUD.create(activity)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setBackgroundColor(Color.TRANSPARENT)
            .setCancellable(true)
            .setAnimationSpeed(2)
            .setDimAmount(0.5f)
        Log.e("SCREEN", this.javaClass.simpleName)

        if (!inputVerified(arguments))
            throw IllegalArgumentException("Chưa truyền đủ tham số")
    }

    abstract fun inflateLayout(layoutInflater: LayoutInflater): VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflateLayout(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifeCycleOwner = ViewLifeCyclerOwner()
        viewLifeCycleOwner?.lifecycle?.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    }

    fun hideLoading() {
        kProgressHUD?.dismiss()
    }

    fun resolveError(error: BaseErrorSignal) {
        activity?.let {
            when (error.errorCode) {
                BaseErrorSignal.ERROR_HAS_MESSAGE -> {
                    Toast.makeText(it, error.errorMessage, Toast.LENGTH_SHORT).show()
                }
                BaseErrorSignal.ERROR_401 -> {
                    Toast.makeText(it, "Xin vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show()

//                    val intent = Intent(it, LoginActivity::class.java)
//                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//                    it.startActivity(intent)

                    it.finish()
                    it.finishAffinity()
                }
                BaseErrorSignal.ERROR_NETWORK -> {
                    val dialog = MaterialDialog.Builder(it)
                        .customView(R.layout.dialog_disconnect_internet, false)
                        .autoDismiss(false)
                        .canceledOnTouchOutside(true)
                        .build()
                    val img_backgroundBlur =
                        dialog.findViewById(R.id.img_backgroundBlur) as ImageView
                    Glide.with(this).load(R.color.colorTransparent)
                        .apply(RequestOptions.bitmapTransform(BlurTransformation(22)))
                        .into(img_backgroundBlur)
                    dialog.show()
                }
                BaseErrorSignal.ERROR_SERVER -> {
                    MaterialDialog.Builder(it)
                        .content("Hệ thống đang được cập nhật. Vui lòng khởi động lại hoặc quản lại sau.")
                        .positiveText("OK")
                        .show()
                }
                BaseErrorSignal.ERROR_UNKNOWN -> {
                    Log.e(
                        "BaseErrorSignal",
                        "Có lỗi xảy ra. Liên lạc với nhà quản trị để được hỗ trợ."
                    )
                }
                else -> {
                }
            }
        }

    }

    fun showLoading() {
//        kProgressHUD?.show()
    }

    fun toast(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }

    fun resolveError(code: Int? = BaseErrorSignal.ERROR_HAS_MESSAGE, message: String? = "") {
        activity?.let {
            when (code) {
                BaseErrorSignal.ERROR_HAS_MESSAGE -> {
                    toast(message ?: "")
                }
                BaseErrorSignal.ERROR_AUTH -> {
                    toast(getString(R.string.error_auth))
                }
                BaseErrorSignal.ERROR_NETWORK -> {
                    val snackBar = Snackbar.make(
                        it.findViewById(android.R.id.content),
                        getString(R.string.error_network),
                        Snackbar.LENGTH_LONG
                    )
                        .setActionTextColor(resources.getColor(R.color.colorPrimary))
                        .setDuration(10 * 1000)
                    val snackBarText = snackBar.view.findViewById<TextView>(R.id.snackbar_text)
                    snackBarText.setTextColor(Color.WHITE)
                    snackBar.show()
                }
                BaseErrorSignal.ERROR_SERVER -> {
                    toast(getString(R.string.error_server))
                }
                BaseErrorSignal.ERROR_PARSE -> {
                    toast("${getString(R.string.error_server)} - $code")
                }
                else -> {
                    toast(message ?: "")
                }
            }
        }

    }

    override fun onStart() {
        super.onStart()
        viewLifeCycleOwner?.lifecycle?.handleLifecycleEvent(Lifecycle.Event.ON_START)
    }

    override fun onResume() {
        super.onResume()
        viewLifeCycleOwner?.lifecycle?.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    override fun onPause() {
        super.onPause()
        viewLifeCycleOwner?.lifecycle?.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    }

    override fun onStop() {
        super.onStop()
        viewLifeCycleOwner?.lifecycle?.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewLifeCycleOwner?.lifecycle?.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }

    override fun startActivity(intent: Intent) {
        super.startActivity(intent)
        activity?.overridePendingTransition(
            R.anim.nav_pop_right_to_left_enter_anim,
            R.anim.nav_slide_left_to_right_exit_anim
        );
    }

    override fun startActivityForResult(intent: Intent, requestCode: Int) {
        super.startActivityForResult(intent, requestCode)
        activity?.overridePendingTransition(
            R.anim.nav_pop_right_to_left_enter_anim,
            R.anim.nav_slide_left_to_right_exit_anim
        );
    }

    fun hideProgressDialog() {
        kProgressHUD?.dismiss()
    }

    fun showProgressDialog() {
//        kProgressHUD?.show()
    }

    class ViewLifeCyclerOwner : LifecycleOwner {
        private val lifeCycleRegistry = LifecycleRegistry(this)
        override fun getLifecycle(): LifecycleRegistry {
            return lifeCycleRegistry
        }

    }

    var viewLifeCycleOwner: ViewLifeCyclerOwner? = null
}