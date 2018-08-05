package fr.arthurvimond.oscletonsdk.sample

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import fr.arthurvimond.oscletonsdk.sample.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    // UI
    private val binding: MainActivityBinding by lazy {
        DataBindingUtil.setContentView<MainActivityBinding>(this, R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

}
