package com.pos.demo.controller;

import com.pos.demo.dto.CheckoutRequest;
import com.pos.demo.model.RentalAgreement;
import com.pos.demo.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @PostMapping("/checkout")
    public RentalAgreement checkout(@RequestBody CheckoutRequest req) {
        return rentalService.checkout(req.getToolCode(), req.getRentalDays(), req.getDiscountPercent(), req.getCheckoutDate());
    }
}
