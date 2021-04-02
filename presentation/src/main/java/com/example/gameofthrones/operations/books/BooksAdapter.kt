package com.example.gameofthrones.operations.books

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gameofthrones.databinding.BooksItemBinding

class BooksAdapter(private val books: List<BookViewEntity>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = BooksItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return BooksHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BooksHolder -> holder.bind(books[position])
        }
    }

    override fun getItemCount(): Int = books.size

    class BooksHolder(private val binding: BooksItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BookViewEntity) = binding.apply {
            tvName.text = item.name
            tvIsbn.text = item.isbn
            tvAuthors.text = item.authors
            tvNumberOfPages.text = item.numberOfPages.toString()
            tvPublisher.text = item.publisher
            tvCountry.text = item.country
            tvMediaType.text = item.mediaType
            tvReleased.text = item.released
        }
    }
}