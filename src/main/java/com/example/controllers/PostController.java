package com.example.controllers;

import java.security.Principal;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.entities.Post;
import com.example.entities.User;
import com.example.services.UserSecurityService;

@Controller
@RequestMapping("/post")
public class PostController {
	
	@Autowired
	UserSecurityService userService;

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String getRegister(Model model,Post post) {
		model.addAttribute("userpost", new Post());
		return "/post/upload";
	}

	@RequestMapping(value="/saveCategory",method=RequestMethod.POST,produces="application/json")
	public @ResponseBody String saveCategory(@RequestBody String jsonCategory){
		System.out.println("coming to ajax ocntroller");


		return "{'something':'otherthing'}";
//		return "";
	}

	

}
