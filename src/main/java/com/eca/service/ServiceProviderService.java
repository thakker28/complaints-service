package com.eca.service;

import com.eca.model.ServiceProviderDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ServiceProviderService {

    @Autowired
    private RestTemplate restTemplate;
    @Value("${serviceProviderEndpoint}")
    String serviceProviderEndpoint;


    public ServiceProviderDetails getServiceProviderDetailsById(Long serviceProviderId){
        ResponseEntity<ServiceProviderDetails> serviceProviderDetails = restTemplate
                .exchange(serviceProviderEndpoint + serviceProviderId, HttpMethod.GET, null, ServiceProviderDetails.class);
        return serviceProviderDetails.getBody();
    }
}
