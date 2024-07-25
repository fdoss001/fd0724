package com.pos.demo.exception;

public class InvalidDiscountPercentException extends RuntimeException {
    public InvalidDiscountPercentException(String message) {
        super(message);
    }
}
