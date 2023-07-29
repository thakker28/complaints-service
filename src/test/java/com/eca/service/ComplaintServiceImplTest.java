package com.eca.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.eca.entity.ComplaintEntity;
import com.eca.model.AssignComplaintRequest;
import com.eca.model.ComplaintRegisterRequest;
import com.eca.model.ComplaintStatus;
import com.eca.model.ComplaintStatusUpdate;
import com.eca.model.UserDetails;
import com.eca.repository.ComplaintRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ComplaintServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ComplaintServiceImplTest {
    @MockBean
    private ComplaintRepository complaintRepository;

    @Autowired
    private ComplaintServiceImpl complaintServiceImpl;

    @MockBean
    private NotificationService notificationService;

    @MockBean
    private UserService userService;

    /**
     * Method under test: {@link ComplaintServiceImpl#registerComplaint(ComplaintRegisterRequest)}
     */
    @Test
    void testRegisterComplaint() {
        ComplaintEntity complaintEntity = new ComplaintEntity();
        complaintEntity.setAdminId("42");
        complaintEntity.setCategory("Category");
        complaintEntity.setCategoryId("42");
        complaintEntity.setCreatedDate(LocalDateTime.of(1, 1, 1, 1, 1));
        complaintEntity.setDescription("The characteristics of someone or something");
        complaintEntity.setId(1L);
        complaintEntity.setResolvedDate(LocalDateTime.of(1, 1, 1, 1, 1));
        complaintEntity.setServiceId(1L);
        complaintEntity.setServiceName("Service Name");
        complaintEntity.setServiceProviderId(1L);
        complaintEntity.setStatus(ComplaintStatus.OPEN);
        complaintEntity.setUserId("42");
        when(complaintRepository.save((ComplaintEntity) any())).thenReturn(complaintEntity);
        doNothing().when(notificationService).registerComplaintNotification((UserDetails) any(),  any());
        when(userService.getUserDetailsById((String) any())).thenReturn(new UserDetails());
        assertSame(complaintEntity, complaintServiceImpl.registerComplaint(new ComplaintRegisterRequest()));
        verify(complaintRepository).save((ComplaintEntity) any());
        verify(notificationService).registerComplaintNotification((UserDetails) any(),  any());
        verify(userService).getUserDetailsById((String) any());
    }

    /**
     * Method under test: {@link ComplaintServiceImpl#getComplaintsByAdminId(Long)}
     */
    @Test
    void testGetComplaintsByAdminId() {
        ArrayList<ComplaintEntity> complaintEntityList = new ArrayList<>();
        when(complaintRepository.findByAdminId((Long) any())).thenReturn(complaintEntityList);
        List<ComplaintEntity> actualComplaintsByAdminId = complaintServiceImpl.getComplaintsByAdminId(1L);
        assertSame(complaintEntityList, actualComplaintsByAdminId);
        assertTrue(actualComplaintsByAdminId.isEmpty());
        verify(complaintRepository).findByAdminId((Long) any());
    }

    /**
     * Method under test: {@link ComplaintServiceImpl#getComplaintsById(Long)}
     */
    @Test
    void testGetComplaintsById() {
        ComplaintEntity complaintEntity = new ComplaintEntity();
        complaintEntity.setAdminId("42");
        complaintEntity.setCategory("Category");
        complaintEntity.setCategoryId("42");
        complaintEntity.setCreatedDate(LocalDateTime.of(1, 1, 1, 1, 1));
        complaintEntity.setDescription("The characteristics of someone or something");
        complaintEntity.setId(1L);
        complaintEntity.setResolvedDate(LocalDateTime.of(1, 1, 1, 1, 1));
        complaintEntity.setServiceId(1L);
        complaintEntity.setServiceName("Service Name");
        complaintEntity.setServiceProviderId(1L);
        complaintEntity.setStatus(ComplaintStatus.OPEN);
        complaintEntity.setUserId("42");
        Optional<ComplaintEntity> ofResult = Optional.of(complaintEntity);
        when(complaintRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(complaintEntity, complaintServiceImpl.getComplaintsById(1L));
        verify(complaintRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link ComplaintServiceImpl#assignComplaint(AssignComplaintRequest)}
     */
    @Test
    void testAssignComplaint() {
        ComplaintEntity complaintEntity = new ComplaintEntity();
        complaintEntity.setAdminId("42");
        complaintEntity.setCategory("Category");
        complaintEntity.setCategoryId("42");
        complaintEntity.setCreatedDate(LocalDateTime.of(1, 1, 1, 1, 1));
        complaintEntity.setDescription("The characteristics of someone or something");
        complaintEntity.setId(1L);
        complaintEntity.setResolvedDate(LocalDateTime.of(1, 1, 1, 1, 1));
        complaintEntity.setServiceId(1L);
        complaintEntity.setServiceName("Service Name");
        complaintEntity.setServiceProviderId(1L);
        complaintEntity.setStatus(ComplaintStatus.OPEN);
        complaintEntity.setUserId("42");
        Optional<ComplaintEntity> ofResult = Optional.of(complaintEntity);

        ComplaintEntity complaintEntity1 = new ComplaintEntity();
        complaintEntity1.setAdminId("42");
        complaintEntity1.setCategory("Category");
        complaintEntity1.setCategoryId("42");
        complaintEntity1.setCreatedDate(LocalDateTime.of(1, 1, 1, 1, 1));
        complaintEntity1.setDescription("The characteristics of someone or something");
        complaintEntity1.setId(1L);
        complaintEntity1.setResolvedDate(LocalDateTime.of(1, 1, 1, 1, 1));
        complaintEntity1.setServiceId(1L);
        complaintEntity1.setServiceName("Service Name");
        complaintEntity1.setServiceProviderId(1L);
        complaintEntity1.setStatus(ComplaintStatus.OPEN);
        complaintEntity1.setUserId("42");
        when(complaintRepository.save((ComplaintEntity) any())).thenReturn(complaintEntity1);
        when(complaintRepository.findById((Long) any())).thenReturn(ofResult);
        doNothing().when(notificationService).sendNotification((ComplaintEntity) any());
        ComplaintEntity actualAssignComplaintResult = complaintServiceImpl.assignComplaint(new AssignComplaintRequest());
        assertSame(complaintEntity, actualAssignComplaintResult);
        assertEquals(ComplaintStatus.ASSIGNED, actualAssignComplaintResult.getStatus());
        assertNull(actualAssignComplaintResult.getServiceProviderId());
        verify(complaintRepository).save((ComplaintEntity) any());
        verify(complaintRepository).findById((Long) any());
        verify(notificationService).sendNotification((ComplaintEntity) any());
    }

    /**
     * Method under test: {@link ComplaintServiceImpl#assignComplaint(AssignComplaintRequest)}
     */
    @Test
    void testAssignComplaint2() {
        ComplaintEntity complaintEntity = mock(ComplaintEntity.class);
        doNothing().when(complaintEntity).setAdminId((String) any());
        doNothing().when(complaintEntity).setCategory((String) any());
        doNothing().when(complaintEntity).setCategoryId((String) any());
        doNothing().when(complaintEntity).setCreatedDate((LocalDateTime) any());
        doNothing().when(complaintEntity).setDescription((String) any());
        doNothing().when(complaintEntity).setId((Long) any());
        doNothing().when(complaintEntity).setResolvedDate((LocalDateTime) any());
        doNothing().when(complaintEntity).setServiceId((Long) any());
        doNothing().when(complaintEntity).setServiceName((String) any());
        doNothing().when(complaintEntity).setServiceProviderId((Long) any());
        doNothing().when(complaintEntity).setStatus((ComplaintStatus) any());
        doNothing().when(complaintEntity).setUserId((String) any());
        complaintEntity.setAdminId("42");
        complaintEntity.setCategory("Category");
        complaintEntity.setCategoryId("42");
        complaintEntity.setCreatedDate(LocalDateTime.of(1, 1, 1, 1, 1));
        complaintEntity.setDescription("The characteristics of someone or something");
        complaintEntity.setId(1L);
        complaintEntity.setResolvedDate(LocalDateTime.of(1, 1, 1, 1, 1));
        complaintEntity.setServiceId(1L);
        complaintEntity.setServiceName("Service Name");
        complaintEntity.setServiceProviderId(1L);
        complaintEntity.setStatus(ComplaintStatus.OPEN);
        complaintEntity.setUserId("42");
        Optional<ComplaintEntity> ofResult = Optional.of(complaintEntity);

        ComplaintEntity complaintEntity1 = new ComplaintEntity();
        complaintEntity1.setAdminId("42");
        complaintEntity1.setCategory("Category");
        complaintEntity1.setCategoryId("42");
        complaintEntity1.setCreatedDate(LocalDateTime.of(1, 1, 1, 1, 1));
        complaintEntity1.setDescription("The characteristics of someone or something");
        complaintEntity1.setId(1L);
        complaintEntity1.setResolvedDate(LocalDateTime.of(1, 1, 1, 1, 1));
        complaintEntity1.setServiceId(1L);
        complaintEntity1.setServiceName("Service Name");
        complaintEntity1.setServiceProviderId(1L);
        complaintEntity1.setStatus(ComplaintStatus.OPEN);
        complaintEntity1.setUserId("42");
        when(complaintRepository.save((ComplaintEntity) any())).thenReturn(complaintEntity1);
        when(complaintRepository.findById((Long) any())).thenReturn(ofResult);
        doNothing().when(notificationService).sendNotification((ComplaintEntity) any());
        complaintServiceImpl.assignComplaint(new AssignComplaintRequest());
        verify(complaintRepository).save((ComplaintEntity) any());
        verify(complaintRepository).findById((Long) any());
        verify(complaintEntity).setCategory((String) any());
        verify(complaintEntity).setCategoryId((String) any());
        verify(complaintEntity).setCreatedDate((LocalDateTime) any());
        verify(complaintEntity).setDescription((String) any());
        verify(complaintEntity).setId((Long) any());
        verify(complaintEntity).setResolvedDate((LocalDateTime) any());
        verify(complaintEntity).setServiceId((Long) any());
        verify(complaintEntity).setServiceName((String) any());
        verify(complaintEntity, atLeast(1)).setServiceProviderId((Long) any());
        verify(complaintEntity, atLeast(1)).setStatus((ComplaintStatus) any());
        verify(complaintEntity).setUserId((String) any());
        verify(notificationService).sendNotification((ComplaintEntity) any());
    }

    /**
     * Method under test: {@link ComplaintServiceImpl#updateComplaint(ComplaintStatusUpdate)}
     */
    @Test
    void testUpdateComplaint() {
        ComplaintEntity complaintEntity = new ComplaintEntity();
        complaintEntity.setAdminId("42");
        complaintEntity.setCategory("Category");
        complaintEntity.setCategoryId("42");
        complaintEntity.setCreatedDate(LocalDateTime.of(1, 1, 1, 1, 1));
        complaintEntity.setDescription("The characteristics of someone or something");
        complaintEntity.setId(1L);
        complaintEntity.setResolvedDate(LocalDateTime.of(1, 1, 1, 1, 1));
        complaintEntity.setServiceId(1L);
        complaintEntity.setServiceName("Service Name");
        complaintEntity.setServiceProviderId(1L);
        complaintEntity.setStatus(ComplaintStatus.OPEN);
        complaintEntity.setUserId("42");
        Optional<ComplaintEntity> ofResult = Optional.of(complaintEntity);

        ComplaintEntity complaintEntity1 = new ComplaintEntity();
        complaintEntity1.setAdminId("42");
        complaintEntity1.setCategory("Category");
        complaintEntity1.setCategoryId("42");
        complaintEntity1.setCreatedDate(LocalDateTime.of(1, 1, 1, 1, 1));
        complaintEntity1.setDescription("The characteristics of someone or something");
        complaintEntity1.setId(1L);
        complaintEntity1.setResolvedDate(LocalDateTime.of(1, 1, 1, 1, 1));
        complaintEntity1.setServiceId(1L);
        complaintEntity1.setServiceName("Service Name");
        complaintEntity1.setServiceProviderId(1L);
        complaintEntity1.setStatus(ComplaintStatus.OPEN);
        complaintEntity1.setUserId("42");
        when(complaintRepository.save((ComplaintEntity) any())).thenReturn(complaintEntity1);
        when(complaintRepository.findById((Long) any())).thenReturn(ofResult);
        ComplaintEntity actualUpdateComplaintResult = complaintServiceImpl.updateComplaint(new ComplaintStatusUpdate());
        assertSame(complaintEntity, actualUpdateComplaintResult);
        assertNull(actualUpdateComplaintResult.getStatus());
        verify(complaintRepository).save((ComplaintEntity) any());
        verify(complaintRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link ComplaintServiceImpl#updateComplaint(ComplaintStatusUpdate)}
     */
    @Test
    void testUpdateComplaint2() {
        ComplaintEntity complaintEntity = mock(ComplaintEntity.class);
        doNothing().when(complaintEntity).setAdminId((String) any());
        doNothing().when(complaintEntity).setCategory((String) any());
        doNothing().when(complaintEntity).setCategoryId((String) any());
        doNothing().when(complaintEntity).setCreatedDate((LocalDateTime) any());
        doNothing().when(complaintEntity).setDescription((String) any());
        doNothing().when(complaintEntity).setId((Long) any());
        doNothing().when(complaintEntity).setResolvedDate((LocalDateTime) any());
        doNothing().when(complaintEntity).setServiceId((Long) any());
        doNothing().when(complaintEntity).setServiceName((String) any());
        doNothing().when(complaintEntity).setServiceProviderId((Long) any());
        doNothing().when(complaintEntity).setStatus((ComplaintStatus) any());
        doNothing().when(complaintEntity).setUserId((String) any());
        complaintEntity.setAdminId("42");
        complaintEntity.setCategory("Category");
        complaintEntity.setCategoryId("42");
        complaintEntity.setCreatedDate(LocalDateTime.of(1, 1, 1, 1, 1));
        complaintEntity.setDescription("The characteristics of someone or something");
        complaintEntity.setId(1L);
        complaintEntity.setResolvedDate(LocalDateTime.of(1, 1, 1, 1, 1));
        complaintEntity.setServiceId(1L);
        complaintEntity.setServiceName("Service Name");
        complaintEntity.setServiceProviderId(1L);
        complaintEntity.setStatus(ComplaintStatus.OPEN);
        complaintEntity.setUserId("42");
        Optional<ComplaintEntity> ofResult = Optional.of(complaintEntity);

        ComplaintEntity complaintEntity1 = new ComplaintEntity();
        complaintEntity1.setAdminId("42");
        complaintEntity1.setCategory("Category");
        complaintEntity1.setCategoryId("42");
        complaintEntity1.setCreatedDate(LocalDateTime.of(1, 1, 1, 1, 1));
        complaintEntity1.setDescription("The characteristics of someone or something");
        complaintEntity1.setId(1L);
        complaintEntity1.setResolvedDate(LocalDateTime.of(1, 1, 1, 1, 1));
        complaintEntity1.setServiceId(1L);
        complaintEntity1.setServiceName("Service Name");
        complaintEntity1.setServiceProviderId(1L);
        complaintEntity1.setStatus(ComplaintStatus.OPEN);
        complaintEntity1.setUserId("42");
        when(complaintRepository.save((ComplaintEntity) any())).thenReturn(complaintEntity1);
        when(complaintRepository.findById((Long) any())).thenReturn(ofResult);
        complaintServiceImpl.updateComplaint(new ComplaintStatusUpdate());
        verify(complaintRepository).save((ComplaintEntity) any());
        verify(complaintRepository).findById((Long) any());
        verify(complaintEntity).setAdminId((String) any());
        verify(complaintEntity).setCategory((String) any());
        verify(complaintEntity).setCategoryId((String) any());
        verify(complaintEntity).setCreatedDate((LocalDateTime) any());
        verify(complaintEntity).setDescription((String) any());
        verify(complaintEntity).setId((Long) any());
        verify(complaintEntity, atLeast(1)).setResolvedDate((LocalDateTime) any());
        verify(complaintEntity).setServiceId((Long) any());
        verify(complaintEntity).setServiceName((String) any());
        verify(complaintEntity).setServiceProviderId((Long) any());
        verify(complaintEntity, atLeast(1)).setStatus((ComplaintStatus) any());
        verify(complaintEntity).setUserId((String) any());
    }

    /**
     * Method under test: {@link ComplaintServiceImpl#getComplaintsByServiceProviderId(Long, ComplaintStatus)}
     */
    @Test
    void testGetComplaintsByServiceProviderId() {
        ArrayList<ComplaintEntity> complaintEntityList = new ArrayList<>();
        when(complaintRepository.findByServiceProviderIdAndStatus((Long) any(), (ComplaintStatus) any()))
                .thenReturn(complaintEntityList);
        List<ComplaintEntity> actualComplaintsByServiceProviderId = complaintServiceImpl
                .getComplaintsByServiceProviderId(1L, ComplaintStatus.OPEN);
        assertSame(complaintEntityList, actualComplaintsByServiceProviderId);
        assertTrue(actualComplaintsByServiceProviderId.isEmpty());
        verify(complaintRepository).findByServiceProviderIdAndStatus((Long) any(), (ComplaintStatus) any());
    }

    /**
     * Method under test: {@link ComplaintServiceImpl#getComplaintsByServiceProviderId(Long, ComplaintStatus)}
     */
    @Test
    void testGetComplaintsByServiceProviderId2() {
        ArrayList<ComplaintEntity> complaintEntityList = new ArrayList<>();
        when(complaintRepository.findByServiceProviderId((Long) any())).thenReturn(complaintEntityList);
        when(complaintRepository.findByServiceProviderIdAndStatus((Long) any(), (ComplaintStatus) any()))
                .thenReturn(new ArrayList<>());
        List<ComplaintEntity> actualComplaintsByServiceProviderId = complaintServiceImpl
                .getComplaintsByServiceProviderId(1L, null);
        assertSame(complaintEntityList, actualComplaintsByServiceProviderId);
        assertTrue(actualComplaintsByServiceProviderId.isEmpty());
        verify(complaintRepository).findByServiceProviderId((Long) any());
    }

    /**
     * Method under test: {@link ComplaintServiceImpl#getComplaintsByServiceProviderId(Long, ComplaintStatus)}
     */
    @Test
    void testGetComplaintsByServiceProviderId3() {
        when(complaintRepository.findByServiceProviderId((Long) any())).thenReturn(new ArrayList<>());
        ArrayList<ComplaintEntity> complaintEntityList = new ArrayList<>();
        when(complaintRepository.findByServiceProviderIdAndStatus((Long) any(), (ComplaintStatus) any()))
                .thenReturn(complaintEntityList);
        List<ComplaintEntity> actualComplaintsByServiceProviderId = complaintServiceImpl
                .getComplaintsByServiceProviderId(1L, ComplaintStatus.ASSIGNED);
        assertSame(complaintEntityList, actualComplaintsByServiceProviderId);
        assertTrue(actualComplaintsByServiceProviderId.isEmpty());
        verify(complaintRepository).findByServiceProviderIdAndStatus((Long) any(), (ComplaintStatus) any());
    }

    /**
     * Method under test: {@link ComplaintServiceImpl#getComplaintsByServiceProviderId(Long, ComplaintStatus)}
     */
    @Test
    void testGetComplaintsByServiceProviderId4() {
        when(complaintRepository.findByServiceProviderId((Long) any())).thenReturn(new ArrayList<>());
        ArrayList<ComplaintEntity> complaintEntityList = new ArrayList<>();
        when(complaintRepository.findByServiceProviderIdAndStatus((Long) any(), (ComplaintStatus) any()))
                .thenReturn(complaintEntityList);
        List<ComplaintEntity> actualComplaintsByServiceProviderId = complaintServiceImpl
                .getComplaintsByServiceProviderId(1L, ComplaintStatus.IN_PROGRESS);
        assertSame(complaintEntityList, actualComplaintsByServiceProviderId);
        assertTrue(actualComplaintsByServiceProviderId.isEmpty());
        verify(complaintRepository).findByServiceProviderIdAndStatus((Long) any(), (ComplaintStatus) any());
    }

    /**
     * Method under test: {@link ComplaintServiceImpl#getComplaintsByServiceProviderId(Long, ComplaintStatus)}
     */
    @Test
    void testGetComplaintsByServiceProviderId5() {
        when(complaintRepository.findByServiceProviderId((Long) any())).thenReturn(new ArrayList<>());
        ArrayList<ComplaintEntity> complaintEntityList = new ArrayList<>();
        when(complaintRepository.findByServiceProviderIdAndStatus((Long) any(), (ComplaintStatus) any()))
                .thenReturn(complaintEntityList);
        List<ComplaintEntity> actualComplaintsByServiceProviderId = complaintServiceImpl
                .getComplaintsByServiceProviderId(1L, ComplaintStatus.CLOSED);
        assertSame(complaintEntityList, actualComplaintsByServiceProviderId);
        assertTrue(actualComplaintsByServiceProviderId.isEmpty());
        verify(complaintRepository).findByServiceProviderIdAndStatus((Long) any(), (ComplaintStatus) any());
    }

    /**
     * Method under test: {@link ComplaintServiceImpl#getAllComplaints(ComplaintStatus)}
     */
    @Test
    void testGetAllComplaints() {
        ArrayList<ComplaintEntity> complaintEntityList = new ArrayList<>();
        when(complaintRepository.findByStatus((ComplaintStatus) any())).thenReturn(complaintEntityList);
        List<ComplaintEntity> actualAllComplaints = complaintServiceImpl.getAllComplaints(ComplaintStatus.OPEN);
        assertSame(complaintEntityList, actualAllComplaints);
        assertTrue(actualAllComplaints.isEmpty());
        verify(complaintRepository).findByStatus((ComplaintStatus) any());
    }

    /**
     * Method under test: {@link ComplaintServiceImpl#getAllComplaints(ComplaintStatus)}
     */
    @Test
    void testGetAllComplaints2() {
        ArrayList<ComplaintEntity> complaintEntityList = new ArrayList<>();
        when(complaintRepository.findAll()).thenReturn(complaintEntityList);
        when(complaintRepository.findByStatus((ComplaintStatus) any())).thenReturn(new ArrayList<>());
        List<ComplaintEntity> actualAllComplaints = complaintServiceImpl.getAllComplaints(null);
        assertSame(complaintEntityList, actualAllComplaints);
        assertTrue(actualAllComplaints.isEmpty());
        verify(complaintRepository).findAll();
    }

    /**
     * Method under test: {@link ComplaintServiceImpl#getAllComplaints(ComplaintStatus)}
     */
    @Test
    void testGetAllComplaints3() {
        when(complaintRepository.findAll()).thenReturn(new ArrayList<>());
        ArrayList<ComplaintEntity> complaintEntityList = new ArrayList<>();
        when(complaintRepository.findByStatus((ComplaintStatus) any())).thenReturn(complaintEntityList);
        List<ComplaintEntity> actualAllComplaints = complaintServiceImpl.getAllComplaints(ComplaintStatus.ASSIGNED);
        assertSame(complaintEntityList, actualAllComplaints);
        assertTrue(actualAllComplaints.isEmpty());
        verify(complaintRepository).findByStatus((ComplaintStatus) any());
    }

    /**
     * Method under test: {@link ComplaintServiceImpl#getAllComplaints(ComplaintStatus)}
     */
    @Test
    void testGetAllComplaints4() {
        when(complaintRepository.findAll()).thenReturn(new ArrayList<>());
        ArrayList<ComplaintEntity> complaintEntityList = new ArrayList<>();
        when(complaintRepository.findByStatus((ComplaintStatus) any())).thenReturn(complaintEntityList);
        List<ComplaintEntity> actualAllComplaints = complaintServiceImpl.getAllComplaints(ComplaintStatus.IN_PROGRESS);
        assertSame(complaintEntityList, actualAllComplaints);
        assertTrue(actualAllComplaints.isEmpty());
        verify(complaintRepository).findByStatus((ComplaintStatus) any());
    }

    /**
     * Method under test: {@link ComplaintServiceImpl#getAllComplaints(ComplaintStatus)}
     */
    @Test
    void testGetAllComplaints5() {
        when(complaintRepository.findAll()).thenReturn(new ArrayList<>());
        ArrayList<ComplaintEntity> complaintEntityList = new ArrayList<>();
        when(complaintRepository.findByStatus((ComplaintStatus) any())).thenReturn(complaintEntityList);
        List<ComplaintEntity> actualAllComplaints = complaintServiceImpl.getAllComplaints(ComplaintStatus.CLOSED);
        assertSame(complaintEntityList, actualAllComplaints);
        assertTrue(actualAllComplaints.isEmpty());
        verify(complaintRepository).findByStatus((ComplaintStatus) any());
    }
}

