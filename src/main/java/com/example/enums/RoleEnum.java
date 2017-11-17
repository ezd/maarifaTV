package com.example.enums;

public enum RoleEnum {
	
	EMPLOYEE("ROLE_EMPLOYEE"),
	EMPLOYER("ROLE_EMPLOYER"),
	POSTER("ROLE_POSTER"),
	ADMIN("ROLE_ADMIN"),
	USER("ROLE_USER");
	
	private final String roleName;
	
	private RoleEnum(String roleName) {
		this.roleName=roleName;
	}


	public String getRoleName() {
		return roleName;
	}
	
	

}
