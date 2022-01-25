package com.example.hellokt.ui.minefragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.baseproject.base.BaseFragment
import com.example.hellokt.databinding.FragmentMineBinding

class MineFragment : BaseFragment<FragmentMineBinding>() {
    override fun getLayout(inflater: LayoutInflater, container: ViewGroup?): FragmentMineBinding =
        FragmentMineBinding.inflate(layoutInflater)

    private val viewModel: MineViewModel by lazy {
        ViewModelProvider(this).get(MineViewModel::class.java)
    }

}