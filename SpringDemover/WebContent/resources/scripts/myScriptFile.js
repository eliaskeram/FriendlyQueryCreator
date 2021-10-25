// Javascript for "fileUpload" Page
$(document).ready( function() {
    $("#fileNameSpace").text("");
    $("#sendFile").hide();
    $("#dangerF").hide();
    if($("#fail").text()!=""){
	$("#dangerF").show();
    }
});

$(function() {
    $("#openFile").click(function(){
	$("#basicModal").modal("show");
    });    
    
    $("#applyData").click(function(){
	var file =  $("#nameServer").val();
	var result = $(".checkboxOptions input[type='radio']:checked").val();
	var results = file + "/" + result;
	$("input[placeholder]").attr("placeholder", results);
	$("#sendFile").show();
	$("#fileNameSpace").text(results);
	$("#basicModal").modal("hide");
    });
    
});