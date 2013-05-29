package com.lee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ReadingCenter {
	private static ReadingCenter readingCenter;
	
	public ReadingCenter() {
		readingCenter = this;
	}
	
	public static ReadingCenter instance() {
		if(readingCenter == null) 
			readingCenter = new ReadingCenter();
		return readingCenter;
	}
	
	/*public void  add(short msgType, String msg) {
		//DateTime format:2009-03-09 12:32:12
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdf.format(date);
		String sql = "insert into octopus.message(datetime,type,msgContent) values('" + dateStr+ "',"+ msgType +", '"+ msg +"')";
		//Util.debug(sql);
		DBHelper.ExeQuery(sql);
	}*/
	
	public int getMaxChartID() {
		String sql = "select max(rid) from octopus.readings";
		Util.debug(sql);
		return DBHelper.nCount(sql);
	}
	
	public void add(int moteid, int reading) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdf.format(date);
		String sql = "insert into octopus.readings(datetime,moteid,reading) values('" + dateStr +"'," + 
						moteid +"," + reading + ")";
		Util.debug(sql);
		DBHelper.ExeQuery(sql);
		
	}
	
	public Map getReadingsWithMap(int id) {
		
		String sql = "select * from octopus.readings where rid > " + id;
		ResultSet rs = DBHelper.getResult(sql);
		List listDT = new ArrayList();
		List listID = new ArrayList();
		List listR = new ArrayList();
		Map map = new HashMap();
		String dateStr;
		Date date;
		long datetime;

		try {
			while(rs.next()) {
				dateStr = rs.getString(2);
				dateStr = dateStr.substring(0,dateStr.length() - 2);
				//Util.debug(dateStr);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				date = sdf.parse(dateStr);
				datetime = date.getTime();
				Util.debug(dateStr + " " + datetime); 
				listDT.add(datetime);
				listID.add(rs.getInt(3));
				listR.add(rs.getInt(4));
			}
			if(listDT.size() == 0) {
				map.put("chartid", id);
			}
			else {
				map.put("datetime", listDT);
				map.put("moteid", listID);
				map.put("reading", listR);
				rs.previous();
				map.put("chartid", rs.getInt(1));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
		/*Iterator<Mote> it = moteNetwork.iterator();
		Map map = new HashMap();
		JSONArray jsonArray = new JSONArray();
		for(;it.hasNext();) {
			map.clear();
			Mote mote = (Mote)it.next();
			map.put("moteid", mote.getMoteId());
			map.put(reading"parentid", mote.getParentId());
			map.put("x", mote.getX());
			map.put("y", mote.getY());
			map.put("reading", mote.getReading());
			map.put("samplingPeriod", mote.getSamplingPeriod());
			map.put("quality",mote.getQuality());
			map.put("timeSinceLastTimeSeen", mote.getTimeSinceLastTimeSeen());
			JSONObject json = JSONObject.fromObject( map ); 
			Util.debug(json.toString());
			jsonArray.add(json);
		}
		if(jsonArray.size() > 0) return jsonArray.toString();
		else return "";*/
	}
	/*public String[] getMsg(int id, String msgType) {
		
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
	
	/*private String[] getMsgFromDB(String sql, int id) {
		
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
		
	}*/
}
