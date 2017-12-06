package com.example.controllers;

import java.security.Principal;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.entities.Category;
import com.example.entities.CategoryJsonResponse;
import com.example.entities.Post;
import com.example.entities.User;
import com.example.services.PostService;
import com.example.services.UserSecurityService;

@Controller
@RequestMapping("/post")
public class PostController {
	
	@Autowired
	UserSecurityService userService;
	@Autowired
	PostService postService;

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String getRegister(Model model,Post post) {
		model.addAttribute("userpost", new Post());
		List<Category> categories=postService.getAllCategories();
		model.addAttribute("categories",categories);
		List<Post> posts=postService.getAllPosts();
		model.addAttribute("posts",posts);
		return "/post/upload";
	}
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String postRegister(Model model,Post post) {
		System.out.println(post.getCatigory()+"\n-"+post.getDescription()+"\n-"+post.getDescriptiveImageUrl()+"\n-"+post.getShortWords()+"\n-"+post.getTitle()+"\n-"+post.getUrl());
		//need to update
		Post savedPost=postService.saveOrUpdatePost(post);
		if(savedPost!=null){
			List<Post> posts=postService.getAllPosts();
			model.addAttribute("posts",posts);
			model.addAttribute("userpost", savedPost);
			List<Category> categories=postService.getAllCategories();
			model.addAttribute("categories",categories);
			model.addAttribute("msgType", "success");
			model.addAttribute("msg", "The content successfuly saved!");
		}else{
			model.addAttribute("msgType", "failer");
			model.addAttribute("msg", "The content has not saved!");
		}
		
		return "/post/upload";
	}
	@RequestMapping(value="/deletCategory",method=RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 @ResponseBody public CategoryJsonResponse deleteCategory(@RequestBody Category category){
		CategoryJsonResponse categoryJsonResponse=new CategoryJsonResponse();
		categoryJsonResponse.setValidated(false);
		postService.deleteCategory(category.getId());
		categoryJsonResponse.setMessage(category.getId()+"");
		categoryJsonResponse.setValidated(true);
		System.out.println("Category deleted:"+category.getId());
		return categoryJsonResponse;
	}

	@RequestMapping(value="/saveCategory",method=RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 @ResponseBody public CategoryJsonResponse saveCategory(@RequestBody Category category){
		System.out.println("coming to ajax ocntroller:"+category.getCategoryName());
		
		CategoryJsonResponse categoryJsonResponse=new CategoryJsonResponse();
		Category existingUser=postService.getCategoryByName(category.getCategoryName());
		if(existingUser!=null){
			categoryJsonResponse.setValidated(false);
			categoryJsonResponse.setMessage("Cagtegory is already exists");
		}else{
			Category savedCategory=postService.saveCategory(category);
			if(savedCategory!=null){
				categoryJsonResponse.setValidated(true);
				categoryJsonResponse.setMessage("Cagtegory is successfuly registerd");
				categoryJsonResponse.setCategory(category);
			}else{
				categoryJsonResponse.setValidated(false);
				categoryJsonResponse.setMessage("Category is not saved. Something goes wrong."	);
				categoryJsonResponse.setCategory(null);
			}
		}
		
		
		
		return categoryJsonResponse;
//		return "";
	}

	

}
