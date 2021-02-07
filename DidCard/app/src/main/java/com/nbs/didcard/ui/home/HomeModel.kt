package com.nbs.didcard.ui.home

import com.nbs.android.lib.base.BaseModel
import com.nbs.didcard.provider.context
import com.nbs.didcard.utils.CardUtils

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class HomeModel : BaseModel() {
    suspend fun getId(): String? {
        return CardUtils.getId(context())
    }
}