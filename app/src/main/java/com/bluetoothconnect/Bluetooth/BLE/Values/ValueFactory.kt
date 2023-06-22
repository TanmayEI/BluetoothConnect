package com.bluetoothconnect.Bluetooth.ble.values

import android.bluetooth.BluetoothGattCharacteristic

interface ValueFactory<T> {
    fun create(value: BluetoothGattCharacteristic): T
}