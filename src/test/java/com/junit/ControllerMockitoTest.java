package com.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.junit.controllers.UserController;
import com.junit.models.User;
import com.junit.services.UserService;

@SpringBootTest(classes = {ControllerMockitoTest.class})
public class ControllerMockitoTest {
	
	@Mock
	UserService userService;
	
	@InjectMocks
	UserController userController;
	
	List<User> all;
	User user;
	
	@Test
	@Order(1)
	public void test_getAllUser() {
		all=new ArrayList<User>();
		all.add(new User(1L,"ankush",22L));
		all.add(new User(2L,"priya",23L));
		//mocking
		when(userService.getAll()).thenReturn(all);
		List<User> res=userController.getAll();
		assertEquals(all,res);
		assertEquals(2,res.size());
	}
	
	
	@Test
	@Order(2)
	public void test_getUserById()
	{
		user=new User(3L,"johny",24L);
		Long Id=3L;
		when(userService.getUserById(Id)).thenReturn(user);
		
	}
	
	

}
