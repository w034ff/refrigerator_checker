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


class JapaneseFragment : Fragment() {

  lateinit var listview: ListView
  lateinit var DishList: MutableList<Dish>
  lateinit var TonkatsuList : ArrayList<Dish>
  lateinit var NikujagaList : ArrayList<Dish>

  var Tonkatsucount : Int = 0
  var Nikujagacount : Int = 0


  override fun onCreateView(
          inflater: LayoutInflater,
          container: ViewGroup?,
          savedInstanceState: Bundle?
  ): View? {

    val root = inflater.inflate(R.layout.fragment_dish, container, false)

    TonkatsuList = arrayListOf(Dish("tonkatsu", 2), Dish("pork_roast", 0), Dish("egg", 0), Dish("cabbage", 0)
                        , Dish("cake_flour", 0), Dish("panko", 0), Dish("tonkatsu_sauce", 0), Dish("egg_soup", 0))
    NikujagaList = arrayListOf(Dish("nikujaga", 2), Dish("sliced_beef", 0), Dish("onion", 0), Dish("carrot", 0)
                        , Dish("potato", 0), Dish("soy_sauce", 0), Dish("french_beans", 0), Dish("salad", 0))

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
            for(i in h.children) {

              val name = i.child("name").getValue(String::class.java)

              if(TonkatsuList.contains(Dish(name.toString(), 1))
                      && i.child("value").getValue(String::class.java).equals("0")){
                val index = TonkatsuList.indexOf(Dish(name.toString(),1))
                TonkatsuList[index] = Dish(name.toString(), 0)

              } else if(i.child("value").getValue(String::class.java).equals("0")){

              } else if(TonkatsuList.contains(Dish(name.toString(), 0))) {
                val index = TonkatsuList.indexOf(Dish(name.toString(),0))
                TonkatsuList[index] = Dish(name.toString(), 1)
                Tonkatsucount = Tonkatsucount + 1

              }
              if(NikujagaList.contains(Dish(name.toString(), 1))
                      && i.child("value").getValue(String::class.java).equals("0")){
                val index = NikujagaList.indexOf(Dish(name.toString(),1))
                NikujagaList[index] = Dish(name.toString(), 0)

              } else if(i.child("value").getValue(String::class.java).equals("0")){

              } else if(NikujagaList.contains(Dish(name.toString(), 0))) {
                val index = NikujagaList.indexOf(Dish(name.toString(),0))
                NikujagaList[index] = Dish(name.toString(), 1)
                Nikujagacount = Nikujagacount + 1

              }
            }
          }
          val Tonkatsu = Tonkatsucount.toDouble() / TonkatsuList.count().toDouble()
          val Nikujaga = Nikujagacount.toDouble() / NikujagaList.count().toDouble()

          DishList.clear()

          if(Tonkatsu > Nikujaga){
            DishList = TonkatsuList.toMutableList()
          } else{
            DishList = NikujagaList.toMutableList()
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