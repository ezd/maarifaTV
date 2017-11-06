package com.example.controllers;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entities.Role;
import com.example.entities.User;
import com.example.entities.UserToken;
import com.example.services.UserRegistrationService;
import com.example.services.UserTokenService;
import com.example.utils.CustomMailSender;
@Controller
public class LoginController {
	
	private static final String UNSUCCESSFULL_USER_SEARCH_TITLE = "Unsuccessfull user search";

	private static final String FORGET_PASSWORD_RESET_EMAIL_TITLE = "Forget password reset";

	private static final String FINAL_GREETING = "\n Regards \n Marifa TV";

	private static final String FORGET_PASSWORD_MESSAGE = "Please follow the following link to reset your password.\n";

	private static final String NEW_USEER_GREETING = "Respected user, we are not able to find any user by your email. Please register or try other email address ";

	@Autowired
	UserRegistrationService userRegistrationService;
	
	@Autowired
	UserTokenService userTokenService;
	@Autowired
	CustomMailSender customMailSender;
	
	@Value("${webmaster.email}")
	String webmasterEmail;
	
	@Value("${token.expiration.inmin}")
	int expirationMiniut;
	
	@RequestMapping("/login")
	public String getLogin(){
		return "login";
	}
	@RequestMapping(value="/forgetPassword", method=RequestMethod.GET)
	public String forgetPassword(Model model,User user){
		System.out.println("come to the get");
		return "emailForm";
	}
	
	@RequestMapping(value="/updatePassword",method=RequestMethod.GET)
	public String updatePassword(Model model,@RequestParam("id") Long id,@RequestParam("token") String token){
		boolean isValidUser=userRegistrationService.isValidUser(id);
		if(!isValidUser){
			model.addAttribute("urlmsg","Unregisterd User.");
		}else if(userTokenService.isTokenNotExist(id,token)){
			model.addAttribute("urlmsg","Unknown token");
		}else if(userTokenService.isTokenExpired(id,token)){
			model.addAttribute("urlmsg", "The token has expried");
		}else{
			model.addAttribute("urlmsg", "success");
			model.addAttribute("userId", id);
		}
		
		return "resetPassword";		
	}
	//activate
	
	@RequestMapping(value="/activate",method=RequestMethod.GET)
	public String activateAccount(Model model,@RequestParam("id") Long id,@RequestParam("token") String token){
		System.out.println("in activate with role of ");
		User existingUser=null;
		boolean isValidUser=userRegistrationService.isValidUser(id);
		if(isValidUser){
			existingUser=userRegistrationService.getUserInfo(id);
		}
		if(!isValidUser){
			model.addAttribute("msg","Unregisterd User.");
		}else if(userTokenService.isTokenNotExist(id,token)){
			model.addAttribute("msg","Unknown token");
		}else{
			System.out.println("in activate redirect");
			existingUser.setActive(true);
			existingUser.setEnabled(true);
			for(Role r:existingUser.getRoles())
				System.out.println(r.getName());
			userRegistrationService.updateUserInfo(existingUser);
			
			return "redirect:/profile/"+id;
		}
		
		return "resetPassword";		
	}
	
	@RequestMapping(value = "/profile/{id}", method = RequestMethod.GET)
	public String getProfile(Model model,Principal principal,@PathVariable("id") Long sendId) {
		System.out.println("in the profile controller");
		if(principal!=null){
			System.out.println("in the principal if");
			String username=principal.getName();
			User backendUser=userRegistrationService.getUserByEmail(username);
			if(sendId!=backendUser.getId()){
				System.out.println("in the backend if");
				return "redirect:/limited/info";
			}else{
				System.out.println("in the backed else");
				model.addAttribute("user", backendUser);
				return "profile";
			}
			
		}else{
			System.out.println("in the prencipal else");
			return "/";
		}		
	}
	
	@RequestMapping(value="/updatePassword",method=RequestMethod.POST)
	public String updatePasswordPost(Model model,@RequestParam("userId") Long userId,@RequestParam("password") String newPassword){
		System.out.println("the new password is:"+newPassword);
		if(userRegistrationService.updateUserPassword(userId,newPassword)){
			model.addAttribute("updatemsg", "success");
			System.out.println("success");
		}else{
			System.out.println("fail");
			model.addAttribute("updatemsg", "fail");
		}
		model.addAttribute("urlmsg", "success");
		return "resetPassword";		
	}
	
	@RequestMapping(value="/forgetPassword", method=RequestMethod.POST)
	public String forgetPasswordSave(HttpServletRequest request,@RequestParam("email") String userEmail,Model model){
		User user=userRegistrationService.getUserByEmail(userEmail);
		if(null!=user){
			UserToken ut=userTokenService.createToken(user, expirationMiniut);
			String url=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/updatePassword?id="+user.getId()+"&&token="+ut.getToken();
			String message=FORGET_PASSWORD_MESSAGE+url+FINAL_GREETING;
			if (customMailSender.sendEmail(user, FORGET_PASSWORD_RESET_EMAIL_TITLE, message,webmasterEmail)){
				System.out.println("email successfuly send");
			};
		}else{
			System.out.println("dont find the email used in the system");
			String url=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/register";
			String message=NEW_USEER_GREETING+url+FINAL_GREETING;
			if (customMailSender.sendEmail(user, UNSUCCESSFULL_USER_SEARCH_TITLE, message,webmasterEmail)){
				System.out.println("email successfuly send");
			};
		}
		model.addAttribute("emailSent", "true");
		return "emailForm";
	}
	
	@RequestMapping("/")
	public String getIndex(Model model){
		model.addAttribute("active", "index");
		return "index";
	}
	
	@RequestMapping("/accessdenide")
	public String getDenied(){
		return "accessdenidePage";
	}

}
