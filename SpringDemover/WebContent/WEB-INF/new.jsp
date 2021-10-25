<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.spring.connection.TableKeys" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="resources/css/newCSS.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<link href='//fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>
<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
<script type="text/javascript" src="<c:url value="/resources/scripts/myScript.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/scripts/myScriptNew.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/scripts/bootbox.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/bootstrap/js/bootstrap.min.js"/>"></script>
<link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet"  type="text/css" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Friendly User Query Creator - Read Database</title>
</head>
<body role="document">
<%!  int i=0;%>
<%!  int i1=0;%>
<%!  int i3=0;%>
<%!  int i4=0;%>
<%!  int i5=1; %>
<%!  int i6=0;%>
<%!  int iMap=0;%>
<div class="container-fluid" id="intro">
   <h1 class="display-3">Welcome to Query Creator</h1>
    <div class="col-md-12">
   <div class ="col-md-10">
    <p>In this Page you can see the tables of your selected database. Every Table has some characteristics (attributes). For Example: The table Authors includes Authors.FirstName as attribute. Here, you set only the desired attributes for your query.
           Read the instructions below, Push the button <font color="red">"Learn more" </font>and see how it works and how to continue to the next step.</p>
    </div>
    <div class ="col-md-2">
    <br>
    </div>
    </div>
    <br>
    <p><a class="btn btn-primary btn-lg"  role="button"  data-target="popup" onclick="window.open('<c:url value="/resources/Presentation/Page3Help/index.html" />','popup','width=750,height=650,top=50,left=800,right=0,scrollbars=no,resizable=no'); return false;">Learn more &raquo;</a></p>
<div class="row">
 <div class="col-md-8">
 <div class="panel panel-primary" id="selecta">
  <div class="panel-heading">
    <h3 class="panel-title">You can drag all the desired elements in the stack below</h3>
  </div>
     <div class="panel-body" id="example">
	  <c:forEach items="${infoTable}" var="listItem4">
		  <table class="draggable" >
		  <tr>
		      <th class="title" ondblclick="showtable('fields${i5}')">${listItem4}</th>
		  </tr>		  
	   <c:forEach items="${columnT}" var="columnT" begin="${i4}" end="${sizeofT.get(i3)+i4-1}">
		  <tr>
		  <td  class="fields" id="fields${i5}" >${listItem4}.${columnT}</td>
		  </tr>
       </c:forEach>                  
		        <c:set var="i4"  value="${i4+sizeofT.get(i3)}"/>
		        <c:set var="i3"  value="${i3+1}"/>         
		  </table>
               <c:set var="i5"  value="${i5+1}"/>  
	   </c:forEach>
  </div>
  </div>
  </div>
  
  <div class="col-md-4">
   <div class="panel panel-info">
     <div class="panel-heading">
       <h3 class="panel-title">Instructions</h3>
     </div>
    <div class="panel-body">
    <ul id="listInfo">
     <li>In this page you set only the attributes in your query</li>
    <li>Drag and drop your desired attributes from the nearby tables</li>
    <li>Each Table has a name (first element) and attributes</li>
    <li>Drop your attributes in the stack as the red arrow below indicates e.g TITLES.title</li>
    <li>Table Primary key: Unique attribute of the table (red color)</li>
    <li>Table Foreign key: Unique attributes in the other tables (blue color)</li>
    <li><font color="red">Don't drop foreign keys in the stack</font></li>
    <li>Filter: Desired condition for your results. e.g. If you would like to represent:
    <ul><li>Titles of the books whose copyright is greater than 2014<ul><li>you must add: TITLES.copyright > 2014, in the next page.</li></ul></li>
   </ul>
    </li>
    <li>The filtering process is included in the next page</li>
    </ul>
    </div>
   </div>
  </div>

  </div>
  <div class="row">
   <div class="col-md-8" id="buttonEq">
      <div class="alert alert-info">
		<button id="ClearQ" type="button" class="btn btn-danger"  style="font-size:18px">Clear Query</button>
		&emsp;<strong>Info:</strong>&emsp;<span>You have the chance to create filter in the next page. Add your elements and create Query.</span>
       </div>
   </div>
 </div>
 <div class="row">
 <div class="col-md-8" id="secondPanel"> 
  <div class="panel panel-primary">
   <div class="panel-heading">
     <h3 class = "panel-title">The information you would like to see:</h3>
   </div>
  <div class="panel-body">
  <div class="col-md-12"> 
  <span><img align ="left" class ="image" src="resources/images/stack.ico" width="60" height="60" alt="Insert here" title ="Insert your option here"/></span>
  <div id="Output">
  <span>&emsp;&emsp;</span>
  </div>
  </div>
  </div>
  </div>
  </div>
 
 <div class="col-md-4" id="thirdPanel">
 <div class="panel panel-primary" id="selectb">
  <div class="panel-heading">
    <h3 class="panel-title">Your Primary and Foreign Keys of your tables</h3>
  </div>
  <div class="panel-body" id="keysDomain">
  <c:forEach items="${tableKeys}" var ="listTableKeys" varStatus="status">
   <c:forEach items="${listTableKeys}" var = "listTbKeys">
          <c:forEach items="${listTbKeys.key}" var = "listFrKeys">
           <c:set var="i1"  value="${i1+1}"/> 
              <c:forEach items="${listFrKeys.key}" var = "listPrKeys">
                 <span id="keysIDs${i1}">${fn:toUpperCase(listPrKeys.key)}.${listPrKeys.value} ->||<- ${listFrKeys.value}.${listTbKeys.value}</span><br/>
           </c:forEach>
   </c:forEach>
   </c:forEach>
   </c:forEach>  
  </div>
  </div>
  </div>
  </div>
 <c:forEach items="${returnable.getColumns()}" var="listResults">
 <c:set var="i6"  value="${i6+1}"/>
   <span style = "visibility: hidden" id="textR${i6}">${listResults }</span> 
 </c:forEach>
 
  <form method="POST">
  <div class="col-md-12"  id ="formQuery">
   <div class="panel panel-info">
   <div class="panel-heading">
    <h3 class="panel-title">SQL Language:</h3>
   </div>

   <div class="panel-body">
    <h3>Your structuring Query is:</h3>
  <div class="panel panel-default">
   <div class="panel-heading"><b>Text:</b></div>
  <div class="panel-body">
 
  <div id="myResults">
  <c:set var="sel" value="SELECT "/>
   <c:set var="fro" value="FROM "/>
   <c:set var="whe" value="WHERE "/>
   <br/>
   <textarea name="select"  id="txtMessage" style="display:none;">${returnable.select }</textarea>   
   <b>${sel }</b><span id="txtMessage2">${returnable.select }</span>
   <textarea name="from"  id="txtMessage3" style="display:none;">${returnable.from }</textarea>   
   <br/>
   <b>${fro }</b><span id="txtMessage4" >${returnable.from }</span>
   <textarea name="where"  id="txtMessage5" style="display:none;">${returnable.where }</textarea>   
   <br/>
   <b>${whe }</b><span id="txtMessage6">${returnable.where }</span>
  </div>
 </div>
  </div>
  </div>
  </div>
  </div>
  <div class="row" id ="createQ">
  <div class="col-md-12">
   <button type="submit" id="create" class="btn btn-primary" style="font-size:18px">Create Query</button>
  </div>
  </div>
</form>
</div>
 <span id="i1" style = "visibility: hidden">${i1 }</span>
 <span id="i4" style = "visibility: hidden">${i4 }</span>
 <span id="sizeofT" style = "visibility: hidden">${sizeofT }</span>
  <c:forEach items="${mapperTables}" var="listMapper">
 <c:set var="iMap"  value="${iMap+1}"/>
   <span style = "visibility: hidden" id="textMPrim${iMap}">${listMapper.primaryT }.${listMapper.primaryC }</span> 
   <span style = "visibility: hidden" id="textMFore${iMap}">${listMapper.foreignT }.${listMapper.foreignC }</span> 
 </c:forEach>
  <div class="footer">
      @Copyright Ilias Keramefs - Friendly User Query Creator
  </div>
</body>
</html>
