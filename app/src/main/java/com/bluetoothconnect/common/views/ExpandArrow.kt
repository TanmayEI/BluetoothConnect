package com.bluetoothconnect.common.views

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.bluetoothconnect.R

class ExpandArrow(context: Context, attrs: AttributeSet? = null) : AppCompatImageView(context, attrs) {

    init {
        setImageResource(R.drawable.ic_arrow_down_on)
    }

    fun setState(shouldShowDetails: Boolean) {
        setImageResource(
                if (shouldShowDetails) R.drawable.ic_arrow_up_on
                else R.drawable.ic_arrow_down_on
        )
    }

}