package com.nbs.didcard.ui.home

import com.nbs.android.lib.base.BaseFragment
import com.nbs.didcard.BR
import com.nbs.didcard.R
import com.nbs.didcard.databinding.FragmentHomeBinding
import com.nbs.didcard.utils.BitmapUtils
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.inject

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_home
    override val mViewModel: HomeViewModel by inject()

    override fun initView() {
        mViewModel.title.set(getString(R.string.app_name))
        rq.setLineColor(resources.getColor(R.color.color_0c123d, null))
        rq.setImageBitmap(BitmapUtils.stringToQRBitmap("13022929300939283"))
    }

    override fun initData() {
    }

    override fun initVariableId(): Int = BR.viewModel

    override fun initObserve() {
    }


}