package com.userservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.userservice.model.Dealer;
import com.userservice.repository.DealerRepository;

@Service
public class DealerService {
    private final DealerRepository repo;
    public DealerService(DealerRepository repo) { this.repo = repo; }
    public Dealer save(Dealer d) { return repo.save(d); }
    public List<Dealer> findAll() { return repo.findAll(); }
    public Dealer findById(Long id) { return repo.findById(id).orElse(null); }
}