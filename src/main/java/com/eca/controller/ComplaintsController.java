package com.eca.controller;

import com.eca.entity.ComplaintEntity;
import com.eca.model.AssignComplaintRequest;
import com.eca.model.ComplaintRegisterRequest;
import com.eca.model.ComplaintStatus;
import com.eca.model.ComplaintStatusUpdate;
import com.eca.service.ComplaintService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
@Slf4j
public class ComplaintsController {

    private final ComplaintService complaintService;

    @PostMapping("/complaints")
    public ResponseEntity<ComplaintEntity> registerComplaint(@RequestBody ComplaintRegisterRequest complaintRegisterRequest){
        log.info("Registering a complaint for user with id = {}", complaintRegisterRequest.getUserId());
        return new ResponseEntity<>(complaintService.registerComplaint(complaintRegisterRequest), HttpStatus.CREATED);
    }

    @GetMapping("/complaints")
    public List<ComplaintEntity> getAllComplaints(@RequestParam(required = false) ComplaintStatus status){
        return complaintService.getAllComplaints(status);
    }

    @GetMapping("/complaints/serviceProvider/{serviceProviderId}")
    public List<ComplaintEntity> getComplaintsByServiceProviderId(@PathVariable Long serviceProviderId, @RequestParam(required = false) ComplaintStatus status){
        return complaintService.getComplaintsByServiceProviderId(serviceProviderId, status);
    }

    @GetMapping("/complaints/{id}")
    public ComplaintEntity getComplaintsById(@PathVariable Long id){
        return complaintService.getComplaintsById(id);
    }

    @PutMapping("/complaints/assignComplaint")
    public ComplaintEntity assignComplaint(@RequestBody AssignComplaintRequest assignComplaintRequest){
        return complaintService.assignComplaint(assignComplaintRequest);
    }

    @PutMapping("/complaints/status")
    public ComplaintEntity updateComplaintStatus(@RequestBody ComplaintStatusUpdate complaintStatusUpdate){
        return complaintService.updateComplaint(complaintStatusUpdate);
    }
}
