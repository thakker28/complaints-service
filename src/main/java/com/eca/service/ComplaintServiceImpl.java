package com.eca.service;

import com.eca.entity.ComplaintEntity;
import com.eca.model.*;
import com.eca.repository.ComplaintRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ComplaintServiceImpl implements ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    UserService userService;

    @Autowired
    NotificationService notificationService;

    @Override
    public ComplaintEntity registerComplaint(ComplaintRegisterRequest complaintRegisterRequest) {
      log.info("Fetching user details for user id = {}", complaintRegisterRequest.getUserId());
      var userDetails = userService.getUserDetailsById(complaintRegisterRequest.getUserId());
      log.info("User details fetched = {}", userDetails);
      ComplaintEntity complaintEntity = ComplaintEntity.builder()
                .serviceId(complaintRegisterRequest.getServiceId())
                .serviceName(complaintRegisterRequest.getServiceName())
                .userId(complaintRegisterRequest.getUserId())
                .description(complaintRegisterRequest.getDescription())
                .createdDate(LocalDateTime.now())
                .status(ComplaintStatus.OPEN)
                .category(complaintRegisterRequest.getCategoryName())
                .categoryId(complaintRegisterRequest.getCategoryId())
                .build();
        ComplaintEntity complaintRecord= complaintRepository.save(complaintEntity);
        notificationService.registerComplaintNotification(userDetails,complaintRecord);
        return complaintRecord;
    }


    @Override
    public List<ComplaintEntity> getComplaintsByAdminId(Long adminId) {
        return complaintRepository.findByAdminId(adminId);
    }

    @Override
    public ComplaintEntity getComplaintsById(Long id) {
        return complaintRepository.findById(id).orElse(null);
    }

    @Override
    public ComplaintEntity assignComplaint(AssignComplaintRequest assignComplaintRequest) {
        ComplaintEntity complaintEntity=complaintRepository.findById(assignComplaintRequest.getComplaintId()).orElse(null);
        if(complaintEntity !=null){
            complaintEntity.setServiceProviderId(assignComplaintRequest.getServiceProviderId());
            complaintEntity.setStatus(ComplaintStatus.ASSIGNED);
            complaintEntity.setAdminId(assignComplaintRequest.getAdminId());
            complaintRepository.save(complaintEntity);
            notificationService.sendNotification(complaintEntity);
        }
        return complaintEntity;
    }

    @Override
    public ComplaintEntity updateComplaint(ComplaintStatusUpdate complaintStatusUpdate) {
        ComplaintEntity complaintEntity=complaintRepository.findById(complaintStatusUpdate.getComplaintId()).orElse(null);
        if(complaintEntity!=null){
            complaintEntity.setStatus(complaintStatusUpdate.getComplaintStatus());
            complaintEntity.setResolvedDate(LocalDateTime.now());
            complaintRepository.save(complaintEntity);
        }
        return complaintEntity;
    }

    @Override
    public List<ComplaintEntity> getComplaintsByServiceProviderId(Long serviceProviderId, ComplaintStatus status) {
        if(status!=null){
            return complaintRepository.findByServiceProviderIdAndStatus(serviceProviderId, status);
        }
        return complaintRepository.findByServiceProviderId(serviceProviderId);
    }

    @Override
    public List<ComplaintEntity> getAllComplaints(ComplaintStatus status) {
        if(status==null){
            return complaintRepository.findAll();
        }
        return complaintRepository.findByStatus(status);
    }
}
