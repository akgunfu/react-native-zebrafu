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
import com.zebrafu.exception.ConnectionFailedException;
import com.zebrafu.exception.ErrorCode;
import com.zebrafu.exception.Exception;
import com.zebrafu.exception.NotConnectedException;
import com.zebrafu.exception.WriteFailedException;

import java.util.HashMap;
import java.util.Map;

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

  private void doPrint(final Connection connection, final String zplData, final Promise promise) {
    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        try {
          connection.open();
          if (connection.isConnected()) {
            try {
              connection.write(zplData.getBytes());
              promise.resolve(true);
            } catch (ConnectionException ex) {
              Log.e("ZEBRA", "An error occurred while writing");
              reject(promise, new WriteFailedException());
            }
          } else {
            Log.e("ZEBRA", "Not connected");
            reject(promise, new NotConnectedException());
          }
        } catch (ConnectionException ex) {
          Log.e("ZEBRA", "Failed to connect zebra printer");
          reject(promise, new ConnectionFailedException());
        } finally {
          try {
            connection.close();
          } catch (ConnectionException ignored) {
            Log.e("ZEBRA", "Failed to close active connection");
          }
        }
      }
    };

    new Thread(runnable).start();
  }

  private void reject(Promise promise, Exception exception) {
    promise.reject(exception.getCode(), exception.getMessage());
  }

  @Override
  public String getName() {
    return "RNZebrafu";
  }

  @Override
  public Map<String, Object> getConstants() {
    final Map<String, Object> constants = new HashMap<>();

    // Add all error codes
    final Map<String, Object> errorCodes = new HashMap<>();
    for (ErrorCode error : ErrorCode.values()) {
      errorCodes.put(error.name(), error.getCode());
    }
    constants.put("ERROR_CODES", errorCodes);

    return constants;
  }
}