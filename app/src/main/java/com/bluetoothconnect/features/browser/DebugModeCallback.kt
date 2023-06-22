package com.bluetoothconnect.features.scan.browser.adapters

import android.bluetooth.BluetoothDevice
import com.bluetoothconnect.Bluetooth.ble.BluetoothDeviceInfo

interface DebugModeCallback {
    fun connectToDevice(position: Int, deviceInfo: BluetoothDeviceInfo)
    fun disconnectDevice(position: Int, device: BluetoothDevice)
    fun addToFavorites(deviceAddress: String)
    fun removeFromFavorites(deviceAddress: String)
    fun toggleViewExpansion(position: Int)
}
