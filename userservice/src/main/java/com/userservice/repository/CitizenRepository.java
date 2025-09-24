package com.userservice.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.userservice.model.Citizen;

@Repository
public interface CitizenRepository extends JpaRepository<Citizen, Long> {
    Optional<Citizen> findByRationCardNo(String rationCardNo);
    Optional<Citizen> findByAuthUserId(String authUserId);
}
