package com.eca.service;

import com.eca.model.AdminDetails;
import com.eca.model.UserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final WebClientService webClientService;

    @Value("${getUserDetailsEndpoint}")
    String individualDetailsEndpoint;

    @Value(("${getAdminDetailsEndpoint}"))
    private String adminDetailsEndpoint;

    @Cacheable(value = "users", key = "#userId")
    public UserDetails getUserDetailsById(String userId){
        var individualDetailsURI = UriComponentsBuilder.fromUriString(individualDetailsEndpoint).path(userId).toUriString();
        log.info("Querying User Service for userid ={}", userId);
        return webClientService.get(individualDetailsURI, new HashMap<>(), UserDetails.class);
    }

    @Cacheable(value = "admin", key = "#adminId")
    public AdminDetails getAdminDetails(String adminId) {
        var adminDetailsURI = UriComponentsBuilder.fromUriString(adminDetailsEndpoint).path(adminId).toUriString();
        log.info("Fetching admin details for admin with adminId ={}", adminId);
        return webClientService.get(adminDetailsURI, new HashMap<>(), AdminDetails.class);
    }
}
