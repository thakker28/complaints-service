package com.eca.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplaintRegisterRequest implements Serializable {

    private Long serviceId;
    private String categoryName;
    private String serviceName;
    private String categoryId;
    private String userId;
    private String description;
}
