package com.eca.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComplaintStatusUpdate {
    private Long complaintId;
    private ComplaintStatus complaintStatus;
}
