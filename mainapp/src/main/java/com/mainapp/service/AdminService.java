package com.mainapp.service;

import com.mainapp.dto.DistributionResponse;
import com.mainapp.dto.InventoryResponse;
import com.mainapp.model.Citizen;
import com.mainapp.repository.CitizenRepository;
import com.mainapp.repository.DealerRepository;
import com.mainapp.repository.DistributionRepository;
import com.mainapp.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final DistributionService distributionService;
    private final InventoryService inventoryService;
    private final CitizenRepository citizenRepository;
    private final DealerRepository dealerRepository;
    private final ProductRepository productRepository;
    private final DistributionRepository distributionRepository;

    // ================== DASHBOARD STATISTICS ==================

    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        
        stats.put("totalCitizens", citizenRepository.count());
        stats.put("totalDealers", dealerRepository.count());
        stats.put("totalProducts", productRepository.count());
        stats.put("totalDistributions", distributionRepository.count());
        stats.put("activeDealers", dealerRepository.findByActive(true).size());
        stats.put("activeProducts", productRepository.findByActive(true).size());
        
        return stats;
    }

    // ================== DEALER REPORTS ==================

    public Map<String, Object> getDealerReport(Long dealerId) {
        Map<String, Object> report = new HashMap<>();

        // Basic dealer info
        dealerRepository.findById(dealerId)
                .ifPresent(dealer -> {
                    report.put("dealerId", dealer.getId());
                    report.put("shopName", dealer.getShopName());
                    report.put("ownerName", dealer.getUser() != null ? dealer.getUser().getFullName() : "Unknown");
                    report.put("region", dealer.getRegion());
                });

        // Distribution statistics
        List<DistributionResponse> distributions = distributionService.getDistributionsByDealer(dealerId);
        report.put("totalDistributions", distributions.size());
        
        Double totalAmount = distributions.stream()
                .mapToDouble(DistributionResponse::getTotalAmount)
                .sum();
        report.put("totalRevenue", totalAmount);

        // Inventory status
        List<InventoryResponse> inventory = inventoryService.getInventoryByDealer(dealerId);
        report.put("inventoryItems", inventory.size());
        
        Double totalStock = inventory.stream()
                .mapToDouble(InventoryResponse::getCurrentStock)
                .sum();
        report.put("totalCurrentStock", totalStock);

        // Citizens linked to dealer
        Long citizenCount = citizenRepository.findByAssignedDealerId(dealerId).stream().count();
        report.put("linkedCitizens", citizenCount);

        return report;
    }

    public List<DistributionResponse> getDealerDistributions(Long dealerId) {
        return distributionService.getDistributionsByDealer(dealerId);
    }

    // ================== PRODUCT REPORTS ==================

    public Map<String, Object> getProductReport(Long productId) {
        Map<String, Object> report = new HashMap<>();

        // Basic product info
        productRepository.findById(productId)
                .ifPresent(product -> {
                    report.put("productId", product.getId());
                    report.put("productName", product.getProductName());
                    report.put("unit", product.getUnit());
                    report.put("pricePerUnit", product.getPricePerUnit());
                    report.put("category", product.getCategory());
                });

        // Distribution statistics
        List<DistributionResponse> distributions = distributionRepository.findByProductId(productId)
                .stream()
                .map(dist -> {
                    return DistributionResponse.builder()
                            .id(dist.getId())
                            .quantity(dist.getQuantity())
                            .totalAmount(dist.getTotalAmount())
                            .distributionDate(dist.getDistributionDate())
                            .build();
                })
                .toList();

        report.put("totalDistributions", distributions.size());
        
        Double totalQuantityDistributed = distributions.stream()
                .mapToDouble(DistributionResponse::getQuantity)
                .sum();
        report.put("totalQuantityDistributed", totalQuantityDistributed);

        return report;
    }

    // ================== CITIZEN REPORTS ==================

    public Map<String, Object> getCitizenReport(String rationCardNumber) {
        Map<String, Object> report = new HashMap<>();

        // Get citizen info
        citizenRepository.findByRationCardNumber(rationCardNumber)
                .ifPresent(citizen -> {
                    report.put("citizenId", citizen.getId());
                    report.put("name", citizen.getUser() != null ? citizen.getUser().getFullName() : "Unknown");
                    report.put("rationCardNumber", citizen.getRationCardNumber());
                    report.put("familySize", citizen.getFamilySize());
                    report.put("category", citizen.getCategory());
                });

        // Distribution history
        List<DistributionResponse> distributions = distributionService.getDistributionsByRationCard(rationCardNumber);
        report.put("totalDistributions", distributions.size());
        
        Double totalAmountPaid = distributions.stream()
                .mapToDouble(DistributionResponse::getTotalAmount)
                .sum();
        report.put("totalAmountPaid", totalAmountPaid);

        report.put("distributionHistory", distributions);

        return report;
    }

    // ================== LOW STOCK ALERTS ==================

    public List<InventoryResponse> getLowStockAlerts() {
        Double threshold = 50.0; // Default threshold
        return inventoryService.getLowStockAlerts(threshold);
    }

    public List<InventoryResponse> getLowStockAlertsByDealer(Long dealerId) {
        Double threshold = 50.0; // Default threshold
        return inventoryService.getLowStockByDealer(dealerId, threshold);
    }

    // ================== DATE RANGE REPORTS ==================

    public List<DistributionResponse> getDistributionsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return distributionService.getDistributionsByDateRange(startDate, endDate);
    }

    public Map<String, Object> getMonthlyReport(int year, int month) {
        Map<String, Object> report = new HashMap<>();

        LocalDateTime startDate = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime endDate = startDate.plusMonths(1).minusSeconds(1);

        List<DistributionResponse> distributions = distributionService.getDistributionsByDateRange(startDate, endDate);
        
        report.put("month", year + "-" + String.format("%02d", month));
        report.put("totalDistributions", distributions.size());
        
        Double totalRevenue = distributions.stream()
                .mapToDouble(DistributionResponse::getTotalAmount)
                .sum();
        report.put("totalRevenue", totalRevenue);

        report.put("distributions", distributions);

        return report;
    }

    // ================== CATEGORY WISE REPORTS ==================

    public Map<String, Object> getCategoryWiseReport(String category) {
        Map<String, Object> report = new HashMap<>();

        List<Map<String, Object>> citizens = citizenRepository.findByCategory(Citizen.CitizenCategory.valueOf(category.toUpperCase()))
                .stream()
                .map(citizen -> {
                    Map<String, Object> citizenMap = new HashMap<>();
                    citizenMap.put("id", citizen.getId());
                    citizenMap.put("name", citizen.getUser() != null ? citizen.getUser().getFullName() : "Unknown");
                    citizenMap.put("rationCardNumber", citizen.getRationCardNumber());
                    citizenMap.put("familySize", citizen.getFamilySize());
                    return citizenMap;
                })
                .toList();

        report.put("category", category.toUpperCase());
        report.put("totalCitizens", citizens.size());
        report.put("citizens", citizens);

        return report;
    }
}
