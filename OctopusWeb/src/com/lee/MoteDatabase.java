package com.lee;



import java.util.*;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.tinyos.message.MoteIF;

public class MoteDatabase {
	private String name;
	private ArrayList<Mote> moteNetwork;
	private boolean mutexFree;
	private boolean active;
	private int maxMoteID;
	
	public static MoteDatabase moteDatabase;
	
	public MoteDatabase(String name) {
		this();
		this.name = name;
		
	}
	
	public MoteDatabase() {
		moteNetwork = new ArrayList<Mote>();
		mutexFree = true;
		active = false;
		moteDatabase = this;
	}
	
	public static MoteDatabase instanceOf() {
		if(moteDatabase == null) 
			moteDatabase = new MoteDatabase();
		return moteDatabase;
	}
	public synchronized void  add(Mote mote, MsgSender ms){
		if(moteNetwork.contains(mote)) {
			//Util.debug("");
			return;
		}
		moteNetwork.ensureCapacity(moteNetwork.size() + 1);
		moteNetwork.add(mote);
		ConsoleCenter.instance().add(Util.MSG_MOTE_ADDED, "Mote Id=["+mote.getMoteId()+"]");
		int id = mote.getMoteId();
		//Ϊʲôactive=trueʱ
		if(maxMoteID < id) {
			maxMoteID = id;
			if(active)
				sendM(ms);
		}
	}
	
	public synchronized void sendMax(MsgSender ms) {
		active = true;
		sendM(ms);
		
	}
	
	private void sendM(MsgSender ms) {
		ms.add(MoteIF.TOS_BCAST_ADDR, Constants.MAX_ID, maxMoteID, "Sending Maximum ID");
	}
	
	public void deleteMote(Mote mote) {
		if(moteNetwork.contains(mote)){
			moteNetwork.remove(mote);
			moteNetwork.trimToSize();
		}
		else {
			Util.debug("[deleteMote] Mote to delete not found");
		}
			
	}
	
	public Mote getMote(int moteID) {
		Mote mote = null;
		Iterator<Mote> iterator = moteNetwork.iterator();
		for(;iterator.hasNext();) {
			mote = (Mote)iterator.next();
			if(moteID == mote.getMoteId()) 
				return mote;
		}
		return null;
	}
	
	public Iterator<Mote> getIterator() {
		return moteNetwork.iterator();
	}

	/*
		These both functions lets someone ask for a mutex
		on the database. If no mutex is available, getMutex()
		returns false, without waiting.
	*/
	
	public void releaseMutex() {
		mutexFree = true;
	}

	public boolean getMutex() {
		if(mutexFree) {
			mutexFree = false;
			return true;
		} else
			return false;
	}
	
	public String getMoteInfo() {
		Iterator<Mote> it = moteNetwork.iterator();
		Map map = new HashMap();
		JSONArray jsonArray = new JSONArray();
		for(;it.hasNext();) {
			map.clear();
			Mote mote = (Mote)it.next();
			map.put("moteid", mote.getMoteId());
			map.put("parentid", mote.getParentId());
			map.put("x", mote.getX());
			map.put("y", mote.getY());
			map.put("reading", mote.getReading());
			map.put("samplingPeriod", mote.getSamplingPeriod());
			map.put("quality",mote.getQuality());
			map.put("timeSinceLastTimeSeen", mote.getTimeSinceLastTimeSeen());
			JSONObject json = JSONObject.fromObject( map ); 
			//Util.debug(json.toString());
			jsonArray.add(json);
		}
		if(jsonArray.size() > 0) return jsonArray.toString();
		else return "";
	}
	
	public String getMoteStatus(int moteid) {
		Iterator<Mote> it = moteNetwork.iterator();
		String moteStatus = "";
		while(it.hasNext()) {
			Mote mote = (Mote) it.next();
			if(moteid == mote.getMoteId()) {
				Map map = new HashMap();
				map.put("moteid", mote.getMoteId());
				map.put("battery", mote.getBattery());
				map.put("requestmode", mote.isInModeAuto());
				map.put("sleepmode", mote.isSleeping());
				map.put("sleepdutycycle",mote.getSleepDutyCycle());
				map.put("awakedutycycle", mote.getAwakeDutyCycle());
				map.put("threshold", mote.getThreshold());
				map.put("periodsampling", mote.getSamplingPeriod());
				JSONObject json = JSONObject.fromObject( map ); 
				moteStatus = json.toString();
				break;
			}
		}
		if(moteStatus == "") Util.debug("GetMoteStatus can not find moteid in DB");
		return moteStatus;
	}
}
