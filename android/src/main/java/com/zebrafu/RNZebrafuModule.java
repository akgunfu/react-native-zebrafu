package com.zebrafu;

import android.util.Log;

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
    public void printOverTCP(String ipAddress, String zplData) {
        doPrint(new TcpConnection(ipAddress, TcpConnection.DEFAULT_ZPL_TCP_PORT), zplData);
    }

    @ReactMethod
    public void printOverBT(String macAddress, String zplData) {
        doPrint(new BluetoothConnection(macAddress), zplData);
    }

    private void doPrint(Connection connection, String zplData) {
        try {
            connection.open();
            if (connection.isConnected()) {
                connection.write(zplData.getBytes());
            }
        } catch (ConnectionException ex) {
            Log.e("ZEBRA", "Failed to connect zebra printer");
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