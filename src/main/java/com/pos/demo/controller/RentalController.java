package com.pos.demo.controller;

import com.pos.demo.model.RentalAgreement;
import com.pos.demo.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/rentals")
public class RentalController {
    @Autowired
    private RentalService rentalService;

    @PostMapping("/checkout")
    public RentalAgreement checkout(@RequestParam String toolCode,
                                    @RequestParam int rentalDays,
                                    @RequestParam int discountPercent,
                                    @RequestParam String checkoutDate) {
        LocalDate date = LocalDate.parse(checkoutDate, DateTimeFormatter.ofPattern("MM/dd/yy"));
        return rentalService.checkout(toolCode, rentalDays, discountPercent, date);
    }
}
