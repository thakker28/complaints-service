package com.eca.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssignComplaintRequest {
    private Long complaintId;
    private Long serviceProviderId;
    private String serviceProviderName;
    private String adminId;
}
