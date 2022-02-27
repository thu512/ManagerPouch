package com.jinjoo.managerpouch.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.jinjoo.managerpouch.PouchApplication
import com.jinjoo.managerpouch.databinding.FragmentMainBinding
import com.jinjoo.managerpouch.util.autoCleared
import com.jinjoo.managerpouch.viewmodel.MainViewModel
import io.reactivex.disposables.CompositeDisposable

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var binding by autoCleared<FragmentMainBinding>()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        initCategoryTab()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }

    private fun initCategoryTab() {
        val adapter = CategoryTabListAdapter()
        compositeDisposable.add(adapter.itemClickSubject.subscribe {
            scrollSelectedItemToCenter(it.first, it.second)
        })

        binding.categoryTabList.apply {
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            this.adapter = adapter
            scrollToPosition(0)
        }
    }

    private fun scrollSelectedItemToCenter(selectedPosition: Int, itemWidth: Int) {
        val layoutManager = binding.categoryTabList.layoutManager as? LinearLayoutManager
        val screenWidth = PouchApplication.getApplicationContext().resources.displayMetrics.widthPixels
        val centerOfScreen: Int = screenWidth / 2 - itemWidth/2
        layoutManager!!.scrollToPositionWithOffset(selectedPosition, centerOfScreen)
    }

}