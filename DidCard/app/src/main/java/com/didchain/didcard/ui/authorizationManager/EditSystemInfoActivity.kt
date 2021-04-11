package com.didchain.didcard.ui.authorizationManager

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.observe
import com.didchain.android.lib.base.BaseActivity
import com.didchain.didcard.R
import com.didchain.didcard.BR
import com.didchain.didcard.Constants
import com.didchain.didcard.IntentKey
import com.didchain.didcard.databinding.ActivityEditSystemInfoBinding
import com.didchain.didcard.room.Account
import kotlinx.android.synthetic.main.activity_edit_system_info.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
@KoinApiExtension
class EditSystemInfoActivity:BaseActivity<EditSystemInfoViewModel,ActivityEditSystemInfoBinding>() {


    override val mViewModel: EditSystemInfoViewModel  by viewModel()

    override fun getLayoutId(savedInstanceState: Bundle?): Int = R.layout.activity_edit_system_info

    override fun initView() {
        mViewModel.title.set(getString(R.string.authorization_system_info))
        mViewModel.showBackImage.set(true)
        val account = intent.getParcelableExtra<Account>(IntentKey.EDIT_ACCOUNT)
        if(account !=null){
            mViewModel.oldAccount = account
            mViewModel.url.set(account.url)
            mViewModel.userName.set(account.userName)
            mViewModel.password.set(account.password)
        }
    }

    override fun initData() {
    }

    override fun initObserve() {
        mViewModel.openSelectSystemEvent.observe(this) {
            val intent = Intent(this@EditSystemInfoActivity, SelectSystemsActivity::class.java)
            intent.putExtra(IntentKey.CURRENT_URL, mViewModel.url.get())
            startActivityForResult(intent, Constants.CODE_ACTIVITY_REQUEST)
        }

        mViewModel.finishResultActivityEvent.observe(this) {
            setResult(RESULT_OK)
            finish()
        }
    }

    override fun statusBarStyle(): Int = STATUSBAR_STYLE_WHITE

    override fun initVariableId(): Int = BR.viewModel

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== RESULT_OK ){
            mViewModel.url.set(data?.getStringExtra(IntentKey.CURRENT_URL))
        }
    }
}