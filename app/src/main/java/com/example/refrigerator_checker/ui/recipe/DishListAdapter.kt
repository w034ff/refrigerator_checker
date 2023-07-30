package com.example.refrigerator_checker.ui.recipe

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.refrigerator_checker.ui.home.HomeFragment
import com.example.refrigerator_checker.R


class DishListAdapter(val context : Context, val DishList: List<Dish>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view: View = LayoutInflater.from(context).inflate(R.layout.item_dish, null)

        val rLayout = view.findViewById<RelativeLayout>(R.id.firstLin)
        val llayout = view.findViewById<RelativeLayout>(R.id.sedoncLin)

        val Image = view.findViewById<ImageView>(R.id.image)
        val Image_dish = view.findViewById<ImageView>(R.id.image_recipe)
        val Name = view.findViewById<TextView>(R.id.dish_food)
        val Name_dish = view.findViewById<TextView>(R.id.recipe_name)
        val None = view.findViewById<TextView>(R.id.none_food)

        val dish = DishList[position]

        if(position == 0){
            Image_dish.setImageResource(context.getResources().getIdentifier(dish.name, "drawable",
                    context.getPackageName()))
            Name_dish.text = "Ingredients for " + dish.name
            rLayout.removeAllViews()

        } else{
            llayout.removeAllViews()
            if(dish.boolean == 0){
                rLayout.setBackgroundColor(Color.rgb(255, 220, 127))
                Image.setImageResource(context.getResources().getIdentifier(dish.name, "drawable",
                        context.getPackageName()))
                Name.text = dish.name
                None.text = "This food is not in the fridge"

            } else{
                Image.setImageResource(context.getResources().getIdentifier(dish.name, "drawable",
                        context.getPackageName()))
                Name.text = dish.name
                None.text = ""

            }
        }

        return view
    }


    override fun getItem(position: Int): Any {
        return DishList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return DishList.size
    }
}