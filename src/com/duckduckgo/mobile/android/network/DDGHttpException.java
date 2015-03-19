package com.duckduckgo.mobile.android.network;

public class DDGHttpException extends Exception {
  /**
   *
   */
  private static final long serialVersionUID = 1L;
  private int status;

  public DDGHttpException() {
  }

  public DDGHttpException(String message) {
    super(message);
  }

  public DDGHttpException(String message, int httpStatus) {
    super(message);
    this.status = httpStatus;
  }

  public DDGHttpException(int httpStatus) {
    this.status = httpStatus;
  }

  public int getHttpStatus() {
    return status;
  }
}
