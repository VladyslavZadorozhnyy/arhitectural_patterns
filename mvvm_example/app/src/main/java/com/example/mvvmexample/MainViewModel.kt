package com.example.mvvmexample

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import java.util.*

class MainViewModel : ViewModel() {
    private var previousState: MainState? = null
    var dataChannel = MutableLiveData<MainState>(null)
    var viewChannel = MutableLiveData<MainState>(null)

    private val stateObserver = Observer<MainState> {
        it?.getMessage()?.let { message ->
            getPreviousState().setMessage(message)
        }
        it?.getNumberValue()?.let { value ->
            getPreviousState().setNumberValue(value)
        }

        viewChannel.value = it
    }

    init {
        dataChannel.observeForever(stateObserver)
    }

    override fun onCleared() {
        super.onCleared()
        dataChannel.removeObserver(stateObserver)
    }

    private val model: Model = Model(dataChannel)

    fun getPreviousState(): MainState {
        if (previousState == null) {
            previousState = MainState()
        }

        return previousState!!
    }

    fun handleIntent(action: MyIntent) {
        if (action == MyIntent.Add) {
            val currentValue = getPreviousState().getNumberValue() ?: 0
            model.addValue(currentValue)
        } else if (action == MyIntent.GenerateValue) {
            model.generateValue()
        } else if (action == MyIntent.Reset) {
            model.resetValue()
        } else {
            model.generateMessage()
        }
    }
}