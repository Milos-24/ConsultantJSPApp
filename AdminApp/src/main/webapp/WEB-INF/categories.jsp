<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="db.Category" %>
<%@ page import="db.Exercise" %>
<%@ page import="db.CategoryDAO" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin</title>
<head>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">

    <style>
        .hidden-content {
            display: none;
        }
    </style>

</head>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="">Categories</a>
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
</nav>

<div class="container mt-5">

    <h1>Categories and Exercises</h1>

    <div class="row">
        <% List<Category> categoryList = CategoryDAO.loadCategories(); %>
        <% for (Category category : categoryList) { %>
            <div class="col-md-4 mb-4">
                <div class="card">
                    <div class="card-header">
                    <div>
                        <span class="float-left" oninput="limitEntryLength(this, 15)" onkeypress="disableEnterKey(event)"
                        contenteditable="true" id="categoryName_<%=category.getId()%>" >
                        <%= category.getName()%></span>
                    </div>      
                    <span class="float-right">
                    <a href="javascript:void(0);" onclick="editCategory(<%= category.getId() %>)" class="btn btn-warning btn-sm">Edit</a>
                    <button type="button" class="btn btn-danger btn-sm" onclick="deleteCategory(<%= category.getId() %>)">
                        <i class="far fa-trash-alt"></i>
                    </button>
                </span>
                    </div>
                    <ul class="list-group list-group-flush">
                        <% List<Exercise> exercises = category.getExercise(); %>
                        <% if (exercises.isEmpty()) { %>
                            <li class="list-group-item">No Exercises Available</li>
                        <% } else { %>
                            <% for (Exercise exercise : exercises) { %>
                            <div>
                            <li class="list-group-item">
                                <span class="float-left" oninput="limitEntryLength(this, 15)" onkeypress="disableEnterKey(event)"
                                contenteditable="true" id="exerciseName_<%=exercise.getId()%>">
                                <%= exercise.getName() %></span>
                            
                                <span class="float-right">
                    <a href="javascript:void(0);" onclick="editExercise(<%= exercise.getId() %>)" class="btn btn-warning btn-sm">Edit</a>
                    <button type="button" class="btn btn-danger btn-sm" onclick="deleteExercise(<%= exercise.getId() %>)">
                        <i class="far fa-trash-alt"></i>
                    </button>
                </span>
                </li>
                </div>
                                
                                </li>
                                
                            <% } 
                         } %>
                    </ul>
                </div>
            </div>
        <% } %>
    </div>
    
    
    <div class="container mt-4">

        <h2 class="mb-4">Add Category</h2>
        <div class="card">
            <div class="card-header">
                <div class="form-group">
                    <label for="categoryName">Category Name:</label>
                    <input maxlength="15" type="text" class="form-control" id="categoryName" placeholder="Enter category name">
                </div>
            </div>
            <div class="card-body">
                <button type="button" class="btn btn-success float-right" onclick="addCategory()">Add Category</button>
            </div>
        </div>

        <h2 class="mt-4 mb-4">Add Exercise</h2>
        <div class="card">
            <div class="card-header">
                <div class="form-group">
                    <label for="exerciseName">Exercise Name:</label>
                    <input maxlength="15" type="text" class="form-control" id="exerciseName" placeholder="Enter exercise name">
                </div>
                <div class="form-group">
                    <label for="exerciseCategory">Category Name:</label>
                    <input maxlength="15" type="text" class="form-control" id="exerciseCategory" placeholder="Enter category name">
                </div>
            </div>
            <div  class="card-body">
                <button type="button" class="btn btn-success float-right" onclick="addExercise()">Add Exercise</button>
            </div>
        </div>
    </div>

</div>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<script>
    function limitEntryLength(element, maxLength) {
        var enteredText = element.innerText;
        if (enteredText.length > maxLength) {
            element.innerText = enteredText.substring(0, maxLength);
        }
    }
    function editCategory(categoryId) {
        var newValue = document.getElementById("categoryName_" + categoryId).innerText;
        window.location.href = "?action=editCategory&id=" + categoryId + "&newVal=" + newValue;
    }

    function editExercise(exerciseId) {
        var newValue = document.getElementById("exerciseName_" + exerciseId).innerText;
        window.location.href = "?action=editExercise&id=" + exerciseId + "&newVal=" + newValue;
    }

    function disableEnterKey(event) {
        if (event.key === 'Enter') {
            event.preventDefault();
        }
    }
    function deleteCategory(categoryId) {
        var confirmDelete = confirm("Are you sure you want to delete this category?");

        if (confirmDelete) {
            window.location.href = "?action=deleteCategory&id="+categoryId;
        }
    }
    
    function deleteExercise(exerciseId) {
        var confirmDelete = confirm("Are you sure you want to delete this exercise?");

        if (confirmDelete) {
            window.location.href = "?action=deleteExercise&id="+exerciseId;

        }
    }
    function addCategory() {
        var categoryName = document.getElementById('categoryName').value;
        
        window.location.href ="?action=addCategory&category="+categoryName;
    }

    function addExercise() {
        var exerciseName = document.getElementById('exerciseName').value;
        var exerciseCategory = document.getElementById('exerciseCategory').value;

        
        window.location.href ="?action=addExercise&name="+exerciseName+"&category="+exerciseCategory;

    }
    

</script>

</body>
</html>