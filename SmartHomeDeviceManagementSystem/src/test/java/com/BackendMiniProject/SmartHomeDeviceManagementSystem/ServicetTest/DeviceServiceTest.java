package com.BackendMiniProject.SmartHomeDeviceManagementSystem.ServicetTest;

import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Entities.Device;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Entities.HouseUser;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Entities.Room;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Exceptions.ResourceNotFoundException;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Models.AuthRequest;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Models.DeviceDTO;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Models.RoomDTO;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Repository.DeviceRepository;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Repository.HouseUserRepository;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Repository.RoomRepository;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Service.DeviceService;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Utilities.Conversion;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DeviceServiceTest {

    @InjectMocks
    private DeviceService deviceService;

    @Mock private HouseUserRepository houseUserRepository;
    @Mock private RoomRepository roomRepository;
    @Mock private DeviceRepository deviceRepository;
    @Mock private Conversion conversion; // whatever your conversion bean/class is called

    // If your service has an "idempotency" Set field, @InjectMocks will inject it only if it's initialized
    // If it’s null inside service, you can initialize it via reflection or ensure service initializes it.
    @Before
    public void setUp() {
        // If your service has idempotency = new HashSet<>() internally, you're good.
        // Otherwise, uncomment and adapt reflection init (field name must match):
        //
        // org.springframework.test.util.ReflectionTestUtils.setField(deviceService, "idempotency", new HashSet<String>());
    }

    @Test
    public void moveDeviceFromOneRoomToAnother_success() {
        // Given
        Long roomId = 1L;
        Long moveToRoomId = 2L;
        String kickstonId = "kickston-123";

        AuthRequest auth = new AuthRequest();
        auth.setUserId(10L);
        auth.setHouseId(20L);

        Room from = new Room();
        from.setRoomId(roomId);
        from.setDevices(new ArrayList<>());

        Room to = new Room();
        to.setRoomId(moveToRoomId);
        to.setDevices(new ArrayList<>());

        Device device = new Device();
        device.setKickstonId(kickstonId);
        device.setRoom(from);

        from.getDevices().add(device);

        // repo mocks
        when(houseUserRepository.findByHouse_HouseIdAndUser_UserId(20L, 10L))
                .thenReturn(Optional.of(new HouseUser()));

        when(roomRepository.findByRoomIdAndHouse_HouseId(roomId, 20L))
                .thenReturn(Optional.of(from));

        when(roomRepository.findByRoomIdAndHouse_HouseId(moveToRoomId, 20L))
                .thenReturn(Optional.of(to));

        // save mocks
        when(roomRepository.save(from)).thenReturn(from);
        when(roomRepository.save(to)).thenReturn(to);
        when(deviceRepository.save(device)).thenReturn(device);

        // conversion mocks
        DeviceDTO deviceDTO = new DeviceDTO();
        RoomDTO toRoomDTO = RoomDTO.builder().type("BEDROOM").devices(new ArrayList<>()).build();

        when(conversion.convertDeviceDTO(device)).thenReturn(deviceDTO);
        when(conversion.convertRoom(to)).thenReturn(toRoomDTO);

        LocalDate today = LocalDate.now();

        // When
        RoomDTO result = deviceService.moveDeviceFromOneRoomToAnother(roomId, moveToRoomId, kickstonId, auth);

        // Then
        assertNotNull(result);
        assertEquals("BEDROOM", result.getType());
        assertNotNull(result.getDevices());
        assertEquals(1, result.getDevices().size());
        assertSame(deviceDTO, result.getDevices().get(0));

        // device moved
        assertFalse(from.getDevices().contains(device));
        assertTrue(to.getDevices().contains(device));

        // owning side updated
        assertSame(to, device.getRoom());

        // modified dates set (best-effort)
        assertEquals(today, from.getModifiedDate());
        assertEquals(today, to.getModifiedDate());
        assertEquals(today, device.getModifiedDate());

        // verify saves + conversions
        verify(roomRepository, times(1)).save(from);
        verify(roomRepository, times(1)).save(to);
        verify(deviceRepository, times(1)).save(device);
        verify(conversion, times(1)).convertDeviceDTO(device);
        verify(conversion, times(1)).convertRoom(to);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void moveDeviceFromOneRoomToAnother_userNotInHouse_throws() {
        Long roomId = 1L;
        Long moveToRoomId = 2L;
        String kickstonId = "kickston-123";

        AuthRequest auth = new AuthRequest();
        auth.setUserId(10L);
        auth.setHouseId(20L);

        when(houseUserRepository.findByHouse_HouseIdAndUser_UserId(20L, 10L))
                .thenReturn(Optional.empty());

        deviceService.moveDeviceFromOneRoomToAnother(roomId, moveToRoomId, kickstonId, auth);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void moveDeviceFromOneRoomToAnother_fromRoomNotFound_throws() {
        Long roomId = 1L;
        Long moveToRoomId = 2L;
        String kickstonId = "kickston-123";

        AuthRequest auth = new AuthRequest();
        auth.setUserId(10L);
        auth.setHouseId(20L);

        when(houseUserRepository.findByHouse_HouseIdAndUser_UserId(20L, 10L))
                .thenReturn(Optional.of(new HouseUser()));

        when(roomRepository.findByRoomIdAndHouse_HouseId(roomId, 20L))
                .thenReturn(Optional.empty());

        deviceService.moveDeviceFromOneRoomToAnother(roomId, moveToRoomId, kickstonId, auth);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void moveDeviceFromOneRoomToAnother_toRoomNotFound_throws() {
        Long roomId = 1L;
        Long moveToRoomId = 2L;
        String kickstonId = "kickston-123";

        AuthRequest auth = new AuthRequest();
        auth.setUserId(10L);
        auth.setHouseId(20L);

        Room from = new Room();
        from.setRoomId(roomId);
        from.setDevices(new ArrayList<>());

        when(houseUserRepository.findByHouse_HouseIdAndUser_UserId(20L, 10L))
                .thenReturn(Optional.of(new HouseUser()));

        when(roomRepository.findByRoomIdAndHouse_HouseId(roomId, 20L))
                .thenReturn(Optional.of(from));

        when(roomRepository.findByRoomIdAndHouse_HouseId(moveToRoomId, 20L))
                .thenReturn(Optional.empty());

        deviceService.moveDeviceFromOneRoomToAnother(roomId, moveToRoomId, kickstonId, auth);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void moveDeviceFromOneRoomToAnother_deviceNotFound_throws() {
        Long roomId = 1L;
        Long moveToRoomId = 2L;
        String kickstonId = "kickston-123";

        AuthRequest auth = new AuthRequest();
        auth.setUserId(10L);
        auth.setHouseId(20L);

        Room from = new Room();
        from.setRoomId(roomId);
        from.setDevices(new ArrayList<>()); // no device

        Room to = new Room();
        to.setRoomId(moveToRoomId);
        to.setDevices(new ArrayList<>());

        when(houseUserRepository.findByHouse_HouseIdAndUser_UserId(20L, 10L))
                .thenReturn(Optional.of(new HouseUser()));

        when(roomRepository.findByRoomIdAndHouse_HouseId(roomId, 20L))
                .thenReturn(Optional.of(from));

        when(roomRepository.findByRoomIdAndHouse_HouseId(moveToRoomId, 20L))
                .thenReturn(Optional.of(to));

        deviceService.moveDeviceFromOneRoomToAnother(roomId, moveToRoomId, kickstonId, auth);
    }

    @Test
    public void moveDeviceFromOneRoomToAnother_idempotencyHit_returnsNullAndDoesNotSave() {
        // Given
        Long roomId = 1L;
        Long moveToRoomId = 2L;
        String kickstonId = "kickston-123";

        AuthRequest auth = new AuthRequest();
        auth.setUserId(10L);
        auth.setHouseId(20L);

        // Minimal happy-path stubs for the FIRST call
        Room from = new Room();
        from.setRoomId(roomId);
        from.setDevices(new ArrayList<>());

        Room to = new Room();
        to.setRoomId(moveToRoomId);
        to.setDevices(new ArrayList<>());

        Device device = new Device();
        device.setKickstonId(kickstonId);
        device.setRoom(from);
        from.getDevices().add(device);

        when(houseUserRepository.findByHouse_HouseIdAndUser_UserId(20L, 10L))
                .thenReturn(Optional.of(new HouseUser()));
        when(roomRepository.findByRoomIdAndHouse_HouseId(roomId, 20L))
                .thenReturn(Optional.of(from));
        when(roomRepository.findByRoomIdAndHouse_HouseId(moveToRoomId, 20L))
                .thenReturn(Optional.of(to));

        when(roomRepository.save(from)).thenReturn(from);
        when(roomRepository.save(to)).thenReturn(to);
        when(deviceRepository.save(device)).thenReturn(device);

        when(conversion.convertDeviceDTO(device)).thenReturn(new DeviceDTO());
        when(conversion.convertRoom(to)).thenReturn(RoomDTO.builder().type("ANY").devices(new ArrayList<>()).build());

        // When: first call populates idempotency
        RoomDTO first = deviceService.moveDeviceFromOneRoomToAnother(roomId, moveToRoomId, kickstonId, auth);

        // Then: first call returns non-null
        assertNotNull(first);

        // Reset interactions to check second call behavior cleanly
        clearInvocations(houseUserRepository, roomRepository, deviceRepository, conversion);

        // When: second call with SAME args + SAME auth object => same key => should return null
        RoomDTO second = deviceService.moveDeviceFromOneRoomToAnother(roomId, moveToRoomId, kickstonId, auth);

        // Then
        assertNull(second);

        // And: should not call repositories/conversion on idempotency hit
        verifyNoInteractions(houseUserRepository, roomRepository, deviceRepository, conversion);
    }
}
