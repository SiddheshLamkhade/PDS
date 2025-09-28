package com.adminservice.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.adminservice.config.UserService;
import com.adminservice.dto.CitizenProxyDto;

import feign.FeignException;

@Service
public class CitizenProxyService {

    private final UserService userService;

    public CitizenProxyService(UserService userService) {
        this.userService = userService;
    }

    public List<CitizenProxyDto> getAllCitizens() {
        try {
            List<CitizenProxyDto> citizens = userService.getCitizens();
            return citizens != null ? citizens : Collections.emptyList();
        } catch (FeignException.NotFound ignored) {
            return Collections.emptyList();
        }
    }

    public Optional<CitizenProxyDto> getCitizenByRationCard(String rationCardNo) {
        try {
            return Optional.ofNullable(userService.getCitizenByRationCard(rationCardNo));
        } catch (FeignException.NotFound ignored) {
            return Optional.empty();
        }
    }

    public Optional<CitizenProxyDto> getCitizenByAuthUserId(String authUserId) {
        try {
            return Optional.ofNullable(userService.getCitizenByAuthUserId(authUserId));
        } catch (FeignException.NotFound ignored) {
            return Optional.empty();
        }
    }
}
