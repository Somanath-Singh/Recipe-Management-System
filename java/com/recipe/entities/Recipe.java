package com.recipe.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "RECIPE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int rid;
	
	private String name;
	
	@Column(length=2000)
	private String ingredients;
	
	private String categories;
	
	private String serving;
	
	private String time;
	
	@Column(length=3000)
	private String description;
	
	private String image;
	
	@ManyToOne
	@JoinColumn(name="id")
	private User user;
	
	

}
