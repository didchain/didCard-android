package com.didchain.didcard.ui.home

import androidgolib.Androidgolib
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList
import androidx.lifecycle.rxLifeScope
import com.didchain.android.lib.base.BaseViewModel
import com.didchain.android.lib.command.BindingAction
import com.didchain.android.lib.command.BindingCommand
import com.didchain.android.lib.event.SingleLiveEvent
import com.didchain.didcard.BR
import com.didchain.didcard.Constants
import com.didchain.didcard.DidCardApp
import com.didchain.didcard.R
import com.didchain.didcard.bean.CardBean
import com.didchain.didcard.bean.ServiceBean
import com.didchain.didcard.provider.context
import com.didchain.didcard.utils.CommonUtils
import com.didchain.didcard.utils.SharedPref
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import me.tatarka.bindingcollectionadapter2.ItemBinding
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
@KoinApiExtension
class HomeViewModel : BaseViewModel(), KoinComponent {
    private val model: HomeModel by inject()
    var cardBean: CardBean? = null
    private val itemTitles = arrayListOf(R.string.home_notice, R.string.home_registered, R.string.home_hotel)
    private val itemIcons = arrayListOf(R.drawable.notice_icon, R.drawable.hospital_icon, R.drawable.hotel_icon)
    val showPasswordEvent = SingleLiveEvent<Boolean>()
    val dismissPasswordEvent = SingleLiveEvent<Boolean>()
    val showQREvent = SingleLiveEvent<Boolean>()
    val openNoSecret: Boolean by SharedPref(context(), Constants.KEY_OPEN_NO_SECRET, false)
    var openFingerPrint: Boolean by SharedPref(context(), Constants.KEY_OPEN_FINGERPRINT, false)
    val id = ObservableField<String>()
    val city = ObservableField<String>("")
    val showLock = ObservableField<Boolean>(true)
    val items: ObservableList<HomeItemViewModel> = ObservableArrayList()
    val itemBinding = ItemBinding.of<HomeItemViewModel>(BR.item, R.layout.item_service)

    init {
        initData()
    }

    fun initData() {
        showLock.set(!Androidgolib.isOpen())
        items.clear()
        itemTitles.forEachIndexed { index, i ->
            items.add(HomeItemViewModel(this, ServiceBean(DidCardApp.instance.getString(itemTitles[index]), itemIcons[index])))
        }

        rxLifeScope.launch {
            cardBean = model.getIDCard()
            id.set(cardBean?.did)
            if (Androidgolib.isOpen()) {
                showQREvent.call()
            }
        }
    }

    val clickUnLock = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            showPasswordEvent.call()
        }
    })

    val clickCopyDid = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            if (id.get() != null) {
                CommonUtils.copyToMemory(context(), id.get()!!)
                showToast(R.string.id_card_copy_success)
            }

        }
    })

    fun openIdCard(password: String) {
        model.openIdCard(password).subscribe(object : SingleObserver<Boolean> {
            override fun onSuccess(t: Boolean) {
                showLock.set(false)
                dismissPasswordEvent.call()
            }

            override fun onSubscribe(d: Disposable) {
                addSubscribe(d)
            }

            override fun onError(e: Throwable) {
                showErrorToast(R.string.open_error, e)
            }

        })
    }


}