package com.recipe.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recipe.entities.User;

public interface IUserRepo extends JpaRepository<User, Integer> {
	
	
	public User findByEmail(String email);
	
	public User findByVerificationCode(String verificationCode);
	
	public List<User> findByRole(String role);
	
	/*
	 * @Query("select u from User u where u.email = :email") public User
	 * getUserByUserName(@Param("email") String email);
	 */

}
