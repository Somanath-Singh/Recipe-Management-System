package com.recipe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recipe.entities.Comments;
import com.recipe.repo.ICommentsRepo;

@Service
public class CommentsServiceImpl implements ICommentsService {

	@Autowired
	private ICommentsRepo commentsRepo;

	@Override
	public Comments saveComments(Comments comments) {

		return commentsRepo.save(comments);

	}

}
