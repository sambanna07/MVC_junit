package com.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.junit.models.User;
import com.junit.repo.UserReposistory;
import com.junit.services.UserService;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(classes = {ServiceMockitoTests.class})
public class ServiceMockitoTests {
	
	// For mocking the repository
	@Mock
	UserReposistory userReposistory;
	
	// For calling the method which we want to test
	@InjectMocks
	UserService userService;
	
	@Test
	@Order(1)
	public void test_getAllUser() {
		// Add some fake data
		List<User> listuser = new ArrayList<>();
		listuser.add(new User(1L, "priya", 22L));
		listuser.add(new User(2L, "riya", 24L));
		listuser.add(new User(3L, "raj", 25L));
		
		// Mocking the repository
		when(userReposistory.findAll()).thenReturn(listuser);
		
		// Checking the test case
		assertEquals(3, userService.getAll().size());
	}
	
	@Test
	@Order(2)
	public void test_getUserById() {
		// Add some fake data
		List<User> listuser = new ArrayList<>();
		listuser.add(new User(1L, "priya", 22L));
		listuser.add(new User(2L, "riya", 24L));
		listuser.add(new User(3L, "raj", 25L));
		
		Long id = 1L;
		
		// Mocking the repository
		when(userReposistory.findAll()).thenReturn(listuser);
		
		// Assert the equality
		assertEquals(id, userService.getUserById(id).getId());
	}
	
	@Test
	@Order(3)
	public void test_getUserByName() {
		// Add some fake data
		List<User> listuser = new ArrayList<>();
		listuser.add(new User(1L, "priya", 22L));
		listuser.add(new User(2L, "riya", 24L));
		listuser.add(new User(3L, "raj", 25L));
		
		String name = "raj";
		
		// Mocking the repository
		when(userReposistory.findAll()).thenReturn(listuser);
		
		// Assert the equality
		assertEquals(name, userService.getUserByName(name).getName());
	}
	
	@Test
	@Order(4)
	public void test_addUser() {
		User user = new User(4L, "ankush", 21L);
		
		// Mocking the repository
		when(userReposistory.save(user)).thenReturn(user);
		
		assertEquals(user, userService.saveUser(user));
	}
	
	@Test
	@Order(5)
	public void test_updateUser() {
		User user = new User(4L, "ankush raj", 21L);
		
		// Mocking the repository
		when(userReposistory.save(user)).thenReturn(user);
		
		assertEquals(user, userService.updateUser(user));
	}
	
	@Test
	@Order(6)
	public void test_deleteUser() {
		User user = new User(4L, "ankush raj", 21L);
		
		// Call the service method to delete the user
		userService.deleteUser(user.getId());
		
		// Verify that the delete method was invoked on the userReposistory mock object
		verify(userReposistory, times(1)).deleteById(user.getId());
	}
}
