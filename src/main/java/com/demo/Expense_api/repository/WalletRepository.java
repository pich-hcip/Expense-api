// repository/WalletRepository.java
package com.demo.Expense_api.repository;

import com.demo.Expense_api.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {
    List<Wallet> findByUserId(UUID userId);

    @Query("SELECT COALESCE(SUM(w.balance), 0) FROM Wallet w WHERE w.user.id = :userId")
    BigDecimal sumBalanceByUserId(UUID userId);
}