package com.bluetoothconnect.fragment.homescreen

interface LocationDependent {

    fun onLocationStateChanged(isLocationOn: Boolean)
    fun onLocationPermissionStateChanged(isPermissionGranted: Boolean)
    fun setupLocationBarButtons()
    fun setupLocationPermissionBarButtons()
}