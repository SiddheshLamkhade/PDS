package com.mainapp.service;

import com.mainapp.dto.PredictionRequest;
import com.mainapp.dto.PredictionResponse;
import com.mainapp.model.Dealer;
import com.mainapp.model.Distribution;
import com.mainapp.model.Product;
import com.mainapp.model.StockPrediction;
import com.mainapp.repository.DealerRepository;
import com.mainapp.repository.DistributionRepository;
import com.mainapp.repository.ProductRepository;
import com.mainapp.repository.StockPredictionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PredictionService {

    private final StockPredictionRepository predictionRepository;
    private final DistributionRepository distributionRepository;
    private final DealerRepository dealerRepository;
    private final ProductRepository productRepository;

    @Transactional
    public PredictionResponse generatePrediction(PredictionRequest request) {
        // Validate dealer and product
        Dealer dealer = dealerRepository.findById(request.getDealerId())
                .orElseThrow(() -> new RuntimeException("Dealer not found with ID: " + request.getDealerId()));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + request.getProductId()));

        // Get historical distribution data (last 3 months)
        LocalDateTime threeMonthsAgo = LocalDateTime.now().minusMonths(3);
        LocalDateTime now = LocalDateTime.now();

        List<Distribution> historicalDistributions = distributionRepository.findByDealerAndProduct(
                request.getDealerId(), request.getProductId());

        // Filter for last 3 months
        List<Distribution> recentDistributions = historicalDistributions.stream()
                .filter(d -> d.getDistributionDate().isAfter(threeMonthsAgo) && 
                           d.getDistributionDate().isBefore(now))
                .collect(Collectors.toList());

        // Calculate average demand (simple forecasting algorithm)
        Double predictedDemand = calculateAverageDemand(recentDistributions);

        // Check if prediction already exists for this month
        predictionRepository.findByDealerIdAndProductIdAndPredictionMonth(
                request.getDealerId(), request.getProductId(), request.getPredictionMonth())
                .ifPresent(existing -> predictionRepository.delete(existing));

        // Create new prediction
        StockPrediction prediction = StockPrediction.builder()
                .dealerId(request.getDealerId())
                .productId(request.getProductId())
                .predictedDemand(predictedDemand)
                .predictionMonth(request.getPredictionMonth())
                .algorithm("SIMPLE_AVERAGE")
                .generatedAt(LocalDateTime.now())
                .build();

        StockPrediction savedPrediction = predictionRepository.save(prediction);
        return mapToPredictionResponse(savedPrediction, dealer, product);
    }

    @Transactional
    public List<PredictionResponse> generatePredictionsForDealer(Long dealerId, String predictionMonth) {
        // Validate dealer
        Dealer dealer = dealerRepository.findById(dealerId)
                .orElseThrow(() -> new RuntimeException("Dealer not found with ID: " + dealerId));

        // Get all active products
        List<Product> products = productRepository.findByActive(true);

        return products.stream()
                .map(product -> {
                    PredictionRequest request = PredictionRequest.builder()
                            .dealerId(dealerId)
                            .productId(product.getId())
                            .predictionMonth(predictionMonth)
                            .build();
                    return generatePrediction(request);
                })
                .collect(Collectors.toList());
    }

    public PredictionResponse getPredictionById(Long id) {
        StockPrediction prediction = predictionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prediction not found with ID: " + id));

        Dealer dealer = dealerRepository.findById(prediction.getDealerId()).orElse(null);
        Product product = productRepository.findById(prediction.getProductId()).orElse(null);

        return mapToPredictionResponse(prediction, dealer, product);
    }

    public List<PredictionResponse> getPredictionsByDealer(Long dealerId) {
        dealerRepository.findById(dealerId)
                .orElseThrow(() -> new RuntimeException("Dealer not found with ID: " + dealerId));

        return predictionRepository.findByDealerId(dealerId).stream()
                .map(prediction -> {
                    Dealer dealer = dealerRepository.findById(prediction.getDealerId()).orElse(null);
                    Product product = productRepository.findById(prediction.getProductId()).orElse(null);
                    return mapToPredictionResponse(prediction, dealer, product);
                })
                .collect(Collectors.toList());
    }

    public List<PredictionResponse> getPredictionsByMonth(String predictionMonth) {
        return predictionRepository.findByPredictionMonth(predictionMonth).stream()
                .map(prediction -> {
                    Dealer dealer = dealerRepository.findById(prediction.getDealerId()).orElse(null);
                    Product product = productRepository.findById(prediction.getProductId()).orElse(null);
                    return mapToPredictionResponse(prediction, dealer, product);
                })
                .collect(Collectors.toList());
    }

    public List<PredictionResponse> getAllPredictions() {
        return predictionRepository.findAll().stream()
                .map(prediction -> {
                    Dealer dealer = dealerRepository.findById(prediction.getDealerId()).orElse(null);
                    Product product = productRepository.findById(prediction.getProductId()).orElse(null);
                    return mapToPredictionResponse(prediction, dealer, product);
                })
                .collect(Collectors.toList());
    }

    // Helper methods
    private Double calculateAverageDemand(List<Distribution> distributions) {
        if (distributions.isEmpty()) {
            return 0.0; // Default prediction if no historical data
        }

        Double totalQuantity = distributions.stream()
                .mapToDouble(Distribution::getQuantity)
                .sum();

        // Calculate average per month
        int months = 3; // We're looking at 3 months of data
        return totalQuantity / months;
    }

    private PredictionResponse mapToPredictionResponse(StockPrediction prediction, 
                                                       Dealer dealer, 
                                                       Product product) {
        return PredictionResponse.builder()
                .id(prediction.getId())
                .dealerId(prediction.getDealerId())
                .dealerName(dealer != null ? dealer.getShopName() : "Unknown")
                .productId(prediction.getProductId())
                .productName(product != null ? product.getProductName() : "Unknown")
                .predictedDemand(prediction.getPredictedDemand())
                .predictionMonth(prediction.getPredictionMonth())
                .algorithm(prediction.getAlgorithm())
                .generatedAt(prediction.getGeneratedAt())
                .build();
    }
}
