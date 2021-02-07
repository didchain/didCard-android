package com.nbs.didcard.ui.splash

import com.nbs.android.lib.base.BaseViewModel
import com.nbs.didcard.R
import com.nbs.didcard.provider.context
import com.nbs.didcard.ui.guide.GuideModel
import com.nbs.didcard.ui.main.MainActivity
import com.nbs.didcard.utils.CardUtils
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class SplashViewModel(val guideModel: GuideModel) : BaseViewModel() {

    val hasAccount: Boolean

    init {
        hasAccount = CardUtils.hasCard(CardUtils.getCardPath(context()))
    }

    fun loadCard() {
        guideModel.loadCard(CardUtils.getCardPath(context()))
            .subscribe(object : SingleObserver<Boolean> {
                override fun onSuccess(loadResult: Boolean) {
                    startActivityAndFinish(MainActivity::class.java)
                }

                override fun onSubscribe(d: Disposable) {
                    addSubscribe(d)
                }

                override fun onError(e: Throwable) {
                    showErrorToast(R.string.splash_load_error, e)
                    finish()
                }

            })
    }
}