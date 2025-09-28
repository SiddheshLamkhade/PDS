package com.userservice.controller;
import org.springframework.web.bind.annotation.RestController;
import com.userservice.service.CitizenService;
import com.userservice.service.DealerService;
import com.userservice.dto.DealerRequest;
import com.userservice.dto.CitizenRequest;
import com.userservice.model.Dealer;
import com.userservice.model.Citizen;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final CitizenService citizenService;
    private final DealerService dealerService;

    public UserController(CitizenService citizenService, DealerService dealerService) {
        this.citizenService = citizenService;
        this.dealerService = dealerService;
    }

    // Dealer endpoints
    @PostMapping("/dealers")
    public ResponseEntity<Dealer> createDealer(@Valid @RequestBody DealerRequest req) {
        Dealer d = new Dealer();
        d.setDealerName(req.dealerName);
        d.setShopLocation(req.shopLocation);
        d.setContactInfo(req.contactInfo);
        return ResponseEntity.ok(dealerService.save(d));
    }

    @GetMapping("/dealers")
    public ResponseEntity<List<Dealer>> listDealers() {
        return ResponseEntity.ok(dealerService.findAll());
    }

    // Citizen endpoints
    @PostMapping("/citizens")
    public ResponseEntity<?> createCitizen(@Valid @RequestBody CitizenRequest req) {
        Dealer dealer = dealerService.findById(req.dealerId);
        if (dealer == null) return ResponseEntity.badRequest().body("Dealer not found");

        Citizen c = new Citizen();
        c.setName(req.name);
        c.setRationCardNo(req.rationCardNo);
        c.setFamilySize(req.familySize);
        c.setCategory(req.category);
        c.setDealer(dealer);

        return ResponseEntity.ok(citizenService.save(c));
    }

    @GetMapping("/citizens")
    public ResponseEntity<List<Citizen>> listCitizens() {
        return ResponseEntity.ok(citizenService.findAll());
    }

    @GetMapping("/citizens/{rationCardNo}")
    public ResponseEntity<?> getCitizenByRationCard(@PathVariable String rationCardNo) {
        return citizenService.findByRationCard(rationCardNo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // for Keycloak integration later:
    @GetMapping("/citizens/by-auth-id/{authUserId}")
    public ResponseEntity<?> getCitizenByAuthId(@PathVariable String authUserId) {
        return citizenService.findByAuthUserId(authUserId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //to get dealer by id
    @GetMapping("/dealers/{dealerId}")
    public ResponseEntity<?> getDealerById(@PathVariable Long dealerId) {
        Dealer dealer = dealerService.findById(dealerId);
        if (dealer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dealer);
    }
}

