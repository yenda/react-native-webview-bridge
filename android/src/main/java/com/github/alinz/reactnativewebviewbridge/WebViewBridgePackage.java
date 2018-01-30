package com.github.alinz.reactnativewebviewbridge;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import im.status.ethereum.function.Function;

public class WebViewBridgePackage implements ReactPackage {

    private boolean debug;
    Function<String, String> callRPC;

    public WebViewBridgePackage(boolean debug, Function<String, String> callRPC) {
        this.debug = debug;
        this.callRPC = callRPC;
    }

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactApplicationContext) {
        return new ArrayList<>();
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactApplicationContext) {
        return Arrays.<ViewManager>asList(
                new WebViewBridgeManager(reactApplicationContext, true, this.callRPC)
        );
    }

    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Arrays.asList();
    }
}
