package com.reactcalculator;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;

public class CustomImageViewManager extends SimpleViewManager<CustomImageView> {
    public static final String REACT_CLASS = "CustomImageView";

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected CustomImageView createViewInstance(ThemedReactContext reactContext) {
        CustomImageView customImageView = new CustomImageView(reactContext);
        customImageView.setImageBitmap(BitmapFactory.decodeFile("/tmp/android.png"));
        customImageView.setBackgroundColor(Color.RED);
        return customImageView;
    }
}
