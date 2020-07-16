package com.example.demo;

import lombok.Data;


@Data
public class DBConfig {
	
	public enum DBType{
		SQL_SERVER,
		BLOCK_CHAIN
	}

	private DBType dbType;
	private String url;
	private String userName;
	private String password;
	private Integer port;
	private String DBName;
	
	
	public DBConfig(DBType dbType, String url, String userName, String password, Integer port, String dBName) {
		super();
		this.dbType = dbType;
		this.url = url;
		this.userName = userName;
		this.password = password;
		this.port = port;
		DBName = dBName;
	}
}
