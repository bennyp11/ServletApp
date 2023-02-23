<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.google.gson.reflect.TypeToken" %>
<%@ page import="com.servletapp.model.Participant" %>
<%@ page import="java.util.List" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="java.io.Reader" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Delete a Participant</title>
</head>
<style>
nav {
  background-color: #333;
  overflow: hidden;
}

nav a {
  float: left;
  color: #f2f2f2;
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;
  font-size: 17px;
}

nav a:hover {
  background-color: #ddd;
  color: black;
}

.active {
  background-color: #4CAF50;
  color: white;
}

nav .icon {
  display: none;
}

@media screen and (max-width: 600px) {
  nav a:not(:first-child) {
    display: none;
  }

  nav a.icon {
    float: right;
    display: block;
  }
}

@media screen and (max-width: 600px) {
  nav.responsive {
    position: relative;
  }

  nav.responsive .icon {
    position: absolute;
    right: 0;
    top: 0;
  }

  nav.responsive a {
    float: none;
    display: block;
    text-align: left;
  }
}

form {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin: 8px auto;
  width: 90%;
  max-width: 500px;
}

label {
  margin: 10px 0;
}

input[type="submit"] {
  padding: 10px 20px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
}

input[type="submit"]:hover {
  background-color: #3e8e41;
}

input[type="text"], input[type="email"] {
  padding: 10px;
  width: 100%;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
  font-size: 16px;
}

@media screen and (max-width: 600px) {
  form {
    margin: 20px auto;
  }
}

table {
  border-collapse: collapse;
  width: 100%;
  max-width: 800px;
  margin: 50px auto;
}

th {
  background-color: #4CAF50;
  color: white;
  font-weight: bold;
  text-align: left;
  padding: 8px;
}

td, th {
  text-align: left;
  border: 1px solid #ddd;
  font-size: 18px;
}

tr {
	text-indent: 8px;
}

tr:nth-child(even) {
  background-color: #f2f2f2;
}
</style>
<body>
<nav>
	<a class="active" href="/ServletApp/index.html">Zappin' Zumba</a>
	<a href="/ServletApp/addparticipant.html">Add Participant</a>
	<a href="/ServletApp/deleteparticipants.jsp">Delete Participants</a>
	<a href="/ServletApp/updateparticipants.html">Update Participants</a>
	<a href="/ServletApp/addbatch.html">Add Batch</a>
	<a href="/ServletApp/deletebatch.jsp">Delete Batch</a>
	<a href="/ServletApp/updatebatch.html">Update Batch</a>
	<a href="javascript:void(0);" class="icon" onclick="myFunction()">&#9776;</a>
</nav>
<%
    // Send a GET request to the ParticipantServlet
    String servletUrl = "http://localhost:8080/ServletApp/participant";
    InputStream inputStream = new URL(servletUrl).openStream();
    Reader reader = new InputStreamReader(inputStream, "UTF-8");
    List<Participant> participantList = new Gson().fromJson(reader, new TypeToken<List<Participant>>(){}.getType());
%>

<!-- Display the retrieved data on the page -->
<table>
    <thead>
    	<th>FIRST NAME</th>
    	<th>LAST NAME</th>
    	<th>EMAIL</th>
    	<th>DELETE</th>
    </thead>
<% for (Participant participant : participantList) { %>
    <tr>
        <td><%= participant.getFirstName() %></td>
        <td><%= participant.getLastName() %></td>
        <td><%= participant.getEmail() %></td>
        <td>
            <form method="POST" action="participant" onsubmit="deleteParticipant(event)">
                <input type="hidden" name="_method" value="DELETE" />
                <input type="hidden" name="email" value="<%= participant.getEmail() %>" />
                <input type="submit" value="delete" />
            </form>
        </td>
    </tr>
<% } %>
</table>
<script>
    function deleteParticipant(event) {
        event.preventDefault();
        const form = event.target;
        const formData = new FormData(form);
        const jsonObject = {};
        for (const [key, value] of formData.entries()) {
            jsonObject[key] = value;
        }
        const json = JSON.stringify(jsonObject);
        const xhr = new XMLHttpRequest();
        xhr.open('DELETE', form.action);
        xhr.setRequestHeader('Content-Type', 'application/json');
        xhr.onload = function() {
            // handle the response as needed
        	location.reload();
        };
        xhr.send(json);
    }
</script>
</body>
</html>