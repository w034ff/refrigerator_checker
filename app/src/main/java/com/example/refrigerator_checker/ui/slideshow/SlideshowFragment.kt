package com.example.refrigerator_checker.ui.slideshow

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
import com.example.refrigerator_checker.R
import com.example.refrigerator_checker.ui.main.Food
import com.example.refrigerator_checker.ui.main.ListAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SlideshowFragment : Fragment() {

  lateinit var foodList: MutableList<Food>
  lateinit var listview: ListView
  lateinit var formatted_M: String
  lateinit var formatted_MM: String

  override fun onCreateView(
          inflater: LayoutInflater,
          container: ViewGroup?,
          savedInstanceState: Bundle?
  ): View? {
    val current = LocalDateTime.now()
    val formatter_M = DateTimeFormatter.ofPattern("yyyy-M-d")
    formatted_M = current.format(formatter_M).toString()
    val formatter_MM = DateTimeFormatter.ISO_DATE
    formatted_MM = current.format(formatter_MM).toString()

    val root = inflater.inflate(R.layout.fragment_slideshow, container, false)

    return root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    val database = FirebaseDatabase.getInstance()
    val today_food = database.getReference("food")

    foodList = mutableListOf()
    listview = requireActivity().findViewById<ListView>(R.id.list_view_today)

    today_food.addValueEventListener(object : ValueEventListener {
      override fun onDataChange(snapshot: DataSnapshot) {
        if (snapshot!!.exists()) {
          foodList.clear()
          for (h in snapshot.children) {
            for(i in h.children) {
              val date = i.child("date").getValue(String::class.java)
              if(formatted_M == date) {
                val food = i.getValue(Food::class.java)
                foodList.add(food!!)
              }
              else if(formatted_MM == date) {
                val food = i.getValue(Food::class.java)
                foodList.add(food!!)
              }
              else{ continue }
            }
          }

          //アダプターにユーザーリストを導入
          val adapter = context?.let { ListAdapter(it, foodList) }
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