package com.didchain.didcard.ui.authorizationManager

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.lifecycle.observe
import com.didchain.android.lib.base.BaseActivity
import com.didchain.didcard.BR
import com.didchain.didcard.IntentKey
import com.didchain.didcard.R
import com.didchain.didcard.databinding.ActivitySelectSystemsBinding
import kotlinx.android.synthetic.main.activity_select_systems.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension


/**
 *Author:Mr'x
 *Time:
 *Description:
 */
@KoinApiExtension
class SelectSystemsActivity : BaseActivity<SelectSystemsViewModel, ActivitySelectSystemsBinding>() {
    override val mViewModel: SelectSystemsViewModel by viewModel()

    override fun getLayoutId(savedInstanceState: Bundle?): Int = R.layout.activity_select_systems

    override fun initView() {
        mViewModel.title.set(getString(R.string.authorization_system_list))
        mViewModel.showBackImage.set(true)
        recyclerView.itemAnimator = null
        val currentUrl = intent.getStringExtra(IntentKey.CURRENT_URL)
        if (!TextUtils.isEmpty(currentUrl)) {
            mViewModel.currentUrl = currentUrl.toString()
        }
        swipeRefreshLayout.isRefreshing = true
        mViewModel.getSystems()

    }

    override fun initData() {
    }

    override fun initObserve() {
        mViewModel.finishRefreshingEvent.observe(this) {
            swipeRefreshLayout.isRefreshing = false
        }

        mViewModel.finishResultActivityEvent.observe(this) {
            val intent = Intent()
            intent.putExtra(IntentKey.CURRENT_URL, it)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    override fun statusBarStyle(): Int = STATUSBAR_STYLE_WHITE

    override fun initVariableId(): Int = BR.viewModel
}