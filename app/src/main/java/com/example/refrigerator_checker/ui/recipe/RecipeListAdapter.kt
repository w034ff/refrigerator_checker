package com.example.refrigerator_checker.ui.recipe

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.refrigerator_checker.ui.home.HomeFragment
import com.example.refrigerator_checker.R


class RecipeListAdapter(val context : Context, val recipeList: List<Recipe>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view: View = LayoutInflater.from(context).inflate(R.layout.item_recipe, null)

        val Image = view.findViewById<ImageView>(R.id.image_recipe)
        val Name = view.findViewById<Button>(R.id.recipe_name)

        val recipe = recipeList[position]

        Image.setImageResource(recipe.imageID)
        Name.text = "SELECT " + recipe.name + " DISH"

        Name.setBackgroundColor(Color.parseColor("#ff99cc00"))
        Name.setOnClickListener{
            view -> (parent as ListView).performItemClick(view, position, R.id.recipe_name.toLong()) }

        return view
    }


    override fun getItem(position: Int): Any {
        return recipeList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return recipeList.size
    }
}