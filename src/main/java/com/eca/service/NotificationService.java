package com.eca.service;

import com.eca.common.model.Notification;
import com.eca.entity.ComplaintEntity;
import com.eca.model.AdminDetails;
import com.eca.model.ServiceProviderDetails;
import com.eca.model.UserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@Async
@Service
@Slf4j
public class NotificationService {


    public static final String NOTIFICATION_TOPIC = "notification-topic";
    public static final String COMPLAINT_USER_REQUEST_NOTIFY = "COMPLAINT_USER_REQUEST_NOTIFY";
    public static final String COMPLAINT_ADMIN_REQUEST_NOTIFY = "COMPLAINT_ADMIN_REQUEST_NOTIFY";
    public static final String COMPLAINT_ADMIN_ASSIGN_NOTIFY = "COMPLAINT_ADMIN_ASSIGN_NOTIFY";
    public static final String COMPLAINT_RESIDENT_ASSIGN_NOTIFY = "COMPLAINT_USER_ASSIGN_NOTIFY";
    public static final String COMPLAINT_SP_ASSIGN_NOTIFY = "COMPLAINT_SP_ASSIGN_NOTIFY";

    @Autowired
    UserService userService;

    @Autowired
    ServiceProviderService serviceProviderService;
    @Autowired
    KafkaTemplate<String, Notification> kafkaTemplate;

    public void registerComplaintNotification(UserDetails userDetails, ComplaintEntity complaintEntity) {

        var adminDetails = userService.getAdminDetails(userDetails.getAdminId());
        var notificationContent = getNotificationContentForComplaintRegistration(userDetails, complaintEntity);

        Notification userNotification = Notification.builder()
                .emailId(userDetails.getEmail())
                .phoneNo(userDetails.getContactNumber())
                .deviceId(userDetails.getDeviceId())
                .category(COMPLAINT_USER_REQUEST_NOTIFY)
                .contents(notificationContent).build();

        Notification adminNotification = Notification.builder()
                .emailId(adminDetails.getEmail())
                .phoneNo(adminDetails.getContactNumber())
                .deviceId(adminDetails.getDeviceId())
                .category(COMPLAINT_ADMIN_REQUEST_NOTIFY)
                .contents(notificationContent).build();
                kafkaTemplate.send(NOTIFICATION_TOPIC, adminNotification);

        kafkaTemplate.send(NOTIFICATION_TOPIC, userNotification);
        log.info("Notification pushed successfully for user with userid = {}", userDetails.getUserId());

    }

    private HashMap<String, Object> getNotificationContentForComplaintRegistration(UserDetails userDetails, ComplaintEntity complaintEntity) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userName", userDetails.getUserName());
        map.put("flatNo", userDetails.getFlatNo());
//        complaintEntity.subscribe(complaintRecord -> {
            map.put("complaintId", complaintEntity.getId());
            map.put("category", complaintEntity.getCategory());
            map.put("categoryId", complaintEntity.getCategoryId());
            map.put("serviceName", complaintEntity.getServiceName());
            map.put("serviceId", complaintEntity.getServiceId());
//        });

        return map;
    }

    public void sendNotification(ComplaintEntity complaintEntity) {
        UserDetails userDetails = userService.getUserDetailsById(complaintEntity.getUserId());
        AdminDetails adminDetails = userService.getAdminDetails(complaintEntity.getAdminId());
        ServiceProviderDetails serviceProviderDetails=serviceProviderService.
                getServiceProviderDetailsById(complaintEntity.getServiceProviderId());

        var adminNotificationContents = getNotificationContentForAdmin(userDetails, complaintEntity);
        var residentNotificationContents = getNotificationContentForResident(complaintEntity, serviceProviderDetails);
        var serviceProviderNotificationContents = getNotificationContentForServiceProvider(userDetails, adminDetails, complaintEntity);


        Notification userNotification = Notification.builder()
                .emailId(userDetails.getEmail())
                .phoneNo(userDetails.getContactNumber())
                .deviceId(userDetails.getDeviceId())
                .category(COMPLAINT_RESIDENT_ASSIGN_NOTIFY)
                .contents(residentNotificationContents).build();
        Notification adminNotification = Notification.builder()
                .emailId(adminDetails.getEmail())
                .phoneNo(adminDetails.getContactNumber())
                .deviceId(adminDetails.getDeviceId())
                .category(COMPLAINT_ADMIN_ASSIGN_NOTIFY)
                .contents(adminNotificationContents).build();
        Notification serviceProviderNotification = Notification.builder()
                .emailId(serviceProviderDetails.getEmailId())
                .phoneNo(serviceProviderDetails.getPhoneNo())
                .deviceId(serviceProviderDetails.getDeviceId())
                .category(COMPLAINT_SP_ASSIGN_NOTIFY)
                .contents(serviceProviderNotificationContents).build();
        kafkaTemplate.send(NOTIFICATION_TOPIC, userNotification);
        kafkaTemplate.send(NOTIFICATION_TOPIC, adminNotification);
        kafkaTemplate.send(NOTIFICATION_TOPIC, serviceProviderNotification);
    }

    private HashMap<String, Object> getNotificationContentForServiceProvider(UserDetails userDetails, AdminDetails adminDetails, ComplaintEntity complaintEntity) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userName", userDetails.getUserName());
        map.put("flatNo", userDetails.getFlatNo());
        map.put("complaintId", complaintEntity.getId());
        map.put("adminEmail", adminDetails.getEmail());
        map.put("AdminContact", adminDetails.getContactNumber());
        return map;
    }

    private HashMap<String, Object> getNotificationContentForResident(ComplaintEntity complaintEntity, ServiceProviderDetails serviceProviderDetails) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("complaintId", complaintEntity.getId());
        map.put("serviceProviderName", serviceProviderDetails.getProviderName());
        map.put("serviceProviderEmail", serviceProviderDetails.getEmailId());
        map.put("serviceProviderContact", serviceProviderDetails.getPhoneNo());
        return map;
    }

    private HashMap<String, Object> getNotificationContentForAdmin(UserDetails userDetails, ComplaintEntity complaintEntity) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userName", userDetails.getUserName());
        map.put("flatNo", userDetails.getFlatNo());
        map.put("complaintId", complaintEntity.getId());
        map.put("serviceName", complaintEntity.getServiceName());
        map.put("serviceProviderId", complaintEntity.getServiceProviderId());
        return map;
    }
}
