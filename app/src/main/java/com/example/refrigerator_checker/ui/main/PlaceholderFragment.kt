package com.example.refrigerator_checker.ui.main

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.example.refrigerator_checker.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {

//    private lateinit var pageViewModel: PageViewModel
    lateinit var foodList: MutableList<Food>
    lateinit var listview : ListView
    var tab_position : Int = 0

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)

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
//                        System.out.println(food)
                        foodList.add(food!!)

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_activity2, menu)
        super.onCreateOptionsMenu(menu, inflater)

        val seachItem = menu.findItem(R.id.action_search)
        val searchView = seachItem.actionView as SearchView

        searchView.queryHint = "Search food ..."
        searchView.clearFocus()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                // text changed
                val auto_name = foodList.filter { Food -> Food.name.startsWith(newText) }
                //アダプターにユーザーリストを導入
                val adapter = context?.let { ListAdapter(it, auto_name) }
                //リストビューにアダプターを設定
                listview.adapter = adapter

                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                // submit button pressed
                val full_name = foodList.filter { Food -> Food.name.startsWith(query) }

                //アダプターにユーザーリストを導入
                val adapter = context?.let { ListAdapter(it, full_name) }
                //リストビューにアダプターを設定
                listview.adapter = adapter

                return false
            }
        })
    }
}