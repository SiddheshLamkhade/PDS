package com.mainapp.repository;

import com.mainapp.model.Dealer;
import com.mainapp.model.DealerStatus;
import com.mainapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DealerRepository extends JpaRepository<Dealer, Long> {

    Optional<Dealer> findByShopLicense(String shopLicense);
    
    Optional<Dealer> findByUser(User user);
    
    Optional<Dealer> findByUserId(Long userId);

    Boolean existsByShopLicense(String shopLicense);

    List<Dealer> findByRegion(String region);

    List<Dealer> findByStatus(DealerStatus status);

    List<Dealer> findByActive(Boolean active);
}
