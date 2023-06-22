package com.bluetoothconnect.base.activities

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bluetoothconnect.common.other.LinearLayoutManagerWithHidingUIElements
import com.bluetoothconnect.common.views.MainActionButton
import com.bluetoothconnect.common.other.WithHidableUIElements
import com.bluetoothconnect.fragment.views.HidableBottomNavigationView

abstract class BaseMainMenuFragment : Fragment(), WithHidableUIElements {

    protected lateinit var bottomNav: HidableBottomNavigationView
    protected var hidableActionButton: MainActionButton? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
/*
        (activity as MainActivity).let {
            bottomNav = it.getMainNavigation()!!
        }
*/
    }

    private fun restoreHiddenUI() {
        hidableActionButton?.show()
        bottomNav.show()
    }

    override fun onResume() {
        super.onResume()
        hidableActionButton?.show()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if(!hidden) {
            hidableActionButton?.show()
        }
    }

    override fun onPause() {
        restoreHiddenUI()
        super.onPause()
    }

    override fun onStop() {
        restoreHiddenUI()
        super.onStop()
    }

/*
    override fun getLayoutManagerWithHidingUIElements(context: Context?): LinearLayoutManagerWithHidingUIElements {
        return LinearLayoutManagerWithHidingUIElements(hidableActionButton, bottomNav, context)
    }
*/

}