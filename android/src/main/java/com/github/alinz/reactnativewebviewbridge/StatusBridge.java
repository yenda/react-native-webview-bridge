package com.github.alinz.reactnativewebviewbridge;

import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.app.Activity;

import com.facebook.react.uimanager.ThemedReactContext;

import im.status.ethereum.function.Function;

class StatusBridge {
    private WebView webView;
    private ThemedReactContext context;
    private Function<String, String> callRPC;

    public StatusBridge(ThemedReactContext context, WebView webView, Function<String, String> callRPC) {
        this.context = context;
        this.webView = webView;
        this.callRPC = callRPC;
    }

    @JavascriptInterface
    public void sendRequest(final String host, final String callbackId, final String json) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                String rpcResponse = callRPC.apply(json).trim();

                final String script = "httpCallback('" + callbackId + "','" + rpcResponse + "');";
                final Activity activity = context.getCurrentActivity();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        evaluateJavascript(webView, script);
                    }
                });
            }
        };
        thread.start();
    }

    @JavascriptInterface
    public String sendRequestSync(final String host, final String json) {
        return this.callRPC.apply(json);
    }

    static private void evaluateJavascript(WebView root, String javascript) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            root.evaluateJavascript(javascript, null);
        } else {
            root.loadUrl("javascript:" + javascript);
        }
    }
}
