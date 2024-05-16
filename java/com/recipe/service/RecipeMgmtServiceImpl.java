package com.recipe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.recipe.entities.Recipe;
import com.recipe.entities.User;
import com.recipe.repo.IRecipeRepo;

import jakarta.servlet.http.HttpSession;


@Service
public class RecipeMgmtServiceImpl implements IRecipeMgmtService {
	
	
	@Autowired
	private IRecipeRepo recipeRepo;
	
	@Override
	  public void removeSessionMessage() {
			
			HttpSession session = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest().getSession();
			session.removeAttribute("msg");
			
		}

	@Override
	public Recipe addRecipe(Recipe recipe) {
		
		return recipeRepo.save(recipe);
		
	}

	@Override
	public List<Recipe> getRecipesByUser(User user) {

		return recipeRepo.findByUser(user);
		
	}

	@Override
	public List<Recipe> getAllRecipes() {
	
		return recipeRepo.findAll();
		
	}

}
