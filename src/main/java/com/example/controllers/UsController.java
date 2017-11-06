package com.example.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entities.User;
import com.example.entities.UserToken;
import com.example.services.UserRegistrationService;
import com.example.services.UserTokenService;
import com.example.utils.CustomMailSender;
@Controller
public class UsController {
	
	

	@RequestMapping("/contactus")
	public String getContacus(Model model){
		model.addAttribute("active", "contactus");
		return "contactus";
	}
	
	@RequestMapping("/aboutus")
	public String getAboutus(Model model){
		model.addAttribute("active", "aboutus");
		return "aboutus";
	}
}
