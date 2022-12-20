package com.example.mvpexample

import java.util.*

class Model : MvpContract.Model, Observable() {
    private var value: Int? = null

    override fun generateNewValue() {
        value = Random().nextInt()
        update()
    }

    override fun resetValue() {
        value = null
        update()
    }

    override fun addValue() {
        if (value != null) {
            value = value!!.plus(1)
            update()
        }
    }

    private fun update() {
        setChanged()
        notifyObservers()
    }

    fun getValue(): Int? {
        return value
    }
}