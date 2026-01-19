package com.EventSphere.BackendAssignment.Repository;

import com.EventSphere.BackendAssignment.Entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event,String> {
}
