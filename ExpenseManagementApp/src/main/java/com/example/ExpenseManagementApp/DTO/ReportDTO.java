package com.example.ExpenseManagementApp.DTO;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
public class ReportDTO {

    private Integer year;
    @Nullable
    private Integer timePeriod;
    private BigDecimal totalAmount;
    private String type;


    public ReportDTO(Object[] record, @Nullable  String period) {
        this.year = (Integer) record[0];
        if (period != null) {
            if ("WEEKLY".equals(period) || "MONTHLY".equals(period)) {
                this.timePeriod = (Integer) record[1];
            }
            this.totalAmount = (BigDecimal) record[2];
            this.type = (String) record[3];
        } else {
            this.totalAmount = (BigDecimal) record[1];
            this.type = (String) record[2];
        }
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Nullable
    public Integer getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(@Nullable Integer timePeriod) {
        this.timePeriod = timePeriod;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
