package com.pos.demo.controller;

import com.pos.demo.dto.CheckoutRequest;
import com.pos.demo.model.RentalAgreement;
import com.pos.demo.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
