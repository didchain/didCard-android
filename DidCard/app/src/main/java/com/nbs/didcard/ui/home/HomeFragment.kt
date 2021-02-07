package com.nbs.didcard.ui.home

import androidx.lifecycle.Observer
import com.lxj.xpopup.XPopup
import com.nbs.android.lib.base.BaseFragment
import com.nbs.didcard.BR
import com.nbs.didcard.R
import com.nbs.didcard.databinding.FragmentHomeBinding
import com.nbs.didcard.utils.BitmapUtils
import com.nbs.didcard.view.PasswordPop
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_home
    override val mViewModel: HomeViewModel by viewModel()

    override fun initView() {
        mViewModel.title.set(getString(R.string.app_name))
        rq.setLineColor(resources.getColor(R.color.color_0c123d, null))
        rq.setImageBitmap(BitmapUtils.stringToQRBitmap("13022929300939283"))
    }

    override fun initData() {
    }

    override fun initVariableId(): Int = BR.viewModel

    override fun initObserve() {
        mViewModel.showPasswordEvent.observe(this, Observer {
            showPasswordDialog()
        })
    }

    private fun showPasswordDialog() {
        XPopup.Builder(mActivity).isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
            .asCustom(PasswordPop(mActivity, object : PasswordPop.UnlockListener {
                override fun unlock() {
                    mViewModel.showLock.set(false)
                }

            })).show()

    }


}