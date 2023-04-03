package vn.gotech.audiobook.base.domain.module

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import vn.gotech.audiobook.base.VersionCheckingViewModel
import vn.gotech.audiobook.ui.category.CategoryNewViewModel
import vn.gotech.audiobook.ui.category.detail.CategoryDetailViewModel

val viewModelModule = module {

    viewModel {
        CategoryNewViewModel(get(), get())
    }

    viewModel {
        CategoryDetailViewModel(get(), get())
    }

    viewModel {
        VersionCheckingViewModel()
    }
}