package com.eca.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceProviderDetails {
    private Long id;
    private String providerName;
    private String deviceId;
    private String phoneNo;
    private String emailId;
}
