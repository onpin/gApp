package com.app.golfapp.Model;

import com.app.golfapp.R;

public enum ModelObject {

    RED(R.drawable.h1, R.layout.costomeview);
  /*  BLUE(R.string.blue, R.layout.view_blue),
    GREEN(R.string.green, R.layout.view_green);*/

    private int mTitleResId;
    private int mLayoutResId;

    ModelObject(int titleResId, int layoutResId) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }
}