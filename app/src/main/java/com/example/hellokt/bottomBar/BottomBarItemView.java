package com.example.hellokt.bottomBar;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.hellokt.R;


public class BottomBarItemView extends FrameLayout {
    private ImageView item_icon;
    private TextView item_name;

    private String itemName;
    private int itemDrawable;

    public BottomBarItemView(@NonNull Context context) {
        super(context);
        init();
    }

    public BottomBarItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BottomBarItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public BottomBarItemView(Context context, String itemName, @DrawableRes int itemDrawable) {
        super(context);
        this.itemName = itemName;
        this.itemDrawable = itemDrawable;
        init();
    }


    private void init() {
        this.inflate(getContext(), R.layout.bottom_bar_item, this);
        item_icon = findViewById(R.id.item_icon);
        item_name = findViewById(R.id.item_name);

        setItemIcon(itemDrawable);
        setItemName(itemName);

    }

    public void setItemName(String name) {
        if (!TextUtils.isEmpty(name))
            item_name.setText(name);
        else
            item_name.setText("");
    }

    public void setItemIcon(@DrawableRes int drawable) {
        item_icon.setImageDrawable(getContext().getResources().getDrawable(drawable));
    }

    public void setSelected(boolean selected) {
        item_icon.setSelected(selected);
        item_name.setSelected(selected);
    }
}
