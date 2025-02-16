package com.example.ExpenseManagementApp.Tests;

import com.example.ExpenseManagementApp.DTO.ReportDTO;
import com.example.ExpenseManagementApp.Model.Category;
import com.example.ExpenseManagementApp.Repositories.TransactionRepository;
//import com.example.ExpenseManagementApp.Services.ReportService;
import com.example.ExpenseManagementApp.Services.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ReportTests {


//    private final ReportService reportService; // The service you're testing
    private final TransactionRepository transactionRepository; // Mock the repository
    @Autowired
    private ReportService reportService;

    @Autowired
    public ReportTests( TransactionRepository transactionRepository) {
//        this.reportService = reportService;
        this.transactionRepository = transactionRepository;
    }


    @Test
    public void testGetTotalExpensesGroupedByYear() {
        Long accountId = 25L;  // Example accountId// Example type


        // Call the service method
        List<ReportDTO> result = reportService.getReportSummaryForTransactions(ReportService.Period.YEARLY, accountId);

// Assert that the result is not null
        assertNotNull(result);
        System.out.println(result.size());
// Iterate over each Object[] in the result
        for (ReportDTO reportDTO : result) {
            // obj[0] is the year (Integer) and obj[1] is the total expense (BigDecimal)

            // Print out the year and total expense
            System.out.println("Year: " + reportDTO.getYear() + ", Total Expense: " + reportDTO.getTotalAmount() + "type" + reportDTO.getType());
        }

    }

    // Test for total expenses grouped by month
    @Test
    public void testGetTotalExpensesGroupedByMonth() {
        Long accountId = 25L;
        String type = "expense";


        List<ReportDTO> result = reportService.getReportSummaryForTransactions(ReportService.Period.MONTHLY, accountId);

        assertNotNull(result);
        System.out.println(result.size());
// Iterate over each Object[] in the result
        // Check size

// Print each result to see if there are multiple months
        for (ReportDTO reportDTO : result) {
            System.out.println("Year: " + reportDTO.getYear() + ", Month: " + reportDTO.getTimePeriod() + ", Total Expense: " + reportDTO.getTotalAmount() + "type" + reportDTO.getType());
        }

    }
//
    // Test for total expenses grouped by week
    @Test
    public void testGetTotalExpensesGroupedByWeek() {
        Long accountId = 25L;
        String type = "expense";


        List<ReportDTO> result = reportService.getReportSummaryForTransactions(ReportService.Period.WEEKLY, accountId);

        assertNotNull(result);
        System.out.println(result.size());
// Iterate over each Object[] in the result
        // Check size

// Print each result to see if there are multiple months
       for (ReportDTO reportDTO : result) {
            System.out.println("Year: " + reportDTO.getYear() + ", Week: " + reportDTO.getTimePeriod() + ", Total Expense: " + reportDTO.getTotalAmount() + "type" + reportDTO.getType());
        }
    }
}
