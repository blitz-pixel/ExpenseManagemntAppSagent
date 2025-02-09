package com.example.ExpenseManagementApp.Services;

import com.example.ExpenseManagementApp.DTO.TransactionDTO;
import com.example.ExpenseManagementApp.Model.Account;
import com.example.ExpenseManagementApp.Model.Category;
import com.example.ExpenseManagementApp.Model.Transaction;
import com.example.ExpenseManagementApp.Repositories.AccountRepository;
import com.example.ExpenseManagementApp.Repositories.CategoryRepository;
import com.example.ExpenseManagementApp.Repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.ExpenseManagementApp.Model.User;

import java.time.Instant;
import java.util.List;
import java.util.logging.Logger;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;
    private final UserService userService;
    private final AccountService accountService;

    Logger logger = Logger.getLogger(TransactionService.class.getName());

    @Autowired
    public TransactionService(TransactionRepository transactionRepository,
                              AccountRepository accountRepository,
                              CategoryRepository categoryRepository, UserService userService, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.categoryRepository = categoryRepository;
        this.userService = userService;
        this.accountService = accountService;
    }

    @Transactional
    public List<TransactionDTO> getTransactions(Long accountId, Category.CatType type) {
        List<Transaction> transactions = transactionRepository.findAllByTypeAndAccountId(type, accountId);
        logger.info(transactions.toString());
        return transactions.stream().map(TransactionDTO::new).toList();
    }

    @Transactional
    public Transaction updateTransaction(Long transactionId, TransactionDTO transactionDTO, Category.CatType type) {
        Transaction existingTransaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));

        Account account = getAccount(transactionDTO.getAccount_id());
        Category category = getCategory(transactionDTO.getParentCategoryName(), account.getType().equals(Account.AccountType.shared) ?
                transactionDTO.getAccount_id() : accountService.getUser(account).getUser_id());

        updateTransactionFields(existingTransaction, transactionDTO, account, category, type);

        return transactionRepository.save(existingTransaction);
    }

    private void updateTransactionFields(Transaction transaction, TransactionDTO transactionDTO, Account account, Category category, Category.CatType type) {
        transaction.setAccount(account);
        transaction.setType(type);
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setDate(transactionDTO.getDate() != null ? transactionDTO.getDate() : Instant.now());
        transaction.setCategory(getCategoryForTransaction(transactionDTO, category));
        transaction.setDescription(transactionDTO.getDescription() != null && !transactionDTO.getDescription().isEmpty() ?
                transactionDTO.getDescription() : null);
    }

    public TransactionDTO getTransactionById(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));
        return new TransactionDTO(transaction);
    }

    @Transactional
    public Transaction addTransaction(TransactionDTO transactionDTO, Category.CatType type) {
        validateTransactionDTO(transactionDTO);
        Account account = getAccount(transactionDTO.getAccount_id());
        Category category = null;
        if (account.getType().equals(Account.AccountType.shared)) {
            category = getCategory(transactionDTO.getParentCategoryName(),transactionDTO.getAccount_id());
        } else {
            User user = accountService.getUser(account);
            category = getCategory(transactionDTO.getParentCategoryName(), user.getUser_id());
        }
        Transaction transaction = createTransaction(transactionDTO, account, category, type);

        return transactionRepository.save(transaction);
    }

    private void validateTransactionDTO(TransactionDTO transactionDTO) {
        if (transactionDTO.getAccount_id() == null) {
            throw new RuntimeException("Account id is required");
        }
    }

    private Account getAccount(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(
                () -> new IllegalArgumentException("Account not found"));
    }

    private Category getCategory(String parentCategoryName,Long accountId) {

        return categoryRepository.findByNameAndId(parentCategoryName,accountId).orElseThrow(
                () -> new IllegalArgumentException("Category not found"));
    }

    private Transaction createTransaction(TransactionDTO transactionDTO, Account account, Category category, Category.CatType type) {
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setType(type);
        transaction.setAmount(transactionDTO.getAmount());
        if (transactionDTO.getDate() != null) {
            transaction.setDate(transactionDTO.getDate());

        } else {
            transaction.setDate(Instant.now());
        }

        transaction.setCategory(getCategoryForTransaction(transactionDTO, category));
        if (transactionDTO.getDescription() != null && !transactionDTO.getDescription().isEmpty()) {
            transaction.setDescription(transactionDTO.getDescription());
        }
        return transaction;
    }

    private Category getCategoryForTransaction(TransactionDTO transactionDTO, Category parentCategory) {
        if (transactionDTO.getSubCategoryName() != null && !transactionDTO.getSubCategoryName().isEmpty()) {
            return categoryRepository.findByParent(transactionDTO.getSubCategoryName(), parentCategory.getId())
                    .orElseThrow(() -> new IllegalArgumentException("SubCategory not found"));
        } else {
            return parentCategory;
        }
    }

    public void DeleteTransaction(Long transactionId) {
        transactionRepository.deleteById(transactionId);
    }
}
