package com.etc.jobsystem.exception.enums;

public enum StateEnums {
	
	//异常定义列表
	FAIL(-1,"操作失败"),
	UPDATEFALSE(-2,"已经更改过了，情等待管理员通过"),
	SUCCESS(0,"操作成功"),
	UNKNOWN_ERROR(1,"未知异常"),
	ADD_SUCCESS(100,"添加成功"),
	FIND_NULL(404,"未查到任何数据"),
	FIND_ERROR(101,"查询异常"),
	ADD_ERROR(102,"添加异常"),
	EDIT_ERROR(103,"修改异常"),
	REMOVE_ERROR(104,"删除异常"),
	NAME_PASS_ERROR(105,"用户名或密码有误"),
	USER_DOES_NOT_EXISTS(106,"用户不存在"),
	USER_ALREADY_EXISTS(107,"用户已存在"),
	USER_TOKEN_NULL(108,"用户未登录"),
	USER_TOKEN_OVERDUE(109,"用户登录过期"),
	USER_TOKEN_ILLEGAL(110,"用户Token非法"),
	USER_INFO_MISMATCH(111,"用户信息不一致"),
	WXUSER_DOES_NOT_SUBSCRIBE(112,"微信用户未关注"),
	WXXCXUSER_AUTO_REG_LOGIN(210,"微信小程序用户自动注册登录"),
	WXID_ALREADY_BINDED_OTHER(113,"该微信已绑定其他账号"),
	WXID_ALREADY_BINDED_SELF(113,"该微信已绑定本账号"),
	USER_ALREADY_BIND_WXID(114,"用户已绑定其他微信id"),
	USER_HAVE_NO_WXID(115,"用户未绑定微信id"),
	CHECK_ERROR(119);
	
	private Integer code;
	private String message;
	private StateEnums(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
	private StateEnums(Integer code) {
		this.code = code;
	}
	private StateEnums() {
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
	
	
}
