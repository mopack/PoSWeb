package com.example.demo;

import java.util.List;

public interface DBAdapter {

	public void StartEngine(DBConfig dbConfig) throws Exception;
	
	public void Create(String id, String value) throws Exception;

	public void Update(String id, String value) throws Exception;
	
	public void Delete(String id) throws Exception;
	
	public DBData Select(String id) throws Exception;
	
	public List<DBData> SelectAll() throws Exception;
}
