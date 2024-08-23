package com.friendlyblood.api.utils.exceptions;

public class InvalidPasswordException extends RuntimeException {

  public InvalidPasswordException() {
    super("Invalid password provided.");
  }

  public InvalidPasswordException(String message) {
    super(message);
  }
}
