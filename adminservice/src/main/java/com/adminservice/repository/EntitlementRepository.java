package com.adminservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adminservice.model.EntitlementRule;

public interface EntitlementRepository extends JpaRepository<EntitlementRule, Long> {
    List<EntitlementRule> findByCategory(String category);
}

