package com.example.mvcexample

import java.util.*

// Model layer to hold data and notify view when data is updated
class Model : Observable() {
    var currentValue: Int? = null

    fun changeValue() {
        currentValue = Random().nextInt()
        setChanged()
        notifyObservers(currentValue)
    }

    fun addValue() {
        currentValue = currentValue?.plus(1)
        setChanged()
        notifyObservers(currentValue)
    }

    fun resetValue() {
        currentValue = null
        setChanged()
        notifyObservers(currentValue)
    }
}