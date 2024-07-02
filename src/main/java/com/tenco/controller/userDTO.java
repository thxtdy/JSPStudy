package com.tenco.controller;

import java.util.HashMap;
import java.util.Map;

public class userDTO {
	private static Map<String, String> users = new HashMap<String, String>();
	private static userDTO INSTANCE = null;

	private userDTO() {}

	public static userDTO getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new userDTO();
		}
		return INSTANCE;
	}
	
	public void put(String id, String password) {
		users.put(id, password);
	}
	
	public int getSize() {
		return users.size();
	}
	
}
