package com.zebrafu.exception;

public enum ErrorCode {

  CONNECTION_ERROR("701"),
  NOT_CONNECTED("702"),
  WRITE_ERROR("703");

  private final String code;

  ErrorCode(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }
}
