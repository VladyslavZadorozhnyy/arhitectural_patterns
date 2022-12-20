package com.example.mvvmexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.example.mvvmexample.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val mainViewModel by viewModel<MainViewModel>()
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = mainViewModel

        mainViewModel.viewChannel.observe(this) {
            it?.getNumberValue()?.let {
                binding.contentView.text = it.toString()
            }
            it?.getMessage()?.let {
                binding.messageView.text= it
            }
        }

        binding.addAction = MyIntent.Add
        binding.resetAction = MyIntent.Reset
        binding.generateAction = MyIntent.GenerateValue
        binding.generateMessageAction = MyIntent.GenerateMessage
    }
}