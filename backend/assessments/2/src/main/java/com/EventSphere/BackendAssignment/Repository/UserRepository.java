package com.EventSphere.BackendAssignment.Repository;

import com.EventSphere.BackendAssignment.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
}
