package com.david4vilac.legostorefragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import androidx.navigation.fragment.findNavController

class HomeFragment : Fragment(R.layout.fragment_home) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnBack: Button = requireView().findViewById(R.id.btnBack)
        btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_authFragment)
        }
    }


}