package com.eca.service;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@ContextConfiguration(classes = {ServiceProviderService.class})
@ExtendWith(SpringExtension.class)
class ServiceProviderServiceTest {
    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private ServiceProviderService serviceProviderService;

    /**
     * Method under test: {@link ServiceProviderService#getServiceProviderDetailsById(Long)}
     */
    @Test
    void testGetServiceProviderDetailsById() throws RestClientException {
        when(restTemplate.exchange((String) any(), (HttpMethod) any(), (HttpEntity<Object>) any(), (Class<Object>) any(),
                (Object[]) any())).thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        assertNull(serviceProviderService.getServiceProviderDetailsById(1L));
        verify(restTemplate).exchange((String) any(), (HttpMethod) any(), (HttpEntity<Object>) any(), (Class<Object>) any(),
                (Object[]) any());
    }

    /**
     * Method under test: {@link ServiceProviderService#getServiceProviderDetailsById(Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetServiceProviderDetailsById2() throws RestClientException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "org.springframework.http.ResponseEntity.getBody()" because "serviceProviderDetails" is null
        //       at com.eca.service.ServiceProviderService.getServiceProviderDetailsById(ServiceProviderService.java:23)
        //   See https://diff.blue/R013 to resolve this issue.

        when(restTemplate.exchange((String) any(), (HttpMethod) any(), (HttpEntity<Object>) any(), (Class<Object>) any(),
                (Object[]) any())).thenReturn(null);
        serviceProviderService.getServiceProviderDetailsById(1L);
    }
}

