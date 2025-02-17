package com.example.ExpenseManagementApp.Controllers;

import com.example.ExpenseManagementApp.DTO.ReportCategoryDTO;
import com.example.ExpenseManagementApp.DTO.ReportDTO;
import com.example.ExpenseManagementApp.Services.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/report")
public class ReportController {

    private final ReportService reportService;
    private final Logger logger = Logger.getLogger(ReportController.class.getName());

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<ReportDTO>> getReportSummary(
            @RequestParam ReportService.Period period,@RequestParam Long accountId) {
        try {
            List<ReportDTO> reportDTO = reportService.getReportSummaryForTransactions(period,accountId);
            return ResponseEntity.ok(reportDTO);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/Categories")
    public ResponseEntity<List<ReportCategoryDTO>> getReportSummaryForCategories(
            @RequestParam ReportService.Period period,@RequestParam Long accountId) {
        try {
            List<ReportCategoryDTO> reportDTO = reportService.getReportSummaryForCategory(period,accountId);
            return ResponseEntity.ok(reportDTO);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
