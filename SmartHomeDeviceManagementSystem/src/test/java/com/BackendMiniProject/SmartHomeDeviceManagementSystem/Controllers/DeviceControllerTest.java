package com.BackendMiniProject.SmartHomeDeviceManagementSystem.Controllers;

import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Controller.DeviceController;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Models.AuthRequest;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Models.RoomDTO;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Service.DeviceService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class DeviceControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DeviceService deviceService;

    @InjectMocks
    private DeviceController deviceController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String BASE_URL = "/device/v1";

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(deviceController)
                .build();
    }

    @Test
    public void moveDeviceFromOneRoomToAnother_success() throws Exception {
        // Given
        String kickstonId = "kickston-123";
        Long roomId = 1L;
        Long moveToRoomId = 2L;

        AuthRequest authRequest = new AuthRequest();
        authRequest.setUserId(10L);
        authRequest.setHouseId(20L);

        RoomDTO roomDTO = RoomDTO.builder()
                .type("BEDROOM")
                .devices(new ArrayList<>())
                .build();

        Mockito.when(deviceService.moveDeviceFromOneRoomToAnother(
                Mockito.eq(roomId),
                Mockito.eq(moveToRoomId),
                Mockito.eq(kickstonId),
                Mockito.any(AuthRequest.class)   // <-- IMPORTANT FIX
        )).thenReturn(roomDTO);

        // When & Then
        mockMvc.perform(
                        post(BASE_URL + "/" + kickstonId + "/room/" + roomId + "/moveTo/" + moveToRoomId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(authRequest))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.type").value("BEDROOM"))
                .andExpect(jsonPath("$.devices").isArray());

        // Verify + assert the request body values that were deserialized
        ArgumentCaptor<AuthRequest> captor = ArgumentCaptor.forClass(AuthRequest.class);

        Mockito.verify(deviceService, times(1))
                .moveDeviceFromOneRoomToAnother(Mockito.eq(roomId), Mockito.eq(moveToRoomId),
                        Mockito.eq(kickstonId), captor.capture());

        AuthRequest captured = captor.getValue();
        org.junit.Assert.assertEquals(Long.valueOf(10L), captured.getUserId());
        org.junit.Assert.assertEquals(Long.valueOf(20L), captured.getHouseId());
    }
}
