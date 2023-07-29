package com.eca.service;

import com.eca.entity.ComplaintEntity;
import com.eca.model.AssignComplaintRequest;
import com.eca.model.ComplaintRegisterRequest;
import com.eca.model.ComplaintStatus;
import com.eca.model.ComplaintStatusUpdate;

import java.util.List;

public interface ComplaintService {

    ComplaintEntity registerComplaint(ComplaintRegisterRequest complaintRegisterRequest);

    List<ComplaintEntity> getComplaintsByAdminId(Long adminId);

    ComplaintEntity getComplaintsById(Long id);

    ComplaintEntity assignComplaint(AssignComplaintRequest assignComplaintRequest);

    ComplaintEntity updateComplaint(ComplaintStatusUpdate complaintStatusUpdate);

    List<ComplaintEntity> getComplaintsByServiceProviderId(Long serviceProviderId, ComplaintStatus status);

    List<ComplaintEntity> getAllComplaints(ComplaintStatus status);
}
