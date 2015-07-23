package com.jeet.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeet.api.Login;
import com.jeet.db.DAO;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("userId");
		String auth = request.getParameter("auth");
		Login login = DAO.instance().getLoginDetail(userId);
		PrintWriter out = response.getWriter();
		if (auth == null) {
			String password = request.getParameter("password");
			if( login != null && password.equals(login.getPassword())) {
				request.getSession().setAttribute("login", login);
				RequestDispatcher dis = request
						.getRequestDispatcher("chat.jsp");
				dis.forward(request, response);
			}else{
				RequestDispatcher dis = request
						.getRequestDispatcher("login.html");
				dis.forward(request, response);
			}

		} else {
			if (login == null) {
				out.println("Available");
			} else {
				out.println("AlreadyExist");
			}
		}

	}

}
