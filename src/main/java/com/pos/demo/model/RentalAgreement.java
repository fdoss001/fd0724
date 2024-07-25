package com.pos.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
public class RentalAgreement {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yy");

    private Tool tool;
    private int rentalDays;
    private LocalDate checkoutDate;
    private LocalDate dueDate;
    private BigDecimal dailyRentalCharge;
    private int chargeDays;
    private BigDecimal preDiscountCharge;
    private int discountPercent;
    private BigDecimal discountAmount;
    private BigDecimal finalCharge;

    @Override
    public String toString() {

        String toFmt = """
                Tool code: %s
                Tool type: %s
                Tool brand: %s
                Rental days: %d
                Checkout date: %s
                Due date: %s
                Daily rental charge: $%.2f
                Charge days: %d
                Pre-discount charge: $%.2f
                Discount percent: %d%%
                Discount amount: $%.2f
                Final charge: $%.2f
                """;
        return String.format(toFmt, tool.getToolCode(), tool.getToolType().getTypeName(), tool.getToolBrand(), rentalDays,
                DATE_FORMATTER.format(checkoutDate), DATE_FORMATTER.format(dueDate), dailyRentalCharge, chargeDays,
                preDiscountCharge, discountPercent, discountAmount, finalCharge);
    }
}
