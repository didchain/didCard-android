package com.nbs.didcard

import com.nbs.android.lib.base.BaseApplication
import com.nbs.didcard.ui.create.account.CreateAccountViewModel
import com.nbs.didcard.ui.guide.GuideViewModel
import com.nbs.didcard.ui.home.HomeFragment
import com.nbs.didcard.ui.home.HomeViewModel
import com.nbs.didcard.ui.idcard.ShowIDCardViewModel
import com.nbs.didcard.ui.idmanager.IDCardManagerViewModel
import com.nbs.didcard.ui.idmanager.UpdatePasswordViewModel
import com.nbs.didcard.ui.main.MainViewModel
import com.nbs.didcard.ui.my.MyFragment
import com.nbs.didcard.ui.my.MyViewModel
import com.nbs.didcard.ui.saveaccount.SaveAccountViewModel
import com.nbs.didcard.ui.scan.ScanViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.androidx.fragment.dsl.fragment
import org.koin.core.context.startKoin
import org.koin.dsl.module

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class DidCardApp : BaseApplication() {

    companion object {
        lateinit var instance: DidCardApp
    }


    override fun onCreate() {
        super.onCreate()
        instance = this
        val appModule = module {

            viewModel { CreateAccountViewModel() }
            viewModel { GuideViewModel() }
            viewModel { HomeViewModel() }
            viewModel { ShowIDCardViewModel() }
            viewModel { IDCardManagerViewModel() }
            viewModel { MainViewModel() }
            viewModel { SaveAccountViewModel() }
            viewModel { UpdatePasswordViewModel() }
            viewModel { MyViewModel() }
            viewModel { ScanViewModel() }
            fragment { MyFragment() }
            fragment { HomeFragment() }


        }
        startKoin {
            androidLogger()
            androidContext(instance)
            modules(appModule)
        }
    }
}

