package com.jeet.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeet.api.Login;
import com.jeet.api.Pipe;
import com.jeet.api.User;
import com.jeet.db.DAO;

/**
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("userId");
		String pass = request.getParameter("pass");
		String repassword = request.getParameter("repass");
		String firstName = request.getParameter("fName");
		String lastName = request.getParameter("lName");
		String gender = request.getParameter("sex");
		int age = Integer.parseInt(request.getParameter("age"));
		String address = request.getParameter("address");
		int phNum = Integer.parseInt(request.getParameter("phoneNum"));
		User newUser = new User(userId, firstName, lastName, gender, age,
				address, phNum);
		
		Login login = new Login(userId, pass);
		System.out.println("RegisterServlet.doPost()"+userId+"  "+pass);
		if(DAO.instance().updateLoginDeails(login)){
			if(DAO.instance().updateUserDetails(newUser)){
				assignPipe(userId);
				RequestDispatcher dis = request
						.getRequestDispatcher("login.html");
				dis.forward(request, response);
			}
		}
	}

	private void assignPipe(String userId){
		List<Pipe> pipes = DAO.instance().getAllPipes();
		for( Pipe pipe : pipes){
			if( !DAO.instance().isPipeAvailable(pipe.getPipeName())){
				DAO.instance().updateUsePipes(userId, pipe.getPipeName());
				break;
			}
		}
	}
}
