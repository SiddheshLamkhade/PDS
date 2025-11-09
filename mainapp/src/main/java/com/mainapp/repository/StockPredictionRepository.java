package com.mainapp.repository;

import com.mainapp.model.StockPrediction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockPredictionRepository extends JpaRepository<StockPrediction, Long> {

    List<StockPrediction> findByDealerId(Long dealerId);

    List<StockPrediction> findByProductId(Long productId);

    List<StockPrediction> findByPredictionMonth(String predictionMonth);

    Optional<StockPrediction> findByDealerIdAndProductIdAndPredictionMonth(
            Long dealerId, Long productId, String predictionMonth);

    List<StockPrediction> findByDealerIdAndPredictionMonth(Long dealerId, String predictionMonth);
}
