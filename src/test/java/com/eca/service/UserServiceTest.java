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

@ContextConfiguration(classes = {UserService.class})
@ExtendWith(SpringExtension.class)
class UserServiceTest {
    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private WebClientService webClientService;

    @Autowired
    private UserService userService;

    /**
     * Method under test: {@link UserService#getUserDetailsById(String)}
     */
    @Test
    void testGetUserDetailsById() throws RestClientException {
        when(restTemplate.exchange((String) any(), (HttpMethod) any(), (HttpEntity<Object>) any(), (Class<Object>) any(),
                (Object[]) any())).thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        assertNull(userService.getUserDetailsById("42"));
        verify(webClientService).get(any(), any(), any());
    }

}

