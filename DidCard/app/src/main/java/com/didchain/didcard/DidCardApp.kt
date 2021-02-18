package com.didchain.didcard

import com.didchain.android.lib.base.BaseApplication
import com.didchain.didcard.ui.authorization.AuthorizationViewModel
import com.didchain.didcard.ui.create.CreateCardModel
import com.didchain.didcard.ui.create.CreateCardViewModel
import com.didchain.didcard.ui.guide.GuideModel
import com.didchain.didcard.ui.guide.GuideViewModel
import com.didchain.didcard.ui.home.HomeFragment
import com.didchain.didcard.ui.home.HomeModel
import com.didchain.didcard.ui.home.HomeViewModel
import com.didchain.didcard.ui.idcard.ShowIDCardViewModel
import com.didchain.didcard.ui.idmanager.IDCardManagerViewModel
import com.didchain.didcard.ui.idmanager.UpdatePasswordViewModel
import com.didchain.didcard.ui.main.MainViewModel
import com.didchain.didcard.ui.my.MyFragment
import com.didchain.didcard.ui.my.MyModel
import com.didchain.didcard.ui.my.MyViewModel
import com.didchain.didcard.ui.saveaccount.SaveAccountViewModel
import com.didchain.didcard.ui.scan.ScanViewModel
import com.didchain.didcard.ui.splash.SplashViewModel
import com.orhanobut.logger.*
import com.orhanobut.logger.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
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

        initKoin()
        initLogger()


    }

    private fun initLogger() {
        val formatStrategy: FormatStrategy =
                PrettyFormatStrategy.newBuilder().showThreadInfo(false) //（可选）是否显示线程信息。 默认值为true
                        .methodCount(2) // （可选）要显示的方法行数。 默认2
                        .methodOffset(7) // （可选）设置调用堆栈的函数偏移值，0的话则从打印该Log的函数开始输出堆栈信息，默认是0
                        //            .logStrategy(customLog) //（可选）更改要打印的日志策略。 默认LogCat
                        .tag(Constants.TAG_NAME) //（可选）每个日志的全局标记。 默认PRETTY_LOGGER（如上图）
                        .build()

        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG //只有在测试环境才打印日志
            }
        })
    }

    private fun initKoin() {
        val appModule = module {

            single { CreateCardModel() }
            single { HomeModel() }
            single { GuideModel() }
            single { MyModel() }

            viewModel { CreateCardViewModel() }
            viewModel { GuideViewModel() }
            viewModel { HomeViewModel() }
            viewModel { ShowIDCardViewModel() }
            viewModel { IDCardManagerViewModel() }
            viewModel { MainViewModel() }
            viewModel { SaveAccountViewModel() }
            viewModel { UpdatePasswordViewModel() }
            viewModel { MyViewModel() }
            viewModel { ScanViewModel() }
            viewModel { AuthorizationViewModel() }
            viewModel { SplashViewModel() }


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

