package com.example.controllers;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.services.UserSecurityService;

@Controller
@RequestMapping("/post")
public class PostController {
	
	@Autowired
	UserSecurityService userService;

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String getRegister(Model model) {
		return "/post/upload";
	}

//	@RequestMapping(value = "/upload", method = RequestMethod.POST)
//	public String postRegister(Model model, TextPost post,Principal username) {
//
//		return "/post/index";
//	}
	
//	@RequestMapping(value = "/index", method = RequestMethod.GET)
//	public String getIndex(Model model,Principal username) {
//		UserDetails userDetails = userService.loadUserByUsername(username.getName());
//		model.addAttribute("active", "upload");
//		return "/post/upload";
//	}

	

}
