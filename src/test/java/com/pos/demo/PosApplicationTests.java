package com.pos.demo;

import com.pos.demo.model.RentalAgreement;
import com.pos.demo.service.RentalService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

@SpringBootTest
public class PosApplicationTests {
	@Autowired
	private RentalService rentalService;

	@Test
	public void testCheckout() {
		RentalAgreement agreement = rentalService.checkout("LADW", 4, 20, LocalDate.of(2024, 7, 2));
		assertEquals("LADW", agreement.getToolCode());
		assertEquals("Ladder", agreement.getToolType());
		assertEquals("Werner", agreement.getToolBrand());
		assertEquals(4, agreement.getRentalDays());
		assertEquals(LocalDate.of(2024, 7, 2), agreement.getCheckoutDate());
		assertEquals(LocalDate.of(2024, 7, 6), agreement.getDueDate());
		assertEquals(1.99, agreement.getDailyRentalCharge());
		assertEquals(2, agreement.getChargeDays());
		assertEquals(3.98, agreement.getPreDiscountCharge());
		assertEquals(20, agreement.getDiscountPercent());
		assertEquals(0.80, agreement.getDiscountAmount());
		assertEquals(3.18, agreement.getFinalCharge());
	}
}
