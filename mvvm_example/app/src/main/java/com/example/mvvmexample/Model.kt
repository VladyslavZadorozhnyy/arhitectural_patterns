package com.example.mvvmexample

import androidx.lifecycle.MutableLiveData
import java.util.*

class Model(private val observer : MutableLiveData<MainState>) {
    fun addValue(previous: Int) {
        val newState = MainState(numberValue = previous + 1)
        updateValue(newState)
    }

    fun resetValue() {
        val newState = MainState(numberValue = 0)
        updateValue(newState)
    }

    fun generateValue() {
        val newValue = Random().nextInt()
        val newState = MainState(numberValue = newValue)
        updateValue(newState)
    }

    fun generateMessage() {
//        Generating and returning random string of length 10
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        val newMessage = (0 until 10)
            .map { allowedChars.random() }
            .joinToString("")
        val newState = MainState(message = newMessage)
        updateValue(newState)
    }

    private fun updateValue(newState: MainState) {
        observer.value = newState
    }
}