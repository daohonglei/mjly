package com.consumer.dao.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConnectionListConfig {
	
	@Value("${ConnectionList.max}")
	private int max;
	
	@Value("${ConnectionList.url}")
	private String url;
	
	@Value("${ConnectionList.username}")
	private String username;
	
	@Value("${ConnectionList.password}")
	private String password;
	
	@Value("${ConnectionList.driver}")
	private String driver;

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
	
	 @Bean 
	 public ConnectionList connectionList () { 
		 ConnectionList connectionList=new ConnectionList(max,url,username,password,driver); 
		 return connectionList; 
	 }
}
