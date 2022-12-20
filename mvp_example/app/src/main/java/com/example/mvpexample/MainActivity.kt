package com.example.mvpexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity(), MvpContract.View {
    private lateinit var contentView: TextView
    private lateinit var presenter: MvpContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        contentView = findViewById(R.id.content)
        contentView.text = "Value not set yet"

        presenter = Presenter(this)
    }

    fun addValue(v: View) {
        presenter.updateModel(Action.Add)
    }

    fun resetValue(v: View) {
        presenter.updateModel(Action.Reset)
    }

    fun generateValue(v: View) {
        presenter.updateModel(Action.Generate)
    }

    override fun updateTextContent(arg: String) {
        contentView.text = arg
    }
}