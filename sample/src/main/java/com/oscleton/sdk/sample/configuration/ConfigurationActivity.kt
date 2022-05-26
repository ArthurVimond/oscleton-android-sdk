package com.oscleton.sdk.sample.configuration

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.oscleton.sdk.sample.R
import com.oscleton.sdk.sample.databinding.ConfigurationActivityBinding
import com.oscleton.sdk.sample.utils.SnackbarUtils
import com.oscleton.sdk.utils.Empty
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

class ConfigurationActivity : AppCompatActivity() {

    // UI
    private val binding: ConfigurationActivityBinding by lazy {
        DataBindingUtil.setContentView<ConfigurationActivityBinding>(this, R.layout.activity_configuration)
    }

    // Data
    private val viewModel: ConfigurationViewModel by lazy {
        ViewModelProviders.of(this).get(ConfigurationViewModel::class.java)
    }

    // Rx
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)

        // Display back arrow
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Set toolbar title
        supportActionBar?.title = resources.getString(R.string.configuration)

        observeProperties()
    }

    private fun observeProperties() {

        // Connection / Discovery success message
        viewModel.onConnectionSuccess
                .mergeWith(viewModel.onComputerIPDiscoverySuccess.map { Empty.VOID })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { SnackbarUtils.showShortSnackbar(binding.root, R.string.connectedToLive) }
                .addTo(compositeDisposable)

        // Connection error message
        viewModel.onConnectionError
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { SnackbarUtils.showShortSnackbar(binding.root, R.string.connection_error) }
                .addTo(compositeDisposable)

        // Discovery error message
        viewModel.onComputerIPDiscoveryError
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { SnackbarUtils.showShortSnackbar(binding.root, R.string.ip_discovery_error) }
                .addTo(compositeDisposable)

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

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}
