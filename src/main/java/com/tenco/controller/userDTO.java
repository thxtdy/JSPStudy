package com.tenco.controller;

import java.util.Map;

public class userDTO {
	private static Map<String, String> users = new Map<String, String>
	private static userDTO INSTANCE;

	private userDTO() {
	}

	public static userDTO getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new userDTO();
		}
		return INSTANCE;
	}
	
}
