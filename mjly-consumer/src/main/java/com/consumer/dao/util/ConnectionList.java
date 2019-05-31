package com.consumer.dao.util;

import java.util.ArrayList;
import java.util.List;

public class ConnectionList {
	
	private int max;
	
	private String url;
	
	private String username;
	
	private String password;
	
	private String driver;
	
	private List<ConnectionUtil> list=new ArrayList<ConnectionUtil>();
	
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}

	public ConnectionList(int max, String url, String username, String password, String driver) {
		super();
		this.max = max;
		this.url = url;
		this.username = username;
		this.password = password;
		this.driver = driver;
		for (int i = 0; i <max; i++) { 
			 ConnectionUtil connectionUtil=new ConnectionUtil(url,username,password,driver);
			 list.add(connectionUtil); 
		 } 
	}

	public ConnectionUtil getConnectionUtil() {
		for (ConnectionUtil connectionUtil : list) {
			if(!connectionUtil.isUse()) {
				return connectionUtil;
			}
		}
		return getConnectionUtil();
		
	}
	public int userNamber() {
		int i=0;
		for (ConnectionUtil connectionUtil : list) {
			if(connectionUtil.isUse()) i++;
		}
		return i;
	}
	
}
