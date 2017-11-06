package com.example.enums;

public enum RoleEnum {
	
	EMPLOYEE("EMPLOYEE"),
	EMPLOYER("EMPLOYER"),
	POSTER("POSTER"),
	ADMIN("ADMIN"),
	USER("USER");
	
	private final String roleName;
	
	private RoleEnum(String roleName) {
		this.roleName=roleName;
	}


	public String getRoleName() {
		return roleName;
	}
	
	

}
