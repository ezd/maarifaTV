package com.example.controllers;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.entities.Role;
import com.example.entities.User;
import com.example.entities.UserToken;
import com.example.enums.RoleEnum;
import com.example.services.UserRegistrationService;
import com.example.services.UserTokenService;
import com.example.utils.CustomMailSender;

@Controller
public class RegistrationController {
	private static final String MSG_TYPE_FAILE = "faile";
	private static final String MSG_TYPE_SUCCESS = "success";
	private static final String SUCCESS_TEXT = "The user successfuly regigerd. Please visit your email and activate your account through the link we send you.";
	private static final String SOMETHING_WRONG_MSG = "Something goes wrong. Unable to save.";
	private static final String EMAIL_ADDRESS_INUSE_MSG = " is already in use. Please try other Email address or reset your account.";
	private static final String EMAIL_ADDRESS_TEXT = "The email address ";
	private static final String FINAL_GREETING = "\n Regards \n Marifa TV";
	private static final String NEW_USEER_ACTIVATE_GREETING = "Welcome respected user, in order to complete your registration, please follow the following link and activate your account\n";
	private static final String ACTIVATE_USER_ACCOUNT_TITLE = "Activate your user account";

	@Autowired
	UserRegistrationService userRegistrationService;
	
	@Autowired
	UserTokenService userTokenService;
	
	
	@Value("${token.expiration.inmin}")
	int expirationMiniut;
	
	@Autowired
	CustomMailSender customMailSender;
	
	@Value("${webmaster.email}")
	String webmasterEmail;
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String getRegister(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("active", "register");
		return "register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String postRegister(Model model, User user, HttpServletRequest request) {
		System.out.println(user.toString());

		Role userRole = new Role(RoleEnum.USER);
		Set<Role> roles = new HashSet<>();
		roles.add(userRole);
		if (userRegistrationService.isUserNameExists(user.getUsername())) {
			model.addAttribute("msg", EMAIL_ADDRESS_TEXT + user.getUsername() + EMAIL_ADDRESS_INUSE_MSG);
		} else {
			user.setActive(false);
			User savedUser=userRegistrationService.saveUserInfo(user, roles);
			if (null == savedUser) {
				model.addAttribute("msg", SOMETHING_WRONG_MSG);
				model.addAttribute("msgType", MSG_TYPE_SUCCESS);
			} else {
				
//				Authentication authentication = new UsernamePasswordAuthenticationToken(user, null,
//						user.getAuthorities());
//				SecurityContextHolder.getContext().setAuthentication(authentication);
				UserToken ut=userTokenService.createToken(user, expirationMiniut);
				String url=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/activate?id="+user.getId()+"&&token="+ut.getToken();
				String message=NEW_USEER_ACTIVATE_GREETING+url+FINAL_GREETING;
				if (customMailSender.sendEmail(user, ACTIVATE_USER_ACCOUNT_TITLE, message,webmasterEmail)){
					System.out.println("email successfuly send");
					model.addAttribute("msg", SUCCESS_TEXT);
					model.addAttribute("msgType", MSG_TYPE_SUCCESS);
					//return "redirect:/profile/" + savedUser.getId();
				}else{
					model.addAttribute("msg", SOMETHING_WRONG_MSG);
					model.addAttribute("msgType", MSG_TYPE_FAILE);
				}
				
			}
		}
		model.addAttribute("user", user);
		model.addAttribute("active", "register");

		return "register";
	}


	@RequestMapping(value="/getUser/{userId}",method=RequestMethod.GET)
	public @ResponseBody User getUser(@PathVariable("userId") Long userId){
		User userInfoById = userRegistrationService.getUserInfo(userId);
		return userInfoById;
	}
	
	@RequestMapping(value="/deleteUser/{userId}",method=RequestMethod.GET)
	public @ResponseBody String deleteUser(@PathVariable("userId") Long userId){
		try {
			userRegistrationService.deletUserInfo(userId);
			return "successfully deleted";
		} catch (Exception e) {
			// TODO: handle exception
			return "not able deleted";
		}
		
		
	}
	
}
