package com.mainapp.repository;

import com.mainapp.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByDealerIdAndProductId(Long dealerId, Long productId);

    List<Inventory> findByDealerId(Long dealerId);

    List<Inventory> findByProductId(Long productId);

    @Query("SELECT i FROM Inventory i WHERE i.currentStock < :threshold")
    List<Inventory> findLowStockInventory(@Param("threshold") Double threshold);

    @Query("SELECT i FROM Inventory i WHERE i.dealerId = :dealerId AND i.currentStock < :threshold")
    List<Inventory> findLowStockByDealer(@Param("dealerId") Long dealerId, @Param("threshold") Double threshold);
}
