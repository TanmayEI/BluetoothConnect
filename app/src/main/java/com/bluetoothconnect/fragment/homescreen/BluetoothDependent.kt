package com.bluetoothconnect.fragment.homescreen

interface BluetoothDependent {

    fun onBluetoothStateChanged(isBluetoothOn: Boolean)
    fun onBluetoothPermissionsStateChanged(arePermissionsGranted: Boolean)
    fun refreshBluetoothDependentUi(isBluetoothOperationPossible: Boolean)
    fun setupBluetoothPermissionsBarButtons()
}