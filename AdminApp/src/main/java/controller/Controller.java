package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.CategoryDAO;
import db.ExerciseDAO;
import db.UserDAO;

@WebServlet("/Controller")
public class Controller extends HttpServlet{

	public Controller() {
		// TODO Auto-generated constructor stub
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String address = "/WEB-INF/login.jsp";
		String action = request.getParameter("action");
		HttpSession session = request.getSession();

		session.setAttribute("notification", "");

		if (action == null || action.equals("")) {
			address = "/WEB-INF/login.jsp";
		}else if(action.equals("login")) {
			if(UserDAO.loadUserCredentialsFromDatabase(request.getParameter("username"),
					request.getParameter("password")))
			{
				address = "/WEB-INF/categories.jsp";
			}
			else
			{
				address = "/WEB-INF/login.jsp";
			}
		}else if (action.equals("logout")) {
			session.invalidate();
			address = "/WEB-INF/pages/login.jsp";
		} else if (action.equals("categories")) {
			address = "/WEB-INF/categories.jsp";			
		} else if (action.equals("users")){
			address = "/WEB-INF/users.jsp";
		} else if (action.equals("statistics")) {
			address = "/WEB-INF/statistics.jsp";
		} else if (action.equals("editExercise") && request.getParameter("id")!=null)												//edit exercise
		{
			ExerciseDAO.editExercise(Integer.parseInt(request.getParameter("id")), request.getParameter("newVal"));
			
			address = "/WEB-INF/categories.jsp";
		}else if (action.equals("editCategory") && request.getParameter("id")!=null)												//edit category
		{
			CategoryDAO.editCategory(Integer.parseInt(request.getParameter("id")), request.getParameter("newVal"));
			
			address = "/WEB-INF/categories.jsp";
		} else if (action.equals("deleteExercise") && request.getParameter("id")!=null)			//delete exercise
		{			
			ExerciseDAO.deleteExercise(Integer.parseInt(request.getParameter("id")));
			address = "/WEB-INF/categories.jsp";
		}else if (action.equals("deleteCategory") && request.getParameter("id")!=null)			//delete category
		{	
			CategoryDAO.deleteCategory(Integer.parseInt(request.getParameter("id")));
			address = "/WEB-INF/categories.jsp";
		} else if (action.equals("addCategory"))												//add category
		{
			CategoryDAO.addCategory(request.getParameter("category"));
			address = "/WEB-INF/categories.jsp";
		} else if (action.equals("addExercise")) 												//add exercise
		{
			ExerciseDAO.addExercise(request.getParameter("name"), request.getParameter("category"));
			address = "/WEB-INF/categories.jsp";
		} else if (action.equals("deleteUser") && request.getParameter("id")!=null)
		{
			UserDAO.deleteUser(Integer.parseInt(request.getParameter("id")));
			address = "/WEB-INF/users.jsp";
		}
		else {
			address = "/WEB-INF/404.jsp";
		}

		

		RequestDispatcher dispatcher = request.getRequestDispatcher(address);
		dispatcher.forward(request, response);
	}

	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException
			, IOException {
		doGet(request, response);
	}

}
