package com.example.gameofthrones.operations.houses

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.example.gameofthrones.R
import com.example.gameofthrones.base.BaseFragment
import com.example.gameofthrones.base.BaseViewModel
import com.example.gameofthrones.databinding.HousesFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HousesFragment : BaseFragment<HousesViewState, HousesViewTransition>() {
    override val viewModel by viewModel<HousesViewModel>()
    private val binding get() = _binding as HousesFragmentBinding

    override fun getViewBinding(container: ViewGroup?): ViewBinding =
        HousesFragmentBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        viewModel.getHouses()
    }

    override fun initListeners() = Unit // Not implemented

    override fun manageState(state: HousesViewState) {
        when (state) {
            is HousesViewState.Houses -> {
                binding.apply {
                    pbLoading.visibility = when (state.loading) {
                        true -> View.VISIBLE
                        false -> View.GONE
                    }

                    state.data?.let {
                        when (it.isEmpty()) {
                            true -> tvMessage.text = getString(R.string.houses_not_found)
                            false -> setRecyclerView(it)
                        }
                    }
                }
            }
        }
    }

    override fun manageTransition(transition: HousesViewTransition) {
        when (transition) {
            // Failure
            is HousesViewTransition.OnNoInternet -> binding.tvMessage.apply {
                text = getString(R.string.no_internet_error)
                visibility = View.VISIBLE
            }
            else -> binding.tvMessage.apply {
                text = getString(R.string.unknown_error)
                visibility = View.GONE
            }
        }
    }

    private fun setRecyclerView(data: List<HouseViewEntity>) {
        binding.rvHouses.apply {
            setHasFixedSize(true)
            val layout = LinearLayoutManager(requireContext())
            layoutManager = layout
            adapter = HousesAdapter(data)
        }
    }

}