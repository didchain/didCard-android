package com.nbs.didcard.ui.my

import com.nbs.android.lib.base.BaseFragment
import com.nbs.didcard.BR
import com.nbs.didcard.R
import com.nbs.didcard.databinding.FragmentMyBinding
import org.koin.android.ext.android.inject
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