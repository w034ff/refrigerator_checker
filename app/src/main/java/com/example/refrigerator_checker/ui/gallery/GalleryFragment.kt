package com.example.refrigerator_checker.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.refrigerator_checker.R
import com.google.firebase.database.FirebaseDatabase


class GalleryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_gallery, container, false)
        val textView: TextView = root.findViewById(R.id.text_gallery)
        textView.text = "Get out of the fridge"

        val Image = root.findViewById<ImageView>(R.id.image_swich)
        Image.setImageResource(requireContext().getResources().getIdentifier("get_out", "drawable",
                requireContext().getPackageName()));

        val database = FirebaseDatabase.getInstance()
        val situation = database.getReference("situation")
        situation.setValue("get_out")

        val switch = root.findViewById<Switch>(R.id.swich)
        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                // ONの処理
                textView.text = "Put in the fridge"
                Image.setImageResource(requireContext().getResources().getIdentifier("put_in", "drawable",
                        requireContext().getPackageName()));
                situation.setValue("put_in")
            } else {
                // OFFの処理
                textView.text = "Get out of the fridge"
                Image.setImageResource(requireContext().getResources().getIdentifier("get_out", "drawable",
                        requireContext().getPackageName()));
                situation.setValue("get_out")
            }
          }

        return root
    }
}