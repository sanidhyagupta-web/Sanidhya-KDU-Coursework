package com.BackendMiniProject.SmartHomeDeviceManagementSystem.Graphql;

import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Entities.*;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class DeviceGraphQLIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @Autowired private UserRepository userRepository;
    @Autowired private HouseRepository houseRepository;
    @Autowired private HouseUserRepository houseUserRepository;
    @Autowired private RoomRepository roomRepository;
    @Autowired private DeviceRepository deviceRepository;

    private Long userId;
    private Long houseId;

    @Before
    public void setUp() {
        // --- Create a valid User (email is NOT NULL in your DB) ---
        User user = new User();
        user.setEmail("test.user@example.com");
        user.setName("Test User");
        user.setPassword("pass123"); // just needs non-null for DB constraint
        user.setAge(25);             // set if NOT NULL in your entity/table
        user.setDeleted(false);      // if NOT NULL
        user = userRepository.save(user);

        // --- Create a valid House (address is NOT NULL) ---
        House house = House.builder()
                .address("221B Baker Street")
                .pincode("123456")
                .devices(new ArrayList<>())
                .rooms(new ArrayList<>())
                .build();
        house = houseRepository.save(house);

        // --- Link User <-> House (membership) ---
        HouseUser membership = new HouseUser();
        membership.setUser(user);
        membership.setHouse(house);
        membership.setRole(Roles.ADMIN);
        houseUserRepository.save(membership);

        userId = user.getUserId();     // adjust if your field/getter differs
        houseId = house.getHouseId();

        // --- Create 12 rooms with type NOT NULL + devices list initialized ---
        for (int i = 1; i <= 12; i++) {
            Room room = Room.builder()
                    .type("ROOM_" + i)
                    .house(house)
                    .devices(new ArrayList<>())
                    .build();
            room = roomRepository.save(room);

            Device device = new Device();
            device.setKickstonId("kickston-" + i);
            device.setRoom(room);
            device.setHouse(house); // if your Device has house FK too
            deviceRepository.save(device);

            // keep both sides consistent (optional but nice)
            room.getDevices().add(device);
        }
    }

    @Test
    public void getAllRoomsAllDevices_whenPageAndSizeOmitted_returnsDefault10() throws Exception {
        // NOTE:
        // - If your GraphQL input type name is AuthRequestInput (common), change it below.
        // - Room fields must match your GraphQL schema. If you don't expose roomId, use id, etc.
        String query =
                "query($auth: AuthRequest) { " +
                        "  getAllRoomsAllDevices(auth: $auth) { " +
                        "    roomId " +
                        "    type " +
                        "    devices { kickstonId } " +
                        "  } " +
                        "}";

        Map<String, Object> variables = new HashMap<>();
        Map<String, Object> auth = new HashMap<>();
        auth.put("userId", userId);
        auth.put("houseId", houseId);
        variables.put("auth", auth);

        Map<String, Object> body = new HashMap<>();
        body.put("query", query);
        body.put("variables", variables);

        mockMvc.perform(
                        post("/graphql")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(body))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errors").doesNotExist())
                // your resolver defaults to p=0, s=10 when null
                .andExpect(jsonPath("$.data.getAllRoomsAllDevices.length()").value(10))
                .andExpect(jsonPath("$.data.getAllRoomsAllDevices[0].type").exists())
                .andExpect(jsonPath("$.data.getAllRoomsAllDevices[0].devices").isArray());
    }
}
