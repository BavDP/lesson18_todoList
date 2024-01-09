package com.example.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.viewpager.adapters.TabAdapter
import com.example.viewpager.models.todoList.TodoTaskState
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class TabViewPagerFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_view_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createViewPager()
    }

    private fun createViewPager() {
        val adapter = TabAdapter(requireActivity())
        val viewPager = requireView().findViewById<ViewPager2>(R.id.todoListPager)
        viewPager.adapter = adapter

        val tabs = requireView().findViewById<TabLayout>(R.id.tabs)
        TabLayoutMediator(tabs, viewPager
        ) { tab, position ->
            tab.text = TodoTaskState.values()[position].toString()
        }.attach()
    }
}