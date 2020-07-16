package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.DBConfig.DBType;


public class DBEngine {
	
	//@Autowired
	private static DBSqlServerAdapter dbAdapter;
	
	public static void main(String args[]) {
		DBConfig config = new DBConfig(DBType.SQL_SERVER, "172.105.197.214", "sa", "XtremeApp53811062", 1433, "PoS");
		
		try {
			System.out.println("Start");
			
			dbAdapter = new DBSqlServerAdapter();
			dbAdapter.StartEngine(config);
			
			//dbAdapter.Create("444", "{444}");
			
			//dbAdapter.Select("444");
			List<DBData> result = dbAdapter.SelectAll();
			
			for(DBData data : result) {
				System.out.println(data);
			}
			System.out.println("End");
		} catch (Exception e) {
			System.out.println("Error:" + e);
			e.printStackTrace();
		}
	}
}
