package com.recipe.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recipe.entities.Recipe;
import com.recipe.entities.User;

public interface IRecipeRepo extends JpaRepository<Recipe, Integer> {
	
	public List<Recipe> findByUser(User user); // new

}
