package fr.arthurvimond.oscletonsdk.sample.configuration

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import fr.arthurvimond.oscletonsdk.sample.R
import fr.arthurvimond.oscletonsdk.sample.databinding.ConfigurationActivityBinding
import fr.arthurvimond.oscletonsdk.sample.utils.SnackbarUtils
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

        // Connection success message
        viewModel.onConnectionSuccess
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { SnackbarUtils.showShortSnackbar(binding.root, R.string.connectedToLive) }
                .addTo(compositeDisposable)

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

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}
