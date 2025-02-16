package com.example.ExpenseManagementApp.DTO;

import jakarta.annotation.Nullable;

import java.math.BigDecimal;

public class ReportCategoryDTO {

        private String parentCategoryName;
        @Nullable
        private String subCategoryName;
        private String type;
        @Nullable
        private Integer year;
        private Integer TimePeriod;
        private BigDecimal totalSpent;

        public ReportCategoryDTO(Object[] record, @Nullable String period) {
            this.parentCategoryName = (String) record[0];
            this.subCategoryName = (String) record[1];
            this.type = (String) record[2];
            this.year = (Integer) record[3];
            if (period != null) {
                if ("WEEKLY".equals(period) || "MONTHLY".equals(period)) {
                    this.TimePeriod = (Integer) record[4];
                }
                this.totalSpent = (BigDecimal) record[5];
            } else {
                this.totalSpent = (BigDecimal) record[4];
            }
        }

    public String getParentCategoryName() {
        return parentCategoryName;
    }

    public void setParentCategoryName(String parentCategoryName) {
        this.parentCategoryName = parentCategoryName;
    }

    @Nullable
    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(@Nullable String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Nullable
    public Integer getYear() {
        return year;
    }

    public void setYear(@Nullable Integer year) {
        this.year = year;
    }

    public Integer getTimePeriod() {
        return TimePeriod;
    }

    public void setTimePeriod(Integer timePeriod) {
        TimePeriod = timePeriod;
    }

    public BigDecimal getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(BigDecimal totalSpent) {
        this.totalSpent = totalSpent;
    }
}
