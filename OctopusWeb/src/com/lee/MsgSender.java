package com.lee;




import java.util.*;
import net.tinyos.message.*;
import net.tinyos.packet.*;
import net.tinyos.util.*;

/*
	This class is a thread that manages a queue. The thread
	sends messages and eventually waits a while, depending
	of the option choosed by the user.
	
	TODO :
*/
public class MsgSender extends Thread {
	private LinkedList<MessageToSend> queue;
	private MessageToSend msgToSend;
	private MoteIF gateway;
	private static MsgSender sender;
	
	public MsgSender(MoteIF gateway) {
		queue = new LinkedList<MessageToSend>();
		this.gateway = gateway;
		sender = this;
	}
	
	/*
		The thread either executes tasks or sleep.
	*/
	public static MsgSender instanceOf() {
		if(sender == null) {
			MoteIF moteIF = new MoteIF();
			sender = new MsgSender(moteIF);
		}
		return sender;
	}
	
	public void run() {
		while(true) {
			if (queue.isEmpty())
				try {Thread.sleep(20);} catch (Exception e) { e.printStackTrace();}
			else {
				try {
					msgToSend = (MessageToSend) queue.remove(0);
					gateway.send(msgToSend.sMsg.get_targetId(), msgToSend.sMsg);
					ConsoleCenter.instance().add(Util.MSG_MESSAGE_SENT,msgToSend.string);
					Thread.sleep(Util.SENDER_WAIT_TIME);
				} catch (Exception e) { e.printStackTrace();}
			} 
		}
 }
	
	/*
		This function adds in the queue the messages to send
	*/

	public synchronized void add(int targetId, int request, int parameters, String string) {
		OctopusSentMsg o = new OctopusSentMsg();
		o.set_request((short)request);
		o.set_targetId(targetId);
		o.set_parameters(parameters);
		queue.add(new MessageToSend(o, string));
	}
}

/*
	This class is composed of the message to send, and
	a string that is sent to the consolePanel.
*/

class MessageToSend {
	public OctopusSentMsg sMsg;
	public String string;
	
	public MessageToSend(OctopusSentMsg sMsg, String string) {
		this.string = string;
		this.sMsg = sMsg;
	}
}
