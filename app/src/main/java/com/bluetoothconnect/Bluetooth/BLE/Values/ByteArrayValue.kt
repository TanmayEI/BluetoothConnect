package com.bluetoothconnect.Bluetooth.ble

import android.bluetooth.BluetoothGattCharacteristic
import com.bluetoothconnect.Bluetooth.ble.values.ValueFactory

/**
 * The plain byte array of the characteristic.
 */
class ByteArrayValue {
    class Factory : ValueFactory<ByteArray> {
        override fun create(value: BluetoothGattCharacteristic): ByteArray {
            return value.value
        }
    }
}