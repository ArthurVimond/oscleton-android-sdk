package fr.arthurvimond.oscletonsdk.sample.configuration

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import fr.arthurvimond.oscletonsdk.sample.R
import fr.arthurvimond.oscletonsdk.sample.databinding.ConfigurationActivityBinding

class ConfigurationActivity : AppCompatActivity() {

    // UI
    private val binding: ConfigurationActivityBinding by lazy {
        DataBindingUtil.setContentView<ConfigurationActivityBinding>(this, R.layout.activity_configuration)
    }

    // Data
    private val viewModel: ConfigurationViewModel by lazy {
        ViewModelProviders.of(this).get(ConfigurationViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)

        // Display back arrow
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Set toolbar title
        supportActionBar?.title = resources.getString(R.string.configuration)
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
