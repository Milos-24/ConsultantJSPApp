package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import beans.MessageBean;
import service.MessageManager;

import javax.servlet.annotation.WebServlet;

public class SearchMessagesServlet extends HttpServlet {
	
	private MessageManager messageManager = new MessageManager(); 
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	BufferedReader reader = request.getReader();
        StringBuilder requestBody = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }

        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(requestBody.toString());

        String searchTerm = jsonElement.getAsJsonObject().get("searchTerm").getAsString();
        String sessionId = jsonElement.getAsJsonObject().get("sessionId").getAsString();

        
        List<MessageBean> filteredMessages = filterMessages(searchTerm, sessionId);
        

        
        Gson gson = new Gson();
        String json = gson.toJson(filteredMessages);
        
        response.setContentType("application/json");
        response.getWriter().write(json);
    }

    private List<MessageBean> filterMessages(String searchTerm, String username) {
        
    	List<MessageBean> filteredMessages = new ArrayList<MessageBean>();
    	filteredMessages = this.messageManager.getMessages();
    	
        return filteredMessages.stream()
        		.filter(p-> p.getContent().contains(searchTerm))
        		.filter(p->p.getRecieverId()==messageManager.getUser(username))
        		.collect(Collectors.toList());
    }
}
