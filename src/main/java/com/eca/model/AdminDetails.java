package com.eca.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminDetails {
    private String userName;
    private String email;
    private String contactNumber;
    private String password;
    private String deviceId;
}
