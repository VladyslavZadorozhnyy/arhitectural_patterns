package com.example.mvpexample

interface MvpContract {
    interface Model {
        fun generateNewValue()

        fun resetValue()

        fun addValue()
    }

    interface  View {
        fun updateTextContent(arg: String)
    }

    interface Presenter {
        fun updateModel(action: Action)

        fun updateView(value: Int?)
    }
}