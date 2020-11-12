package com.example.hellokt.bottomBar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;


public class BottomBarLayout extends LinearLayout implements BottomBarController {
    private List<BottomBarItemView> items;
    private List<Fragment> fragments;
    private int sourceFrameLayout;
    private FragmentActivity activity;

    public BottomBarLayout(Context context) {
        super(context);
        init();
    }

    public BottomBarLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BottomBarLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        items = new ArrayList<>();
        fragments = new ArrayList<>();
    }

    @Override
    public BottomBarLayout setSourceFrameLayout(FragmentActivity activity, int frameLayoutId) {
        this.sourceFrameLayout = frameLayoutId;
        this.activity = activity;
        return this;
    }

    /**
     * 添加Item
     *
     * @param view
     */
    @Override
    public BottomBarLayout addItemView(BottomBarItemView view, Fragment fragment) {
        items.add(view);
        fragments.add(fragment);
        return this;
    }

    /**
     * 根据position 移除指定的View
     *
     * @param position
     * @return
     */
    @Override
    public boolean removeItemView(int position) {
        int childCount = getChildCount();
        if (childCount <= 0) return false;
        View removeView;
        try {
            removeView = getChildAt(position);
            if (removeView == null) return false;
            removeView(removeView);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 移除指定的View
     *
     * @param view
     * @return
     */
    @Override
    public boolean removeItemView(BottomBarItemView view) {
        if (view == null) return false;
        try {
            removeView(view);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void setSelected(int position) {
        if (items != null && items.size() > 0)
            for (int i = 0; i < items.size(); i++) {
                if (position == i) {
                    items.get(i).setSelected(true);
                    activity.getSupportFragmentManager().beginTransaction().show(fragments.get(i)).commit();
                } else {
                    items.get(i).setSelected(false);
                    activity.getSupportFragmentManager().beginTransaction().hide(fragments.get(i)).commit();
                }

            }
    }

    @Override
    public void initialise() {
        for (int i = 0; i < items.size(); i++) {
            addView(items.get(i));
            LayoutParams lp = (LayoutParams) items.get(i).getLayoutParams();
            lp.weight = 1;
            lp.height = LayoutParams.MATCH_PARENT;
            lp.width = LayoutParams.MATCH_PARENT;
            lp.gravity = Gravity.CENTER;
            items.get(i).setLayoutParams(lp);
            final int finalI = i;
            items.get(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSelected(finalI);
                }
            });

            activity.getSupportFragmentManager().beginTransaction().add(sourceFrameLayout, fragments.get(i)).commit();
        }
    }
}
