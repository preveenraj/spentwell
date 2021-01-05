package com.spentwell

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.spentwell.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val list = mutableListOf<String>()
        list.add("Sample")
        list.add("Prabha")
        list.add("Preveen")
        list.add("Sample")
        list.add("Prabha")
        list.add("Preveen")
        list.add("Sample")
        list.add("Prabha")
        list.add("Preveen")
        list.add("Sample")
        list.add("Prabha")
        list.add("Preveen")
        binding.recyclerView.adapter = SampleAdapter(list)
    }
}