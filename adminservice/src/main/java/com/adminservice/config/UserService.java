package com.adminservice.config;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.adminservice.dto.CitizenProxyDto;
import com.adminservice.dto.DealerProxyDto;

@FeignClient(name = "userservice", path = "/api/users")
public interface UserService {

	@GetMapping("/dealers")
	List<DealerProxyDto> getDealers();

	@GetMapping("/dealers/{dealerId}")
	DealerProxyDto getDealerById(@PathVariable("dealerId") Long dealerId);

	@GetMapping("/citizens")
	List<CitizenProxyDto> getCitizens();

	@GetMapping("/citizens/{rationCardNo}")
	CitizenProxyDto getCitizenByRationCard(@PathVariable("rationCardNo") String rationCardNo);

	@GetMapping("/citizens/by-auth-id/{authUserId}")
	CitizenProxyDto getCitizenByAuthUserId(@PathVariable("authUserId") String authUserId);
}
