package com.example.nasa_nws_dk.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.nasa_nws_dk.R
import com.example.nasa_nws_dk.databinding.ActivityMainBinding
import com.example.nasa_nws_dk.util.HelperFunctions.Companion.showSnackBar
import com.example.nasa_nws_dk.util.NetworkConnectivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        navController = findNavController(R.id.nav_host_fragment)

        if (savedInstanceState == null) {
            setUpNavigation()
        }

        // Observe connectivity and provide feedback to the user
        NetworkConnectivity(this).observe(this, Observer {
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
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) ||
                super.onSupportNavigateUp()
    }
}