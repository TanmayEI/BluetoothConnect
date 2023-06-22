package com.topcutlawn.Fragments.HomeFragment.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bluetoothconnect.databinding.CustomScanViewBinding
import com.bluetoothconnect.fragment.homefragment.ViewModel


class DeviceAdapter  (
    var homedetailslist: List<ViewModel>,
    val mContext: Context,
) : RecyclerView.Adapter<DeviceAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CustomScanViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CustomScanViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(homedetailslist[position]) {

                binding.ip.setText(homedetailslist.get(position).ModelName)
                binding.range.setText(homedetailslist.get(position).BrandName+" dBm")
                binding.time.setText(homedetailslist.get(position).Time+" dBm")



            }
/*
            binding.llHome.setOnClickListener {
                val mainIntent = Intent(mContext, ResidentialActivity::class.java)
                mContext.startActivity(mainIntent)
            }
*/

        }
    }

    override fun getItemCount(): Int {
        return homedetailslist.size
    }
}