package com.example.gameofthrones.operations.splash

import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.gameofthrones.base.BaseFragment
import com.example.gameofthrones.databinding.SplashFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : BaseFragment<SplashViewState, SplashViewTransiton>() {

    override val viewModel by viewModel<SplashViewModel>()
    private val binding get() = _binding as SplashFragmentBinding

    override fun getViewBinding(container: ViewGroup?): ViewBinding =
        SplashFragmentBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        viewModel.getCategories()
    }

    override fun initListeners() = Unit // Not implemented

    override fun manageState(state: SplashViewState) {
        when (state) {
            is SplashViewState.Categories -> {
                if (!state.data.isNullOrEmpty()) {
                    viewModel.goToCategories(state.data!!)
                }
            }
        }
    }

    override fun manageTransition(transition: SplashViewTransiton) {
        when (transition) {
            // Success
            is SplashViewTransiton.GoToCategories -> findNavController().navigate(
                SplashFragmentDirections.actionSplashFragmentToCategoriesFragment(transition.categories.toTypedArray())
            )

            //Failure
            is SplashViewTransiton.OnNoInternet -> Unit // Not implemented
            else -> Unit // Not implemented
        }
    }
}