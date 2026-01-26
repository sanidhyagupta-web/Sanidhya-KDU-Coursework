package com.BackendMiniProject.SmartHomeDeviceManagementSystem.Repository;

import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Entities.Room;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room,Long> {

    @Query("""
select distinct r
from Room r
left join fetch r.devices
where r.house.id = :houseId
""")
    List<Room> findByHouseIdWithDevices(@Param("houseId") Long houseId, Pageable pageable);

    Optional<Room> findByRoomIdAndHouse_HouseId(Long roomId, Long houseId);
}
