package com.EventSphere.BackendAssignment.Repository;

import com.EventSphere.BackendAssignment.Entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,String> {

}
