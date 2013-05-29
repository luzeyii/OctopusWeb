package com.lee;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConsoleCenter {
	private static ConsoleCenter consoleCenter;
	
	public ConsoleCenter() {
		consoleCenter = this;
	}
	
	public static ConsoleCenter instance() {
		if(consoleCenter == null) 
			consoleCenter = new ConsoleCenter();
		return consoleCenter;
	}
	
	public void  add(short msgType, String msg) {
		//DateTime format:2009-03-09 12:32:12
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdf.format(date);
		String sql = "insert into octopus.message(datetime,type,msgContent) values('" + dateStr+ "',"+ msgType +", '"+ msg +"')";
		//Util.debug(sql);
		DBHelper.ExeQuery(sql);
	}
	
	public int getMaxMsgID() {
		String sql = "select max(msgid) from octopus.message";
		Util.debug(sql);
		return DBHelper.nCount(sql);
	}
	
	public String[] getMsg(int id, String msgType) {
		
		String sql = "select * from octopus.message where msgid >= "+ id + " and type in (";
		if(msgType.contains("receive")) sql += "0,";
		if(msgType.contains("send")) sql += "1,";
		if(msgType.contains("add")) sql += "2,";
		if(msgType.contains("update")) sql += "3,";
		sql = sql.substring(0, sql.length() - 1) + ")";
		Util.debug(sql);
		return(getMsgFromDB(sql,id));
	}
	
	/*public String[] getMsg(Date date) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = sdf.format(date);
		String sql = "select * from octopus.message where datetime >= '"+ datetime +"'";
		Util.debug(sql);
		return(getMsgFromDB(sql));
		
	}*/
	
	private String[] getMsgFromDB(String sql, int id) {
		
		String[] msg = null;
		ResultSet rs = DBHelper.getResult(sql);
		try {
			if(rs != null && rs.next() ) {
					
					rs.last();
					msg = new String[rs.getRow() + 1];
					rs.beforeFirst();
					
					int i=0;
					while(rs.next() && msg.length > 0) {
						msg[i++] = "[" + rs.getString(2) +"] [MsgType="+ rs.getInt(3) + "]" + rs.getString(4) + "\n";
					}
					//save the last msgid
					rs.previous();
					msg[i] = Integer.toString(rs.getInt(1));
					rs.close();
					
			}
			else {
				if(rs != null) {
					msg = new String[1];
					msg[0] = Integer.toString(id);
				}
				else {
					Util.debug("rs is null");
					
				}
			}
			return msg;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
}
