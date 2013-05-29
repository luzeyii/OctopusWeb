package com.servlet;

import com.lee.Util;

import com.lee.Constants;
import com.lee.Mote;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.tinyos.message.MoteIF;

import com.lee.MoteDatabase;
import com.lee.MsgSender;

/**
 * Servlet implementation class ORequest
 */
@WebServlet("/ORequest")
public class ORequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ORequest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		response.setContentType("text/html;charset=gbk");
		if("getMoteStatus".equals(action)) {
			int moteid = Integer.parseInt(request.getParameter("moteid"));
			String moteStatus = MoteDatabase.instanceOf().getMoteStatus(moteid);
			response.getWriter().print(moteStatus);
		}
		else if("setsleep".equals(action)) {
			int moteid = Integer.parseInt(request.getParameter("moteid"));
			boolean broadcast = Boolean.parseBoolean(request.getParameter("broadcast"));
			MsgSender sender = MsgSender.instanceOf();
			Mote localMote;
			if(broadcast) {
				sender.add(MoteIF.TOS_BCAST_ADDR, (int)Constants.SLEEP_REQUEST,
						0, "setSleeping() [Id=broadcast]");
				for (Iterator it=MoteDatabase.instanceOf().getIterator(); it.hasNext(); ) {
					localMote = (Mote)it.next();
					localMote.setSleeping();
				}
				
			}
			else {
				localMote = MoteDatabase.instanceOf().getMote(moteid);
				sender.add(localMote.getMoteId(), (int)Constants.SLEEP_REQUEST,
						0, "setSleeping() [Id="+localMote.getMoteId()+"]");
				localMote.setSleeping();
				String moteStatus = MoteDatabase.instanceOf().getMoteStatus(moteid);
				response.getWriter().print(moteStatus);
			}
		}
		else if("setawake".equals(action)) {
			int moteid = Integer.parseInt(request.getParameter("moteid"));
			boolean broadcast = Boolean.parseBoolean(request.getParameter("broadcast"));
			MsgSender sender = MsgSender.instanceOf();
			Mote localMote;
			if(broadcast) {
				sender.add(MoteIF.TOS_BCAST_ADDR, (int)Constants.WAKE_UP_REQUEST,
						0, "setWakeUp() [Id=broadcast]");
				for (Iterator it=MoteDatabase.instanceOf().getIterator(); it.hasNext(); ) {
					localMote = (Mote)it.next();
					localMote.setAwake();
				}
				
			}
			else {
				localMote = MoteDatabase.instanceOf().getMote(moteid);
				sender.add(localMote.getMoteId(), (int)Constants.WAKE_UP_REQUEST,
						0, "setWakeUp() [Id="+localMote.getMoteId()+"]");
				localMote.setAwake();
				String moteStatus = MoteDatabase.instanceOf().getMoteStatus(moteid);
				response.getWriter().print(moteStatus);
			}
		}
		else if("setmodeauto".equals(action)) {
			int moteid = Integer.parseInt(request.getParameter("moteid"));
			boolean broadcast = Boolean.parseBoolean(request.getParameter("broadcast"));
			MsgSender sender = MsgSender.instanceOf();
			Mote localMote;
			if(broadcast) {
				sender.add(MoteIF.TOS_BCAST_ADDR, (int)Constants.SET_MODE_AUTO_REQUEST,
						0, "setModeAuto() [Id=broadcast]");
				for (Iterator it=MoteDatabase.instanceOf().getIterator(); it.hasNext(); ) {
					localMote = (Mote)it.next();
					localMote.setModeAuto();
				}
				
			}
			else {
				localMote = MoteDatabase.instanceOf().getMote(moteid);
				sender.add(localMote.getMoteId(), (int)Constants.SET_MODE_AUTO_REQUEST,
						0, "setModeAuto() [Id="+localMote.getMoteId()+"]");
				localMote.setModeAuto();
				String moteStatus = MoteDatabase.instanceOf().getMoteStatus(moteid);
				response.getWriter().print(moteStatus);
			}
		}
		else if("setmodequery".equals(action)) {
			int moteid = Integer.parseInt(request.getParameter("moteid"));
			boolean broadcast = Boolean.parseBoolean(request.getParameter("broadcast"));
			MsgSender sender = MsgSender.instanceOf();
			Mote localMote;
			if(broadcast) {
				sender.add(MoteIF.TOS_BCAST_ADDR, (int)Constants.SET_MODE_QUERY_REQUEST,
						0, "setModeQuery() [Id=broadcast]");
				for (Iterator it=MoteDatabase.instanceOf().getIterator(); it.hasNext(); ) {
					localMote = (Mote)it.next();
					localMote.setModeQuery();
				}
				
			}
			else {
				localMote = MoteDatabase.instanceOf().getMote(moteid);
				sender.add(localMote.getMoteId(), (int)Constants.SET_MODE_QUERY_REQUEST,
						0, "setModeQuery() [Id="+localMote.getMoteId()+"]");
				localMote.setModeQuery();
				String moteStatus = MoteDatabase.instanceOf().getMoteStatus(moteid);
				response.getWriter().print(moteStatus);
			}
		}
		else if("setsleepdutycycle".equals(action)) {
			int moteid = Integer.parseInt(request.getParameter("moteid"));
			int value = Integer.parseInt(request.getParameter("value"));
			boolean broadcast = Boolean.parseBoolean(request.getParameter("broadcast"));
			MsgSender sender = MsgSender.instanceOf();
			Mote localMote;
			if(broadcast) {
				sender.add(MoteIF.TOS_BCAST_ADDR, (int)Constants.SET_SLEEP_DUTY_CYCLE_REQUEST,
						Util.dutyCycleToInt(value),
						"Set SleepDutyCycle = "+value+"% [Id=broadcast]");
				for (Iterator it=MoteDatabase.instanceOf().getIterator(); it.hasNext(); ) {
					localMote = (Mote)it.next();
					localMote.setSleepDutyCycle(Util.dutyCycleToInt(value));
				}
				
			}
			else {
				localMote = MoteDatabase.instanceOf().getMote(moteid);
				sender.add(localMote.getMoteId(), (int)Constants.SET_SLEEP_DUTY_CYCLE_REQUEST,
						Util.dutyCycleToInt(value),
						"Set SleepDutyCycle = "+value+"% [Id="+localMote.getMoteId()+"]");
				localMote.setSleepDutyCycle(Util.dutyCycleToInt(value));
				String moteStatus = MoteDatabase.instanceOf().getMoteStatus(moteid);
				response.getWriter().print(moteStatus);
			}
		}
		else if("setawakedutycycle".equals(action)) {
			int moteid = Integer.parseInt(request.getParameter("moteid"));
			int value = Integer.parseInt(request.getParameter("value"));
			boolean broadcast = Boolean.parseBoolean(request.getParameter("broadcast"));
			MsgSender sender = MsgSender.instanceOf();
			Mote localMote;
			if(broadcast) {
				sender.add(MoteIF.TOS_BCAST_ADDR, (int)Constants.SET_AWAKE_DUTY_CYCLE_REQUEST,
						Util.dutyCycleToInt(value),
						"Set AwakeDutyCycle = "+(int)value+"% [Id=broadcast]");
				for (Iterator it=MoteDatabase.instanceOf().getIterator(); it.hasNext(); ) {
					localMote = (Mote)it.next();
					localMote.setAwakeDutyCycle(Util.dutyCycleToInt(value));
				}
				
			}
			else {
				localMote = MoteDatabase.instanceOf().getMote(moteid);
				sender.add(localMote.getMoteId(), (int)Constants.SET_AWAKE_DUTY_CYCLE_REQUEST,
						Util.dutyCycleToInt(value),
						"Set AwakeDutyCycle = "+ value +"% [Id="+localMote.getMoteId()+"]");
				localMote.setAwakeDutyCycle(Util.dutyCycleToInt(value));
				String moteStatus = MoteDatabase.instanceOf().getMoteStatus(moteid);
				response.getWriter().print(moteStatus);
			}
		}
		else if("setthreshold".equals(action)) {
			int moteid = Integer.parseInt(request.getParameter("moteid"));
			int value = Integer.parseInt(request.getParameter("value"));
			boolean broadcast = Boolean.parseBoolean(request.getParameter("broadcast"));
			MsgSender sender = MsgSender.instanceOf();
			Mote localMote;
			if(broadcast) {
				sender.add(MoteIF.TOS_BCAST_ADDR, (int)Constants.SET_THRESHOLD_REQUEST,
						Util.thresholdToInt(value),
						"Set Threshold = "+value+"% [Id=broadcast]");
				for (Iterator it=MoteDatabase.instanceOf().getIterator(); it.hasNext(); ) {
					localMote = (Mote)it.next();
					localMote.setThreshold(value);
				}
				
			}
			else {
				localMote = MoteDatabase.instanceOf().getMote(moteid);
				sender.add(localMote.getMoteId(), (int)Constants.SET_THRESHOLD_REQUEST,
						Util.thresholdToInt(value),
						"Set Threshold = "+ value +"% [Id="+localMote.getMoteId()+"]");
				localMote.setThreshold(value);
				String moteStatus = MoteDatabase.instanceOf().getMoteStatus(moteid);
				response.getWriter().print(moteStatus);
			}
		}
		else if("setperiodsampling".equals(action)) {
			int moteid = Integer.parseInt(request.getParameter("moteid"));
			int value = Integer.parseInt(request.getParameter("value"));
			boolean broadcast = Boolean.parseBoolean(request.getParameter("broadcast"));
			MsgSender sender = MsgSender.instanceOf();
			Mote localMote;
			if(broadcast) {
				sender.add(MoteIF.TOS_BCAST_ADDR, (int)Constants.SET_PERIOD_REQUEST,
						value, "Set PeriodSampling = "+value+"ms [Id=broadcast]");
				for (Iterator it=MoteDatabase.instanceOf().getIterator(); it.hasNext(); ) {
					localMote = (Mote)it.next();
					localMote.setSamplingPeriod(value);
				}
				
			}
			else {
				localMote = MoteDatabase.instanceOf().getMote(moteid);
				sender.add(localMote.getMoteId(), (int)Constants.SET_PERIOD_REQUEST,
						value, "Set PeriodSampling = "+value+"ms [Id="+localMote.getMoteId()+"]");
				localMote.setSamplingPeriod(value);
				String moteStatus = MoteDatabase.instanceOf().getMoteStatus(moteid);
				response.getWriter().print(moteStatus);
			}
		}
		else if("getreading".equals(action)) {
			int moteid = Integer.parseInt(request.getParameter("moteid"));
			boolean broadcast = Boolean.parseBoolean(request.getParameter("broadcast"));
			MsgSender sender = MsgSender.instanceOf();
			Mote localMote;
			if(broadcast) {
				sender.add(MoteIF.TOS_BCAST_ADDR, (int)Constants.GET_READING_REQUEST,
						0, "Reading() [Id=broadcast]");
				
			}
			else {
				localMote = MoteDatabase.instanceOf().getMote(moteid);
				sender.add(localMote.getMoteId(), (int)Constants.GET_READING_REQUEST,
						0, "Reading() [Id="+localMote.getMoteId()+"]");
			}
		}
		else if("setbootnetwork".equals(action)) {
			MsgSender sender = MsgSender.instanceOf();
			sender.add(MoteIF.TOS_BCAST_ADDR,Constants.BOOT_REQUEST,0,"Sending Boot Message");
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
