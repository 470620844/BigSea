package com.chs.bigsea.an

import android.app.Activity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chs.bigsea.R
import com.chs.lib_core.base.BaseFragment
import com.chs.lib_core.imageloader.ImageLoader
import com.gyf.immersionbar.ImmersionBar
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener
import com.zhpan.bannerview.BannerViewPager
import com.zhpan.bannerview.adapter.OnPageChangeListenerAdapter
import com.zhpan.bannerview.constants.IndicatorSlideMode
import com.zhpan.bannerview.constants.PageStyle
import kotlinx.android.synthetic.main.fragment_wan.*
import kotlinx.android.synthetic.main.title_bar.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class AnFragment : BaseFragment<AnViewModel>(){

    private val mViewModel:AnViewModel by viewModel()
    private val mAdapter:HomeAdapter by lazy { HomeAdapter(mViewModel.mHomeRecyclerData.value) }
    private lateinit var bannerViewPager:BannerViewPager<HomeBanner,NetViewHolder>
    private var bannerHeight:Int = 0
    companion object {
        fun newInstance() = AnFragment()
    }

    override fun layoutId(): Int {
        return R.layout.fragment_wan
    }

    override fun initView() {
        recyclerview.layoutManager = LinearLayoutManager(requireContext())
        recyclerview.adapter = mAdapter
        mAdapter.setNewData(ArrayList())
        addBannerView()
        ImmersionBar.with(requireActivity()).transparentStatusBar().init()
        ImmersionBar.setTitleBar(this, toolbar)
    }

    override fun initListener() {
        super.initListener()
        refreshview.setOnRefreshListener {
             refreshview.finishRefresh()
        }
        refreshview.setOnMultiPurposeListener(object : SimpleMultiPurposeListener(){
            override fun onHeaderMoving(
                header: RefreshHeader?, isDragging: Boolean, percent: Float, offset: Int, headerHeight: Int, maxDragHeight: Int) {
                super.onHeaderMoving(header, isDragging, percent, offset, headerHeight, maxDragHeight)
                if(isDragging&&toolbar.visibility==View.VISIBLE){
                    toolbar.visibility = View.GONE
                }
                if(offset == 0 && toolbar.visibility==View.GONE){
                    toolbar.visibility = View.VISIBLE
                }
            }

            override fun onHeaderFinish(header: RefreshHeader?, success: Boolean) {
                super.onHeaderFinish(header, success)
                toolbar.visibility = View.VISIBLE
            }
        })
        recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var totalDy = 0
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                totalDy += dy
                if (totalDy <= bannerHeight) {
                    val alpha: Float = totalDy.toFloat() / bannerHeight
                    toolbar.setBackgroundColor(
                        ColorUtils.blendARGB(
                            Color.TRANSPARENT
                            , ContextCompat.getColor(requireActivity(), R.color.colorPrimary), alpha
                        )
                    )
                } else {
                    toolbar.setBackgroundColor(
                        ColorUtils.blendARGB(
                            Color.TRANSPARENT
                            , ContextCompat.getColor(requireActivity(), R.color.colorPrimary), 1f
                        )
                    )
                }
            }
        })
    }
    private fun addBannerView() {
        val bannerView = LayoutInflater.from(requireContext()).inflate(R.layout.header_home,recyclerview,false)
        bannerViewPager = bannerView.findViewById(R.id.banner)
        val ivBanner = bannerView.findViewById<ImageView>(R.id.iv_banner)
        val flHeaderBg = bannerView.findViewById<FrameLayout>(R.id.fl_header_bg)
//        ImmersionBar.setTitleBarMarginTop(this, bannerViewPager)

        val list = getList<HomeBanner>()
        loadHeaderBg(list[0].url,ivBanner,flHeaderBg)
        bannerViewPager.setCanLoop(true)
            .setIndicatorSlideMode(IndicatorSlideMode.NORMAL)
            .setPageMargin(resources.getDimensionPixelOffset(R.dimen.dp_10))
            .setRevealWidth(resources.getDimensionPixelOffset(R.dimen.dp_10))
            .setPageStyle(PageStyle.MULTI_PAGE)
            .setHolderCreator{NetViewHolder()}
            .setInterval(3000)
            .setOnPageChangeListener(object : OnPageChangeListenerAdapter(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    loadHeaderBg(list[position].url,ivBanner,flHeaderBg)
                }
            })
            .create(list)
        bannerViewPager.startLoop()
        mAdapter.addHeaderView(bannerView)
        val bannerParams = bannerViewPager.layoutParams
        val titleBarParams = toolbar.layoutParams
        bannerHeight =
            bannerParams.height - titleBarParams.height - ImmersionBar.getStatusBarHeight(requireActivity())

    }

    fun loadHeaderBg(url:String,ivBanner:ImageView,bgView:View){
        ImageLoader.url(url)
            .intoBg(ivBanner,bgView)
    }

    override fun onPause() {
        super.onPause()
        bannerViewPager.stopLoop()
    }

    override fun immersionBarEnabled(): Boolean {
        return true
    }

    override fun initImmersionBar() {
        ImmersionBar.with(this)
            .keyboardEnable(false)
            .transparentStatusBar()
            .init()
    }

    private fun <T> getList(): MutableList<HomeBanner> {
        val res = mutableListOf<HomeBanner>()
        res.add(HomeBanner("https://www.wanandroid.com/blogimgs/74a84e45-7f93-476d-bc85-446e1d118b6f.png",""))
        res.add(HomeBanner("https://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png",""))
        res.add(HomeBanner("https://www.wanandroid.com/blogimgs/62c1bd68-b5f3-4a3c-a649-7ca8c7dfabe6.png",""))
        return res
    }

    override fun initData() {
        mViewModel.getBannerData()
        mViewModel.getHomeListData()
        mViewModel.mHomeRecyclerData.observe(this, Observer {
            mAdapter.addData(it)
        })
    }
}
