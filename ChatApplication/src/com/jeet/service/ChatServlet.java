package com.jeet.service;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeet.api.Login;
import com.jeet.api.Pipe;
import com.jeet.db.DAO;

/**
 * Servlet implementation class ChatServlet
 */
public class ChatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ChatServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String friends = request.getParameter("getFriends");
		String logout = request.getParameter("logout");
		Login login = (Login) request.getSession().getAttribute("login");
		String currentUserId = login.getUserId();
		if (friends != null) {
			String listOfNames = userInJSonFormat(currentUserId);
			response.getWriter().println(listOfNames);
		} else if(logout != null){ 
			request.getSession().invalidate();
		}else{
			String msg = request.getParameter("msg");
			String msgTo = request.getParameter("msgTo");
			response.getWriter().println("You said : "+msg);
			Pipe pipe = DAO.instance().getPipe(msgTo);
			send(login.getUserId()+" said : "+msg, pipe.getPipeName());
		}
	}

	private String userInJSonFormat(String currentUserId) {
		List<String> userNames = DAO.instance().getAllUserNames(currentUserId);
		String users = "[";
		String u = "";
		for (String user : userNames) {
			u += "\"" + user + "\"" + ",";
		}
		u = u.substring(0, u.length() - 1);
		users = users + u + "]";
		return users;
	}
	
	private void send(String message, String queueName) {
		
		System.out.println("sending msg: \" "+message+"\" to "+queueName);

		Connection connection = null;

		try {
			Properties props = new Properties();
			props.put(Context.INITIAL_CONTEXT_FACTORY,
					"org.jnp.interfaces.NamingContextFactory");
			props.put(Context.PROVIDER_URL, "localhost:1099");
			props.put(Context.URL_PKG_PREFIXES,
					"org.jboss.naming:org.jnp.interfaces");

			InitialContext iniCtx = new InitialContext(props);

			QueueConnectionFactory qcf = (QueueConnectionFactory) iniCtx
					.lookup("ConnectionFactory"); // step 1
			Queue que = (Queue) iniCtx.lookup(queueName);
			Destination dest = (Destination) que;

			connection = qcf.createConnection(); // step 2

			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE); // step 3

			MessageProducer producer = session.createProducer(dest); // step 4

			TextMessage txtMessage = session.createTextMessage(message); // step
																			// 5
																			// message.

			producer.send(txtMessage); // step 6
			System.out.println("Chat Servlet -- message sent :"+txtMessage);

		} catch (JMSException e) {
			System.err.println("Exception occurred: " + e.toString());
		} catch (NamingException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
					System.err.println("Exception occurred: " + e.toString());
				}
			}
		}

	}

}
