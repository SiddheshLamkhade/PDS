package com.adminservice.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.adminservice.config.UserService;
import com.adminservice.dto.DealerProxyDto;

import feign.FeignException;

@Service
public class DealerProxyService {

    private final UserService userService;

    public DealerProxyService(UserService userService) {
        this.userService = userService;
    }

    public List<DealerProxyDto> getAllDealers() {
        try {
            List<DealerProxyDto> dealers = userService.getDealers();
            return dealers != null ? dealers : Collections.emptyList();
        } catch (FeignException.NotFound ignored) {
            return Collections.emptyList();
        }
    }

    public Optional<DealerProxyDto> getDealerById(Long dealerId) {
        try {
            return Optional.ofNullable(userService.getDealerById(dealerId));
        } catch (FeignException.NotFound ignored) {
            return Optional.empty();
        }
    }
}
