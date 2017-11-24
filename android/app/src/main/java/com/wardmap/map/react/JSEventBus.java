package com.wardmap.map.react;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import javax.annotation.Nullable;

// Send event to javascript code

public class JSEventBus {
    private final ReactContext reactContext;

    public JSEventBus(ReactContext reactContext) {
        this.reactContext = reactContext;
    }

    public void sendEvent(ReactContext reactContext,
                          String eventName, @Nullable WritableMap params) {
        this.reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }

    public void sendEvent(String eventName, @Nullable String params) {
        this.reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }
    public void sendEvent(String eventName, @Nullable boolean params) {
        this.reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }
}
