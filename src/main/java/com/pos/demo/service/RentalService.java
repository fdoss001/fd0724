package com.pos.demo.service;

import com.pos.demo.exception.InvalidDiscountPercentException;
import com.pos.demo.exception.InvalidRentalDaysException;
import com.pos.demo.model.RentalAgreement;
import com.pos.demo.model.Tool;
import com.pos.demo.model.ToolType;
import com.pos.demo.repository.ToolRepository;
import com.pos.demo.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
            throw new InvalidRentalDaysException("Rental day count must be 1 or greater.");
        }
        if (discountPercent < 0 || discountPercent > 100) {
            throw new InvalidDiscountPercentException("Discount percent must be between 0 and 100.");
        }

        Tool tool = toolRepository.findById(toolCode).orElseThrow(() -> new IllegalArgumentException("Invalid tool code."));
        ToolType toolType = tool.getToolType();

        LocalDate dueDate = checkoutDate.plusDays(rentalDays);
        int chargeDays = calculateChargeDays(toolType, checkoutDate, dueDate);

        BigDecimal preDiscountCharge = toolType.getDailyCharge().multiply(BigDecimal.valueOf(chargeDays));
        BigDecimal discountAmount = preDiscountCharge.multiply(BigDecimal.valueOf(discountPercent)).divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
        BigDecimal finalCharge = preDiscountCharge.subtract(discountAmount);

        RentalAgreement agreement = new RentalAgreement();
        agreement.setTool(tool);
        agreement.setRentalDays(rentalDays);
        agreement.setCheckoutDate(checkoutDate);
        agreement.setDueDate(dueDate);
        agreement.setDailyRentalCharge(toolType.getDailyCharge());
        agreement.setChargeDays(chargeDays);
        agreement.setPreDiscountCharge(preDiscountCharge.setScale(2, RoundingMode.HALF_UP));
        agreement.setDiscountPercent(discountPercent);
        agreement.setDiscountAmount(discountAmount.setScale(2, RoundingMode.HALF_UP));
        agreement.setFinalCharge(finalCharge.setScale(2, RoundingMode.HALF_UP));

        return agreement;
    }

    private int calculateChargeDays(ToolType toolType, LocalDate checkoutDate, LocalDate dueDate) {
        int chargeDays = 0;
        //start the loop at day after checkout, keep going while not due date, increment date by 1
        for (LocalDate date = checkoutDate.plusDays(1); !date.isAfter(dueDate); date = date.plusDays(1)) {
            if (!toolType.isWeekendCharge() && (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                //if no weekend charge and current day is weekend, skip
                continue;
            }
            if (!toolType.isHolidayCharge() && DateUtils.isApprovedFreeHoliday(date)) {
                //if no holiday charge and current day is approved free holiday
                continue;
            }
            chargeDays++;
        }
        return chargeDays;
    }
}
