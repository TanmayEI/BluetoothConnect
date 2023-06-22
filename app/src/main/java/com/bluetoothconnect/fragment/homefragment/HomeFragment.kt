package com.bluetoothconnect.fragment.homefragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bluetoothconnect.R
import com.bluetoothconnect.activity.loginactivity.LoginViewModel
import com.bluetoothconnect.activity.registrationnactivity.RegistrationNavigator
import com.bluetoothconnect.databinding.FragmentFirstBinding
import com.equalinfotech.miiwey.base.BaseActivity
import com.equalinfotech.miiwey.base.BaseFragment
import com.topcutlawn.Fragments.HomeFragment.Adapters.DeviceAdapter


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : BaseFragment<FragmentFirstBinding, HomeViewModel>(),
    HomeNavigator {

    private lateinit var homedetailslist: List<ViewModel>
    // This property is only valid between onCreateView and
    // onDestroyView.
    private lateinit var deviceAdapter: DeviceAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dataBinding = FragmentFirstBinding.inflate(inflater, container, false)
        return dataBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        home_list()


        dataBinding.recycler.layoutManager = LinearLayoutManager(requireContext())
        dataBinding.recycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        deviceAdapter = DeviceAdapter(homedetailslist,requireContext())

        dataBinding.recycler.adapter = deviceAdapter

        dataBinding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }


    override fun onError(message: String) {
        TODO("Not yet implemented")
    }

    override fun getLayoutID(): Int {
        return R.layout.fragment_first
    }

    override fun getViewModel(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }

    fun home_list(){
        homedetailslist = listOf(
            ViewModel(
                "5F:58:77:E6:18:0D","-70","95"
            ),
            ViewModel(
                "76:6B:F9:E6:BE:91","-52","98"
            ),
            ViewModel(
                "63:58:77:E6:18:0D","-64","103"
            ),
            ViewModel(
                "52:58:77:E6:18:0D","-76","102"
            ),

        )
    }


}