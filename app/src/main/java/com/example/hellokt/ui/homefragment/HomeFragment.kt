package com.example.hellokt.ui.homefragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.baseproject.base.BaseFragment
import com.example.hellokt.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override fun getLayout(inflater: LayoutInflater, container: ViewGroup?): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

}