package com.oscleton.sdk.sample

import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.oscleton.sdk.sample.configuration.ConfigurationActivity
import com.oscleton.sdk.sample.controller.ControllerActivity
import com.oscleton.sdk.sample.databinding.MainActivityBinding
import com.oscleton.sdk.sample.receiver.ReceiverActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // UI
    private val binding: MainActivityBinding by lazy {
        DataBindingUtil.setContentView<MainActivityBinding>(this, R.layout.activity_main)
    }

    // Data
    private val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel

        viewModel.configure()

        bindProperties()
    }

    private fun bindProperties() {

        // Controller
        controllerTextView.setOnClickListener { goToControllerActivity() }

        // Receiver
        receiverTextView.setOnClickListener { goToReceiverActivity() }

        // Configuration
        configurationTextView.setOnClickListener { goToConfigurationActivity() }

    }

    private fun goToControllerActivity() {
        val intent = Intent(this, ControllerActivity::class.java)
        startActivity(intent)
    }

    private fun goToReceiverActivity() {
        val intent = Intent(this, ReceiverActivity::class.java)
        startActivity(intent)
    }

    private fun goToConfigurationActivity() {
        val intent = Intent(this, ConfigurationActivity::class.java)
        startActivity(intent)
    }

}
