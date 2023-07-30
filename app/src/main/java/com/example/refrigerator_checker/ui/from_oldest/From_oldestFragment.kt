package com.example.refrigerator_checker.ui.from_oldest

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.refrigerator_checker.R
import com.example.refrigerator_checker.ui.main.Food
import com.example.refrigerator_checker.ui.main.ListAdapter
import com.example.refrigerator_checker.ui.main.SectionsPagerAdapter
import com.example.refrigerator_checker.ui.main.SectionsPagerAdapter_oldest
import com.google.android.material.tabs.TabLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class From_oldestFragment : Fragment() {

  override fun onCreateView(
          inflater: LayoutInflater,
          container: ViewGroup?,
          savedInstanceState: Bundle?
  ): View? {

    val root = inflater.inflate(R.layout.fragment_from_oldest, container, false)

    return root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    val sectionsPagerAdapter = context?.let { SectionsPagerAdapter_oldest(it, childFragmentManager) }
    val viewPager: ViewPager = requireActivity().findViewById(R.id.view_pager)
    viewPager.adapter = sectionsPagerAdapter
    val tabs: TabLayout = requireActivity().findViewById(R.id.tabs)
    tabs.setupWithViewPager(viewPager)

  }
}