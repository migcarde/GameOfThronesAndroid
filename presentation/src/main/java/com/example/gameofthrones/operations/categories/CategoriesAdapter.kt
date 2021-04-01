package com.example.gameofthrones.operations.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gameofthrones.R
import com.example.gameofthrones.databinding.CategoriesItemBinding

class CategoriesAdapter(
    private val categories: List<CategoryViewEntity>,
    private val onClick: (Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = CategoriesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CategoriesHolder(view) { onClick(it) }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CategoriesHolder -> holder.bind(categories[position])
        }
    }

    override fun getItemCount(): Int = categories.size

    class CategoriesHolder(
        private val binding: CategoriesItemBinding,
        val onClickCategory: (Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CategoryViewEntity) = binding.apply {
            tvTitle.text = item.categoryName
        }
    }
}