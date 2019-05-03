package com.zebrafu.exception;


public class NotConnectedException extends Exception {

  public NotConnectedException() {
    super(ErrorCode.NOT_CONNECTED.getCode(), "Device is not connected");
  }
}
