package com.pos.demo;

import com.pos.demo.exception.InvalidDiscountPercentException;
import com.pos.demo.model.RentalAgreement;
import com.pos.demo.service.RentalService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest
@ActiveProfiles("test")
public class RentalServiceTests {
	@Autowired
	private RentalService rentalService;

	@Test
	public void test1() {
		assertThrows(InvalidDiscountPercentException.class, () -> {
			rentalService.checkout("JAKR", 5, 101, LocalDate.of(2015, 9, 3));
		}, "Discount percent must be between 0 and 100.");
	}

	@Test
	public void test2() {
		RentalAgreement agreement = rentalService.checkout("LADW", 3, 10, LocalDate.of(2020, 7, 2));
		assertEquals("LADW", agreement.getTool().getToolCode());
		assertEquals("Ladder", agreement.getTool().getToolType().getTypeName());
		assertEquals("Werner", agreement.getTool().getToolBrand());
		assertEquals(3, agreement.getRentalDays());
		assertEquals(LocalDate.of(2020, 7, 2), agreement.getCheckoutDate());
		assertEquals(LocalDate.of(2020, 7, 5), agreement.getDueDate());
		assertEquals(new BigDecimal("1.99"), agreement.getDailyRentalCharge());
		assertEquals(2, agreement.getChargeDays());
		assertEquals(new BigDecimal("3.98"), agreement.getPreDiscountCharge());
		assertEquals(10, agreement.getDiscountPercent());
		assertEquals(new BigDecimal("0.40"), agreement.getDiscountAmount());
		assertEquals(new BigDecimal("3.58"), agreement.getFinalCharge());
	}

	@Test
	public void test3() {
		RentalAgreement agreement = rentalService.checkout("CHNS", 5, 25, LocalDate.of(2015, 7, 2));
		assertEquals("CHNS", agreement.getTool().getToolCode());
		assertEquals("Chainsaw", agreement.getTool().getToolType().getTypeName());
		assertEquals("Stihl", agreement.getTool().getToolBrand());
		assertEquals(5, agreement.getRentalDays());
		assertEquals(LocalDate.of(2015, 7, 2), agreement.getCheckoutDate());
		assertEquals(LocalDate.of(2015, 7, 7), agreement.getDueDate());
		assertEquals(new BigDecimal("1.49"), agreement.getDailyRentalCharge());
		assertEquals(3, agreement.getChargeDays());
		assertEquals(new BigDecimal("4.47"), agreement.getPreDiscountCharge());
		assertEquals(25, agreement.getDiscountPercent());
		assertEquals(new BigDecimal("1.12"), agreement.getDiscountAmount());
		assertEquals(new BigDecimal("3.35"), agreement.getFinalCharge());
	}

	@Test
	public void test4() {
		RentalAgreement agreement = rentalService.checkout("JAKD", 6, 0, LocalDate.of(2015, 9, 3));
		assertEquals("JAKD", agreement.getTool().getToolCode());
		assertEquals("Jackhammer", agreement.getTool().getToolType().getTypeName());
		assertEquals("DeWalt", agreement.getTool().getToolBrand());
		assertEquals(6, agreement.getRentalDays());
		assertEquals(LocalDate.of(2015, 9, 3), agreement.getCheckoutDate());
		assertEquals(LocalDate.of(2015, 9, 9), agreement.getDueDate());
		assertEquals(new BigDecimal("2.99"), agreement.getDailyRentalCharge());
		assertEquals(3, agreement.getChargeDays());
		assertEquals(new BigDecimal("8.97"), agreement.getPreDiscountCharge());
		assertEquals(0, agreement.getDiscountPercent());
		assertEquals(new BigDecimal("0.00"), agreement.getDiscountAmount());
		assertEquals(new BigDecimal("8.97"), agreement.getFinalCharge());
	}

	@Test
	public void test5() {
		RentalAgreement agreement = rentalService.checkout("JAKR", 9, 0, LocalDate.of(2015, 7, 2));
		assertEquals("JAKR", agreement.getTool().getToolCode());
		assertEquals("Jackhammer", agreement.getTool().getToolType().getTypeName());
		assertEquals("Rigid", agreement.getTool().getToolBrand());
		assertEquals(9, agreement.getRentalDays());
		assertEquals(LocalDate.of(2015, 7, 2), agreement.getCheckoutDate());
		assertEquals(LocalDate.of(2015, 7, 11), agreement.getDueDate());
		assertEquals(new BigDecimal("2.99"), agreement.getDailyRentalCharge());
		assertEquals(5, agreement.getChargeDays());
		assertEquals(new BigDecimal("14.95"), agreement.getPreDiscountCharge());
		assertEquals(0, agreement.getDiscountPercent());
		assertEquals(new BigDecimal("0.00"), agreement.getDiscountAmount());
		assertEquals(new BigDecimal("14.95"), agreement.getFinalCharge());
	}

	@Test
	public void test6() {
		RentalAgreement agreement = rentalService.checkout("JAKR", 4, 50, LocalDate.of(2020, 7, 2));
		assertEquals("JAKR", agreement.getTool().getToolCode());
		assertEquals("Jackhammer", agreement.getTool().getToolType().getTypeName());
		assertEquals("Rigid", agreement.getTool().getToolBrand());
		assertEquals(4, agreement.getRentalDays());
		assertEquals(LocalDate.of(2020, 7, 2), agreement.getCheckoutDate());
		assertEquals(LocalDate.of(2020, 7, 6), agreement.getDueDate());
		assertEquals(new BigDecimal("2.99"), agreement.getDailyRentalCharge());
		assertEquals(1, agreement.getChargeDays());
		assertEquals(new BigDecimal("2.99"), agreement.getPreDiscountCharge());
		assertEquals(50, agreement.getDiscountPercent());
		assertEquals(new BigDecimal("1.50"), agreement.getDiscountAmount());
		assertEquals(new BigDecimal("1.49"), agreement.getFinalCharge());
	}

	@Test
	public void printingTest() {
		RentalAgreement agreement = rentalService.checkout("JAKR", 4, 50, LocalDate.of(2020, 7, 2));
		String agreementPrint = agreement.toString();
		String expectedPrint = """
				Tool code: JAKR
				Tool type: Jackhammer
				Tool brand: Rigid
				Rental days: 4
				Checkout date: 07/02/20
				Due date: 07/06/20
				Daily rental charge: $2.99
				Charge days: 1
				Pre-discount charge: $2.99
				Discount percent: 50%
				Discount amount: $1.50
				Final charge: $1.49
				""";
		assertEquals(expectedPrint, agreementPrint);
	}
}
