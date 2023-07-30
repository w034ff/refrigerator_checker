package com.example.refrigerator_checker.ui.main

import android.content.Context
import android.content.res.AssetManager
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.refrigerator_checker.R


class ListAdapter(val context : Context, val foodList: List<Food>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        val view: View = inflater.inflate(R.layout.item_processed, parent, false)
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_food, null)

        val Image = view.findViewById<ImageView>(R.id.image)
        val Name = view.findViewById<TextView>(R.id.name_food)
        val Date = view.findViewById<TextView>(R.id.date_food)

        val food = foodList[position]

        Image.setImageResource(context.getResources().getIdentifier(food.name, "drawable",
                                context.getPackageName()))
        Name.text = food.name
        Date.text = "put in : " + food.date + "（" + food.value + " piece）"

        return view
    }


    override fun getItem(position: Int): Any {
        return foodList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return foodList.size
    }
}