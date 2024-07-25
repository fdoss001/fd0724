package com.pos.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "TOOL")
public class Tool {
    @Id
    private String toolCode;
    private String toolType;
    private String toolBrand;

    public ToolType getToolType() {
        return ToolType.valueOf(toolType.toUpperCase());
    }
}
