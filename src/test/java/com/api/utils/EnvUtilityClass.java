package com.api.utils;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvUtilityClass {
	
	public static Dotenv enDotenv;
	
	private EnvUtilityClass() {
		
	}
	
	static {
		enDotenv=Dotenv.load();
	}
	
	public static String getValue(String key) {
		return enDotenv.get(key);
	}

}
