package org.mpmg.mpapp.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.location.Location
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.mpmg.mpapp.R
import org.mpmg.mpapp.core.extensions.observe
import org.mpmg.mpapp.databinding.ActivityMainBinding
import org.mpmg.mpapp.services.LocationService
import org.mpmg.mpapp.ui.viewmodels.LocationViewModel
import org.mpmg.mpapp.ui.viewmodels.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController

    private var mLocationService: LocationService? = null
    private var isBoundToLocationService: Boolean = false

    private val locationViewModel: LocationViewModel by viewModel()
    private val mainActivityViewModel: MainActivityViewModel by viewModel()

    private val mLocationListener = object : LocationService.LocationServiceListener {
        override fun onNewLocation(location: Location) {
            locationViewModel.updateCurrentLocation(location)
        }
    }

    private val mConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mLocationService = (service as LocationService.LocalBinder).service
            mLocationService?.setLocationServiceListener(mLocationListener)
            isBoundToLocationService = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            mLocationService = null
            isBoundToLocationService = false
        }
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavigationController()
        setupObservers()

        if (!isBoundToLocationService) {
            startLocationService()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isBoundToLocationService) {
            unbindService(mConnection)
            isBoundToLocationService = false
        }
    }

    private fun setupObservers() {
        observe(mainActivityViewModel.snackbarMessage) {
            it ?: return@observe
            launchSnackbar(it)
        }
    }

    private fun startLocationService() {
        val intent = Intent(this, LocationService::class.java)
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
    }

    private fun setupNavigationController() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    private fun launchSnackbar(message: String) {
        Snackbar.make(binding.coordinatorLayoutMainActivity, message, Snackbar.LENGTH_SHORT).show()
    }
}
