// Javascript for "queries" Page
var table;
var num=0;
$(document).ready(function() {
    $(" #resetQuery" ).hide();
    $(" #updateQuery" ).hide();
    $(" #filterSelection" ).hide();
  
    if($( "#filter" ).text()!=""){
	   $(" #resetQuery" ).show();
    }
    
    // DataTable
   table = $('#resultsSet').DataTable({
	  "aLengthMenu": [[5, 10, 15, 20, 25, -1], [5, 10, 15, 20, "All"]],
	  "columnDefs": [{
	      "defaultContent": "-",
	      "targets": "_all"
	    }]
	  });
    
    // Setup - add a text input to each footer cell
    $('#resultsSet tfoot th').each( function () {
        var title = $(this).text();
        if(title!="")
        $(this).html( '<input type="text" placeholder="Search '+title+'" />' );
    });
    
    // Apply the search
    table.columns().every( function () {
        var that = this;
        $( 'input', this.footer() ).on( 'keyup change', function () {
            if ( that.search() !== this.value ) {
                that
                    .search( this.value )
                    .draw();
            }
        });
    });
    
   // Default Value for the form
    var data1 = table.row(':eq(0)').data();
    for(var i=0;i<data1.length;i++){
        if(data1[i].indexOf('&amp;')>= 0){
    	data1[i] = data1[i].replace(/\&amp;/g,'&');
        }
    }
    for(var i=1;i<data1.length;i++){
       document.getElementById("inputColumn"+ i).defaultValue = data1[i];
   }
    
    // Click on the row, update form contain
    $('#resultsSet tbody').on('click','tr', function () {
        var data = table.row( this ).data();
        num = data[0];
        num--;
        for(var i=0;i<data.length;i++){
            if(data[i].indexOf('&amp;')>= 0){
        	data[i] = data[i].replace(/\&amp;/g,'&');
            }
        }
        for(var i=1;i<data.length;i++){
           document.getElementById("inputColumn"+ i).defaultValue = data[i];
       }
    });
} );

$(function() {
    $("#createFilter").click(function(){
	  $( "#filterTable" ).empty();
	  $( "#filter" ).empty();
	  $("#basicTables").modal("show");
	  $("#filterSelection").show();
	  $("#resetQuery").hide();
});
    
    $("#cancelData").click(function(){
         $( "#filterTable" ).empty();
         $( "#filter" ).empty();
         $("#filterSelection").hide();
    });
        
    $("#cancelData2").click(function(){
         $( "#filterTable" ).empty();
         $( "#filter" ).empty();
         $("#filterSelection").hide();
    });
        
    $("#cancelData3").click(function(){
        $( "#filterTable" ).empty();
        $( "#filter" ).empty();
        $("#filterSelection").hide();
    });
    
    $("#applyDataTables").click(function(){
    	var result = $(".checkboxOptions1 input[type='radio']:checked").val();
    	$( "#filterTable" ).append(result);
    	$( "#filter" ).append(result);
    	$("#basicTables").modal("hide");
    	$("#basicSelectionTables").modal("show");
      });
    
    $("#applyDataOperator").click(function(){
    	var resultL = $(".checkboxOptions2 input[type='radio']:checked").val();
    	$( "#basicSelectionTables" ).modal("hide");
    	$( "#filterTable" ).append(resultL);
    	$( "#filter" ).append(resultL);
    	var resultS ="";
    	bootbox.prompt("Write your value: text or number",
    		function(result){ 
    	           resultS = result;
    	           $( "#filterTable" ).append(resultS);
    	           $( "#filter" ).append(resultS);
    	           $( "#extendSelectionTables" ).modal("show");
               });
    });

    $("#applyDataOperator2").click(function(){
    	var resultL = $(".checkboxOptions3 input[type='radio']:checked").val();
    	$("#extendSelectionTables").modal("hide");
    	if(resultL!="EMPTY"){
            	$( "#filterTable" ).append(resultL);
            	$( "#filter" ).append(resultL);
            	var resultS ="";
            	$("#basicTables").modal("show");
    	}
    	$("#updateQuery").show();
    });
    
    $("#basicTables").on('hidden.bs.modal', function(e) {
        $("#basicTables .modal-body").find('input:radio').prop('checked', false);
    });
    
    $("#basicSelectionTables").on('hidden.bs.modal', function(e) {
        $("#basicSelectionTables .modal-body").find('input:radio').prop('checked', false);
    });
    
    $("#extendSelectionTables").on('hidden.bs.modal', function(e) {
        $("#extendSelectionTables .modal-body").find('input:radio').prop('checked', false);
    });

    $("#next").click(function(){
	num++;
	if(num>table.data().length-1) num = 0;
	var temp = ':eq(' + num +')';
	var data1 = table.row(temp).data();
	    for(var i=0;i<data1.length;i++){
	        if(data1[i].indexOf('&amp;')>= 0){
	    	data1[i] = data1[i].replace(/\&amp;/g,'&');
	        }
	    }
	    for(var i=1;i<data1.length;i++){
	       document.getElementById("inputColumn"+ i).defaultValue = data1[i];
	   }
	  
    });
    
    $("#previous").click(function(){
	num--;
	if(num<0) num = table.data().length-1;
	var temp = ':eq(' + num +')';
	var data1 = table.row(temp).data();
	    for(var i=0;i<data1.length;i++){
	        if(data1[i].indexOf('&amp;')>= 0){
	    	data1[i] = data1[i].replace(/\&amp;/g,'&');
	        }
	    }
	    for(var i=1;i<data1.length;i++){
	       document.getElementById("inputColumn"+ i).defaultValue = data1[i];
	   } 
    });
    
    $("#updateQuery").click(function(){
	$("#resetQuery").show();
    });
    
    $("#secondSQL").click(function(){
	$("#sqlLanguage").modal("show");
    });    
    
    $("#resetQuery").click(function(){
        $( "#filterTable" ).empty();
        $( "#filter" ).empty();
        $( "#filterTemp" ).empty();
        $("#filterSelection").empty();
        $("#filterSelection").hide();
    });
    
});