package com.example.hellokt.utils

import android.view.Gravity
import android.widget.Toast

class ToastUtil {
    companion object{
        fun showToast(str: String?) {
            val toast = Toast.makeText(GlobalContext.getAppContext(), str, Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }
    }

}