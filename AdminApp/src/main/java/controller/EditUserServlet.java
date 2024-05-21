package controller;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import db.User;
import db.UserDAO;


public class EditUserServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public EditUserServlet() {
		// TODO Auto-generated constructor stub
	}
	
	 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 BufferedReader reader = request.getReader();
		    StringBuilder requestBody = new StringBuilder();
		    String line;

		    while ((line = reader.readLine()) != null) {
		        requestBody.append(line);
		    }

		    Gson gson = new Gson();
		    User user = gson.fromJson(requestBody.toString(), User.class);
		    
		    
		    UserDAO.editUser(user);

	 
		    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/users.jsp");
		    dispatcher.forward(request, response);
	 }
	

}
