package com.junit.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.junit.models.User;


@Repository
public interface UserReposistory extends JpaRepository<User, Long>{
	


}
