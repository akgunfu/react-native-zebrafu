package com.zebrafu.exception;

public class ConnectionFailedException extends Exception {

  public ConnectionFailedException() {
    super(ErrorCode.CONNECTION_ERROR.getCode(), "Cannot establish connection with Zebra Printer");
  }

}
