package com.example.ExpenseManagementApp.Services;

import com.example.ExpenseManagementApp.DTO.ReportCategoryDTO;
import com.example.ExpenseManagementApp.DTO.ReportDTO;
import com.example.ExpenseManagementApp.Repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {


    private final TransactionRepository transactionRepository;

    @Autowired
    public ReportService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<ReportCategoryDTO> getReportSummaryForCategory(Period period, Long accountId) {

        switch (period) {
            case WEEKLY:
                List<Object[]> totalAmountForEachTypeWeekly = transactionRepository.getCategoryWiseTransactionWeekly(accountId);
                List<ReportCategoryDTO> reportForWeekly = totalAmountForEachTypeWeekly.stream()
                        .map(record -> new ReportCategoryDTO(record, "WEEKLY"))
                        .toList();
                return reportForWeekly;
            case MONTHLY:
                List<Object[]> totalAmountForEachTypeMonthly = transactionRepository.getCategoryWiseTransactionMonthly(accountId);
                List<ReportCategoryDTO> reportForMonthly = totalAmountForEachTypeMonthly.stream()
                        .map(record -> new ReportCategoryDTO(record, "MONTHLY"))
                        .toList();
                return reportForMonthly;
            case YEARLY:
                List<Object[]> totalAmountForEachTypeYearly = transactionRepository.getCategoryWiseTransactionYearly(accountId);
                List<ReportCategoryDTO> reportForYearly = totalAmountForEachTypeYearly.stream()
                        .map(record -> new ReportCategoryDTO(record, null))
                        .toList();
                return reportForYearly;
            default:
                throw new IllegalArgumentException("Invalid period");
        }
    }

        public List<ReportDTO> getReportSummaryForTransactions(Period period,Long accountId) {

        switch (period) {
            case WEEKLY:
                List<Object[]>  totalAmountForEachTypeWeekly=transactionRepository.getTotalExpensesGroupedByWeekForAccount(accountId);
                List<ReportDTO> reportForWeekly = totalAmountForEachTypeWeekly.stream()
                        .map(record -> new ReportDTO(record, "WEEKLY"))
                        .toList();
                return reportForWeekly;
            case MONTHLY:
                List<Object[]>  totalAmountForEachTypeMonthly =transactionRepository.getTotalExpensesGroupedByMonthForAccount(accountId);
                List<ReportDTO> reportForMonthly = totalAmountForEachTypeMonthly.stream()
                        .map(record -> new ReportDTO(record, "MONTHLY"))
                        .toList();
                return reportForMonthly;
            case YEARLY:
                List<Object[]>  totalAmountForEachTypeYearly =transactionRepository.getTotalExpensesGroupedByYearForAccount(accountId);
                List<ReportDTO> reportForYearly = totalAmountForEachTypeYearly.stream()
                        .map(record -> new ReportDTO(record, null))
                        .toList();
                return reportForYearly;
            default:
                throw new IllegalArgumentException("Invalid period");
        }


//        return new ReportDTO(
//                period,
//                totalExpenses != null ? totalExpenses : BigDecimal.ZERO,
//                totalRevenues != null ? totalRevenues : BigDecimal.ZERO,
//                categoryWiseExpenses
//        );

    }

    public enum Period {
         WEEKLY, MONTHLY, YEARLY
    }
}
