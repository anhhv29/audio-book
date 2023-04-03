package vn.gotech.audiobook.ui.profiles

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.afollestad.materialdialogs.MaterialDialog
import vn.gotech.audiobook.R
import vn.gotech.audiobook.base.BaseFragment
import vn.gotech.audiobook.base.common.UserDataManager
import vn.gotech.audiobook.base.domain.BaseErrorSignal
import vn.gotech.audiobook.base.domain.utils.Status
import vn.gotech.audiobook.databinding.FragmentProfilesBinding
import vn.gotech.audiobook.extensions.appViewModel
import vn.gotech.audiobook.extensions.hideKeyboard
import vn.gotech.audiobook.extensions.showSoftKeyboard
import vn.gotech.audiobook.ui.change_password.ChangePasswordActivity
import vn.gotech.audiobook.ui.login.LoginActivity

class ProfilesFragment : BaseFragment<FragmentProfilesBinding>(),
    SwipeRefreshLayout.OnRefreshListener {
    private val profilesViewModel: ProfilesViewModel by appViewModel()

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentProfilesBinding =
        FragmentProfilesBinding.inflate(layoutInflater)

    companion object {
        fun newInstance(params: Bundle): ProfilesFragment {
            val f = ProfilesFragment()
            f.arguments = params
            return f
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        profilesViewModel.resultProfilesResponse().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    showLoading()
                }
                Status.SUCCESS -> {
                    hideLoading()
                    binding.apply {
                        it.data?.let { it1 ->
                            UserDataManager.currentUserId = it1.id?.toLong() ?: -1L
                            UserDataManager.currentUserName = it1.name ?: ""
                            UserDataManager.currentCreateAt = it1.createdAt ?: ""
                            UserDataManager.currentUserPhone = it1.phone ?: ""
                            UserDataManager.currentPackage = it1.packageId ?: -1

                            tvName.text = it1.name
                            tvTimeCreated.text = "Tham gia từ tháng ${it1.createdAt}"
                            edtName1.setText(it1.name)
                            tvPhone.text = it1.phone
                            tvPacketName.text = "${it1.packageId}"
                            tvExpiryDate.text = "${it1.createdAt}"

                        }
                    }
                }

                Status.ERROR -> {
                    hideLoading()
                    resolveError(it.code, it.message)
                }
            }
        }

        profilesViewModel.resultUpdateProfilesResponse().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    showLoading()
                }
                Status.SUCCESS -> {
                    hideLoading()
                    binding.apply {
                        if (it.code == BaseErrorSignal.RESPONSE_SUCCESS) {
                            UserDataManager.currentUserName = edtName1.text.toString().trim()
                            ivEditUserNameDone.visibility = View.GONE
                            ivEditUserName.visibility = View.VISIBLE
                            edtName1.isFocusableInTouchMode = false
                            edtName1.isFocusable = false
                            tvName.text = edtName1.text.toString().trim()
                            edtName1.hideKeyboard()
                            toast("Cập nhật thông tin cá nhân thành công")
                        } else {
                            hideLoading()
                            resolveError(it.code, it.message)
                        }
                    }
                }

                Status.ERROR -> {
                    hideLoading()
                    toast("Cập nhật thông tin cá nhân thất bại")
                }
            }
        }
        if (UserDataManager.accessToken.isNotEmpty())
            profilesViewModel.getProfiles(true)
    }

    override fun onRefresh() {
        profilesViewModel.getProfiles(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogout.setOnClickListener {
            dialogLogOut(it.context)
        }

        binding.ivEditPassword.setOnClickListener {
            val i = Intent(activity, ChangePasswordActivity::class.java)
            startActivity(i)
        }

        binding.ivEditUserName.setOnClickListener {
            binding.ivEditUserName.visibility = View.GONE
            binding.ivEditUserNameDone.visibility = View.VISIBLE
            binding.edtName1.isFocusableInTouchMode = true
            binding.edtName1.isFocusable = true
            binding.edtName1.requestFocus()
            binding.edtName1.showSoftKeyboard()
        }

        binding.ivEditUserNameDone.setOnClickListener {
            val name = binding.edtName1.text.toString().trim()
            if (isRequiredFieldsValid(name)) {
                profilesViewModel.updateUserInfo(name)
            }
        }
    }

    private fun isRequiredFieldsValid(name: String): Boolean {
        if (name.trim().isEmpty()) {
            binding.edtName1.error = "Họ tên không được để trống"
            binding.edtName1.requestFocus()
            return false
        }
        return true
    }

    private var dialogLogOut: MaterialDialog? = null
    private fun dialogLogOut(context: Context) {
        if (dialogLogOut != null && dialogLogOut!!.isShowing) return
        dialogLogOut = MaterialDialog.Builder(context)
            .customView(R.layout.dialog_logout, false)
            .autoDismiss(false)
            .canceledOnTouchOutside(false)
            .build()

        dialogLogOut?.let { dialog ->
            val tvNo = dialog.findViewById(R.id.tvNo)
            tvNo.setOnClickListener { dialog.dismiss() }

            val tvYes = dialog.findViewById(R.id.tvYes)
            tvYes.setOnClickListener {
                UserDataManager.deleteUserInfo()
                val intent = Intent(requireActivity(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                activity?.finish()
                activity?.finishAffinity()
                dialog.dismiss()
            }

            val window: Window? = dialog.window
            if (window != null) {
                window.attributes.windowAnimations = R.style.BottomDialog
                window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
            dialog.show()
        }
    }
}