package com.didchain.didcard.ui.authorizationManager

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.observe
import com.didchain.android.lib.base.BaseActivity
import com.didchain.didcard.R
import com.didchain.didcard.BR
import com.didchain.didcard.Constants
import com.didchain.didcard.IntentKey
import com.didchain.didcard.databinding.ActivityAuthorizationSystemBinding
import kotlinx.android.synthetic.main.activity_select_systems.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
@KoinApiExtension
class AuthorizationSystemActivity:BaseActivity<AuthorizationSystemViewModel,ActivityAuthorizationSystemBinding>() {
    override val mViewModel: AuthorizationSystemViewModel by viewModel()

    override fun getLayoutId(savedInstanceState: Bundle?): Int = R.layout.activity_authorization_system

    override fun initView() {
        mViewModel.title.set(getString(R.string.my_authorization_management))
        mViewModel.showBackImage.set(true)
        recyclerView.itemAnimator = null
    }

    override fun initData() {
    }

    override fun initObserve() {
        mViewModel.editAccountEvent.observe(this){
            val intent = Intent(this@AuthorizationSystemActivity, EditSystemInfoActivity::class.java)
            intent.putExtra(IntentKey.EDIT_ACCOUNT,it)
            startActivityForResult(intent, Constants.CODE_ACTIVITY_REQUEST)
        }

        mViewModel.addSystemEvent.observe(this){
            val intent = Intent(this@AuthorizationSystemActivity, EditSystemInfoActivity::class.java)
            startActivityForResult(intent, Constants.CODE_ACTIVITY_REQUEST)
        }
    }

    override fun statusBarStyle(): Int = STATUSBAR_STYLE_GRAY

    override fun initVariableId(): Int =BR.viewModel

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK && requestCode == Constants.CODE_ACTIVITY_REQUEST){
            mViewModel.getData()
        }
    }
}