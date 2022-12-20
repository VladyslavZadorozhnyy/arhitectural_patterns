package com.example.mvcexample

import com.example.mvcexample.other.Action
import java.util.*

// Controller holds model layer and gets input from user
class Controller() {
    private val model = Model()

    fun addModelObserver(observer: Observer) {
        model.addObserver(observer)
    }

    fun addActionToModel(action: Action) {
        if (action == Action.Add) {
            model.addValue()
        } else if (action == Action.GenerateNew) {
            model.changeValue()
        } else {
            model.resetValue()
        }
    }
}