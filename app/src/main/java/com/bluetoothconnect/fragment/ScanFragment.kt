package com.bluetoothconnect.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bluetoothconnect.Bluetooth.ble.ScanResultCompat
import com.bluetoothconnect.Bluetooth.Services.BluetoothService
import com.bluetoothconnect.MainActivity
import com.bluetoothconnect.R
import com.bluetoothconnect.databinding.FragmentFirstBinding
import com.bluetoothconnect.databinding.FragmentScanBinding
import com.bluetoothconnect.features.scan.browser.fragments.BrowserFragment
import com.bluetoothconnect.fragment.ScanFragmentViewModel
import com.bluetoothconnect.fragment.SettingsStorage
import com.bluetoothconnect.fragment.homefragment.HomeViewModel
import com.bluetoothconnect.fragment.homescreen.ViewPagerFragment
import com.equalinfotech.miiwey.base.BaseFragment


class ScanFragment : BaseFragment<FragmentScanBinding, ScanFragmentViewModel>(), BluetoothService.ScanListener {

    private lateinit var settingsPreferences: SettingsStorage

    private var btService: BluetoothService? = null


    override fun getLayoutID(): Int {
        return R.layout.fragment_scan
    }

    override fun getViewModel(): Class<ScanFragmentViewModel> {
        return ScanFragmentViewModel::class.java
    }

    private var scanFragmentListener: ScanFragmentListener? = null
    private val viewPagerFragment = ViewPagerFragment()

    private var isFilterViewOn = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this, ScanFragmentViewModel.Factory(context))
                .get(ScanFragmentViewModel::class.java)

        settingsPreferences = SettingsStorage(context)
        setupBackStackCallbacks()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        dataBinding = FragmentScanBinding.inflate(inflater, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = getString(R.string.fragment_scan_label)

      //  observeChanges()
/*
        if (!viewPagerFragment.isAdded) {
            childFragmentManager.beginTransaction().apply {
                add(R.id.child_fragment_container, viewPagerFragment)
                commit()
            }
        }
*/
    }

    override fun onResume() {
        super.onResume()
       // (activity as MainActivity).toggleMainNavigation(!isFilterViewOn)
        viewModel.updateActiveConnections(btService?.getActiveConnections())
    }

    override fun onPause() {
        super.onPause()
       // viewModel.setIsScanningOn(false)
    }

    fun setScanFragmentListener(listener: ScanFragmentListener) {
        scanFragmentListener = listener
    }

    private fun setupBackStackCallbacks() {
        activity?.onBackPressedDispatcher?.addCallback(this, backPressedCallback)
    }


    private fun convertScanSetting() : Int? {
        val scanPreference = settingsPreferences.loadScanSetting()
        return if (scanPreference != 0) scanPreference else null

    }

    fun toggleFilterFragment(shouldShowFilterFragment: Boolean) {
        if (shouldShowFilterFragment) {
            childFragmentManager.beginTransaction().apply {
               // hide(viewPagerFragment)
               // add(R.id.child_fragment_container, FilterFragment())
                addToBackStack(null)
                commit()
            }
        } else {
            childFragmentManager.popBackStack()
            activity?.title = getString(R.string.fragment_scan_label)
        }
        (activity as MainActivity).apply {
           // toggleMainNavigation(!shouldShowFilterFragment)
           // toggleHomeIcon(shouldShowFilterFragment)
        }
        isFilterViewOn = !isFilterViewOn
    }

    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (isFilterViewOn) {
             //   toggleFilterFragment(shouldShowFilterFragment = false)
            }
            else {
                isEnabled = false
                requireActivity().onBackPressed()
            }
        }
    }

    override fun handleScanResult(scanResult: ScanResultCompat) {
        viewModel.handleScanResult(scanResult)
    }

    override fun onDiscoveryFailed() {
       // viewModel.setIsScanningOn(false)
    }

    override fun onDiscoveryTimeout() {
       // viewModel.setIsScanningOn(false)
    }

    interface ScanFragmentListener {
        fun onScanningStateChanged(isOn: Boolean)
    }

}