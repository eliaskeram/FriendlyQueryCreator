<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="resources/css/loginCSS.css">
<link href='//fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>
<link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet"  type="text/css" />
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script type="text/javascript" src="<c:url value="/resources/scripts/loginScript.js"/>"></script>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Friendly User Query Creation - Login Form Page</title>
</head>
<body role="document" style="background-color: #eee">
      <div class="container" id="intro">
        <h1 class="display-3">Welcome to Friendly Query Editor</h1>
        <p>Our Application is used to create Queries with simple and visual interface. At the first use and anytime you can see how our interface works. Just push the button <font color="red">"Learn more"</font> and you can see how to continue to the next step from our Application.</p>
        <p><a class="btn btn-primary btn-lg"  role="button"  data-target="popup" onclick="window.open('<c:url value="/resources/Presentation/Page1Help/index.html" />','popup','width=650,height=650,top=50,left=800,right=0,scrollbars=no,resizable=no'); return false;">Learn more &raquo;</a></p>
    </div>
<div class="container">
      <form class="form-signin" method="POST" action="loginPage">
        <h2 class="form-signin-heading">Sign In to Server</h2>
        <span class="glyphicon glyphicon-user" aria-hidden="true"></span>&emsp;<label for="inputName" >Username</label>
        <input type="text" name = "userName" id="inputName" class="form-control" placeholder="User Name" required autofocus>
        <span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>&emsp;<label for="inputPassword">Password</label>
        <input type="password" name = "password" id="inputPassword" class="form-control" placeholder="Password" required>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
         <div id = "dangerF" class="alert alert-danger" role="alert">
  			<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
  			<span class="sr-only">Error:</span><span id="fail">${error}</span>
   		  </div>
      </form>
</div> <!-- /container -->
<div class="modal hide fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h3 id="myModalLabel">Help Page</h3>
  </div>
  <div class="modal-body">
  </div>
</div>
  <div class="footer">
      @Copyright Ilias Keramefs - Friendly User Query Creator
  </div>
</body>
</html>