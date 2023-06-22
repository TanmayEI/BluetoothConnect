package com.bluetoothconnect.fragment.homescreen

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bluetoothconnect.R
import com.google.android.material.tabs.TabLayoutMediator
import com.bluetoothconnect.Bluetooth.Services.BluetoothService
import com.bluetoothconnect.databinding.FragmentViewPagerBinding
import com.bluetoothconnect.features.scan.active_connections.fragments.ConnectionsFragment
import com.bluetoothconnect.features.scan.browser.fragments.BrowserFragment
import com.bluetoothconnect.fragment.ScanFragmentViewModel
import com.bluetoothconnect.fragments.ScanFragment
import com.equalinfotech.miiwey.base.BaseFragment


class ViewPagerFragment : BaseFragment<FragmentViewPagerBinding,ScanFragmentViewModel>() {

    private var bluetoothService: BluetoothService? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.updateActiveConnections(bluetoothService?.getActiveConnections())
        BrowserFragment()
    }

    fun getScanFragment() = parentFragment as ScanFragment

    fun setBluetoothService(service: BluetoothService?) {
        bluetoothService = service
    }

    override fun getLayoutID(): Int {
        return R.layout.fragment_view_pager
        }

    override fun getViewModel(): Class<ScanFragmentViewModel> {
        return ScanFragmentViewModel::class.java
    }
    }


