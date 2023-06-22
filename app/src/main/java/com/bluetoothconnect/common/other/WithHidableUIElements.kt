package com.bluetoothconnect.common.other

import android.content.Context

interface WithHidableUIElements {
    fun getLayoutManagerWithHidingUIElements(context: Context?): LinearLayoutManagerWithHidingUIElements
}