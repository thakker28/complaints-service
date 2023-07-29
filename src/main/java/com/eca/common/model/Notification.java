package com.eca.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {
    private String emailId;
    private String phoneNo;
    private String deviceId;
    private String category;
    private HashMap<String,Object> contents;

}
