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


class WesternFragment : Fragment() {

  lateinit var listview: ListView
  lateinit var DishList: MutableList<Dish>
  lateinit var OmericeList: ArrayList<Dish>

  var Omericecount: Int = 0


  override fun onCreateView(
          inflater: LayoutInflater,
          container: ViewGroup?,
          savedInstanceState: Bundle?
  ): View? {

    val root = inflater.inflate(R.layout.fragment_dish, container, false)

    OmericeList = arrayListOf(Dish("omelet_rice", 2), Dish("chicken_thigh", 0), Dish("onion", 0), Dish("hot_rice", 2)
            , Dish("tomato_ketchup", 0), Dish("egg", 0), Dish("butter", 0))

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

              if(i.child("value").getValue(String::class.java).equals("0")
                      && OmericeList.contains(Dish(name.toString(), 1))){
                val index = OmericeList.indexOf(Dish(name.toString(), 1))
                OmericeList[index] = Dish(name.toString(), 0)

              } else if(i.child("value").getValue(String::class.java).equals("0")){

              } else if (OmericeList.contains(Dish(name.toString(), 0))) {
                val index = OmericeList.indexOf(Dish(name.toString(), 0))
                OmericeList[index] = Dish(name.toString(), 1)
                Omericecount = Omericecount + 1

              }
            }
          }
          val Omerice = Omericecount.toDouble() / OmericeList.count().toDouble()
          val other = 0

          DishList.clear()

          if (Omerice >= other) {
            DishList = OmericeList.toMutableList()

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
