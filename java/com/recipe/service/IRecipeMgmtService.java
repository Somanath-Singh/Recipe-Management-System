package com.recipe.service;

import java.util.List;

import com.recipe.entities.Recipe;
import com.recipe.entities.User;

public interface IRecipeMgmtService {
	
	
	public Recipe addRecipe(Recipe recipe);
	
	
	public List<Recipe> getRecipesByUser(User user);
	
	public List<Recipe> getAllRecipes();
	
	public void removeSessionMessage();

}
