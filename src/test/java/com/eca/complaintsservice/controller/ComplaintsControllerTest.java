package com.eca.complaintsservice.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.eca.entity.ComplaintEntity;
import com.eca.model.AssignComplaintRequest;
import com.eca.model.ComplaintRegisterRequest;
import com.eca.model.ComplaintStatus;
import com.eca.model.ComplaintStatusUpdate;
import com.eca.service.ComplaintService;
import com.eca.controller.ComplaintsController;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import reactor.core.publisher.Mono;

@ContextConfiguration(classes = {ComplaintsController.class})
@ExtendWith(SpringExtension.class)
class ComplaintsControllerTest {
    @MockBean
    private ComplaintService complaintService;

    @Autowired
    private ComplaintsController complaintsController;

    /**
     * Method under test: {@link ComplaintsController#registerComplaint(ComplaintRegisterRequest)}
     */
    @Test
    void testRegisterComplaint() throws Exception {
        ComplaintEntity complaintEntity = new ComplaintEntity();
        complaintEntity.setAdminId("2c9ed08188edf2900188edf386980001");
        complaintEntity.setStatus(ComplaintStatus.OPEN);
        complaintEntity.setCreatedDate(LocalDateTime.of(1, 1, 1, 1, 1));
        complaintEntity.setDescription("The characteristics of someone or something");
        complaintEntity.setId(123L);
        complaintEntity.setResolvedDate(LocalDateTime.of(1, 1, 1, 1, 1));
        complaintEntity.setServiceId(123L);
        complaintEntity.setServiceName("Service Name");
        complaintEntity.setServiceProviderId(123L);
        complaintEntity.setUserId("2c9ed08188edf2900188edf386980000");
        when(complaintService.registerComplaint((ComplaintRegisterRequest) any())).thenReturn(complaintEntity);

        ComplaintRegisterRequest complaintRegisterRequest = new ComplaintRegisterRequest();
        complaintRegisterRequest.setDescription("The characteristics of someone or something");
        complaintRegisterRequest.setServiceId(123L);
        complaintRegisterRequest.setServiceName("Service Name");
        complaintRegisterRequest.setUserId("2c9ed08188edf2900188edf386980000");
        String content = (new ObjectMapper()).writeValueAsString(complaintRegisterRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/complaints")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(complaintsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"serviceId\":123,\"serviceName\":\"Service Name\",\"serviceProviderId\":123,\"userId\":\"2c9ed08188edf2900188edf386980000\",\"adminId\":\"2c9ed08188edf2900188edf386980001\",\"createdDate\":[1,1,1,1,1],\"resolvedDate\":[1,1,1,1,1],\"status\":\"OPEN\",\"description\":\"The characteristics of someone or something\",\"category\":null,\"categoryId\":null}"));
    }


    /**
     * Method under test: {@link ComplaintsController#getComplaintsById(Long)}
     */
    @Test
    void testGetComplaintsById() throws Exception {
        ComplaintEntity complaintEntity = new ComplaintEntity();
        complaintEntity.setAdminId("2c9ed08188edf2900188edf386980000");
        complaintEntity.setStatus(ComplaintStatus.IN_PROGRESS);
        complaintEntity.setCreatedDate(LocalDateTime.of(1, 1, 1, 1, 1));
        complaintEntity.setDescription("The characteristics of someone or something");
        complaintEntity.setId(123L);
        complaintEntity.setResolvedDate(LocalDateTime.of(1, 1, 1, 1, 1));
        complaintEntity.setServiceId(123L);
        complaintEntity.setServiceName("Service Name");
        complaintEntity.setServiceProviderId(123L);
        complaintEntity.setUserId("2c9ed08188edf2900188edf386980000");
        when(complaintService.getComplaintsById((Long) any())).thenReturn(complaintEntity);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/complaints/{id}", 123L);
        MockMvcBuilders.standaloneSetup(complaintsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"serviceId\":123,\"serviceName\":\"Service Name\",\"serviceProviderId\":123,\"userId\":\"2c9ed08188edf2900188edf386980000\",\"adminId\":\"2c9ed08188edf2900188edf386980000\",\"createdDate\":[1,1,1,1,1],\"resolvedDate\":[1,1,1,1,1],\"status\":\"IN_PROGRESS\",\"description\":\"The characteristics of someone or something\",\"category\":null,\"categoryId\":null}"));
    }

    /**
     * Method under test: {@link ComplaintsController#assignComplaint(AssignComplaintRequest)}
     */
    @Test
    void testAssignComplaint() throws Exception {
        ComplaintEntity complaintEntity = new ComplaintEntity();
        complaintEntity.setAdminId("2c9ed08188edf2900188edf386980001");
        complaintEntity.setStatus(ComplaintStatus.ASSIGNED);
        complaintEntity.setCreatedDate(LocalDateTime.of(1, 1, 1, 1, 1));
        complaintEntity.setDescription("The characteristics of someone or something");
        complaintEntity.setId(123L);
        complaintEntity.setResolvedDate(LocalDateTime.of(1, 1, 1, 1, 1));
        complaintEntity.setServiceId(123L);
        complaintEntity.setServiceName("Service Name");
        complaintEntity.setServiceProviderId(123L);
        complaintEntity.setUserId("2c9ed08188edf2900188edf386980000");
        when(complaintService.assignComplaint((AssignComplaintRequest) any())).thenReturn(complaintEntity);

        AssignComplaintRequest assignComplaintRequest = new AssignComplaintRequest();
        assignComplaintRequest.setComplaintId(123L);
        assignComplaintRequest.setServiceProviderId(123L);
        String content = (new ObjectMapper()).writeValueAsString(assignComplaintRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/v1/complaints/assignComplaint")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(complaintsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"serviceId\":123,\"serviceName\":\"Service Name\",\"serviceProviderId\":123,\"userId\":\"2c9ed08188edf2900188edf386980000\",\"adminId\":\"2c9ed08188edf2900188edf386980001\",\"createdDate\":[1,1,1,1,1],\"resolvedDate\":[1,1,1,1,1],\"status\":\"ASSIGNED\",\"description\":\"The characteristics of someone or something\",\"category\":null,\"categoryId\":null}"));
    }

    /**
     * Method under test: {@link ComplaintsController#updateComplaintStatus(ComplaintStatusUpdate)}
     */
    @Test
    void testResolveComplaint() throws Exception {
        ComplaintEntity complaintEntity = new ComplaintEntity();
        complaintEntity.setAdminId("2c9ed08188edf2900188edf386980001");
        complaintEntity.setStatus(ComplaintStatus.CLOSED);
        complaintEntity.setCreatedDate(LocalDateTime.of(1, 1, 1, 1, 1));
        complaintEntity.setDescription("The characteristics of someone or something");
        complaintEntity.setId(123L);
        complaintEntity.setResolvedDate(LocalDateTime.of(1, 1, 1, 1, 1));
        complaintEntity.setServiceId(123L);
        complaintEntity.setServiceName("Service Name");
        complaintEntity.setServiceProviderId(123L);
        complaintEntity.setUserId("2c9ed08188edf2900188edf386980000");
        when(complaintService.updateComplaint((ComplaintStatusUpdate) any())).thenReturn(complaintEntity);

        ComplaintStatusUpdate complaintStatusUpdate = new ComplaintStatusUpdate();
        complaintStatusUpdate.setComplaintId(123L);
        complaintStatusUpdate.setComplaintStatus(ComplaintStatus.CLOSED);
        String content = (new ObjectMapper()).writeValueAsString(complaintStatusUpdate);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/v1/complaints/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(complaintsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"serviceId\":123,\"serviceName\":\"Service Name\",\"serviceProviderId\":123,\"userId\":\"2c9ed08188edf2900188edf386980000\",\"adminId\":\"2c9ed08188edf2900188edf386980001\",\"createdDate\":[1,1,1,1,1],\"resolvedDate\":[1,1,1,1,1],\"status\":\"CLOSED\",\"description\":\"The characteristics of someone or something\",\"category\":null,\"categoryId\":null}"));
    }
}

