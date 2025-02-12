package com.example.ExpenseManagementApp.Services;

import com.example.ExpenseManagementApp.DTO.RecurringTransactionDTO;
import com.example.ExpenseManagementApp.DTO.TransactionDTO;
import com.example.ExpenseManagementApp.Model.Category;
import com.example.ExpenseManagementApp.Model.Recurringtransaction;
import com.example.ExpenseManagementApp.Model.Transaction;
import com.example.ExpenseManagementApp.Repositories.AccountRepository;
import com.example.ExpenseManagementApp.Repositories.RecurringTransactionRepository;
import com.example.ExpenseManagementApp.Repositories.TransactionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Slf4j
@Service
public class RecurringTransactionService {

    private final RecurringTransactionRepository recurringTransactionRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public RecurringTransactionService(RecurringTransactionRepository recurringTransactionRepository, AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.recurringTransactionRepository = recurringTransactionRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    Logger logger = Logger.getLogger(RecurringTransactionService.class.getName());
    public Recurringtransaction createRecurringTransaction(Transaction transaction,TransactionDTO transactionDTO) {
        if (recurringTransactionRepository.existsById(transaction.getId())) {
            throw new IllegalArgumentException("Recurring transaction already exists");
        }
        Recurringtransaction recurringTransaction = new Recurringtransaction();
        recurringTransaction.setTransaction(transaction);
        recurringTransaction.setFrequency(transactionDTO.getFrequency());
        recurringTransaction.setCurrentDate(LocalDate.now());
        recurringTransaction.setNextDate(calculateNextDate(recurringTransaction.getCurrentDate(), transactionDTO.getFrequency()));

        recurringTransaction.setActive(true);
        recurringTransactionRepository.save(recurringTransaction);
        return recurringTransaction;
    }

    public List<RecurringTransactionDTO> getRecurringTransactions(Long accountId, ZoneId userZone) {
        List<Recurringtransaction> recurringTransactions = recurringTransactionRepository.findRTransactionsByTransactionId(accountId);

        return recurringTransactions.stream().map(recurringTransaction -> {
            RecurringTransactionDTO dto = new RecurringTransactionDTO(recurringTransaction);
            ZonedDateTime userStartDate = recurringTransaction.getCurrentDate().atStartOfDay(userZone);
            ZonedDateTime userNextDate = recurringTransaction.getNextDate().atStartOfDay(userZone);
            dto.setCurrentDate(userStartDate.toLocalDate());
            dto.setNextDate(userNextDate.toLocalDate());

            return dto;
        }).toList();
    }


    // not completed
//    @Scheduled(cron = "0 0 0 * * *") // Run at midnight
//    private void updateRecurringTransactionTimings() {
//        List<Recurringtransaction> recurringTransactions = recurringTransactionRepository.findAll();
//
//        for (Recurringtransaction recurringTransaction : recurringTransactions) {
//            if (LocalDate.now().isAfter(recurringTransaction.getNextDate())) {
//                recurringTransaction.setCurrentDate(recurringTransaction.getNextDate());
//                recurringTransaction.setNextDate(calculateNextDate(recurringTransaction.getCurrentDate(), recurringTransaction.getFrequency()));
//                recurringTransactionRepository.save(recurringTransaction);
//            }
//        }
//    }



    // To test
    @Transactional
    @Scheduled(cron = "0 */5 * * * *")
    protected void updateRecurringTransactionTimingsSample() {
        List<Recurringtransaction> recurringTransactions = recurringTransactionRepository.findAll();
        List<Transaction> newTransactions = new ArrayList<>();

        for (Recurringtransaction recurringTransaction : recurringTransactions) {
            recurringTransaction.setCurrentDate(recurringTransaction.getNextDate());
            recurringTransaction.setNextDate(
                    calculateNextDate(recurringTransaction.getCurrentDate(), recurringTransaction.getFrequency())
            );

            Transaction transaction = recurringTransaction.getTransaction();

            Transaction t = new Transaction(
                    transaction.getAmount(),
                    transaction.getCategory(),
                    Instant.from(recurringTransaction.getNextDate().atStartOfDay()),
                    transaction.getDescription(),
                    transaction.getAccount(),
                    transaction.getType()
            );

            recurringTransaction.setTransaction(t);
            newTransactions.add(t);
            logger.info(t.getId().toString());
        }

        transactionRepository.saveAll(newTransactions);
        entityManager.flush();
        recurringTransactionRepository.saveAll(recurringTransactions);
        logger.info("Updated recurring transactions");

    }

    private LocalDate calculateNextDate(LocalDate currentDate, Recurringtransaction.RFrequency frequency) {
        return switch (frequency) {
            case DAILY -> currentDate.plusDays(1);
            case WEEKLY -> currentDate.plusWeeks(1);
            case MONTHLY -> currentDate.plusMonths(1);
            case YEARLY -> currentDate.plusYears(1);
        };
    }
}

