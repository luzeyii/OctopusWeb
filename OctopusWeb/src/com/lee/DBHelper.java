package com.lee;


import java.sql.*;

/**
 * 
 */
public class DBHelper {
	DBHelper(){
	}
	
	/**
	 *
	 * @return
	 * @throws Exception
	 */
	public static Connection getConnection(){
		String CLASSFORNAME = Constants.MYSQL_CLASSNAME;
		
		Connection conn;		
		
		try{
			Class.forName(CLASSFORNAME);
			conn=java.sql.DriverManager.getConnection(Constants.MYSQL_CONNECTION,Constants.MYSQL_USERNAME,Constants.MYSQL_PASSWORD);
			
			return conn;
		}
		catch(Exception e){
			System.out.println("getConnection error\n");
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static int nCount(String sql){
		Connection conn = getConnection();
		int count = 0;
		
		try{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				count = rs.getInt(1);
				rs.close();
			}
			stmt.close();
			conn.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return count;
	}
	
	
	public static void ExeQuery(String sql){
		Connection conn = getConnection();
		
		try{
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			conn.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	public static ResultSet getResult(String sql){
		Connection conn = getConnection();
		ResultSet rs = null;
		
		try{
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			//stmt.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return rs;
	}
	
	public static void CloseConnection(Connection conn){
		try{
			conn.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void CloseStatement(Statement stmt){
		try{
			stmt.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void CloseResultSet(ResultSet rs){
		try{
			rs.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
