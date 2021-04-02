package com.example.gameofthrones.operations.houses

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.gameofthrones.databinding.HousesItemBinding

class HousesAdapter(private val houses: List<HouseViewEntity>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = HousesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return HousesHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HousesHolder -> holder.bind(houses[position])
        }
    }

    override fun getItemCount(): Int = houses.size

    class HousesHolder(private val binding: HousesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HouseViewEntity) = binding.apply {
            item.picture?.let {
                ivHousePicture.load(it)
                ivHousePicture.visibility = View.VISIBLE
            }
            tvName.text = item.name
            tvRegion.text = item.region
            tvTitle.text = item.title
        }
    }

}