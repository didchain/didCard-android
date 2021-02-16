package com.didchain.didcard.ui

import androidgolib.Androidgolib
import com.didchain.android.lib.base.BaseModel
import com.didchain.didcard.bean.CardBean
import com.didchain.didcard.provider.context
import com.didchain.didcard.utils.CardUtils
import com.didchain.didcard.utils.CommonSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleOnSubscribe

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
open class IDCardModel:BaseModel() {
    suspend fun getIDCard(): CardBean? {
        return CardUtils.loadCardBeanByPath(CardUtils.getCardPath(context()))
    }

    fun openIdCard(password: String): Single<Boolean> {
        return  Single.create(SingleOnSubscribe<Boolean> { emitter ->
            Androidgolib.open(password)
            emitter.onSuccess(true)
        }).compose(CommonSchedulers.io2mainAndTimeout<Boolean>())
    }
}