package com.api.utils;

import java.io.IOException;

import com.api.constants.Role;

public class AuthTokenDemoRunner {

	public static void main(String[] args) throws IOException {
		
		for(int i=1;i<=100;i++) {
			System.out.println(AuthTokenProvider.getAuthToken(Role.FD));
		}

	}

}
