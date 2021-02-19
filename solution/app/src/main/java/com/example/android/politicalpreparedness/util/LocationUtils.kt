package com.example.android.politicalpreparedness.util

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Handler
import android.os.Looper
import com.example.android.politicalpreparedness.PoliticalApp
import java.util.concurrent.Executors

object LocationUtils {
    private const val PROVIDER = LocationManager.GPS_PROVIDER

    private val locationPermissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    )

    private val requestExecutor by lazy { Executors.newSingleThreadExecutor() }

    private val locationManager: LocationManager?
        get() =
            PoliticalApp.context.getSystemService(Context.LOCATION_SERVICE) as? LocationManager

    @SuppressLint("MissingPermission")
    fun requestSingleUpdate(block: (Location) -> Unit) {
        fun doRequest() {
            locationManager?.getLastKnownLocation(PROVIDER)?.let {
                block(it)
                return
            }

            val handler = LocationListener {
                Handler(Looper.getMainLooper()).post { block(it) }
            }

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                locationManager?.getCurrentLocation(PROVIDER, null, requestExecutor) {
                    it?.let { handler.onLocationChanged(it) }
                }
            } else {
                @Suppress("DEPRECATION")
                locationManager?.requestSingleUpdate(PROVIDER, handler, null)
            }
        }

        if (!hasLocationPermissions()) {
            PermissionManager.requestPermissions(*locationPermissions) {
                if (it.areAllGranted) {
                    doRequest()
                }
            }
        } else {
            doRequest()
        }
    }

    fun hasLocationPermissions(): Boolean =
            PermissionManager.arePermissionsGranted(*locationPermissions)

    fun requestPermissions(handler: (PermissionsResultEvent) -> Unit) =
            PermissionManager.requestPermissions(*locationPermissions, handler = handler)
}