package com.jeet.service;

import java.io.IOException;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
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
 * Servlet implementation class ChatReciever
 */
public class ChatReciever extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChatReciever() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 Login login = (Login) request.getSession().getAttribute("login");
		 Pipe pipe = DAO.instance().getPipe(login.getUserId());
		 String msg = consumer(pipe.getPipeName());
		 response.getWriter().println(msg);
	}
	
	public String consumer(String queueName) {
		String message = "";
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
			MessageConsumer consumer = session.createConsumer(dest); // step 4
			connection.start();
			Message m = consumer.receive(1);
			if (m != null) {
				if (m instanceof TextMessage) {
					
					TextMessage txtMessage = (TextMessage) m;
					message = txtMessage.getText();
				}
			}
			
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
		return message; 

	}

}
