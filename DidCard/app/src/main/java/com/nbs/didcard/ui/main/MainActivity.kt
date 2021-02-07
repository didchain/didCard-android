package com.nbs.didcard.ui.main

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.nbs.android.lib.base.BaseActivity
import com.nbs.didcard.BR
import com.nbs.didcard.R
import com.nbs.didcard.databinding.ActivityMainBinding
import com.nbs.didcard.ui.home.HomeFragment
import com.nbs.didcard.ui.my.MyFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    private val fragments = arrayListOf<Fragment>()
    private val titles = arrayOf(R.string.main_home, R.string.main_my)
    private val icons = arrayOf(R.drawable.tab_home_selector, R.drawable.tab_my_selector)

    override fun getLayoutId(savedInstanceState: Bundle?): Int = R.layout.activity_main
    override val mViewModel: MainViewModel by viewModel()
    private val myFragment by inject<MyFragment>()
    private val homeFragment by inject<HomeFragment>()
    override fun initView() {
        tablayout.setupWithViewPager(viewpager)

    }

    override fun initData() {
        fragments.add(homeFragment)
        fragments.add(myFragment)
        viewpager.adapter = MainPagerAdapter(supportFragmentManager, fragments)
        tablayout.setupWithViewPager(viewpager, false)

        titles.forEachIndexed { index, titleId ->
            tablayout.getTabAt(index)?.setCustomView(getItemView(index, titleId, icons[index]))
        }
    }

    override fun initObserve() {
    }

    override fun statusBarStyle(): Int = STATUSBAR_STYLE_TRANSPARENT

    override fun initVariableId(): Int = BR.viewModel

    fun getItemView(index: Int, titleId: Int, iconId: Int): View {
        val view: View = if (index == 0) {
            View.inflate(this, R.layout.item_tab_home, null)
        } else {
            View.inflate(this, R.layout.item_tab_my, null)
        }

        val icon = view.findViewById<ImageView>(R.id.icon)
        val title = view.findViewById<TextView>(R.id.title)
        title.text = getString(titleId)
        icon.background = getDrawable(iconId)
        return view
    }


}