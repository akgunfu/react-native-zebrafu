package com.zebrafu.exception;

public class Exception {

  private final String code;

  private final String message;


  public Exception(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
