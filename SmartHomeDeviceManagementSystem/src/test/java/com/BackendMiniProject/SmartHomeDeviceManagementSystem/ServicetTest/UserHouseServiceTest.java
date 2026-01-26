package com.BackendMiniProject.SmartHomeDeviceManagementSystem.ServicetTest;

import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Entities.House;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Entities.HouseUser;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Repository.HouseUserRepository;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Service.UserHouseService;

import org.junit.Test;
import org.junit.runner.RunWith;

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
public class UserHouseServiceTest {

    @Mock
    private HouseUserRepository houseUserRepository;

    @InjectMocks
    private UserHouseService userHouseService;

    private final Pageable pageable = PageRequest.of(0, 5);

    @Test
    public void getAllHousesUserBelong_success_returnsHousePage() {
        // given
        Long userId = 7L;

        House h1 = House.builder().houseId(1L).address("A1").build();
        House h2 = House.builder().houseId(2L).address("A2").build();

        HouseUser hu1 = HouseUser.builder().house(h1).build();
        HouseUser hu2 = HouseUser.builder().house(h2).build();

        Page<HouseUser> page =
                new PageImpl<>(List.of(hu1, hu2), pageable, 2);

        when(houseUserRepository.findByUser_UserId(userId, pageable))
                .thenReturn(page);

        // when
        Page<House> result =
                userHouseService.getAllHousesUserBelong(userId, pageable);

        // then
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertTrue(result.getContent().contains(h1));
        assertTrue(result.getContent().contains(h2));
        assertEquals(2, result.getTotalElements());

        verify(houseUserRepository).findByUser_UserId(userId, pageable);
        verifyNoMoreInteractions(houseUserRepository);
    }

    @Test
    public void getAllHousesUserBelong_noHouses_returnsEmptyPage() {
        // given
        Long userId = 7L;

        Page<HouseUser> emptyPage =
                Page.empty(pageable);

        when(houseUserRepository.findByUser_UserId(userId, pageable))
                .thenReturn(emptyPage);

        // when
        Page<House> result =
                userHouseService.getAllHousesUserBelong(userId, pageable);

        // then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        assertEquals(0, result.getTotalElements());

        verify(houseUserRepository).findByUser_UserId(userId, pageable);
    }
}
