package com.pos.demo.exception;

public class InvalidRentalDaysException extends RuntimeException {
    public InvalidRentalDaysException(String message) {
        super(message);
    }
}