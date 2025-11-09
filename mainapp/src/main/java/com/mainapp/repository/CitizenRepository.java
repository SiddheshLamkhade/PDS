package com.mainapp.repository;

import com.mainapp.model.Citizen;
import com.mainapp.model.Citizen.CitizenCategory;
import com.mainapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CitizenRepository extends JpaRepository<Citizen, Long> {

    Optional<Citizen> findByRationCardNumber(String rationCardNumber);

    Optional<Citizen> findByUser(User user);
    
    Optional<Citizen> findByUserId(Long userId);

    List<Citizen> findByAssignedDealerId(Long dealerId);

    Boolean existsByRationCardNumber(String rationCardNumber);

    List<Citizen> findByCategory(CitizenCategory category);
}
