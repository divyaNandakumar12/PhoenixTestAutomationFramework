package com.api.constants;

public enum Warrenty {
	
	 IN_WARRENTY(1),
	 OUT_OF_WARRENTY(2);
	
	int code;
	private Warrenty(int code) {
		this.code=code;
	}
	
	public int getCode() {
		return code;
	}

}
