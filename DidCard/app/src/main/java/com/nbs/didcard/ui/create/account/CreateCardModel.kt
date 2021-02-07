package com.nbs.didcard.ui.create.account

import androidgolib.Androidgolib
import com.nbs.android.lib.base.BaseModel
import com.nbs.didcard.utils.CommonSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleOnSubscribe

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class CreateCardModel : BaseModel() {

    fun createAccount(password: String): Single<String> {
        return Single.create(SingleOnSubscribe<String> { emitter ->
            val newWallet = Androidgolib.newCard(password)
            emitter.onSuccess(String(newWallet))
        }).compose(CommonSchedulers.io2mainAndTimeout<String>())
    }
}