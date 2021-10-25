<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">

<link rel="stylesheet" href="https://cdn.datatables.net/r/bs-3.3.5/jq-2.1.4,dt-1.10.8/datatables.min.css">
<link rel="stylesheet" type="text/css" href="resources/css/queriesCSS.css">
<script src="//code.jquery.com/jquery-1.12.3.js"></script>
<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>

<script type="text/javascript" src="https://cdn.datatables.net/r/bs-3.3.5/jqc-1.11.3,dt-1.10.8/datatables.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/1.10.12/js/dataTables.bootstrap.min.js"></script>
<script type="text/javascript" src="<c:url value="/resources/scripts/bootbox.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/bootstrap/js/bootstrap.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/bootstrap/js/bootstrap.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/scripts/myScriptResults.js"/>"></script>
<link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet"  type="text/css" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Friendly User Query Creator - Results of your Query</title>
</head>
<body role="document">
<%!  int i=0;%>
<%!  int i1=0;%>
<%!  int i2=0;%>
<div class="container-fluid" id="intro">
   <h1 class="display-3">Results and Query Filter Creator</h1>
   <div class="col-md-12">
   <div class ="col-md-10">
    <p>In this Page you can see the results of your query. You can search any of your results, add filter to your query, see the query as SQL Language.
           Read the instructions below, Push the button <font color="red">"Learn more"</font> and see how it works and how to continue.</p>
    </div>
    <div class ="col-md-2">
    <br>
    </div>
    </div>
    <br>
    <p><a class="btn btn-primary btn-lg"  role="button"  data-target="popup" onclick="window.open('<c:url value="/resources/Presentation/Page4Help1/index.html" />','popup','width=750,height=650,top=50,left=800,right=0,scrollbars=no,resizable=no'); return false;">Learn more &raquo;</a></p>
</div>
  <div class="col-md-12">
  <div class="panel panel-primary">
   <div class="panel-heading">
    <h3 class="panel-title">The Results of Query</h3>
   </div>
   <div class="panel-body" id="top">
	<table id="resultsSet" class="table table-sm table-bordered table-striped table-hover">
		<thead class="thead-inverse">
			<tr>
			<th><b>#</b></th>
				<c:forEach items="${SendQuery}" var="listResult">
			<th><b>${listResult}</b></th>
 				</c:forEach>
 			</tr>
 		</thead>
 		<tfoot>
 		<tr>
 		<th></th>
 		<c:forEach items="${SendQuery}" var="listResult">
			<th>${listResult}</th>
 		</c:forEach>
 		</tr>
 		</tfoot>
 		<tbody>
 			<c:forEach var="i1" begin="1" end="${NumberList }">
 			<tr>
				<th id = "centerAll" scope="row">${i1}</th>
				  <c:forEach items="${Map}" var="listMap" begin="${i}" end="${i+NumberColumns-1}">
			        <td>${listMap}</td>
				  </c:forEach>
				  <c:set var="i"  value="${i+NumberColumns}"/>
			</tr>
				  </c:forEach>
		</tbody>
 	</table>
   	</div>
   	</div>
   	</div>
   	<form method="POST" action="queries">
   <div class="col-md-8">
   <span style="font-size: 16pt">Would you like to add filter for your results? &emsp;</span>
   		 <button  type="button" id="createFilter" class="btn btn-primary" style="font-size:18px">Create Filter</button>  
   		 <button type="submit" id="updateQuery" class="btn btn-success" style="font-size:18px">Update Query</button>
   		 <button type="submit" id="resetQuery" class="btn btn-danger" style="font-size:18px">Reset Query</button>
   		 <button type="button" id="confStartQuery" class="btn btn-success" onclick="document.location.href='new'" style="font-size:18px">Back Page</button>
  <br>
   <br>
   <div class="panel panel-info">
   <div class="panel-heading">
    <h3 class="panel-title">SQL Language:</h3>
   </div>
   <div class="panel-body" id="resultQuery">
	  <div class="panel panel-default">
	   <div class="panel-heading"><b>Text:</b></div>
	  <div class="panel-body">
	  <div id="myResults">
	  <c:set var="sel" value="SELECT "/>
	   <c:set var="fro" value="FROM "/>
	   <c:set var="whe" value="WHERE "/>
	   <b style="color: blue">${sel }</b>&emsp;<span style="color: red" id="txtMessage2">${returnable.select }</span>
	   <br/>
	   <b style="color: blue">${fro }</b>&emsp;<span style="color: red" id="txtMessage4" >${returnable.from }</span>
	   <br/>
	   <b style="color: blue">${whe }</b>&emsp;<span style="color: red" id="txtMessage6">${returnable.where }</span>
	  </div>
	  <div id="filterSelection">
	  <b style="color: blue">FILTER </b>&emsp;<span style="color: red" id="filterTable" >${filter}</span> 
	  <textarea name="filter"  id="filter" style="display:none;">${returnable.filter }</textarea> 
	  </div>
	</div>
	</div>
  </div>
  </div>
  <span style="font-size: 16pt">Second Way for SQL Language?</span>
         &emsp;<button  type="button" id="secondSQL" class="btn btn-primary" style="font-size:18px">Open SQL</button>  
  <br/>
  </div>
  </form>  
  <div class="col-md-4">
      <div class="alert alert-info">
		&emsp;<strong>Filter Info:</strong>&emsp;
		<ul id="listInfo">
	    <li>If the value of your table column element is text, please add Character Operator</li>
	    <li>If the value of your table column element is number, please add Number Operator</li>
	    <li>Press Cancel, if you would like to cancel the filtering process</li>
	    </ul>
      </div>
   </div>
  <div class="col-md-4">
  <div class="panel panel-info">
   <div class="panel-heading">
    <h3 class="panel-title">Navigation Results as Form:</h3>
   </div>
   <div class="panel-body" id="formResult">
   <div class="form-group" id="myFormResults" >
   <c:forEach items="${SendQuery}" var="listResultForm">
   <c:set var="i2"  value="${i2+1}"/> 
        <label style="color: blue" for="inputColumn">${fn:toUpperCase(fn:substring(listResultForm, 0, 1))}${fn:substring(listResultForm, 1,fn:length(listResultForm)) }</label>
        <input style="color: red;" type="text" class="form-control" id="inputColumn${i2 }"  disabled>
        <br/>
   </c:forEach>
     <button type="button" id="previous" class="btn btn-info" style="font-size:18px">Previous</button>&emsp;
     <button type="button" id="next" class="btn btn-info" style="font-size:18px">Next</button>
   </div>
   </div>
   </div>
  </div>
  
     <!-- Popup Second Way for SQL query !--> 
	  <div class="modal fade" id="sqlLanguage" tabindex="-1" role="dialog" aria-labelledby="sqlLanguage" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	            <h4 class="modal-title" id="myModalLabel">Alternative SQL Language for your Structuring Query:</h4>
	            </div>
	            <div class="modal-body">
	                <c:set var="sel" value="SELECT "/>
				   <c:set var="fro" value="FROM "/>
				   <c:set var="whe" value="WHERE "/>
				   <b style="color: blue">${sel }</b>&emsp;<span style="color: red">${returnable.select }</span>
				   <br/>
				   <b style="color: blue">${fro }</b>&emsp;<span style="color: red">${returnable.alternFrom }</span>
				   <br/>
				   <b style="color: blue">${whe }</b>&emsp;<span style="color: red">${returnable.alternWhere }</span>
	            </div>
	            <div class="modal-footer">
	           <button type="button" class="btn btn-success" data-dismiss="modal">OK <span class="glyphicon glyphicon-ok" aria-hidden="true"></span> </button>
	        </div>
	    </div>
	  </div>
	</div>
	
   <!-- Popup Filter Table Selection !--> 
	  <div class="modal fade" id="basicTables" tabindex="-1" role="dialog" aria-labelledby="basicTables" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	            <h4 class="modal-title" id="myModalLabel">Chooose the element that you would like to add in your filter:</h4>
	            </div>
	            <div class="modal-body">
	               <form class="checkboxOptions1">
	               <div class="radio-toolbar">
	               <c:forEach items="${returnableInit.getColumns()}" var="listTables">
	                <div class="radio radio-primary" id="tableSelection">
	                   <label id="opt"><input type="radio" name="optradio" value="${listTables }">${listTables }</label>
	                    <br/>
	                </div>
	                </c:forEach>
	                </div>
  				   </form>
	            </div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-danger" data-dismiss="modal" id ="cancelData">Cancel <span class="glyphicon glyphicon-remove" aria-hidden="true"></span> </button>
	                <button type="button" class="btn btn-success" id="applyDataTables">Apply <span class="glyphicon glyphicon-ok" aria-hidden="true"></span> </button>
	        </div>
	    </div>
	  </div>
	</div>

	 <!-- Popup Filter List Operators Selection !--> 
	  <div class="modal fade" id="basicSelectionTables" tabindex="-1" role="dialog" aria-labelledby="basicSelectionTables" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	            <h4 class="modal-title">Chooose the Operator that you would like to add in your filter:</h4>
	            </div>
	            <div class="modal-body">
	               <form class="checkboxOptions2">
	               <div class="radio-toolbar">
	                <div class="radio radio-primary" id="operators">
	                  <p>Comparison Operators for Numbers</p>
	                   <label id="opt2"><input type="radio" name="optradio" value=" = ">Equal =....</label>
	                   <br/>
	                   <label id="opt2"><input type="radio" name="optradio" value =" != ">Not Equal != ....</label>
	                   <br/>
	                   <label id="opt2"><input type="radio" name="optradio" value =" > ">Greater than &gt;....</label>
	                   <br/>
	                   <label id="opt2"><input type="radio" name="optradio" value =" >= ">Greater or Equal than >=....</label>
	                   <br/>
	                   <label id="opt2"><input type="radio" name="optradio" value =" < ">Less than &lt;....</label>
	                   <br/>
	                   <label id="opt2"><input type="radio" name="optradio" value =" <= ">Less or Equal than &lt;....</label>  
	                   <br/>
	                    <br/>
	                   <p>Comparison Operators for Characters</p> 
	                   <label id="opt2"><input type="radio" name="optradio" value=" = ">Equal =....</label>
	                   <br/> 
	                   <label id="opt2"><input type="radio" name="optradio" value =" != ">Not Equal != ....</label>
	                   <br/>
	                   <label id="opt2"><input type="radio" name="optradio" value=" LIKEStart ">Start with ....</label>
	                   <br/>
	                   <label id="opt2"><input type="radio" name="optradio" value=" LIKEEnd ">End with ....</label>
	                   <br/> 
	                   <label id="opt2"><input type="radio" name="optradio" value=" LIKEContain ">Contains  ....</label>
	                   <br/>
	                   <label id="opt2"><input type="radio" name="optradio" value=" NOTLIKEContain ">Not Contains  ....</label>
	                   <br/>
	                   <label id="opt2"><input type="radio" name="optradio" value=" IN ">Specific values (insert "," between 2 or more values, without space) ....</label>
	                   <br/>       
	                </div>
	                </div>
  				   </form>
	            </div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-danger" data-dismiss="modal" id ="cancelData2">Cancel <span class="glyphicon glyphicon-remove" aria-hidden="true"></span> </button>
	                <button type="button" class="btn btn-success" id="applyDataOperator">Apply <span class="glyphicon glyphicon-ok" aria-hidden="true"></span> </button>
	        </div>
	    </div>
	  </div>
	</div>
	<!-- Popup Filter More Conditions or not Filter !--> 
	  <div class="modal fade" id="extendSelectionTables" tabindex="-1" role="dialog" aria-labelledby="extendSelectionTables" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	            <h4 class="modal-title">Chooose the Operator that you would like to add in your filter:</h4>
	            </div>
	            <div class="modal-body">
	               <form class="checkboxOptions3">
	               <div class="radio-toolbar">
	                <div class="radio radio-primary" id="condition">
	                   <label id="opt2"><input type="radio" name="optradio" value=" AND ">Add new Condition for your filter ....</label>
	                   <br/>
	                   <label id="opt2"><input type="radio" name="optradio" value =" OR "> Either the current condition or add new condition ....</label>
	                   <br/>
	                   <label id="opt2"><input type="radio" name="optradio" checked ="checked" value ="EMPTY">Return Session, nothing else to do</label>
	                </div>
	                </div>
  				   </form>
	            </div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-danger" data-dismiss="modal" id ="cancelData3">Cancel <span class="glyphicon glyphicon-remove" aria-hidden="true"></span> </button>
	                <button type="button" class="btn btn-success" id="applyDataOperator2">Apply <span class="glyphicon glyphicon-ok" aria-hidden="true"></span> </button>
	        </div>
	    </div>
	  </div>
	</div>
	  
	<div class="footer">
      @Copyright Ilias Keramefs - Friendly User Query Creator
    </div>
</body>
</html>