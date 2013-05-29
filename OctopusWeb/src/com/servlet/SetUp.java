package com.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.tinyos.message.MoteIF;

import com.lee.*;
/**
 * Servlet implementation class SetUp
 */
@WebServlet("/SetUp")
public class SetUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetUp() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init(ServletConfig config) throws ServletException {
    	 MoteIF gateway = new MoteIF();
		Logger logger = new Logger();
		MoteDatabase moteDatabase = new MoteDatabase();
		MsgSender sender = new MsgSender(gateway);
		MsgReceiver receiver = new MsgReceiver(moteDatabase, sender, logger);
		gateway.registerListener(new OctopusCollectedMsg(),receiver);
		sender.start();
		Util.debug("Web Application sets up...."); 
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
