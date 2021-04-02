package com.example.gameofthrones.operations.chars

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gameofthrones.databinding.CharsItemBinding

class CharsAdapter(private val chars: List<CharViewEntity>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = CharsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CharsHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CharsHolder -> holder.bind(chars[position])
        }
    }

    override fun getItemCount(): Int = chars.size

    class CharsHolder(private val binding: CharsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CharViewEntity) = binding.apply {
            tvName.text = item.name
            tvGender.text = item.gender
            item.culture?.let {
                tvCulture.text = it
                tvCulture.visibility = View.VISIBLE
            }
            item.born?.let {
                tvBorn.text = it
                tvBorn.visibility = View.VISIBLE
            }
            item.died?.let {
                tvDied.text = it
                tvDied.visibility = View.VISIBLE
            }
            item.titles?.let {
                tvTitles.text = it
                tvTitles.visibility = View.VISIBLE
            }
            item.aliases?.let {
                tvAliases.text = it
                tvAliases.visibility = View.VISIBLE
            }
            item.father?.let {
                tvFather.text = it
                tvFather.visibility = View.VISIBLE
            }
            item.mother?.let {
                tvMother.text = it
                tvMother.visibility = View.VISIBLE
            }
            item.spouse?.let {
                tvSpouse.text = it
                tvSpouse.visibility = View.VISIBLE
            }
            item.allegiances?.let {
                tvAllegiances.text = it
                tvAllegiances.visibility = View.VISIBLE
            }
            item.playedBy?.let {
                tvPlayedBy.text = it
                tvPlayedBy.visibility = View.VISIBLE
            }
        }
    }

}