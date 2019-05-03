package com.zebrafu.exception;

public class WriteFailedException extends Exception {

  public WriteFailedException() {
    super(ErrorCode.WRITE_ERROR.getCode(), "Writing to printer has failed");
  }
}
