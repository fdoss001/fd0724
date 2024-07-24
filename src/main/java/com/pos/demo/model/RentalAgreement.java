package com.pos.demo.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RentalAgreement {
    private String toolCode;
    private String toolType;
    private String toolBrand;
    private int rentalDays;
    private LocalDate checkoutDate;
    private LocalDate dueDate;
    private double dailyRentalCharge;
    private int chargeDays;
    private double preDiscountCharge;
    private int discountPercent;
    private double discountAmount;
    private double finalCharge;

    public void printAgreement() {
        System.out.printf("Tool code: %s\nTool type: %s\nTool brand: %s\nRental days: %d\nCheckout date: %s\nDue date: %s\nDaily rental charge: $%.2f\nCharge days: %d\nPre-discount charge: $%.2f\nDiscount percent: %d%%\nDiscount amount: $%.2f\nFinal charge: $%.2f\n",
                toolCode, toolType, toolBrand, rentalDays, checkoutDate, dueDate, dailyRentalCharge, chargeDays, preDiscountCharge, discountPercent, discountAmount, finalCharge);
    }
}

