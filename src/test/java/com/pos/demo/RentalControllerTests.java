package com.pos.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pos.demo.dto.CheckoutRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class RentalControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCheckout() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        CheckoutRequest request = new CheckoutRequest();
        request.setToolCode("LADW");
        request.setRentalDays(5);
        request.setDiscountPercent(10);
        request.setCheckoutDate(LocalDate.parse("07/02/24", formatter));

        MvcResult result = mockMvc.perform(post("/api/rentals/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        String responseString = result.getResponse().getContentAsString();
        assertTrue(responseString.contains("LADW"));
        assertTrue(responseString.contains("LADDER"));
        assertTrue(responseString.contains("Werner"));
        assertTrue(responseString.contains("5"));
        assertTrue(responseString.contains("2024-07-02"));
        assertTrue(responseString.contains("2024-07-07"));
        assertTrue(responseString.contains("1.99"));
        assertTrue(responseString.contains("4"));
        assertTrue(responseString.contains("7.96"));
        assertTrue(responseString.contains("10"));
        assertTrue(responseString.contains("0.80"));
        assertTrue(responseString.contains("7.16"));
    }
}