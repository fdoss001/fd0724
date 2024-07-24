package com.pos.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tools")
public class Tool {
    @Id
    private String code;
    private String type;
    private String brand;
    private double dailyCharge;
    private boolean weekendCharge;
    private boolean holidayCharge;
}
