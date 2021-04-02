package com.example.gameofthrones.operations.chars

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.example.gameofthrones.R
import com.example.gameofthrones.base.BaseFragment
import com.example.gameofthrones.databinding.CharsFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharsFragment : BaseFragment<CharsViewState, CharsViewTransition>() {
    override val viewModel by viewModel<CharsViewModel>()
    private val binding get() = _binding as CharsFragmentBinding

    override fun getViewBinding(container: ViewGroup?): ViewBinding =
        CharsFragmentBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        viewModel.getChars()
    }

    override fun initListeners() = Unit // Not implemented

    override fun manageState(state: CharsViewState) {
        when (state) {
            is CharsViewState.Chars -> {
                binding.apply {
                    pbLoading.visibility = when (state.loading) {
                        true -> View.VISIBLE
                        false -> View.GONE
                    }

                    state.data?.let {
                        when (it.isEmpty()) {
                            true -> {
                                tvMessage.text = getString(R.string.chars_not_found)
                                tvMessage.visibility = View.VISIBLE
                            }
                            false -> setRecyclerView(it)
                        }
                    }
                }
            }
        }
    }

    override fun manageTransition(transition: CharsViewTransition) {
        when (transition) {
            // Failure
            is CharsViewTransition.OnNoInternet -> binding.tvMessage.apply {
                text = getString(R.string.no_internet_error)
                visibility = View.VISIBLE
            }
            else -> binding.tvMessage.apply {
                text = getString(R.string.unknown_error)
                visibility = View.VISIBLE
            }
        }
    }

    private fun setRecyclerView(data: List<CharViewEntity>) {
        binding.rvChars.apply {
            setHasFixedSize(true)
            val layout = LinearLayoutManager(requireContext())
            layoutManager = layout
            adapter = CharsAdapter(data)
        }
    }
}