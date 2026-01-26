package com.BackendMiniProject.SmartHomeDeviceManagementSystem.Controllers;

import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Controller.HouseController;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Entities.House;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Service.UserHouseService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserHouseControllerTest {

    @Mock
    private UserHouseService userHouseService;

    @InjectMocks
    private HouseController userController;

    @Test
    public void getAllHousesUserBelong_returnsPagedHouses() {
        // given
        Long userId = 7L;

        House h1 = House.builder().houseId(1L).build();
        House h2 = House.builder().houseId(2L).build();

        int pageNo = 0;
        int pageSize = 10;

        Pageable expectedPageable = PageRequest.of(pageNo, pageSize);
        Page<House> expectedPage =
                new PageImpl<>(List.of(h1, h2), expectedPageable, 2);

        when(userHouseService.getAllHousesUserBelong(eq(userId), any(Pageable.class)))
                .thenReturn(expectedPage);

        // when
        Page<House> result =
                userController.getAllHousesUserBelong(userId, pageNo, pageSize);

        // then
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals(2, result.getTotalElements());
        assertEquals(pageNo, result.getNumber());
        assertEquals(pageSize, result.getSize());

        ArgumentCaptor<Pageable> pageableCaptor =
                ArgumentCaptor.forClass(Pageable.class);

        verify(userHouseService)
                .getAllHousesUserBelong(eq(userId), pageableCaptor.capture());

        assertEquals(expectedPageable, pageableCaptor.getValue());

        verifyNoMoreInteractions(userHouseService);
    }
}
