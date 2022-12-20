package com.example.mvcexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.mvcexample.other.Action
import java.util.*

// This class represents UI layer of the application
class MainActivity : AppCompatActivity(), Observer {
    lateinit var controller: Controller
    lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        controller = Controller()
        controller.addModelObserver(this)

        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.value)
        textView.text = "Value has not been set yet"
    }

    fun generateNewValue(v: View) {
        controller.addActionToModel(Action.GenerateNew)
    }

    fun resetValue(v: View) {
        controller.addActionToModel(Action.Reset)
    }

    fun addValue(v: View) {
        controller.addActionToModel(Action.Add)
    }

    override fun update(model: Observable?, value: Any?) {
        if (value == null) {
            textView.text = "Value has not been set yet again"
        } else {
            textView.text = "Value is: $value"
        }
    }
}