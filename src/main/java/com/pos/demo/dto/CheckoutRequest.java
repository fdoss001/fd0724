package com.pos.demo.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDate;

@Data
public class CheckoutRequest {
    private String toolCode;
    private int rentalDays;
    private int discountPercent;
    @JsonFormat(pattern = "MM/dd/yy")
    private LocalDate checkoutDate;
}
