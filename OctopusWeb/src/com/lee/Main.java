package com.lee;



import java.text.SimpleDateFormat;
import java.util.Date;

import net.tinyos.message.MoteIF;

public class Main {
	
	public static void main(String[] args) { 
		
		/*MoteIF gateway = new MoteIF();
		Logger logger = new Logger();
		MoteDatabase moteDatabase = new MoteDatabase();
		MsgSender sender = new MsgSender(gateway);
		MsgReceiver receiver = new MsgReceiver(moteDatabase, sender, logger);
		gateway.registerListener(new OctopusCollectedMsg(),receiver);
		sender.start();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] str = ConsoleCenter.instance().getMsg(new Date());
		for(int i =0; i<str.length; i  ++) {
			Util.debug(str[i]);
		}*/
		long l  = new Date().getTime();
		Util.debug(Long.toString(l));
	}
}
