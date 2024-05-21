package servlet;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.MessageManager;

public class DeleteMessageServlet extends HttpServlet{
	
	 protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws IOException {
		 String messageId = request.getParameter("id");
		 
		 MessageManager messageManager = new MessageManager(); 
	     messageManager.deleteMessage(Integer.parseInt(messageId));
	     
	     response.getWriter().write("Message deleted!");
	 }

	public DeleteMessageServlet() {
		// TODO Auto-generated constructor stub
	}

}
