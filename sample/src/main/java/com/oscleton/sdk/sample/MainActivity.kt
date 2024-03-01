package com.oscleton.sdk.sample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.oscleton.sdk.sample.configuration.ConfigurationActivity
import com.oscleton.sdk.sample.controller.ControllerActivity
import com.oscleton.sdk.sample.databinding.ActivityMainBinding
import com.oscleton.sdk.sample.receiver.ReceiverActivity

class MainActivity : AppCompatActivity() {

    // UI
    private lateinit var binding: ActivityMainBinding

    // Data
    private val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel.configure()

        bindProperties()
    }

    private fun bindProperties() {

        // Controller
        binding.controllerTextView.setOnClickListener { goToControllerActivity() }

        // Receiver
        binding.receiverTextView.setOnClickListener { goToReceiverActivity() }

        // Configuration
        binding.configurationTextView.setOnClickListener { goToConfigurationActivity() }

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
