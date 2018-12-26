package com.yxbkj.yxb.entity.module;

public class Constants {
	public static final int CODE_MAX_TIME=1*60; //短信验证码最大值秒数
	public static final int YIAN_TOKEN_MAX_TIME=1*60*60*24; //易安token最大值秒数
	public static final int TOKEN_MAX_TIME=0; //用户Token有效期
	public static final String SUCCESS="SUCCESS";
	
	public static final String REDIS_RESOURCE="REDIS_RESOURCE";
	
	public static final String REDIS_RESOURCE_TREE="REDIS_RESOURCE_TREE";





	public enum RoleType{
		initial("内置角色",1),
		custom("自定义角色",2),
		business("业务角色",3);
		
		private String name;
		private int value;
		
		public String getName() {
			return name;
		}
		public int getValue() {
			return value;
		}

		RoleType(String name, int value) {
			this.name = name;
			this.value = value;
		}
	}
	
	public enum ResourceType{
		module("模块",1),
		column("栏目",2),
		button("按钮",3);
		
		private String name;
		private int value;
		public String getName() {
			return name;
		}
		public int getValue() {
			return value;
		}

		ResourceType(String name, int value) {
			this.name = name;
			this.value = value;
		}
		
	}
}
