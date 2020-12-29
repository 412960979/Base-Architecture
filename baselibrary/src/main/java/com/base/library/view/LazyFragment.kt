package com.base.library.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * # 懒加载，预加载
预加载：1.内存使用增加；2.数据加载增多，接口调用增多

懒加载：
目的：减少同一时刻内存的使用;  为了解决预加载中出现的性能问题

ViewPager2提供了懒加载的接口
 */
abstract class LazyFragment : Fragment() {
    private var rootView: View? = null
    private var isViewCreate = false

    // 表示当前fragment之前的状态
    private var currentViewVisibleState = false

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)

        if (isViewCreate) {
            // 当前fragment创建之后，布局解析完之后菜执行
            if (!currentViewVisibleState && isVisibleToUser) {
                // 之前为不可见，现在为可见
                dispatchUserVisibleHint(true)
            } else if (currentViewVisibleState && !isVisibleToUser) {
                // 之前为可见，现在为不可见
                dispatchUserVisibleHint(false)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutRes(), null, false)
        }

        isViewCreate = true
        if (userVisibleHint) {
            userVisibleHint = isViewCreate
        }
        return rootView
    }

    override fun onResume() {
        super.onResume()

        // 恢复加载数据
        if (userVisibleHint && !currentViewVisibleState){
            dispatchUserVisibleHint(true)
        }
    }

    override fun onPause() {
        super.onPause()

        // 暂停加载数据
        if (userVisibleHint && currentViewVisibleState){
            dispatchUserVisibleHint(false)
        }
    }


    open abstract fun getLayoutRes(): Int

    private fun dispatchUserVisibleHint(visibleState: Boolean) {
        currentViewVisibleState = visibleState

        if (visibleState) {
            // 加载数据
            loadData()
        } else {
            // 暂停加载数据
            pauseLoadData()
        }
    }

    fun loadData() {}

    fun pauseLoadData() {}
}