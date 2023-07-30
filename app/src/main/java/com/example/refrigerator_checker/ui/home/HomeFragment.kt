package com.example.refrigerator_checker.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.refrigerator_checker.R
import com.example.refrigerator_checker.ui.main.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout

class HomeFragment : Fragment() {

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?
  ): View? {
    val root = inflater.inflate(R.layout.fragment_home, container, false)

    return root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    val sectionsPagerAdapter = context?.let { SectionsPagerAdapter(it, childFragmentManager) }
    val viewPager: ViewPager = requireActivity().findViewById(R.id.view_pager)
    viewPager.adapter = sectionsPagerAdapter
    val tabs: TabLayout = requireActivity().findViewById(R.id.tabs)
    tabs.setupWithViewPager(viewPager)

  }
}