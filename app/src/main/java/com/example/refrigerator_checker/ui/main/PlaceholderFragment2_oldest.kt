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

class PlaceholderFragment2_oldest : Fragment(){

    lateinit var foodList2: MutableList<Food>
    lateinit var listview2 : ListView


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main2, container, false)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val database = FirebaseDatabase.getInstance()

        val process_food = database.getReference("food").child("process")

        foodList2 = mutableListOf()
        listview2 = requireActivity().findViewById<ListView>(R.id.list_view2)

        process_food.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot!!.exists()) {
                    foodList2.clear()
                    for (h in snapshot.children) {
                        if(h.child("value").getValue(String::class.java).equals("0")){
                            continue
                        }
                        val food = h.getValue(Food::class.java)
                        foodList2.add(food!!)

                    }
                    foodList2.sortBy{ Food -> Food.date }
                    //アダプターにユーザーリストを導入
                    val adapter = context?.let { ListAdapter(it, foodList2) }
                    //リストビューにアダプターを設定
                    listview2.adapter = adapter

                }
            }
            override fun onCancelled(error: DatabaseError) {
                //エラー処理
                Log.w("onCancelled", "error:", error.toException())
            }
        })

    }
}