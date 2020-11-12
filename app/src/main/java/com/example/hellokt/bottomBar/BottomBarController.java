package com.example.hellokt.bottomBar;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public interface BottomBarController {
    BottomBarLayout setSourceFrameLayout(FragmentActivity activity, @IdRes int frameLayoutId);
    BottomBarLayout addItemView(BottomBarItemView view, Fragment fragment);

    boolean removeItemView(int position);

    boolean removeItemView(BottomBarItemView view);

    void setSelected(int position);

    void initialise();
}
