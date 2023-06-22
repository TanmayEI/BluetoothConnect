/*
 * Bluegiga’s Bluetooth Smart Android SW for Bluegiga BLE modules
 * Contact: support@bluegiga.com.
 *
 * This is free software distributed under the terms of the MIT license reproduced below.
 *
 * Copyright (c) 2013, Bluegiga Technologies
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files ("Software")
 * to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF
 * ANY KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS FOR A  PARTICULAR PURPOSE.
 */
package com.bluetoothconnect.features.scan.browser.fragments

import android.annotation.SuppressLint
import android.bluetooth.*
import android.content.Context
import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.view.*
import android.view.View.*
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.bluetoothconnect.R
import com.bluetoothconnect.Utils.SharedPrefUtils
import com.bluetoothconnect.base.activities.BaseServiceDependentMainMenuFragment
import com.bluetoothconnect.fragment.homescreen.ViewPagerFragment
import com.bluetoothconnect.Bluetooth.Services.BluetoothService
import com.bluetoothconnect.Bluetooth.ble.BluetoothDeviceInfo
import com.bluetoothconnect.Bluetooth.ble.ErrorCodes.getDeviceDisconnectedMessage
import com.bluetoothconnect.Bluetooth.ble.ErrorCodes.getFailedConnectingToDeviceMessage
import com.bluetoothconnect.Bluetooth.ble.TimeoutGattCallback
import com.bluetoothconnect.common.other.CardViewListDecoration
import com.bluetoothconnect.common.other.LinearLayoutManagerWithHidingUIElements
import com.bluetoothconnect.features.scan.browser.adapters.DebugModeDeviceAdapter
import com.bluetoothconnect.fragment.ScanFragmentViewModel
import com.bluetoothconnect.fragment.homescreen.BluetoothDependent
import com.bluetoothconnect.fragments.ScanFragment
import com.bluetoothconnect.databinding.FragmentBrowserBinding
import com.bluetoothconnect.features.scan.browser.adapters.DebugModeCallback
import com.bluetoothconnect.fragment.homescreen.LocationDependent


class BrowserFragment : BaseServiceDependentMainMenuFragment(){

    private lateinit var viewModel: ScanFragmentViewModel
    private lateinit var viewBinding: FragmentBrowserBinding
    private var bluetoothService: BluetoothService? = null

    private lateinit var sharedPrefUtils: SharedPrefUtils
    private var devicesAdapter: DebugModeDeviceAdapter? = null
    private lateinit var handler: Handler

    private var deviceToConnect: BluetoothDeviceInfo? = null
    private var blockConnectionAttempts = false

    override val bluetoothDependent = object : BluetoothDependent {

        override fun onBluetoothStateChanged(isBluetoothOn: Boolean) {
            toggleBluetoothBar(isBluetoothOn, viewBinding.bluetoothBar)
            viewBinding.btnScanning.isEnabled = isBluetoothOperationPossible()

            getScanFragment().setScanFragmentListener(scanFragmentListener)
            viewModel.let {
                it.setIsScanningOn(isBluetoothOperationPossible())
                if (!isBluetoothOn) {
                  //  it.reset()
                    it.shouldResetChart = true
                }
            }
        }

        override fun onBluetoothPermissionsStateChanged(arePermissionsGranted: Boolean) {
            toggleBluetoothPermissionsBar(arePermissionsGranted, viewBinding.bluetoothPermissionsBar)
            viewBinding.btnScanning.isEnabled = isBluetoothOperationPossible()

            viewModel.let {
                it.setIsScanningOn(isBluetoothOperationPossible())
                if (!arePermissionsGranted) {
                   // it.reset()
                    it.shouldResetChart = true
                }
            }
        }

        override fun refreshBluetoothDependentUi(isBluetoothOperationPossible: Boolean) {
            /* Not needed */
        }

        override fun setupBluetoothPermissionsBarButtons() {
            viewBinding.bluetoothPermissionsBar.setFragmentManager(childFragmentManager)
        }
    }

    override val locationDependent = object : LocationDependent {

        override fun onLocationStateChanged(isLocationOn: Boolean) {
            toggleLocationBar(isLocationOn, viewBinding.locationBar)
        }
        override fun onLocationPermissionStateChanged(isPermissionGranted: Boolean) {
            viewBinding.apply {
                toggleLocationPermissionBar(isPermissionGranted, locationPermissionBar)
                btnScanning.isEnabled = isPermissionGranted
            }
        }
        override fun setupLocationBarButtons() {
            viewBinding.locationBar.setFragmentManager(childFragmentManager)
        }

        override fun setupLocationPermissionBarButtons() {
            viewBinding.locationPermissionBar.setFragmentManager(childFragmentManager)
        }
    }

    private val scanFragmentListener = object: ScanFragment.ScanFragmentListener{
        override fun onScanningStateChanged(isOn: Boolean) {
            toggleScanningButton(isOn)
            toggleRefreshInfoRunnable(isOn)

            if (!isOn) handler.removeCallbacks(refreshScanRunnable)
        }
    }


    private val refreshScanRunnable = Runnable {
        viewModel.let {
            if (!it.getIsScanningOn()) {
                it.setIsScanningOn(true)
            } else {
             //   getScanFragment().toggleScannerState(true)
            }
        }
    }

    private fun getScanFragment() = (parentFragment as ViewPagerFragment).getScanFragment()

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        setHasOptionsMenu(true)
        viewBinding = FragmentBrowserBinding.inflate(inflater)
        hidableActionButton = viewBinding.btnScanning
        return viewBinding.root
    }

    override fun getLayoutManagerWithHidingUIElements(context: Context?): LinearLayoutManagerWithHidingUIElements {
        TODO("Not yet implemented")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = getString(R.string.fragment_scan_label)
        sharedPrefUtils = SharedPrefUtils(requireContext())
        handler = Handler(Looper.getMainLooper())

        Toast.makeText(requireContext(),"Browsr Fragment",Toast.LENGTH_SHORT).show()

        setUiListeners()

        initFullPageInfoViews()
        initDevicesRecyclerView()

        getScanFragment().setScanFragmentListener(scanFragmentListener) // for initial app enter

        bluetoothService?.apply {
            registerGattServerCallback(gattServerCallback)
            registerGattCallback(gattCallback)
        }
    }


    private fun initFullPageInfoViews() {
        viewBinding.lookingForDevicesBackground.apply {
            image.apply {
                setImageResource(R.drawable.redesign_ic_main_view_browser_scanning_spinner)
                (drawable as? AnimatedVectorDrawable)?.start()
            }
            textPrimary.text = getString(R.string.device_scanning_background_message)
            textSecondary.visibility = View.GONE
        }
    }



    private fun toggleFilterDescriptionView(description: String?) {
        viewBinding.activeFiltersDescription.apply {
            description?.let {
                visibility = View.VISIBLE
                text = it
            } ?: run { visibility = View.GONE }
        }
    }

    private fun toggleRefreshInfoRunnable(isOn: Boolean) {
        handler.let {
            if (isOn) {
                it.removeCallbacks(updateScanInfoRunnable)
                it.postDelayed(updateScanInfoRunnable, SCAN_UPDATE_PERIOD)
            }
            else it.removeCallbacks(updateScanInfoRunnable)
        }
    }

    private val updateScanInfoRunnable = object : Runnable {
        override fun run() {
            handler.postDelayed(this, SCAN_UPDATE_PERIOD)
        }
    }

    private fun setUiListeners() {
        viewBinding.btnScanning.setOnClickListener { viewModel.toggleScanningState() }
    }

    override fun onResume() {
        super.onResume()
        bluetoothService?.apply {
            registerGattServerCallback(gattServerCallback)
            registerGattCallback(gattCallback)
        }

    }


    private fun toggleScanningButton(isScanningOn: Boolean) {
        viewBinding.btnScanning.apply {
            text = getString(
                    if (isScanningOn) R.string.button_stop_scanning
                    else R.string.button_start_scanning
            )
            setIsActionOn(isScanningOn)
        }
    }

    override fun onPause() {
        super.onPause()
        bluetoothService?.apply {
            unregisterGattServerCallback()
            unregisterGattCallback()
        }
    }





    private fun initDevicesRecyclerView() {
        devicesAdapter = DebugModeDeviceAdapter(mutableListOf(), debugModeCallback)
        viewBinding.rvDebugDevices.apply {
            layoutManager = getLayoutManagerWithHidingUIElements(requireContext())
            addItemDecoration(CardViewListDecoration())
            adapter = devicesAdapter
        }
    }

    private val debugModeCallback = object : DebugModeCallback {
        override fun connectToDevice(position: Int, deviceInfo: BluetoothDeviceInfo) {
            if (viewModel.getIsScanningOn()) {
                viewModel.setIsScanningOn(false)
            }

            deviceToConnect = deviceInfo
            showConnectingAnimation()

            handler.postDelayed({
                bluetoothService?.let {
                    it.isNotificationEnabled = false
                    bluetoothService?.connectGatt(deviceInfo.device, true, gattCallback)
                }
            }, ANIMATION_DELAY)
        }

        override fun disconnectDevice(position: Int, device: BluetoothDevice) {
            bluetoothService?.disconnectGatt(device.address)
        }

        override fun addToFavorites(deviceAddress: String) {
        }

        override fun removeFromFavorites(deviceAddress: String) {
        }


        override fun toggleViewExpansion(position: Int) {
        }
    }


    private fun showConnectingAnimation() {
        activity?.runOnUiThread {
            viewBinding.apply {
                btnScanning.visibility = View.GONE
                flyInBar.visibility = View.VISIBLE
                flyInBar.startFlyInAnimation(getString(R.string.debug_mode_device_selection_connecting_bar))
            }
        }
    }

    private fun hideConnectingAnimation() {
        activity?.runOnUiThread {
            devicesAdapter?.notifyDataSetChanged()
            viewBinding.apply {
                flyInBar.clearBarAnimation()
                flyInBar.visibility = View.GONE
                btnScanning.visibility = View.VISIBLE
            }
        }
    }

    private val gattCallback = object : TimeoutGattCallback() {
        override fun onTimeout() {
            Toast.makeText(requireContext(), getString(R.string.toast_connection_timed_out), Toast.LENGTH_SHORT).show()
            hideConnectingAnimation()
            deviceToConnect = null
        }

        override fun onMaxRetriesExceeded(gatt: BluetoothGatt) {
        }


    }





    /* Getting connection from other device when on BrowserFragment */
    private val gattServerCallback = object : BluetoothGattServerCallback() {
        override fun onConnectionStateChange(device: BluetoothDevice, status: Int, newState: Int) {
            super.onConnectionStateChange(device, status, newState)
            if (status == BluetoothGatt.GATT_SUCCESS && newState == BluetoothProfile.STATE_CONNECTED) {
                if (bluetoothService?.isAnyConnectionPending() == false && !blockConnectionAttempts) {
                    deviceToConnect = BluetoothDeviceInfo(device)
                    bluetoothService?.connectGatt(device, requestRssiUpdates = true)
                }
            }
        }
    }


    @SuppressLint("MissingPermission")
    private fun getDeviceName(gatt: BluetoothGatt) : String {
        return gatt.device.name ?: getString(R.string.not_advertising_shortcut)
    }


    companion object {
        private const val RESTART_SCAN_TIMEOUT = 1000L
        private const val SCAN_UPDATE_PERIOD = 1000L //ms
        private const val START_ACTIVITY_DELAY = 250L
        private const val ANIMATION_DELAY = 1000L // give time to display animation
    }
}
