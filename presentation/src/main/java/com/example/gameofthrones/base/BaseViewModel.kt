package com.example.gameofthrones.base

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.commons_android.SingleLiveEvent

abstract class BaseViewModel<State: Parcelable, Transition>: ViewModel() {
    protected val state = MutableLiveData<State>()
    protected val transition = SingleLiveEvent<Transition>()

    fun getState() = state as LiveData<State>
    fun getTransition() = transition as LiveData<Transition>

    fun loadState(state: State) {
        this.state.value = state
    }
}