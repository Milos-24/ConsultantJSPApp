<%@page import="service.MailManager"%>
<%@page import="beans.MessageBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="org.apache.commons.fileupload.servlet.ServletFileUpload" %>
<%@ page import="org.apache.commons.fileupload.disk.DiskFileItemFactory" %>
<%@ page import="org.apache.commons.fileupload.FileItem" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Optional" %>
<%@ page import="java.io.File" %>
<%@ page import="java.io.IOException" %>
<jsp:useBean id="messageManager" class="service.MessageManager" scope="application"></jsp:useBean>
    
<!DOCTYPE html>
<%
	if (request.getParameter("refresh") != null) {
		messageManager.loadMessagesFromDB();
	}
%>
<%

    String uploadFolder = "C:\\Users\\Milos\\Documents\\apache-tomcat-9.0.78\\apache-tomcat-9.0.78\\webapps\\upload";
    String fileName = null;
    boolean sendEmail = false;
    String responseMessage = null;

    if(request.getParameter("messageId")!=null){
    	
    int id1 = Integer.parseInt(request.getParameter("messageId"));
    //System.out.println(id1); ovo vrati 2
    MessageBean message = messageManager.getMessageById(id1);

    System.out.println(message);
    
    boolean isMultipart = ServletFileUpload.isMultipartContent(request);

    if (isMultipart) {
        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            File repository = (File) application.getAttribute("javax.servlet.context.tempdir");
            factory.setRepository(repository);

            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> fileItems = upload.parseRequest(request);

            for (FileItem item : fileItems) {
                if (!item.isFormField()) {
                    fileName = new File(item.getName()).getName();
                    String filePath = uploadFolder + File.separator + fileName;
                    item.write(new File(filePath));
                } else {
                    Optional<FileItem> messageItem = fileItems.stream().filter(f -> "message".equals(f.getFieldName())).findFirst();
                    sendEmail = fileItems.stream().filter(f -> "submit".equals(f.getFieldName())).findFirst().isPresent();
                    if (messageItem.isPresent()) {
                        responseMessage = messageItem.get().getString();
                    }
                }
            }
        } catch (Exception e) {
        }
    }
    System.out.println(messageManager.getRecieverMail(message.getSenderId()));
    if (sendEmail) {
        MailManager.sendMail(messageManager.getRecieverMail(message.getSenderId()), "Response to IP message", responseMessage, new File(uploadFolder, fileName));
        messageManager.readMessage(message.getId());


        response.sendRedirect("http://localhost:8080/ConsultantApp/messages.jsp");
    }
    }
%>


<html lang="en">
<head>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">

    <style>
        .hidden-content {
            display: none;
        }
    </style>

	<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <title>Messages</title>
</head>
<body>

    <nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="#">Consultant</a>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav ml-auto">
         <li class="nav-item">
                <div class="input-group">
                    <input type="text" id="searchInput" class="form-control" placeholder="Search" aria-label="Search" aria-describedby="basic-addon2">
                    <div class="input-group-append">
                        <button class="btn btn-outline-secondary" type="button" onClick="searchClick()">
                            <i class="fas fa-search"></i>
                        </button>
                    </div>
                </div>
            </li>
            
            <li class="nav-item">
            <span style="padding: 0 10px;"></span>
                <button type="button" class="btn btn-info" name="refresh" onclick="refreshTable()">
                    Refresh <i class="fas fa-sync-alt"></i>
                </button>
            </li>
            <li class="nav-item">
                <span style="padding: 0 10px;"></span>
                <button type="button" class="btn btn-danger" onclick="logout()">
                    Logout <i class="fas fa-sign-out-alt"></i>
                </button>
            </li>
       
        </ul>
    </div>
</nav>
    

    <div class="container">
        <div class="row">
            <div class="col-12">
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th scope="col">Number</th>
                            <th scope="col">Subject</th>
                            <th scope="col">Author</th>
                            <th scope="col">Time</th>
                            <th scope="col">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% 
                        int id = messageManager.getUser((String)session.getAttribute("username"));
                        int j=0;

                        if(id!=0){
                        for (int i = 0; i < messageManager.getMessages().size(); i++) {
                        	if(id==messageManager.getMessages().get(i).getRecieverId())		//vjerovatno je ovo
                            {
                        		                        		
                        		MessageBean data = messageManager.getMessages().get(i);
                        %>
                        <tr>
                            <th scope="row"><%= ++j %></th>
                            <td><%= data.getSubject() %></td>
                            <td><%= messageManager.getUser(data.getSenderId()) %></td>
                            <td><%= data.getTimestamp() %></td>
                            <td>
                                <button type="button" class="btn btn-primary" onclick="showText(<%= i %>, '<%= data.getSubject() %>')">
                                    <i class="far fa-eye"></i>
                                </button>
                                <button type="button" class="btn btn-success" onclick="sendMail(<%= i %>, '<%= data.getSubject() %>')">
                                	<i class="fas fa-edit"></i></button>
                                <button type="submit" name="deleteButton" class="btn btn-danger" onClick="deleteMessage(<%= data.getId() %>)"><i class="far fa-trash-alt" >
                                	</i></button>
                            </td>
                        </tr>
                        <tr class="hidden-content" id="textRow_<%= i %>">
                            <td colspan="5">
                                 <%= data.getContent() %>.
                            </td>
                        </tr>
                        <tr class="hidden-content" id="sendTextRow_<%= i %>">
                            <td colspan="5">
							    <div class="input-group">
							    <form class="w-100 p-2" method="POST" action="messages.jsp?messageId=<%=data.getId()%>" enctype="multipart/form-data">
							        <input type="text" class="form-control" id="messageInput_<%= i %>" placeholder="Type your mail response" name="message">
							        <div class="input-group-append">
							            <div class="custom-file">
							                <input type="file" class="custom-file-input" id="fileInput_<%= i %>" aria-describedby="inputGroupFileAddon" onchange="updateFileLabel(<%= i %>)" name="fileUpload">
							                <label class="custom-file-label" id="fileInputLabel_<%= i %>" for="fileInput_<%= i %>">Choose file</label>
							            </div>
						       		</div>
							        <div class="input-group-append">
							            <input type="submit" class="btn btn-primary" name="submit">
							        </div>
							    </form>
							    </div>
							</td>
                        </tr>
                        <% } 
                        }
                        }%>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
	<input type="hidden" id="sessionId" value="<%= session.getAttribute("username") %>">

    <script>
        function showText(index, subject) {
            var textRow = document.getElementById('textRow_' + index);
            textRow.classList.toggle('hidden-content');
        }
        function sendMail(index, subject) {
            var textRow = document.getElementById('sendTextRow_' + index);
            textRow.classList.toggle('hidden-content');
        }
        function refreshTable() {
            $.ajax({
                type: 'GET',
                url: '<%= request.getRequestURI() %>?refresh=true', 
                success: function (data) {
                	location.reload();
                },
                error: function (error) {
                    console.log('Error refreshing table: ' + error);
                }
            });
        }
        function logout()
        {
        	window.location.href="login.jsp";	
        }
        
        function updateFileLabel(index) {
            var input = document.getElementById("fileInput_" + index);
            var fileName = input.files[0].name;
            var label = document.getElementById("fileInputLabel_" + index);
            
            if (label) {
                label.innerHTML = fileName;
            }
        }
        
        function deleteMessage(id)
        {
        	$.ajax({
                url: 'delete',
                method: 'POST',
                data: { id: id },
                success: function(response) {
                   refreshTable();
                   console.log('Message deleted successfully');
                },
                error: function(xhr, status, error) {
                    console.error('Error deleting message: ' + error);
                }
            });	
        }
        
        function searchClick()
        {
        	var searchTerm = document.getElementById("searchInput").value;
        	var sessionId = document.getElementById("sessionId").value;
        	$.ajax({
        	    url: 'http://localhost:8080/ConsultantApp/searchMessages',
        	    type: 'POST',
        	    contentType: 'application/json',
        	    data: JSON.stringify({ searchTerm: searchTerm, sessionId: sessionId }),
        	    success: function (data) {
        	        updateTable(data);
        	    },
        	    error: function (error) {
        	        console.error('Error:', error);
        	    }
        	});
        }
        
        function updateTable(filteredMessages) {
        	
        	var tableHead = document.querySelector('thead');
            tableHead.innerHTML = ''; 

            var headerRow = tableHead.insertRow(0);

            var headers = ['Number', 'Subject', 'Time', 'Content']; 
            for (var i = 0; i < headers.length; i++) {
                var cell = headerRow.insertCell(i);
                cell.innerHTML = headers[i];
            }
        	
        	
        	
            var tableBody = document.querySelector('tbody');
            tableBody.innerHTML = '';

            

            for (var i = 0; i < filteredMessages.length; i++) {
                var data = filteredMessages[i];

                var row = tableBody.insertRow(-1);

                var cellNumber = row.insertCell(0);
                var cellSubject = row.insertCell(1);
                var cellTime = row.insertCell(2);
                var cellContent = row.insertCell(3);
                
                cellNumber.innerHTML = i + 1;
                cellSubject.innerHTML = data.subject;
                cellTime.innerHTML = data.timestamp; 
                cellContent.innerHTML = data.content;
            }
        }
       
    </script>
</body>
</html>
