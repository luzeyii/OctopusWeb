package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lee.ConsoleCenter;
import com.lee.Util;

/**
 * Servlet implementation class Console
 */
@WebServlet("/Console")
public class OConsole extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OConsole() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doget");
		service(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("dopost");
		service(request,response);
	}
	
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		if("getInitID".equals(request.getParameter("action"))) {
			int maxID = ConsoleCenter.instance().getMaxMsgID();
			Util.debug(Integer.toString(maxID));
			session.setAttribute("id", maxID);
			//session.setAttribute("chartid", maxID);
		}
		else {
			int id = Integer.parseInt(session.getAttribute("id").toString());
			//Util.debug(Integer.toString(id));
			//Util.debug(request.getParameter("msgType"));
			
			String msgType= request.getParameter("msgType");
			//get message
			
			String[] msg = ConsoleCenter.instance().getMsg(id,msgType);
			StringBuffer sb = new StringBuffer();
			for(int i = 0; i < msg.length - 1; i ++) {
				sb.append(msg[i]);
			}
			
			
			response.setContentType("text/html;charset=gbk");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());	
			//session.setAttribute("msg", sb.toString());
			Util.debug(sb.toString());
			//save the last id
			int maxID = Integer.parseInt(msg[msg.length - 1]);
			session.setAttribute("id", maxID);
		}
	}
}
