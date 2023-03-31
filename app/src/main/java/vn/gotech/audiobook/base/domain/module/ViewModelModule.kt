package vn.gotech.audiobook.base.domain.module

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import vn.gotech.audiobook.base.VersionCheckingViewModel
//import vn.gotech.audiobook.ui.book.detail.BookDetailViewModel
//import vn.gotech.audiobook.ui.category.CategoryNewViewModel
//import vn.gotech.audiobook.ui.category.detail.CategoryDetailViewModel
//import vn.gotech.audiobook.ui.change_password.ChangePasswordViewModel
//import vn.gotech.audiobook.ui.change_password.otp.ChangePasswordOtpViewModel
//import vn.gotech.audiobook.ui.favorite.FavoriteViewModel
//import vn.gotech.audiobook.ui.forgot.ForgotViewModel
//import vn.gotech.audiobook.ui.forgot.otp.ForgotOtpViewModel
//import vn.gotech.audiobook.ui.home.HomeViewModel
//import vn.gotech.audiobook.ui.login.LoginViewModel
//import vn.gotech.audiobook.ui.profiles.ProfilesViewModel
//import vn.gotech.audiobook.ui.register.RegisterViewModel
//import vn.gotech.audiobook.ui.register.otp.RegisterOtpViewModel

val viewModelModule = module {

//    viewModel {
//        BookDetailViewModel(get(), get())
//    }
//    viewModel {
//        CategoryDetailViewModel(get(), get())
//    }
//    viewModel {
//        CategoryNewViewModel(get(), get())
//    }
//    viewModel {
//        ChangePasswordOtpViewModel(get(), get())
//    }
//    viewModel {
//        ChangePasswordViewModel(get(), get())
//    }
//    viewModel {
//        FavoriteViewModel(get(), get())
//    }
//    viewModel {
//        ForgotViewModel(get(), get())
//    }
//    viewModel {
//        ForgotOtpViewModel(get(), get())
//    }
//    viewModel {
//        HomeViewModel(get(), get(), get())
//    }
//    viewModel {
//        LoginViewModel(get(), get())
//    }
//
//    viewModel {
//        ProfilesViewModel(get(), get())
//    }
//    viewModel {
//        RegisterOtpViewModel(get(), get())
//    }
//    viewModel {
//        RegisterViewModel(get(), get())
//    }

    viewModel {
        VersionCheckingViewModel()
    }
}