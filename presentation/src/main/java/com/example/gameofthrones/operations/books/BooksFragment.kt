package com.example.gameofthrones.operations.books

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.example.gameofthrones.R
import com.example.gameofthrones.base.BaseFragment
import com.example.gameofthrones.base.BaseViewModel
import com.example.gameofthrones.databinding.BooksFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class BooksFragment : BaseFragment<BooksViewState, BooksViewTransition>() {

    override val viewModel by viewModel<BooksViewModel>()
    private val binding get() = _binding as BooksFragmentBinding

    override fun getViewBinding(container: ViewGroup?): ViewBinding =
        BooksFragmentBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        viewModel.getBooks()
    }

    override fun initListeners() = Unit // Not implemented

    override fun manageState(state: BooksViewState) {
        when (state) {
            is BooksViewState.Books -> {
                binding.pbLoading.visibility = when (state.loading) {
                    true -> View.VISIBLE
                    false -> View.GONE
                }

                if (!state.data.isNullOrEmpty()) {
                    binding.rvBooks.visibility = View.VISIBLE
                    setRecyclerView(state.data!!)
                }
            }
        }
    }

    override fun manageTransition(transition: BooksViewTransition) {
        when (transition) {
            // Failure
            is BooksViewTransition.OnNoInternet -> {
                binding.tvMessage.apply {
                    text = getString(R.string.no_internet_error)
                    visibility = View.VISIBLE
                }
            }
            else -> binding.tvMessage.apply {
                text = getString(R.string.unknown_error)
                visibility = View.VISIBLE
            }
        }
    }

    private fun setRecyclerView(data: List<BookViewEntity>) {
        binding.rvBooks.apply {
            setHasFixedSize(true)
            val layout = LinearLayoutManager(requireContext())
            layoutManager = layout
            adapter = BooksAdapter(data)
        }
    }
}