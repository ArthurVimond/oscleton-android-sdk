package fr.arthurvimond.oscletonsdk.sample.controller

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import fr.arthurvimond.oscletonsdk.sample.R
import fr.arthurvimond.oscletonsdk.sample.databinding.ControllerActivityBinding

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
