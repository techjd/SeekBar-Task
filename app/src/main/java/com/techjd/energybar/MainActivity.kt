package com.techjd.energybar

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)

        val list = arrayListOf(
            EnergyBar(1, 32),
            EnergyBar(33, 56),
            EnergyBar(57, 85),
            EnergyBar(86, 100)
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = Adapter(
            list, this
        )
    }
}