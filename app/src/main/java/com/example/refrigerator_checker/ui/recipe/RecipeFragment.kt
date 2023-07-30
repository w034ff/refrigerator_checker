package com.example.refrigerator_checker.ui.recipe

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.refrigerator_checker.R
import com.example.refrigerator_checker.ui.from_oldest.From_oldestFragment
import com.example.refrigerator_checker.ui.gallery.GalleryFragment
import com.example.refrigerator_checker.ui.home.HomeFragment
import com.example.refrigerator_checker.ui.main.Food
import com.example.refrigerator_checker.ui.slideshow.SlideshowFragment
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class RecipeFragment : Fragment() {

  lateinit var listview: ListView
  lateinit var recipeList : ArrayList<Recipe>
//  lateinit var recipeList: MutableList<Recipe>

  override fun onCreateView(
          inflater: LayoutInflater,
          container: ViewGroup?,
          savedInstanceState: Bundle?
  ): View? {

    val root = inflater.inflate(R.layout.fragment_recipe, container, false)

    val Japanese = Recipe("JAPANESE", R.drawable.nikujaga)
    val Western = Recipe("WESTERN", R.drawable.omelet_rice)
    val Chinese = Recipe("CHINESE", R.drawable.pepper_steak)
    val Korean = Recipe("KOREAN", R.drawable.kimchi_nabe)
    recipeList = arrayListOf(Japanese, Western, Chinese, Korean)

    return root
  }
  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    listview = requireActivity().findViewById<ListView>(R.id.list_view_recipe)

    val adapter = context?.let { RecipeListAdapter(it, recipeList) }
    listview.adapter = adapter

    listview.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->

      recipeList.clear()
      val adapter = context?.let { RecipeListAdapter(it, recipeList) }
      listview.adapter = adapter

      val fragmentTransaction = childFragmentManager.beginTransaction()
      fragmentTransaction.addToBackStack(null)
//      fragmentTransaction.replace(R.id.container, HomeFragment())
      when (position) {
        0 -> fragmentTransaction.replace(R.id.container, JapaneseFragment()).commit()
        1 -> fragmentTransaction.replace(R.id.container, WesternFragment()).commit()
        2 -> fragmentTransaction.replace(R.id.container, ChineseFragment()).commit()
        3 -> fragmentTransaction.replace(R.id.container, KoreanFragment()).commit()
      }
    })

  }

}