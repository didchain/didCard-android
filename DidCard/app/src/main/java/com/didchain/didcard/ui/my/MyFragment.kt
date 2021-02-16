package com.didchain.didcard.ui.my

import androidx.lifecycle.Observer
import com.didchain.android.lib.base.BaseFragment
import com.didchain.didcard.BR
import com.didchain.didcard.R
import com.didchain.didcard.databinding.FragmentMyBinding
import com.didchain.didcard.utils.DialogUtils
import com.didchain.didcard.view.PasswordPop
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.interfaces.SimpleCallback
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class MyFragment : BaseFragment<MyViewModel, FragmentMyBinding>() {
    lateinit var passwordDialog: BasePopupView
    override fun getLayoutId(): Int = R.layout.fragment_my
    override val mViewModel: MyViewModel by viewModel()
    override fun initView() {
        mViewModel.title.set(getString(R.string.my))
    }

    override fun initData() {
    }

    override fun initVariableId(): Int = BR.viewModel

    override fun initObserve() {
        mViewModel.showPasswordDialog.observe(this, object : Observer<Boolean> {
            override fun onChanged(open: Boolean) {
                if (open) {
                    showPasswordDialog()
                }
            }
        })
        mViewModel.dismissPasswordDialog.observe(this, object : Observer<Boolean> {
            override fun onChanged(open: Boolean?) {
                if(MyFragment::passwordDialog.isLateinit && passwordDialog.isShow){
                    passwordDialog.dismiss()
                }
            }
        })
    }

    private fun showPasswordDialog() {
        passwordDialog = DialogUtils.showPasswordDialog(mActivity, object : PasswordPop.InputPasswordListener {
                override fun input(password: String) {
                    mViewModel.openIdCard(password)
                }

            }, object : SimpleCallback() {
                override fun onBackPressed(popupView: BasePopupView?): Boolean {
                    mViewModel.openNoScretObservable.set(!mViewModel.openNoScretObservable.get())
                    return false
                }
            })
    }


}