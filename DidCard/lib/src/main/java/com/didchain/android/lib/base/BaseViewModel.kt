package com.didchain.android.lib.base

import android.graphics.Bitmap
import android.os.Bundle
import androidx.databinding.ObservableField
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.didchain.android.lib.R
import com.didchain.android.lib.command.BindingAction
import com.didchain.android.lib.command.BindingCommand
import com.didchain.android.lib.event.SingleLiveEvent
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.Job
import java.util.*


/**
 * @description:
 * @author:  Mr.x
 * @date :   2020/11/3 8:15 AM
 */

abstract class BaseViewModel : ViewModel(), IBaseViewModel {
    var title = ObservableField<String>("")
    val showBackImage = ObservableField<Boolean>(false)
    val showRightText = ObservableField<Boolean>(false)
    val showRightIv = ObservableField<Boolean>(false)
    val rightText = ObservableField<String>("")
    val rightIv= ObservableField<Int>()

    val jobs = mutableListOf<Job>()

    val clickBackCommand = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            finish()
        }

    })

    val clickRightTvCommand = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            clickRightTv()
        }

    })

    val clickRightIvCommand = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            clickRightTv()
        }

    })

    open fun clickRightTv() {

    }

    open fun clickRightIv() {

    }

    val uc: UIChangeLiveData by lazy {
        UIChangeLiveData()
    }

    fun showToast(msgId: Int) {
        uc.toastEvent.postValue(msgId)
    }

    fun showToast(msg: String) {
        uc.toastStrEvent.postValue(msg)
    }

    fun showErrorToast(msgId: Int, t: Throwable) {
        if (t.message.equals("Job was cancelled")) {
            return
        }
        uc.toastEvent.postValue(msgId)
    }

    open fun showDialog(titleId: Int= R.string.empty) {
        uc.showDialogEvent.postValue(titleId)
    }

    open fun showDialogNotCancel(titleId: Int= R.string.empty) {
        uc.showDialogNotCancelEvent.postValue(titleId)
    }

    open fun showDialogNotCancel(title: String) {
        uc.showDialogNotCancelStrEvent.postValue(title)
    }

    open fun showDialog(title: String) {
        uc.showDialogStrEvent.postValue(title)
    }

    open fun dismissDialog() {
        uc.dismissDialogEvent.postValue(System.currentTimeMillis())
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    open fun startActivity(clz: Class<*>) {
        startActivity(clz, null, false)
    }

    open fun startActivityAndFinish(clz: Class<*>) {
        startActivity(clz, null, true)
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    open fun startActivity(clz: Class<*>, bundle: Bundle?, finish: Boolean = false) {
        val params: MutableMap<String, Any> = HashMap()
        params[ParameterField.CLASS] = clz
        params[ParameterField.FINISH] = finish
        if (bundle != null) {
            params[ParameterField.BUNDLE] = bundle
        }
        uc.startActivityEvent.postValue(params)
    }


    fun startWebActivity(url: String) {
        uc.startWebActivityEvent.postValue(url)
    }

    /**
     * 关闭界面
     */
    open fun finish() {
        uc.finishEvent.call()
    }

    class UIChangeLiveData : SingleLiveEvent<Any>() {
        val toastEvent: SingleLiveEvent<Int> by lazy {
            SingleLiveEvent<Int>()
        }

        val toastStrEvent: SingleLiveEvent<String> by lazy {
            SingleLiveEvent<String>()
        }

        val showDialogEvent: SingleLiveEvent<Int> by lazy {
            SingleLiveEvent<Int>()
        }
        val showDialogNotCancelEvent: SingleLiveEvent<Int> by lazy {
            SingleLiveEvent<Int>()
        }
        val showDialogNotCancelStrEvent: SingleLiveEvent<String> by lazy {
            SingleLiveEvent<String>()
        }
        val showDialogStrEvent: SingleLiveEvent<String> by lazy {
            SingleLiveEvent<String>()
        }
        val dismissDialogEvent: SingleLiveEvent<Long> by lazy {
            SingleLiveEvent<Long>()
        }
        val startActivityEvent: SingleLiveEvent<Map<String, Any>> by lazy {
            SingleLiveEvent<Map<String, Any>>()
        }

        val startWebActivityEvent: SingleLiveEvent<String> by lazy {
            SingleLiveEvent<String>()
        }
        val finishEvent: SingleLiveEvent<Void> by lazy {
            SingleLiveEvent<Void>()
        }
        val onBackPressedEvent: SingleLiveEvent<Void> by lazy {
            SingleLiveEvent<Void>()
        }

    }

    fun cancelRequest() {

    }

    override fun onAny(owner: LifecycleOwner?, event: Lifecycle.Event?) {
    }

    override fun onCreate() {
    }

    override fun onDestroy() {
        removeAllDisposable()
    }

    override fun onStart() {
    }

    override fun onStop() {
    }

    override fun onResume() {
    }

    override fun onPause() {
    }

    private val mCompositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    open fun addSubscribe(disposable: Disposable) {
        mCompositeDisposable.add(disposable)
    }

    open fun removeAllDisposable() {
        mCompositeDisposable.clear()
    }
}

object ParameterField {
    var CLASS = "CLASS"
    var BUNDLE = "BUNDLE"
    var FINISH = "FINISH"
}

