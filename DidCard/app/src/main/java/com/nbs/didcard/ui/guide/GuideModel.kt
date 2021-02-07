package com.nbs.didcard.ui.guide

import androidgolib.Androidgolib
import com.nbs.didcard.utils.CommonSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleOnSubscribe

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class GuideModel {

    fun loadCard(path: String): Single<Boolean> {
        return Single.create(SingleOnSubscribe<Boolean> { emitter ->
            val loadResult = Androidgolib.loadCardByPath(path)
            emitter.onSuccess(loadResult)
        }).compose(CommonSchedulers.io2mainAndTimeout<Boolean>())
    }
}