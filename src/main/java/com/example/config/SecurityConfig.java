package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.services.UserSecurityService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	 @Autowired
	 UserSecurityService uSecurityService;
	 @Bean
	 public BCryptPasswordEncoder mypasswordEncoder(){
		 return new BCryptPasswordEncoder();
	 }
	
	private static final String[] RESOURCES = {
			"/images/**","/css/**","/js/**","/","/webjars/**","/login","/contactus","/aboutus","/saveUser","/forgetPassword"
			,"/updatePassword","/register","/accessdenide","/activate","/post/**","/upload"
	};
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//.antMatchers("/profile/**").hasRole("USER")
//
//    	.antMatchers("/post/**").hasAnyRole("USER","POSTER","ADMIN")
		http
	    .csrf().disable()
        .authorizeRequests()
        	.antMatchers(RESOURCES).permitAll()
        	.antMatchers("/admin/**").hasRole("ADMIN")
        	.antMatchers("/profile/**").hasRole("USER")
            .anyRequest().authenticated()
        .and()
        .formLogin()
            .loginPage("/login").defaultSuccessUrl("/index")
            .and()
			.logout().invalidateHttpSession(true).permitAll()
			.and();
		
	}
	
	@Autowired
	protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth
        .userDetailsService(uSecurityService).passwordEncoder(mypasswordEncoder());
	}

}
