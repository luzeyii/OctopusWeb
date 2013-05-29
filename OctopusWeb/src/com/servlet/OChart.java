package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import com.lee.ConsoleCenter;
import com.lee.ReadingCenter;
import com.lee.Util;
import java.util.*;

/**
 * Servlet implementation class Chart
 */
@WebServlet("/Chart")
public class OChart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OChart() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		if("getInitID".equals(request.getParameter("action"))) {
			int maxID = ReadingCenter.instance().getMaxChartID();
			//Util.debug(Integer.toString(maxID));
			session.setAttribute("chartid", maxID);
		}
		else {
			int chartid = Integer.parseInt(session.getAttribute("chartid").toString());
			response.setContentType("text/html;charset=gbk");
			PrintWriter out = response.getWriter();
			String when = request.getParameter("when");
			if("now".equals(when)) {
				Map map = (Map)ReadingCenter.instance().getReadingsWithMap(chartid);
				chartid = Integer.parseInt(map.get("chartid").toString());
				session.setAttribute("chartid", chartid);
				if(map.size() == 1) {
					out.print("null");
				}
				else {
					JSONObject json = JSONObject.fromObject( map ); 
					out.print(json.toString());
				}
			}
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
