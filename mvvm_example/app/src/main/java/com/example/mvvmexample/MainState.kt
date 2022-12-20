package com.example.mvvmexample

class MainState(
    private var message: String? = null,
    private var numberValue: Int? = null,
) {
    fun getMessage() = message
    fun getNumberValue() = numberValue

    fun setMessage(newMessage: String) {
        message = newMessage
    }

    fun setNumberValue(newValue: Int) {
        numberValue = newValue
    }
}