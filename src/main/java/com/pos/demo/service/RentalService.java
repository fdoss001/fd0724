package com.pos.demo.service;

import com.pos.demo.model.RentalAgreement;
import com.pos.demo.model.Tool;
import com.pos.demo.repository.ToolRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

@Slf4j
@Service
public class RentalService {
    @Autowired
    private ToolRepository toolRepository;

    public RentalAgreement checkout(String toolCode, int rentalDays, int discountPercent, LocalDate checkoutDate) {
        if (rentalDays < 1) {
            throw new IllegalArgumentException("Rental day count must be 1 or greater.");
        }
        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Discount percent must be between 0 and 100.");
        }

        Tool tool = toolRepository.findById(toolCode).orElseThrow(() -> new IllegalArgumentException("Invalid tool code."));
        LocalDate dueDate = checkoutDate.plusDays(rentalDays);
        int chargeDays = calculateChargeDays(tool, checkoutDate, dueDate);

        double preDiscountCharge = chargeDays * tool.getDailyCharge();
        double discountAmount = preDiscountCharge * discountPercent / 100;
        double finalCharge = preDiscountCharge - discountAmount;

        RentalAgreement agreement = new RentalAgreement();
        agreement.setToolCode(tool.getCode());
        agreement.setToolType(tool.getType());
        agreement.setToolBrand(tool.getBrand());
        agreement.setRentalDays(rentalDays);
        agreement.setCheckoutDate(checkoutDate);
        agreement.setDueDate(dueDate);
        agreement.setDailyRentalCharge(tool.getDailyCharge());
        agreement.setChargeDays(chargeDays);
        agreement.setPreDiscountCharge(roundToCents(preDiscountCharge));
        agreement.setDiscountPercent(discountPercent);
        agreement.setDiscountAmount(roundToCents(discountAmount));
        agreement.setFinalCharge(roundToCents(finalCharge));

        return agreement;
    }

    private int calculateChargeDays(Tool tool, LocalDate checkoutDate, LocalDate dueDate) {
        int chargeDays = 0;
        for (LocalDate date = checkoutDate.plusDays(1); !date.isAfter(dueDate); date = date.plusDays(1)) {
            if (!tool.isWeekendCharge() && (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                continue;
            }
            if (!tool.isHolidayCharge() && (isIndependenceDay(date) || isLaborDay(date))) {
                continue;
            }
            chargeDays++;
        }
        return chargeDays;
    }

    private boolean isIndependenceDay(LocalDate date) {
        if (date.getMonth() == Month.JULY && date.getDayOfMonth() == 4) {
            return true;
        }
        return (date.getMonth() == Month.JULY && date.getDayOfMonth() == 5 && date.getDayOfWeek() == DayOfWeek.MONDAY)
                || (date.getMonth() == Month.JULY && date.getDayOfMonth() == 3 && date.getDayOfWeek() == DayOfWeek.FRIDAY);
    }

    private boolean isLaborDay(LocalDate date) {
        return date.getMonth() == Month.SEPTEMBER && date.getDayOfWeek() == DayOfWeek.MONDAY && date.getDayOfMonth() <= 7;
    }

    private double roundToCents(double amount) {
        return Math.round(amount * 100.0) / 100.0;
    }
}
