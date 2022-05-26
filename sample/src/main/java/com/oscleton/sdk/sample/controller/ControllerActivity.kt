package com.oscleton.sdk.sample.controller

import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.oscleton.sdk.sample.R
import com.oscleton.sdk.sample.databinding.ControllerActivityBinding

class ControllerActivity : AppCompatActivity() {

    // UI
    private val binding: ControllerActivityBinding by lazy {
        DataBindingUtil.setContentView<ControllerActivityBinding>(this, R.layout.activity_controller)
    }

    // Data
    private val viewModel: ControllerViewModel by lazy {
        ViewModelProviders.of(this).get(ControllerViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)

        // Display back arrow
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Set toolbar title
        supportActionBar?.title = resources.getString(R.string.controller)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
        // Back arrow
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
