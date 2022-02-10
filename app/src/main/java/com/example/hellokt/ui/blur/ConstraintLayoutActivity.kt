package com.example.hellokt.ui.blur

import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintSet
import com.alibaba.android.arouter.facade.annotation.Route
import com.demo.toolkit.ext.onClickSafe
import com.example.baseproject.base.BaseVmActivity
import com.example.baseproject.router.ARouterConfig
import com.example.hellokt.databinding.ActivityConstraintBinding

@Route(path = ARouterConfig.AROUTER_PATH_CONSTRAINT_LAYOUT_ACTIVITY)
class ConstraintLayoutActivity : BaseVmActivity<ActivityConstraintBinding>() {

    override fun getLayout(): ActivityConstraintBinding =
        ActivityConstraintBinding.inflate(layoutInflater)

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        binding.toolBar.setNavigationOnClickListener { finish() }
    }

    override fun initData() {
        binding.tvConstraintSet.onClickSafe {
            val constraintSet = ConstraintSet()
            constraintSet.clone(binding.clConstraint)
            constraintSet.constrainHeight(binding.vTest.id, 200)
            constraintSet.applyTo(binding.clConstraint)
        }
//        binding.tvPlaceholder.onClickSafe {
//            binding.template.templateBanner.setContentId(binding.vTest.id)
//        }
    }

}