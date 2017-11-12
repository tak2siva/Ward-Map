package com.wardmap;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import javax.annotation.Nullable;

public class JSEventBus {
    private final ReactContext reactContext;

    public JSEventBus(ReactContext reactContext) {
        this.reactContext = reactContext;
    }

    public void sendEvent(ReactContext reactContext,
                          String eventName, @Nullable WritableMap params) {
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }
}
