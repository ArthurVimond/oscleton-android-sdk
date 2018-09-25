package com.oscleton.sdk.sample.receiver

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.oscleton.sdk.sample.R
import com.oscleton.sdk.sample.databinding.ReceiverActivityBinding

class ReceiverActivity : AppCompatActivity() {

    // UI
    private val binding: ReceiverActivityBinding by lazy {
        DataBindingUtil.setContentView<ReceiverActivityBinding>(this, R.layout.activity_receiver)
    }

    // Data
    private val viewModel: ReceiverViewModel by lazy {
        ViewModelProviders.of(this).get(ReceiverViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)

        // Display back arrow
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Set toolbar title
        supportActionBar?.title = resources.getString(R.string.receiver)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
        // Back arrow
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
