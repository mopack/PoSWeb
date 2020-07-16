package com.example.demo;

import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.jpa.repository.Query;


import org.springframework.stereotype.Repository;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;



public class JdbcMSConnect {

	
	/*public static void main(String args[]) {
		Create("339", "{你家的火}");
	}*/
	
	public static void Create(String id, String value) {
		Connection conn = null;
		String sql = "INSERT INTO DATA (ID, VALUE) VALUES (?, ?)";
		
		try{
			SQLServerDataSource ds = new SQLServerDataSource();
			ds.setUser("sa");  
			ds.setPassword("XtremeApp53811062");  
			ds.setServerName("172.105.197.214");  
			ds.setPortNumber(1433);
			ds.setDatabaseName("PoS");
			conn = ds.getConnection();
			
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
	
	/*private void execute(String sql) {
		Connection conn = null;
		
		try{
			SQLServerDataSource ds = new SQLServerDataSource();
			ds.setUser("sa");  
			ds.setPassword("XtremeApp53811062");  
			ds.setServerName("172.105.197.214");  
			ds.setPortNumber(1433);
			ds.setDatabaseName("PoS");
			conn = ds.getConnection();
			
			PreparedStatement ps = conn.prepareStatement(sql);
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
	*/
	
	/*
	public void save(ContentResourceFile contentResourceFile){
		//Connection c = dataSource.getConnection();
		
		Connection conn = null;
        String sql = "INSERT INTO BCS_CONTENT_RESOURCE_FILE (RESOURCE_ID, FILE_DATA, MODIFY_USER, MODIFY_TIME) VALUES ("
        		+"?, ?, ?, ?)";
		try{
			conn = dataSource.getConnection();
			
			// UTIL Date to SQL Date
			java.util.Date utilDate = contentResourceFile.getModifyTime();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            logger.debug("utilDate:" + utilDate);
            logger.debug("sqlDate:" + sqlDate);
            
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, contentResourceFile.getResourceId());
			ps.setBytes(2, contentResourceFile.getFileData());
			ps.setString(3, contentResourceFile.getModifyUser());
			ps.setDate(4, sqlDate);
			ps.executeUpdate();
			ps.close();
		}catch(SQLException e){
			logger.info("JdbcConnect Error!");
			logger.info(e.getMessage());
			throw new RuntimeException(e);			
		}finally{
			if (conn != null){
				try{
					conn.close();
				}catch(SQLException e){
					logger.info("JdbcConnect Error!");
					logger.info(e.getMessage());
				}
			}
		}
	}
	
	public void execute(String sql){
		Connection conn = null;
		
		try{
//			SQLServerDataSource ds = new SQLServerDataSource();
//			ds.setUser("sa");  
//			ds.setPassword("XtremeApp53811062");  
//			ds.setServerName("172.105.197.214");  
//			ds.setPortNumber(1433);
//			ds.setDatabaseName("PoS");
			conn = dataSource.getConnection();
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();
		}catch(SQLException e){
			logger.info("JdbcConnect Error!");
			logger.info(e.getMessage());
			throw new RuntimeException(e);			
		}finally{
			if (conn != null){
				try{
					conn.close();
				}catch(SQLException e){
					logger.info("JdbcConnect Error!");
					logger.info(e.getMessage());
				}
			}
		}
	}
	*/
}
