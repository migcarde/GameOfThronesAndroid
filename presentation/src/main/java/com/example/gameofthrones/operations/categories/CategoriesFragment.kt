package com.example.gameofthrones.operations.categories

import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.example.gameofthrones.base.BaseFragment
import com.example.gameofthrones.databinding.CategoriesFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoriesFragment : BaseFragment<CategoriesViewState, CategoriesViewTransition>() {
    override val viewModel by viewModel<CategoriesViewModel>()
    private val binding get() = _binding as CategoriesFragmentBinding
    private val args: CategoriesFragmentArgs by navArgs()

    override fun getViewBinding(container: ViewGroup?): ViewBinding =
        CategoriesFragmentBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        args.categories?.let { viewModel.setCategories(it.toList()) }
    }

    override fun initListeners() = Unit // Not implemented

    override fun manageState(state: CategoriesViewState) {
        when (state) {
            is CategoriesViewState.Categories -> {
                if (!state.data.isNullOrEmpty()) {
                    setRecyclerView(state.data)
                }
            }
        }
    }

    private fun setRecyclerView(data: List<CategoryViewEntity>) {
        binding.apply {
            rvCategories.apply {
                setHasFixedSize(true)
                val layout = LinearLayoutManager(requireContext())
                layoutManager = layout
                adapter = CategoriesAdapter(categories = data, ::goTo)
            }
        }
    }

    fun goTo(type: Int) = Unit // Not implemented

    override fun manageTransition(transition: CategoriesViewTransition) {
        when (transition) {
            is CategoriesViewTransition.GoToBooks -> Unit // Not implemented
            is CategoriesViewTransition.GoToHouses -> Unit // Not implemented
            is CategoriesViewTransition.GoToChars -> Unit // Not implemented
        }
    }
}