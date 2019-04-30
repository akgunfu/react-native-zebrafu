package com.zebrafu;

import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.zebra.sdk.comm.BluetoothConnection;
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.comm.TcpConnection;

@SuppressWarnings("unused")
public class RNZebrafuModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    public RNZebrafuModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @ReactMethod
    public void printOverTCP(String ipAddress, String zplData, Promise promise) {
        doPrint(new TcpConnection(ipAddress, TcpConnection.DEFAULT_ZPL_TCP_PORT), zplData, promise);
    }

    @ReactMethod
    public void printOverBT(String macAddress, String zplData, Promise promise) {
        doPrint(new BluetoothConnection(macAddress), zplData, promise);
    }

    private void doPrint(Connection connection, String zplData, Promise promise) {
        try {
            connection.open();
            if (connection.isConnected()) {
                connection.write(zplData.getBytes());
                promise.resolve(true);
            }
        } catch (ConnectionException ex) {
            Log.e("ZEBRA", "Failed to connect zebra printer");
            promise.reject(new Exception("Failed to connect zebra printer"));
        } finally {
            try {
                connection.close();
            } catch (ConnectionException ex) {
                Log.e("ZEBRA", "Failed to close active connection");
            }
        }
    }

    @Override
    public String getName() {
        return "RNZebrafu";
    }
}