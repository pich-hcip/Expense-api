package com.demo.Expense_api.service;

import com.demo.Expense_api.dto.budget.BudgetRequest;
import com.demo.Expense_api.dto.budget.BudgetResponse;
import com.demo.Expense_api.entity.Budget;
import com.demo.Expense_api.entity.Category;
import com.demo.Expense_api.entity.User;
import com.demo.Expense_api.repository.BudgetRepository;
import com.demo.Expense_api.repository.CategoryRepository;
import com.demo.Expense_api.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;

    public List<BudgetResponse> getCurrentMonthBudgets(UUID userId) {
        LocalDate periodStart = YearMonth.now().atDay(1);
        return budgetRepository.findByUserIdAndPeriodMonth(userId, periodStart).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public BudgetResponse createOrUpdateBudget(User user, BudgetRequest request) {
        UUID categoryId = UUID.fromString(request.getCategoryId());
        LocalDate periodStart = YearMonth.now().atDay(1);

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        Budget budget = budgetRepository
                .findByUserIdAndCategoryIdAndPeriodMonth(user.getId(), categoryId, periodStart)
                .orElse(Budget.builder()
                        .user(user)
                        .category(category)
                        .periodMonth(periodStart)
                        .build());

        budget.setMonthlyLimit(request.getMonthlyLimit());

        Budget saved = budgetRepository.save(budget);
        return toResponse(saved);
    }

    public void deleteBudget(UUID userId, UUID budgetId) {
        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new IllegalArgumentException("Budget not found"));

        if (!budget.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("You don't have access to this budget");
        }

        budgetRepository.delete(budget);
    }

    private BudgetResponse toResponse(Budget budget) {
        YearMonth currentMonth = YearMonth.now();
        LocalDate start = currentMonth.atDay(1);
        LocalDate end = currentMonth.atEndOfMonth();

        BigDecimal spent = transactionRepository.sumExpenseByCategoryBetween(
                budget.getUser().getId(), budget.getCategory().getId(), start, end
        );

        BigDecimal limit = budget.getMonthlyLimit();
        BigDecimal remaining = limit.subtract(spent);
        double percentUsed = limit.compareTo(BigDecimal.ZERO) > 0
                ? spent.divide(limit, 4, java.math.RoundingMode.HALF_UP).doubleValue() * 100
                : 0;

        return BudgetResponse.builder()
                .id(budget.getId())
                .categoryId(budget.getCategory().getId().toString())
                .categoryName(budget.getCategory().getName())
                .categoryIcon(budget.getCategory().getIcon())
                .categoryColorHex(budget.getCategory().getColorHex())
                .monthlyLimit(limit)
                .spentSoFar(spent)
                .remaining(remaining)
                .percentUsed(percentUsed)
                .build();
    }
}