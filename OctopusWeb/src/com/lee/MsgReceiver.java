package com.lee;


import net.tinyos.message.*;
import net.tinyos.packet.*;
import net.tinyos.util.*;
import java.util.Date;

public class MsgReceiver implements MessageListener {
	private MoteDatabase moteDatabase;
	private MsgSender ms;
	private Logger logger;
	
	
	public MsgReceiver(MoteDatabase moteDatabase, MsgSender ms, Logger logger) {
		this.moteDatabase = moteDatabase;
		this.ms = ms;
		this.logger = logger;
		
	}
	
	public void messageReceived(int dest_addr, Message msg) {
		
		if(! (msg instanceof OctopusCollectedMsg)) {
			return ;
		}
		
		OctopusCollectedMsg collectedMsg = (OctopusCollectedMsg) msg;
		int moteID = collectedMsg.get_moteId();
		int reply = collectedMsg.get_reply();
		Mote localMote = moteDatabase.getMote(moteID);
		waitMutex();
		
		if((reply & Constants.REPLY_MASK) == Constants.NO_REPLY) {
			ConsoleCenter.instance().add(Util.MSG_MESSAGE_RECEIVED,"Automatic Msg From Id=" + collectedMsg.get_moteId()
					+ " [reading = " + collectedMsg.get_reading() + "]");
			ReadingCenter.instance().add(collectedMsg.get_moteId(), collectedMsg.get_reading());
			if(localMote == null) {
				localMote = new Mote(collectedMsg.get_moteId(),collectedMsg.get_count(),
									collectedMsg.get_reading(),collectedMsg.get_parentId(),
									collectedMsg.get_quality(),new Date().getTime());
				moteDatabase.add(localMote, ms);
				logger.addRecord(localMote);
			}
			else {
				localMote.setCount(collectedMsg.get_count());
				localMote.setReading(collectedMsg.get_reading());
				localMote.setParentId(collectedMsg.get_parentId());
				localMote.setQuality(collectedMsg.get_quality());
				localMote.setLastTimeSeen(new Date().getTime());
				logger.addRecord(localMote);
			}
		}
		else {
			if(localMote == null) 		// if the mote is NOT in the database
				ConsoleCenter.instance().add(Util.MSG_MESSAGE_RECEIVED,"Reply from unknown mote id=" + collectedMsg.get_moteId());
			else {
				ConsoleCenter.instance().add(Util.MSG_MESSAGE_RECEIVED,"Reply from mote id=" + collectedMsg.get_moteId());
				localMote.setParentId(collectedMsg.get_parentId()); // to change (stack feature)
				localMote.setQuality(collectedMsg.get_quality());
				localMote.setLastTimeSeen(new Date().getTime());
				switch (reply & Constants.REPLY_MASK) {
					case (Constants.BATTERY_AND_MODE_REPLY):
						localMote.setBattery(collectedMsg.get_reading());
						if((reply & Constants.MODE_MASK) == Constants.MODE_AUTO) {
							localMote.setModeAuto();
						} else {
							localMote.setModeQuery();
						}
						if((reply & Constants.SLEEP_MASK) == Constants.SLEEPING) {
							localMote.setSleeping();
						} else {
							localMote.setAwake();
						}
						break;
					case (Constants.PERIOD_REPLY):
						localMote.setSamplingPeriod(collectedMsg.get_reading());
						break;
					case (Constants.THRESHOLD_REPLY):
						localMote.setThreshold(collectedMsg.get_reading());
						break;
					case (Constants.SLEEP_DUTY_CYCLE_REPLY):
						localMote.setSleepDutyCycle(collectedMsg.get_reading());
						break;
					case (Constants.AWAKE_DUTY_CYCLE_REPLY):
						localMote.setAwakeDutyCycle(collectedMsg.get_reading());
						break;
				}
			}
			
		}
		moteDatabase.releaseMutex();
		//requestPanel.moteUpdatedEvent(localMote);
	}
	
	private void waitMutex() {
		while(!moteDatabase.getMutex()) {
			try {
				Thread.sleep(Util.MUTEX_WAIT_TIME_MS);	
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}
