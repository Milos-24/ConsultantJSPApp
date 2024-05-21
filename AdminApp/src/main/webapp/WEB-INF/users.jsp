<%@page import="db.User"%>
<%@page import="java.util.List"%>
<%@page import="db.UserDAO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<title>Admin</title>
<head>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">

    <style>
        .hidden-content {
            display: none;
        }
    </style>

	<script src="https://code.jquery.com/jquery-3.1.1.min.js" ></script>
	<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
</head>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="">Users</a>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav ml-auto">
             <li class="nav-item">
                <span style="padding: 0 10px;"></span>
                <a class="nav-link" href="?action=categories">Categories</a>
            </li>
            <li class="nav-item">
                <span style="padding: 0 10px;"></span>
                <a class="nav-link" href="?action=users">Users</a>
            </li>
            <li class="nav-item">
                <span style="padding: 0 10px;"></span>
                <a class="nav-link" href="?action=statistics">Statistics</a>
            </li>
        </ul>
    </div>
</nav>`

<div class="container mt-5">

	 	<h1>Users</h1>

    <div class="row">
    <% List<User> userList = UserDAO.loadUsersFromDatabase(); %>
    <% for (User user : userList) { %>
        <div class="col-md-4 mb-4">
            <div class="card">
                <div class="card-header">
				    
				    <span class="float-left ml-2" oninput="limitEntryLength(this, 15)"
				        onkeypress="disableEnterKey(event)" contenteditable="true" id="username_<%=user.getId()%>">
				        <%= user.getUsername() %>
				    </span>
				    <span class="float-right">
				        <a href="javascript:void(0);" onclick="editUser(<%= user.getId() %>)"
				            class="btn btn-warning btn-sm">Edit</a>
				        <button type="button" class="btn btn-danger btn-sm"
				            onclick="deleteUser(<%= user.getId() %>)">
				            <i class="far fa-trash-alt"></i>
				        </button>
				    </span>
				</div>
                <ul class="list-group list-group-flush">
                    <li class="list-group-item">
                        <strong>First Name:</strong>
                        <span class="float-right">
                            <input class="text-right" type="text" id="firstname_<%=user.getId()%>" value="<%= user.getFirstname() %>">
                        </span>
                    </li>
                    <li class="list-group-item">
                        <strong>Last Name:</strong>
                        <span class="float-right">
                            <input class="text-right" type="text" id="lastname_<%=user.getId()%>" value="<%= user.getLastname() %>">
                        </span>
                    </li>
                    <li class="list-group-item">
                        <strong>Email:</strong>
                        <span class="float-right">
                            <input class="text-right" type="text" id="email_<%=user.getId()%>" value="<%= user.getEmail() %>">
                        </span>
                    </li>
                    <li class="list-group-item">
                        <strong>Locked:</strong>
                        <span class="float-right">
                            <input class="text-right" type="checkbox" id="locked_<%=user.getId()%>" <%= user.isLocked() ? "checked" : "" %>>
                        </span>
                    </li>
                    <li class="list-group-item">
                        <strong>Enabled:</strong>
                        <span class="float-right">
                            <input class="text-right" type="checkbox" id="enabled_<%=user.getId()%>" <%= user.isEnabled() ? "checked" : "" %>>
                        </span>
                    </li>
                    <li class="list-group-item">
                        <strong>Avatar:</strong> <div class="float-right avatar-container">
				        <div class="avatar-letter float-right">
				            <%= user.getUsername().substring(0, 1).toUpperCase() %>
				        </div>
				    </div>
                    </li>
                    <li class="list-group-item">
                        <strong>City:</strong>
                        <span class="float-right">
                            <input class="text-right" type="text" id="city_<%=user.getId()%>" value="<%= user.getCity() %>">
                        </span>
                    </li>
                </ul>
            </div>
        </div>
    <% } %>
</div>

</div>

<div class="col-md-4 mb-4 mx-auto"">
<h1 >Add user</h1>
        <div class="card">
            <div class="card-header">
                <h5 class="card-title">Add User</h5>
            </div>
            <ul class="list-group list-group-flush">
                <li class="list-group-item">
                    <strong>Username:</strong>
                    <span class="float-right">
                        <input type="text" id="username" placeholder="Enter username" class="text-right">
                    </span>
                </li>
                <li class="list-group-item">
                    <strong>Password:</strong>
                    <span class="float-right">
                        <input type="password" id="password" placeholder="Enter password" class="text-right">
                    </span>
                </li>
                <li class="list-group-item">
                    <strong>First Name:</strong>
                    <span class="float-right">
                        <input type="text" id="firstname" placeholder="Enter first name" class="text-right">
                    </span>
                </li>
                <li class="list-group-item">
                    <strong>Last Name:</strong>
                    <span class="float-right">
                        <input type="text" id="lastname" placeholder="Enter last name" class="text-right">
                    </span>
                </li>
                <li class="list-group-item">
                    <strong>Email:</strong>
                    <span class="float-right">
                        <input type="text" id="email" placeholder="Enter email" class="text-right">
                    </span>
                </li>
                <li class="list-group-item">
                    <strong>Locked:</strong>
                    <span class="float-right">
                        <input type="checkbox" id="locked">
                    </span>
                </li>
                <li class="list-group-item">
                    <strong>Enabled:</strong>
                    <span class="float-right">
                        <input type="checkbox" id="enabled" checked>
                    </span>
                </li>
                <li class="list-group-item">
				    <strong>User Type:</strong>
				    <span class="float-right">
				        <select id="userType">
				            <option value="consultant">Consultant</option>
				            <option value="fitnessUser">Fitness User</option>
				        </select>
				    </span>
				</li>
                <li class="list-group-item">
                    <strong>City:</strong>
                    <span class="float-right">
                        <input type="text" id="city" placeholder="Enter city" class="text-right">
                    </span>
                </li>
            </ul>
            <div class="card-footer text-right">
                <button type="button" class="btn btn-success btn-sm" onclick="addUser()">Add User</button>
            </div>
        </div>
    </div>



<script>

function disableEnterKey(event) {
    if (event.key === 'Enter') {
        event.preventDefault();
    }
}
function deleteUser(userId) {
    var confirmDelete = confirm("Are you sure you want to delete this user?");

    if (confirmDelete) {
        window.location.href = "?action=deleteUser&id="+userId;
    }
}

function editUser(userId)
{
	var username = document.getElementById("username_" + userId).innerText.trim();
    var firstname = document.getElementById("firstname_" + userId).value;
    var lastname = document.getElementById("lastname_" + userId).value;
    var email = document.getElementById("email_" + userId).value;
    var locked = document.getElementById("locked_" + userId).checked;
    var enabled = document.getElementById("enabled_" + userId).checked;
    var city = document.getElementById("city_" + userId).value;
    
    var userData = {
            id: userId,
            username: username,
            firstname: firstname,
            lastname: lastname,
            email: email,
            locked: locked,
            enabled: enabled,
            city: city
        };
    
    $.ajax({
        url: 'http://localhost:8080/AdminApp/editUser', 
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(userData),
        success: function (data) {
            console.log(data);
        },
        error: function (error) {
            console.error('Error:', error);
        }
    });
}

function addUser() {

    var firstname = document.getElementById("firstname").value;
    var password = document.getElementById("password").value;
    var lastname = document.getElementById("lastname").value;
    var username = document.getElementById("username").value;
    var email = document.getElementById("email").value;
    var locked = document.getElementById("locked").checked;
    var enabled = document.getElementById("enabled").checked;
    var userType = document.getElementById("userType").value;
    var city = document.getElementById("city").value;

    var userTypeId;
    if (userType === "fitnessUser") {
        userTypeId = 3;
    } else if (userType === "consultant") {
        userTypeId = 2;
    }

    console.log(userTypeId);
    var user = {
        firstname: firstname,
        password: password,
        lastname: lastname,
        username: username,
        email: email,
        locked: locked,
        enabled: enabled,
        user_type_id: userTypeId,
        city: city
    };

    $.ajax({
        url: 'http://localhost:8080/AdminApp/addUser', 
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(user),
        success: function (data) {
            console.log(data);
        },
        error: function (error) {
            console.error('Error:', error);
        }
    });
}



</script>



</body>
<style>
    .avatar-container {
        width: 40px;
        height: 40px;
        border-radius: 50%;
        background-color: purple; 
        display: flex;
        align-items: center;
        justify-content: center;
        margin-right: 10px;
    }

    .avatar-letter {
        font-size: 18px;
        color: #ffffff; 
        font-weight: bold;
    }
</style>

</html>