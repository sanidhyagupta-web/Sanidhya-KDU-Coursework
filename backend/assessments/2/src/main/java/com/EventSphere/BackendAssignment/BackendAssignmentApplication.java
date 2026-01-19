package com.EventSphere.BackendAssignment;

import com.EventSphere.BackendAssignment.Entities.Role;
import com.EventSphere.BackendAssignment.Entities.User;
import com.EventSphere.BackendAssignment.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Random;

@SpringBootApplication
public class BackendAssignmentApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BackendAssignmentApplication.class, args);
	}

	@Autowired
	private UserRepository userRepository;


	@Override
	public void run(String... args) throws Exception {
		User user = User.builder().
				name("Aakash")
				.address("Bangalore")
				.age(29)
				.role(Role.ADMIN)
				.password("abc")
				.hasTicket(false)
				.build();

		User user1 = User.builder().
				name("Sanidhya")
				.address("Bangalore")
				.age(21)
				.role(Role.USER)
				.password("abc")
				.hasTicket(false)
				.build();

		userRepository.save(user1);
		userRepository.save(user);

	}
}
