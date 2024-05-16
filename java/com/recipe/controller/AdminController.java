package com.recipe.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.recipe.entities.Comments;
import com.recipe.entities.Recipe;
import com.recipe.entities.User;
import com.recipe.repo.ICommentsRepo;
import com.recipe.repo.IRecipeRepo;
import com.recipe.repo.IUserRepo;
import com.recipe.service.ICommentsService;
import com.recipe.service.IRecipeMgmtService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private IUserRepo userRepo;

	@Autowired
	private IRecipeRepo recipeRepo;

	@Autowired
	private ICommentsRepo commentsRepo;

	@Autowired
	private IRecipeMgmtService recipeService;

	@Autowired
	private ICommentsService commentsService;
	
	
	// method for adding common data to response
		@ModelAttribute
		public User getAdminData(Model m, Principal p) {

			String username = p.getName();

			User user = userRepo.findByEmail(username);

			m.addAttribute("user", user);

			return user;

		}

	// admin dash-board is shown when admin enter in the browser
	// "localhost:4041/user/index"
	@GetMapping("/profile")
	public String dashboard(Model model) {

		model.addAttribute("title", "Admin : Dash - Profile");

		return "admin/profile";

	}

	// open setting handler
	@GetMapping("/setting")
	public String openSettings() {

		return "admin/settings";

	}

	// change password

	@PostMapping("/change-password")
	public String getChangePassword(@RequestParam("oldPassword") String oldPassword,
			@RequestParam("newPassword") String newPassword, Principal principal, HttpSession session) {

		System.out.println(newPassword);

		String email = principal.getName();

		User loginuser = userRepo.findByEmail(email);

		if (this.bCryptPasswordEncoder.matches(oldPassword, loginuser.getPassword())) {

			// change the password
			loginuser.setPassword(bCryptPasswordEncoder.encode(newPassword));

			userRepo.save(loginuser);

			// message
			session.setAttribute("msg", "Password change successfully ...");
			session.setAttribute("type", "alert-success");

			return "redirect:/admin/setting";

		} else {

			// error

			// message
			session.setAttribute("msg", "Enter correct password ...");
			session.setAttribute("type", "alert-danger");

			return "redirect:/admin/setting";

		}

	}
	
	
	// open add recipe form
			@GetMapping("/add-recipes")
			public String openAddContactForm(Model model) {

				model.addAttribute("title", "Add : Recipes");
				model.addAttribute("post", new Recipe());

				return "admin/add_recipes";

			}

			// procession add recipe form

			@PostMapping("/process-recipes")
			public String processContact(@ModelAttribute("recipe") Recipe recipe, @RequestParam("imageName") MultipartFile file,
					Principal principal, HttpSession session) {

				try {
					// get the user object
					String email = principal.getName();
					User user = userRepo.findByEmail(email);

					// processing and uploading file

					if (file.isEmpty()) {

						// if the file is empty then try our message
						System.out.println("File is empty");
						recipe.setImage("recipe.jpg");

					} else {
						// upload the file to folder and update the name of the file into the database
						// set the image name to Contact table
						recipe.setImage(file.getOriginalFilename());

						// give the name of the folder where image stored
						File saveFile = new ClassPathResource("static/img").getFile();

						// get the path of the image
						java.nio.file.Path path = Paths
								.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());

						// Files.copy(input, target, option);
						Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

						System.out.println("file saved");

					}

					// set the user details to recipe table for foreign key purpose
					recipe.setUser(user);

					recipeService.addRecipe(recipe);// save Recipe

					System.out.println("DATA " + recipe);

					// success message
					session.setAttribute("msg", "Your Recipe saved successfully .");
					session.setAttribute("type", "alert-info");

				} catch (Exception se) {

					se.printStackTrace();
					// error message
					session.setAttribute("msg", "something went wrong ." + se.getMessage());
					session.setAttribute("type", "alert-danger");

				}

				return "redirect:/admin/add-recipes";

			}
			

	// show all recipe handler

	@GetMapping("/show-all-recipes")
	public String showAllRecipe(Model m) {

		List<Recipe> recipe = recipeService.getAllRecipes();

		// m.addAttribute("postsList",posts ); //old

		m.addAttribute("recipes", recipe);

		return "admin/all_recipes";

	}

	// showing perticular recipe details

	@GetMapping("/{rid}/recipe")
	public String showContactDetails(@PathVariable("rid") Integer rId, Model model, Principal principal) {

		// get the posts object by using post id
		Optional<Recipe> postsOptional = recipeRepo.findById(rId);
		Recipe recipe = postsOptional.get();

		String userNmae = recipe.getUser().getName();

		// gets the all comments of the posts

		List<Comments> comments = commentsRepo.findAllByCid(rId);

		model.addAttribute("recipe", recipe);

		model.addAttribute("comments", comments);

		model.addAttribute("name", userNmae);

		model.addAttribute("title", recipe.getName());

		return "admin/recipe-details";

	}

	// show all Users handler

	@GetMapping("/show-all-users")
	public String showAllUsers(Model m) {

		List<User> user = userRepo.findByRole("ROLE_USER");

		m.addAttribute("users", user);

		return "admin/all_user";

	}

	// showing perticular user details

	@GetMapping("/{uid}/user")
	public String showUserDetails(@PathVariable("uid") Integer uId, Model model) {

		// get the contact object by using contact id
		User user = userRepo.findById(uId).get();

		model.addAttribute("user", user);
		model.addAttribute("title", user.getName());

		return "admin/perticular-usee-profile";

	}

}
