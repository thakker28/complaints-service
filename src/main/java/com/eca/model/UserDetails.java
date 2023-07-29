package com.eca.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails implements Serializable {
    @JsonAlias("individualId")
    private String userId;

    private String userName;
    private String flatNo;
    private String deviceId;
    private String contactNumber;
    private String email;
    private String adminId;
}
