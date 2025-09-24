package com.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.userservice.model.Dealer;

@Repository
public interface DealerRepository extends JpaRepository<Dealer, Long> { }
