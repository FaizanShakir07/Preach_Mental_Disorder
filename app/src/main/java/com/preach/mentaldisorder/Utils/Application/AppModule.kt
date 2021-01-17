package com.preach.mentaldisorder.Utils.Application

import com.preach.mentaldisorder.Models.Source.Repository.DataRepository
import com.preach.mentaldisorder.Models.Source.ServerConnection.RetrofitClientInstance
import com.preach.mentaldisorder.Utils.GeneralUtils.SessionManager
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {

    factory { RetrofitClientInstance(get()) }
    factory { SessionManager(get()) }
    factory { DataRepository(get(), get(), get()) }

}

val viewModelModules = module {
//    viewModel {
//        ProfileViewModel(get())
//    }

}