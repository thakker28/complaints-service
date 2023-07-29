package com.eca.entity;

import com.eca.model.ComplaintStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Mono;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="COMPLAINTS")
public class ComplaintEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private Long serviceId;

    private String serviceName;

    private Long serviceProviderId;

    private String userId;

    private String adminId;

    private LocalDateTime createdDate;

    private LocalDateTime resolvedDate;

    private ComplaintStatus status;

    private String description;
    private String category;
    private String categoryId;


}
