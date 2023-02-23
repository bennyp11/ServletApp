<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>View Participants in Batch</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<%
  boolean redirected = false;
  if (request.getParameter("redirected") != null) {
    redirected = true;
  }
  if (!redirected) {
	var urlEncodedData = encodeURIComponent(JSON.stringify(data)).replace(/\r?\n|\r/g, "");
    String redirectUrl = "/ServletApp/viewparticipants.jsp?redirected=true&data=" + urlEncodedData;
    response.sendRedirect(redirectUrl);
  }
%>
  <table>
    <thead>
      <tr>
        <th>Batch ID</th>
        <th>Date</th>
        <th>Time</th>
        <th>Participant Emails</th>
      </tr>
    </thead>
    <tbody></tbody>
  </table>
  <script>
  $(document).ready(function() {
	  var tbody = $('table tbody');
	  $.get('/ServletApp/viewparticipants.jsp?getbatchparticipants=true', function(response) {
	    var urlEncodedData = '<%= request.getParameter("batchList") %>';
	    var data = decodeURIComponent(urlEncodedData.replace(/\+/g, ' '));
	    console.log("DATA: "+data);
	    for (var i = 0; i < data.length; i++) {
	      var batch = data[i];
	      console.log("BATCH: "+batch);
	      var row = '<tr><td>' + batch.batchId + '</td><td>' + batch.date + '</td><td>' + batch.time + '</td><td>' + batch.participantEmails.join(', ') + '</td></tr>';
	      tbody.append(row);
	    }
	  });
	});

  </script>
</body>
</html>
