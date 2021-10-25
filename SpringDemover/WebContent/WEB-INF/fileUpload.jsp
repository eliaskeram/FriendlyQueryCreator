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
<link rel="stylesheet" type="text/css" href="resources/css/fileUploadCSS.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<link href='//fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>
<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
<script type="text/javascript" src="<c:url value="/resources/scripts/myScriptFile.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/scripts/myScript.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/scripts/bootbox.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/bootstrap/js/bootstrap.min.js"/>"></script>
<link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet"  type="text/css" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Friendly User Query Creator - Load DataBase</title>
</head>
<body role="document">
<%!  int i=0;%>
	<div class="container" id="intro">
		<h1 class="display-3">Selecting Database</h1>
        <p>In this Step you could select your database, push the button <font color="red">"Learn more"</font> and see how to continue to the next step.</p>
        <p><a class="btn btn-primary btn-lg"  role="button"  data-target="popup" onclick="window.open('<c:url value="/resources/Presentation/Page2Help/index.html" />','popup','width=650,height=650,top=50,left=800,right=0,scrollbars=no,resizable=no'); return false;">Learn more &raquo;</a></p>
	</div>
	<div class="h-divider"></div>
	<div class = "container">
	<h3>Database Name from Server to read:</h3>
    <div class="col-md-8">
    <form class="form-signin" method="POST" action="fileUpload">	
		<div class="input-group input-group-lg">
  			<span class="input-group-addon" id="sizing-addon1">mysql:</span>
			  <input type="text" class="form-control" placeholder="${fileName}" readonly="readonly" aria-describedby="sizing-addon1">
  			<span class="input-group-btn">
        	  <button class="btn btn-primary" type="button" id="openFile" data-toggle="tooltip" data-placement="bottom" title="Open File"> <span class="glyphicon glyphicon-hdd" aria-hidden="true"></span></button>
        	  <button class="btn btn-success" type="submit" id="sendFile" data-toggle="tooltip" data-placement="bottom" title="Go to Read"> <span class="glyphicon glyphicon-log-in" aria-hidden="true"></span></button>
  			</span>
		</div>
		  <div id = "dangerF" class="alert alert-danger" role="alert">
  			<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
  			<span class="sr-only">Error:</span><span id="fail">${error}</span>
   		  </div>
		  <textarea name="fileNameS" style = "visibility: hidden" id="fileNameSpace">${fileNameS }</textarea>
		  <textarea name="fileNameServer" style = "visibility: hidden" id="nameServers">${listDatabases }</textarea>
		  <textarea name="fileServer" style = "visibility: hidden" id="nameServer">${fileServer }</textarea>	  
    </form>
    </div> 
	</div>
	<!-- Popup !--> 
	  <div class="modal fade" id="basicModal" tabindex="-1" role="dialog" aria-labelledby="basicModal" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	            <h4 class="modal-title" id="myModalLabel">Choose your Database:</h4>
	            </div>
	            <div class="modal-body">
	               <form class="checkboxOptions">
	               <div class="radio-toolbar">
	               <c:forEach items="${listDatabases }" var="listDatabasesNum">
	                <c:set var="i"  value="${i+1}"/> 
	                <div class="radio radio-primary">
	                   <label id="opt"><input type="radio" name="optradio" value="${listDatabasesNum }">${listDatabasesNum }</label>
	                </div>
	                </c:forEach>
	                </div>
  				   </form>
	            </div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-danger" data-dismiss="modal">Cancel <span class="glyphicon glyphicon-remove" aria-hidden="true"></span> </button>
	                <button type="button" class="btn btn-success" id="applyData">Apply <span class="glyphicon glyphicon-ok" aria-hidden="true"></span> </button>
	        </div>
	    </div>
	  </div>
	</div>

  <div class="footer">
      @Copyright Ilias Keramefs - Friendly User Query Creator
  </div>
</body>
</html>