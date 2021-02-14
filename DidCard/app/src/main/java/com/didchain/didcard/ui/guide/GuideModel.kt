package com.didchain.didcard.ui.guide

import androidgolib.Androidgolib
import com.didchain.didcard.utils.CommonSchedulers
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

    fun import(idCardJson: String,password: String): Single<String> {
        return Single.create(SingleOnSubscribe<String> { emitter ->
            val id = String(Androidgolib.import_(password,idCardJson))
            emitter.onSuccess(id)
        }).compose(CommonSchedulers.io2mainAndTimeout<String>())
    }
}