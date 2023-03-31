package vn.gotech.audiobook.base.domain.module

import vn.gotech.audiobook.base.domain.repository.RequestAuthRepository
import vn.gotech.audiobook.base.domain.repository.RequestNoAuthRepository
import org.koin.dsl.module

val repoModule = module {
    single {
        RequestNoAuthRepository(get())
    }
    single {
        RequestAuthRepository(get())
    }
}