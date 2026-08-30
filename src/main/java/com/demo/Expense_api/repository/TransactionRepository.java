// repository/TransactionRepository.java
package com.demo.Expense_api.repository;

import com.demo.Expense_api.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    List<Transaction> findTop10ByUserIdOrderByTransactionDateDescCreatedAtDesc(UUID userId);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t " +
            "WHERE t.user.id = :userId AND t.kind = 'INCOME' " +
            "AND t.transactionDate BETWEEN :start AND :end")
    BigDecimal sumIncomeBetween(UUID userId, LocalDate start, LocalDate end);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t " +
            "WHERE t.user.id = :userId AND t.kind = 'EXPENSE' " +
            "AND t.transactionDate BETWEEN :start AND :end")
    BigDecimal sumExpenseBetween(UUID userId, LocalDate start, LocalDate end);
}