package com.didchain.didcard.ui.idcard

import android.os.Bundle
import androidx.lifecycle.Observer
import com.didchain.android.lib.base.BaseActivity
import com.didchain.didcard.BR
import com.didchain.didcard.R
import com.didchain.didcard.databinding.ActivityShowIdCardBinding
import com.didchain.didcard.utils.BitmapUtils
import kotlinx.android.synthetic.main.activity_show_id_card.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class ShowIDCardActivity : BaseActivity<ShowIDCardViewModel, ActivityShowIdCardBinding>() {

    override fun getLayoutId(savedInstanceState: Bundle?): Int = R.layout.activity_show_id_card
    override val mViewModel: ShowIDCardViewModel by viewModel()

    override fun initView() {
        mViewModel.title.set(getString(R.string.id_card_title))
        mViewModel.showBackImage.set(true)
    }

    override fun initData() {
    }

    override fun initObserve() {

        mViewModel.idCardJsonEvent.observe(this, object : Observer<String> {
            override fun onChanged(qrjson: String?) {
                qrjson?.let {
                    idQR.setImageBitmap(BitmapUtils.stringToQRBitmap(it))
                }

            }
        })
    }

    override fun initVariableId(): Int = BR.viewModel
    override fun statusBarStyle(): Int = STATUSBAR_STYLE_GRAY

}