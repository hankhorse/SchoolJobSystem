package com.etc.jobsystem.exception;


import com.etc.jobsystem.exception.enums.StateEnums;

public class MyException extends RuntimeException {
	
	private Integer code;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
	
	public MyException(Integer code, String message) {
		super(message);
		this.code = code;
	}
	
	public MyException(StateEnums enums) {
		super(enums.getMessage());
		this.code = enums.getCode();
	}
	
	public MyException(StateEnums enums, String message) {
		super(message);
		this.code = enums.getCode();
	}

}
