package com.oscleton.sdk.sample.receiver

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.oscleton.sdk.sample.R
import com.oscleton.sdk.sample.databinding.ActivityReceiverBinding

class ReceiverActivity : AppCompatActivity() {

    // UI
    private lateinit var binding: ActivityReceiverBinding

    // Data
    private val viewModel: ReceiverViewModel by lazy {
        ViewModelProviders.of(this).get(ReceiverViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReceiverBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Display back arrow
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Set toolbar title
        supportActionBar?.title = resources.getString(R.string.receiver)
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
