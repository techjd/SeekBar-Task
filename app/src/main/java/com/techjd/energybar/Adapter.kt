package com.techjd.energybar

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

class Adapter(
    val energyBars: ArrayList<EnergyBar>,
    val context: Context
) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val seekBar = itemView.findViewById<SeekBar>(R.id.seekBar)
        val left = itemView.findViewById<Button>(R.id.left)
        val right = itemView.findViewById<Button>(R.id.right)

        @RequiresApi(Build.VERSION_CODES.O)
        fun setMinMax(min: Int, max: Int) {
            seekBar.min = min
            seekBar.max = max
            seekBar.progress = max
            left.text = min.toString()
            right.text = max.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout, parent, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = energyBars[position]
        holder.setMinMax(item.min, item.max)
        holder.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                holder.right.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (seekBar?.min == seekBar?.progress) {
                    if (holder.adapterPosition != 0) {
                        deleteElement(holder.adapterPosition)
                    } else {
                        deleteAll()
                    }
                } else if (abs(seekBar!!.max - seekBar.progress) > 2) {
                    val newSeekBar = EnergyBar(
                        seekBar.progress + 1,
                        seekBar.max
                    )
                    seekBar.max = seekBar.progress
                    seekBar.progress = seekBar.max
                    addElement(newSeekBar, holder.adapterPosition + 1)

                } else {
                    seekBar.progress = seekBar.max
                    Toast.makeText(context, "Minimum Segment Length is 2", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        })
    }

    override fun getItemCount(): Int {
        return energyBars.size
    }

    fun addElement(bar: EnergyBar, position: Int) {
        energyBars.add(position, bar)
        println(energyBars)
        notifyItemInserted(position)
    }

    fun deleteElement(position: Int) {
        energyBars[position - 1].max = energyBars[position].max
        energyBars.removeAt(position)
        notifyItemRemoved(position)
        notifyItemChanged(position - 1)
    }

    fun deleteAll() {
        energyBars.removeAll(energyBars)
        energyBars.add(
            EnergyBar(
                1,
                100
            )
        )
        notifyDataSetChanged()
    }
}