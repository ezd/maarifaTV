package com.example.entities;

public enum ContentType {
		TextContentType("text"),
		NonTextContentType("nontext");
		String type;
		
		ContentType(String type) {
			this.type=type;
		}
		
		public String getType(){
			return this.type;
		}
}
