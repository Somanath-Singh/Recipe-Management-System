package com.recipe.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.recipe.entities.Comments;

public interface ICommentsRepo extends JpaRepository<Comments, Integer> {

	@Query("SELECT c FROM Comments c WHERE c.recipe.rid =:recipeId")
	public List<Comments> findAllByCid(int recipeId);
	
	
}
