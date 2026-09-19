package com.demo.Expense_api.repository;

import com.demo.Expense_api.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    @Query("SELECT t FROM Transaction t LEFT JOIN FETCH t.category " +
            "WHERE t.user.id = :userId " +
            "ORDER BY t.transactionDate DESC, t.createdAt DESC")
    List<Transaction> findByUserIdOrderByTransactionDateDescCreatedAtDesc(@Param("userId") UUID userId);

    @Query("SELECT t FROM Transaction t LEFT JOIN FETCH t.category " +
            "WHERE t.user.id = :userId " +
            "ORDER BY t.transactionDate DESC, t.createdAt DESC")
    List<Transaction> findTop10ByUserIdOrderByTransactionDateDescCreatedAtDesc(@Param("userId") UUID userId);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t " +
            "WHERE t.user.id = :userId AND t.kind = 'INCOME' " +
            "AND t.transactionDate BETWEEN :start AND :end")
    BigDecimal sumIncomeBetween(@Param("userId") UUID userId, @Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t " +
            "WHERE t.user.id = :userId AND t.kind = 'EXPENSE' " +
            "AND t.transactionDate BETWEEN :start AND :end")
    BigDecimal sumExpenseBetween(@Param("userId") UUID userId, @Param("start") LocalDate start, @Param("end") LocalDate end);

    long countByCategoryId(UUID categoryId);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t " +
            "WHERE t.user.id = :userId AND t.category.id = :categoryId AND t.kind = 'EXPENSE' " +
            "AND t.transactionDate BETWEEN :start AND :end")
    BigDecimal sumExpenseByCategoryBetween(@Param("userId") UUID userId, @Param("categoryId") UUID categoryId,
                                           @Param("start") LocalDate start, @Param("end") LocalDate end);

}