package com.bluetoothconnect.Bluetooth.ble

class ConnectedDeviceInfo(val connection: GattConnection) {
    var bluetoothInfo = BluetoothDeviceInfo(connection.gatt!!.device)
}