package com.example.refrigerator_checker.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.refrigerator_checker.R
import com.google.android.material.tabs.TabLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment_oldest : Fragment() {

    lateinit var foodList: MutableList<Food>
    lateinit var listview : ListView
    lateinit var formatted: String


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ISO_DATE
        formatted = current.format(formatter).toString()

        val root = inflater.inflate(R.layout.fragment_main, container, false)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val database = FirebaseDatabase.getInstance()

        val fresh_food = database.getReference("food").child("fresh")

        foodList = mutableListOf()
        listview = requireActivity().findViewById<ListView>(R.id.list_view1)

        fresh_food.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot!!.exists()) {
                    foodList.clear()
                    for (h in snapshot.children) {
                        if(h.child("value").getValue(String::class.java).equals("0")){
                            continue
                        }
                        val food = h.getValue(Food::class.java)
                        foodList.add(food!!)

                    }
                    foodList.sortBy{ Food -> Food.date }
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