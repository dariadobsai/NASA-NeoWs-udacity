package com.example.nasa_nws_dk.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.nasa_nws_dk.R
import com.example.nasa_nws_dk.databinding.ActivityMainBinding
import com.example.nasa_nws_dk.util.HelperFunctions.Companion.showSnackBar
import com.example.nasa_nws_dk.util.NetworkConnectivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )

        if (savedInstanceState == null) {
            setUpNavigation()
        }

        val networkConnectivity = NetworkConnectivity(this)

        networkConnectivity.observe(this, Observer {
            lifecycleScope.launch {
                withContext(Dispatchers.Main) {
                    binding.contextView.showSnackBar(
                        when (it) {
                            true -> getString(R.string.snackbar_is_online)
                            else -> getString(R.string.snackbar_is_not_online)
                        }
                    )
                }

            }
        })
    }

    private fun setUpNavigation() {
        val controller = findNavController(R.id.nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration(controller.graph)
        binding.toolbar.setupWithNavController(controller, appBarConfiguration)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        setUpNavigation() // Enable back navigation
    }
}