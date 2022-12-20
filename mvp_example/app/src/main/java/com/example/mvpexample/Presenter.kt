package com.example.mvpexample

import java.util.*

class Presenter(private val view: MvpContract.View) : MvpContract.Presenter, Observer {
    private val model = Model()

    init {
        model.addObserver(this)
    }

    override fun updateModel(action: Action) {
        if (action == Action.Add) {
            model.addValue()
        } else if (action == Action.Generate) {
            model.generateNewValue()
        } else {
            model.resetValue()
        }
    }

    override fun updateView(value: Int?) {
        view.updateTextContent("Value is: $value")
    }

    override fun update(p0: Observable?, p1: Any?) {
        updateView(model.getValue())
    }
}