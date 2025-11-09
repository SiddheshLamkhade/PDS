package com.mainapp.repository;

import com.mainapp.model.Citizen.CitizenCategory;
import com.mainapp.model.Quota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuotaRepository extends JpaRepository<Quota, Long> {

    // Find quota by product, category, month, and year
    Optional<Quota> findByProductIdAndCategoryAndMonthAndYear(
        Long productId, 
        CitizenCategory category, 
        Integer month, 
        Integer year
    );

    // Find all quotas for a specific month and year
    List<Quota> findByMonthAndYear(Integer month, Integer year);

    // Find all quotas for a specific category in a month
    List<Quota> findByCategoryAndMonthAndYear(
        CitizenCategory category, 
        Integer month, 
        Integer year
    );

    // Find all active quotas
    List<Quota> findByActive(Boolean active);

    // Find all quotas for a specific product
    List<Quota> findByProductId(Long productId);

    // Find active quotas for current month
    @Query("SELECT q FROM Quota q WHERE q.month = :month AND q.year = :year AND q.active = true")
    List<Quota> findActiveQuotasByMonthAndYear(
        @Param("month") Integer month, 
        @Param("year") Integer year
    );

    // Check if quota exists
    boolean existsByProductIdAndCategoryAndMonthAndYear(
        Long productId, 
        CitizenCategory category, 
        Integer month, 
        Integer year
    );
}
