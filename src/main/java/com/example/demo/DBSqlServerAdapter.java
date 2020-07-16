package com.example.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import lombok.Data;

@Data
public class DBSqlServerAdapter implements DBAdapter{
	
	private DBConfig dbConfig;


	@Override
	public void StartEngine(DBConfig dbConfigInput) throws Exception {
		this.dbConfig = dbConfigInput;
	}
	
	@Override
	public void Create(String id, String value) throws Exception {
		Connection conn = null;
		
		try{
			SQLServerDataSource ds = new SQLServerDataSource();
			ds.setUser(dbConfig.getUserName());  
			ds.setPassword(dbConfig.getPassword());  
			ds.setServerName(dbConfig.getUrl());  
			ds.setPortNumber(dbConfig.getPort());
			ds.setDatabaseName(dbConfig.getDBName());
			conn = ds.getConnection();
			
			String sql = "INSERT INTO DATA (ID, VALUE) VALUES (?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, value);
			ps.executeUpdate();
			
			ps.close();
		}catch(SQLException e){
			System.out.println("JdbcConnect Error!");
			System.out.println(e.getMessage());
			throw new RuntimeException(e);			
		}finally{
			if (conn != null){
				try{
					conn.close();
				}catch(SQLException e){
					System.out.println("JdbcConnect Error!");
					System.out.println(e.getMessage());
				}
			}
		}
	}
	
	@Override
	public void Update(String id, String value) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Delete(String id) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<DBData> SelectAll() throws Exception {
		Connection conn = null;
		
		try{
			SQLServerDataSource ds = new SQLServerDataSource();
			ds.setUser(dbConfig.getUserName());  
			ds.setPassword(dbConfig.getPassword());  
			ds.setServerName(dbConfig.getUrl());  
			ds.setPortNumber(dbConfig.getPort());
			ds.setDatabaseName(dbConfig.getDBName());
			conn = ds.getConnection();
			
			
			String sql = "select * from DATA";
			
			Statement stmt=conn.createStatement();

			ResultSet rs = stmt.executeQuery(sql);
	
			List<DBData> result = new ArrayList();
			while(rs.next()) {
				DBData dbData = new DBData();
				
				for(int i = 1; i <= 2; i++) {
					System.out.println("[Select Result] i=" + i + ", s=" + rs.getString(i)); 
				}
				dbData.setId(rs.getString(1));
				dbData.setValue(rs.getString(2));
				
				result.add(dbData);
			}
			
			conn.close(); 
			return result;
			
		}catch(Exception e){
			System.out.println("[SelectAll] error:" + e);
			return null;
		}
	}
	
	@Override
	public DBData Select(String id) throws Exception {
		Connection conn = null;
		
		try{
			SQLServerDataSource ds = new SQLServerDataSource();
			ds.setUser(dbConfig.getUserName());  
			ds.setPassword(dbConfig.getPassword());  
			ds.setServerName(dbConfig.getUrl());  
			ds.setPortNumber(dbConfig.getPort());
			ds.setDatabaseName(dbConfig.getDBName());
			conn = ds.getConnection();
			
			
			String sql = "select * from DATA where ID = N'" + id + "'";
			
			Statement stmt=conn.createStatement();

			ResultSet rs = stmt.executeQuery(sql);
	
			DBData dbData = new DBData();
			while(rs.next()) {
				for(int i = 1; i <= 2; i++) {
					System.out.println("[Select Result] i=" + i + ", s=" + rs.getString(i)); 
				}
				dbData.setId(id);
				dbData.setValue(rs.getString(2));
			}
			System.out.println("dbData:"+dbData);
			
			conn.close(); 
			return dbData;
		}catch(Exception e){
			System.out.println("[Select] error:" + e);
			return null;
		}
	}



}
