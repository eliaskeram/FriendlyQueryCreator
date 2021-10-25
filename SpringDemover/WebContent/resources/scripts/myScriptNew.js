// Javascript for "new" Page
$(document).ready( function() {
    $("#formQuery").hide();
    var idKeys = $( "#i1" ).text();
    var tdValues = $( "#i4" ).text();
    var lenValues =  $( "#sizeofT" ).text();
    
    var key = [];
    var columnsValues=[];

    for(var i=1;i<=idKeys;i++){
	var keys = $( "#keysIDs" + i).text();
	key.push(keys.toLowerCase());
        var i1=0;
        for(var i1=1;i1<=tdValues;i1++){
	      var column = $( "#textR" + i1).text();
	      if(keys.includes(column)){
		  columnsValues.push(column);
	      }
         }
     }
    var sizeOf = columnsValues.length;
    for(var a=1;a<=idKeys;a++){
	var columnPrim = $( "#textMPrim" + a).text();
	var columnForeign = $( "#textMFore" + a).text();
	$("td:contains("+columnPrim+")").addClass("keysPrimary");
	$("td:contains("+columnForeign+")").addClass("keysForeign");
     }

	 if($("#txtMessage2").text()!=""){
		$("#ClearQ").prop('disabled', true);
		$("#myResults").show();
		$("#dublicate").hide();
		$("#createQ").show();
	     $("#Output").text($("#txtMessage2").text());
	     if($("#txtMessage6").text()=="")  $("#Output2").text("..");
	     else {
                    $("#Output2").text($("#txtMessage6").text());
             }
	}
	 else {	
             $( ".draggable .fields" ).show();	
             $("#myResults").hide();
             $("#run").hide();
             $("#dublicate").hide();
    	     $("#ClearQ").prop('disabled', true);
    	     $("#whereAreas").hide();
    	     $("#createQ").hide();
	}
});

$(function() {
$("#myResults").show();	

 var tempValues = [];
$('#Output').droppable({  
    drop: function(e, ui){  
  	  var result = $( "#txtMessage" );
  	  var result2 = $( "#txtMessage3" );
  	  var value = 0;
  	  var value2 = 0;
  	  var str=0;
  	  addPopup++;

  	 var temp = $("<span id='drop'></span>").text(ui.draggable.text()).appendTo(this);

  	 if(tempValues.indexOf(temp.text())==-1) {
          tempValues.push(temp.text());
  	  value = ui.helper.text();	
  	  value2 = ui.draggable.text();
  	  result.insertAtCaret(value+", ");
  	  
  	  if(value2.indexOf(".")>-1){ str = value2.substr(0,value2.indexOf("."));  result2.insertAtCaret(str+", ");
  	  }else {result2.insertAtCaret(value2+", ");}
  	  showme(result.val());
  	  showme2(result2.val());
  	  showme3(result.val());
  	  
          $("#whereAreas").show();
          $("#ClearQ").prop('disabled', false);
          $("#run").show();
          $("#createQ").show();
          $("#dublicate").show();
      }
  	 else{
  	   $('span:contains(' + temp.text() + ')').each(function(index, element) { 
  	        $(element).text($(element).text().replace( temp.text(), ""));
  	    });
  	 }
    },	      
});

$("td").draggable({
	disabled: false,
        helper: "clone",
        revert: "invalid",
        start: function (e, ui) {
            ui.helper.animate({
                fontSize: "18px",
                color: "red",
                border: "0px",
                backgroundColor : "transparent",
                zIndex: "1",
            });
        },
});

$("#ClearQ").click(function(){
	$('#Output').empty();
	$('#Output').append("<span>&emsp;</span>");
	clear();
	$("#ClearQ").prop('disabled', true);
	$("#run").hide();
	$("#dublicate").hide();
	$("#whereAreas").hide();
	$("#createQ").hide();
	tempValues = [];
});
});

function remove(s){
	 var str = s;
	 str = str.replace(/[ ]/g," ").split(",");
	 var result = [];
	 for(var i = 0; i < str.length ; i++){
	     if(result.indexOf(str[i]) == -1) {
		 result.push(str[i]);
		}
             }
	 result=result.join(",");
	 return result;
}

var addPopup = 0;

function showme(num) {
	 var s = num.length-1;
	 var str = num;
	 $( "#txtMessage2" ).text(remove(str).slice(0,-2));
};

function showme2(num) {
	 var s = num.length-1;
	 var str = num;	
	 $( "#txtMessage4" ).text(remove(str).slice(0,-2));
};

function showme3(num) {
	 var s = num.length-1;
	 var str = num;
	 $( "#Output" ).text(remove(str).slice(0,-2));
	 
};

function clear(){
	$( "#txtMessage" ).val("");	
	$( "#txtMessage3" ).val("");
	$( "#txtMessage6" ).val("");
	var result = $( "#txtMessage" );
  	var result2 = $( "#txtMessage3" );
  	showme(result.val());
	showme2(result2.val());
	$('#Output').append("<span></span>"); 	
};

$.fn.insertAtCaret = function(myValue) {
	return this.each(function() {
		var me = this;
		if (document.selection) { // IE
			me.focus();
			sel = document.selection.createRange();
			sel.text = myValue;
			me.focus();
		} else if (me.selectionStart || me.selectionStart == '0') { // Real browsers
			var startPos = me.selectionStart, endPos = me.selectionEnd, scrollTop = me.scrollTop;
			me.value = me.value.substring(0, startPos) + myValue + me.value.substring(endPos, me.value.length);
			me.focus();
			me.selectionStart = startPos + myValue.length;
			me.selectionEnd = startPos + myValue.length;
			me.scrollTop = scrollTop;
		} else {
			me.value += myValue;
			me.focus();
		}
	});
};