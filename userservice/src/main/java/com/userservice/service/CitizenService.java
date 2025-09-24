package com.userservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.userservice.model.Citizen;
import com.userservice.repository.CitizenRepository;

@Service
public class CitizenService {
    private final CitizenRepository repo;
    public CitizenService(CitizenRepository repo) { this.repo = repo; }

    public Citizen save(Citizen c) { return repo.save(c); }
    public Optional<Citizen> findByRationCard(String rc) { return repo.findByRationCardNo(rc); }
    public Optional<Citizen> findByAuthUserId(String authUserId) { return repo.findByAuthUserId(authUserId); }
    public List<Citizen> findAll() { return repo.findAll(); }
}