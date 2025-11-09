package com.mainapp.repository;

import com.mainapp.model.Distribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DistributionRepository extends JpaRepository<Distribution, Long> {

    List<Distribution> findByCitizenId(Long citizenId);

    List<Distribution> findByDealerId(Long dealerId);

    List<Distribution> findByProductId(Long productId);

    List<Distribution> findByStatus(String status);

    @Query("SELECT d FROM Distribution d WHERE d.distributionDate BETWEEN :startDate AND :endDate")
    List<Distribution> findByDateRange(@Param("startDate") LocalDateTime startDate, 
                                       @Param("endDate") LocalDateTime endDate);

    @Query("SELECT d FROM Distribution d WHERE d.citizenId = :citizenId AND d.distributionDate BETWEEN :startDate AND :endDate")
    List<Distribution> findByCitizenAndDateRange(@Param("citizenId") Long citizenId,
                                                  @Param("startDate") LocalDateTime startDate,
                                                  @Param("endDate") LocalDateTime endDate);

    @Query("SELECT d FROM Distribution d WHERE d.dealerId = :dealerId AND d.productId = :productId")
    List<Distribution> findByDealerAndProduct(@Param("dealerId") Long dealerId, 
                                               @Param("productId") Long productId);

    @Query("SELECT d FROM Distribution d WHERE d.citizenId = :citizenId AND d.productId = :productId")
    List<Distribution> findByCitizenIdAndProductId(@Param("citizenId") Long citizenId, 
                                                    @Param("productId") Long productId);
}
