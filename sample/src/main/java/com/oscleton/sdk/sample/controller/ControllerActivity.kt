package com.oscleton.sdk.sample.controller

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.oscleton.sdk.sample.R
import com.oscleton.sdk.sample.databinding.ActivityControllerBinding

class ControllerActivity : AppCompatActivity() {

    // UI
    private lateinit var binding: ActivityControllerBinding

    // Data
    private val viewModel: ControllerViewModel by lazy {
        ViewModelProviders.of(this).get(ControllerViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityControllerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

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
