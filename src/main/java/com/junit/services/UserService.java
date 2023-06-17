package com.junit.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.junit.models.User;
import com.junit.repo.UserReposistory;

@Service
public class UserService {
	
	@Autowired
	private UserReposistory userRepository;

	/**
	 * Retrieves all users.
	 * 
	 * @return List of User objects
	 */
	public List<User> getAll() {		
		return userRepository.findAll();
	}
	
	/**
	 * Retrieves a user by ID.
	 * 
	 * @param id User ID
	 * @return User object if found, null otherwise
	 */
	public User getUserById(Long id) {
		List<User> userList = userRepository.findAll();
		User user = null;
		for(User u : userList) {
			if(u.getId().equals(id))
				user = u;
		}
		return user;
	}
	
	/**
	 * Retrieves a user by name.
	 * 
	 * @param name User name
	 * @return User object if found, null otherwise
	 */
	public User getUserByName(String name) {
		List<User> userList = userRepository.findAll();
		User user = null;
		for(User u : userList) {
			if(u.getName().equalsIgnoreCase(name))
				user = u;
		}
		return user;
	}
	
	/**
	 * Saves a new user.
	 * 
	 * @param user User object to save
	 * @return Saved User object
	 */
	public User saveUser(User user) {
		userRepository.save(user);
		return user;
	}
	
	/**
	 * Updates an existing user.
	 * 
	 * @param user User object to update
	 * @return Updated User object
	 */
	public User updateUser(User user) {
		userRepository.save(user);
		return user;
	}
	
	/**
	 * Deletes a user by ID.
	 * 
	 * @param id User ID
	 */
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}
}
