package com.didchain.didcard.ui.my

import com.didchain.android.lib.base.BaseFragment
import com.didchain.didcard.BR
import com.didchain.didcard.R
import com.didchain.didcard.databinding.FragmentMyBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class MyFragment : BaseFragment<MyViewModel, FragmentMyBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_my
    override val mViewModel: MyViewModel by viewModel()
    override fun initView() {
        mViewModel.title.set(getString(R.string.my))
    }

    override fun initData() {
    }

    override fun initVariableId(): Int = BR.viewModel

    override fun initObserve() {
    }


}