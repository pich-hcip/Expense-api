package com.demo.Expense_api.repository;

import com.demo.Expense_api.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BudgetRepository extends JpaRepository<Budget, UUID> {

    @Query("SELECT b FROM Budget b LEFT JOIN FETCH b.category " +
            "WHERE b.user.id = :userId AND b.periodMonth = :periodMonth")
    List<Budget> findByUserIdAndPeriodMonth(@Param("userId") UUID userId, @Param("periodMonth") LocalDate periodMonth);

    Optional<Budget> findByUserIdAndCategoryIdAndPeriodMonth(UUID userId, UUID categoryId, LocalDate periodMonth);
}