package com.nbs.didcard.ui.main

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

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {
    val fragments = arrayListOf<Fragment>()
    private val titles = arrayOf(R.string.main_home, R.string.main_my)
    private val icons = arrayOf(R.drawable.tab_home_selector, R.drawable.tab_my_selector)

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView() {
        tablayout.setupWithViewPager(viewpager)

    }

    override fun initData() {
        fragments.add(HomeFragment())
        fragments.add(MyFragment())
        viewpager.adapter = MainPagerAdapter(supportFragmentManager, fragments)
        tablayout.setupWithViewPager(viewpager, false)

        titles.forEachIndexed { index, titleId ->
            tablayout.getTabAt(index)?.setCustomView(getItemView(index, titleId, icons[index]))
        }
    }

    override fun initObserve() {
    }

    override fun statusBarStyle(): Int = STATUSBAR_STYLE_GRAY

    override fun initVariableId(): Int = BR.viewModel

    fun getItemView(index: Int, titleId: Int, iconId: Int): View {
        val view: View
        if (index == 0) {
            view = View.inflate(this, R.layout.item_tab_home, null)
        } else {
            view = View.inflate(this, R.layout.item_tab_my, null)
        }

        val icon = view.findViewById<ImageView>(R.id.icon)
        val title = view.findViewById<TextView>(R.id.title)
        title.text = getString(titleId)
        icon.background = getDrawable(iconId)
        return view
    }

    override val mViewModel: MainViewModel
        get() = createViewModel(MainViewModel::class.java)

}