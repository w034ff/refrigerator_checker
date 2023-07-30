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
import com.example.refrigerator_checker.ui.main.ListAdapter
import com.example.refrigerator_checker.ui.slideshow.SlideshowFragment
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class ChineseFragment : Fragment() {

  lateinit var listview: ListView
  lateinit var DishList: MutableList<Dish>
  lateinit var PeppersteakList: ArrayList<Dish>

  var Psteakcount: Int = 0


  override fun onCreateView(
          inflater: LayoutInflater,
          container: ViewGroup?,
          savedInstanceState: Bundle?
  ): View? {

    val root = inflater.inflate(R.layout.fragment_dish, container, false)

    PeppersteakList = arrayListOf(Dish("pepper_steak", 2), Dish("cookdo_pepper_steak", 0)
            , Dish("sliced_beef", 0), Dish("green_pepper", 0), Dish("bamboo_shoot", 0), Dish("starch", 0))

    return root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    val database = FirebaseDatabase.getInstance()
    val japanese = database.getReference("food")

    DishList = mutableListOf()
    listview = requireActivity().findViewById<ListView>(R.id.list_view_dish)

    japanese.addValueEventListener(object : ValueEventListener {
      override fun onDataChange(snapshot: DataSnapshot) {
        if (snapshot!!.exists()) {
//          foodList.clear()
          for (h in snapshot.children) {
            for (i in h.children) {

              val name = i.child("name").getValue(String::class.java)

              if(PeppersteakList.contains(Dish(name.toString(), 1))
                      && i.child("value").getValue(String::class.java).equals("0")){
                val index = PeppersteakList.indexOf(Dish(name.toString(), 1))
                PeppersteakList[index] = Dish(name.toString(), 0)

              } else if(i.child("value").getValue(String::class.java).equals("0")){

              } else if (PeppersteakList.contains(Dish(name.toString(), 0))) {
                val index = PeppersteakList.indexOf(Dish(name.toString(), 0))
                PeppersteakList[index] = Dish(name.toString(), 1)
                Psteakcount = Psteakcount + 1

              }
            }
          }
          val Psteak = Psteakcount.toDouble() / PeppersteakList.count().toDouble()
          val other = 0

          DishList.clear()

          if (Psteak >= other) {
            DishList = PeppersteakList.toMutableList()

          } else {

          }

          //アダプターにユーザーリストを導入
          val adapter = context?.let { DishListAdapter(it, DishList) }
          //リストビューにアダプターを設定
          listview.adapter = adapter

        }
      }

      override fun onCancelled(error: DatabaseError) {
        //エラー処理
        Log.w("onCancelled", "error:", error.toException())
      }
    })
  }
}
