package com.pos.demo.model;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public enum ToolType {
    LADDER("Ladder", new BigDecimal("1.99"), true, false),
    CHAINSAW("Chainsaw", new BigDecimal("1.49"), false, true),
    JACKHAMMER("Jackhammer", new BigDecimal("2.99"), false, false);

    private final String typeName;
    private final BigDecimal dailyCharge;
    private final boolean weekendCharge;
    private final boolean holidayCharge;

    ToolType(String typeName, BigDecimal dailyCharge, boolean weekendCharge, boolean holidayCharge) {
        this.typeName = typeName;
        this.dailyCharge = dailyCharge;
        this.weekendCharge = weekendCharge;
        this.holidayCharge = holidayCharge;
    }

}
