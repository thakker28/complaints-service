package com.eca.repository;

import com.eca.entity.ComplaintEntity;
import com.eca.model.ComplaintStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplaintRepository extends JpaRepository<ComplaintEntity,Long> {

    List<ComplaintEntity> findByAdminId(Long adminId);
    List<ComplaintEntity> findByServiceProviderId(Long serviceProviderId);

    List<ComplaintEntity> findByServiceProviderIdAndStatus(Long serviceProviderId, ComplaintStatus status);

    List<ComplaintEntity> findByStatus(ComplaintStatus status);
}
