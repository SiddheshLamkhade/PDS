package com.adminservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adminservice.model.Commodity;

public interface CommodityRepository extends JpaRepository<Commodity, Long> {
    Optional<Commodity> findByNameIgnoreCase(String name);
}
