package com.tunaskelapa.pantunn.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tunaskelapa.pantunn.R
import com.tunaskelapa.pantunn.data.DataPantun
import com.tunaskelapa.pantunn.databinding.ItemBinding

class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    private val listPantun = ArrayList<DataPantun>()
    fun setData(items : ArrayList<DataPantun>) {
        listPantun.clear()
        listPantun.addAll(items)
        notifyDataSetChanged()
    }
    class ViewHolder(private val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pantun : DataPantun) {
            with(binding) {
                tvGenre.text = pantun.genre
                tvPantun.text = root.resources.getString(R.string.tv_pantun_item, pantun.bait1, pantun.bait2, pantun.bait3, pantun.bait4)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(listPantun[position])

    override fun getItemCount(): Int = listPantun.size
}