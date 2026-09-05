package com.demo.Expense_api.repository;

import com.demo.Expense_api.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

    @Query("SELECT c FROM Category c WHERE c.kind = :kind AND (c.user.id = :userId OR c.user IS NULL)")
    List<Category> findByKindAndUserIdOrUserIdIsNull(@Param("kind") Category.Kind kind, @Param("userId") UUID userId);
}