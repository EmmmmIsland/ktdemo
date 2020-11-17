package com.example.hellokt.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.hellokt.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(),View.OnClickListener{
    var hh = "hello"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val cc = view?.findViewById<TextView>(R.id.tv_gg);
        cc?.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_gg -> {
                Toast.makeText(context, hh, Toast.LENGTH_LONG).show()
            }
        }
    }


}