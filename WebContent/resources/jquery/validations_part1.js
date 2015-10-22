validationPart1 = function(){};

validationPart1.start = function(){
	$("#tab-1").addClass("selected");
	$("div.page").hide();
	$("div#query1").show();
	
	$( "#part2" ).bind("click" , function(){
		
	});
	
	
	$("#part1Content ul li").bind("click" , function(){
		var id = $(this).attr("id");
		
		$("ul li").removeClass("selected");
		$("#"+id).addClass("selected");
		$("div.page").hide();
		var numId = id.split("-")[1];
		$("div#query"+numId).show();
		
		if( numId == 2 ){
			validationPart1.fetchData("Q2:DD" , 2);
		}
		else if( numId == 3 ){
			var arr = ["DN" , "ClusterID"];
			for(var i = 0; i< 2;i++){
				validationPart1.fetchData("Q3:"+arr[i] , 3+i);
			}
		}
		else if( numId == 4 ){
			var arr = ["DN" , "GoID"];
			for(var i = 0; i< 2;i++){
				validationPart1.fetchData("Q4:"+arr[i] , 5+i);
			}
		}
		else if( numId == 5 ){
			validationPart1.fetchData("Q5:GoID" , 7);
		}
		else if( numId == 6 ){
			validationPart1.fetchData("Q6:GoID" , 8);
		}
		
	});

	$("#mainSelect").change(function(){
		$("#resultQ1").text("");
		alert("select called");
		var val = $(this).val();
		$("#optionSelectQ1").attr("data-type",val);
		var data = $(this).attr("data-key")+":"+val;
		alert("data:"+data);
		if( val == "NA" ){
			$("#errorPart1 span").text("Please select option other than default");
		}
		else{
			$("#errorPart1 span").text("");
			validationPart1.fetchData(data , 1);
		}
	});
	
	
	$("#q1Run").bind("click" , function(){
		var type = $(this).attr("data-rid");
		var val = $("#optionSelectQ1").val();
		var data = type+"-"+$("#optionSelectQ1").attr("data-type")+":"+val;
		alert("val: "+data);
		if( val == "NA" ){
			$("#errorPart1 span").text("Please select option other than default");
		}
		else{
			$("#errorPart1 span").text("");
			validationPart1.fetchResult(data , "result"+type);
		}
	});
	
	$("button[data-type='query']").bind("click" , function(){
		
		$("#errorPart1 span").text("");
		
		var type = $(this).attr("data-rid");
		var data = "";
		
		if( type == "Q2" ){
			data = type+":"+$("#optionSelectQ2").val();
		}
		else if( type == "Q3" ){
			data = type+":"+$("#optionSelectQ3-1").val()+","+$("#optionSelectQ3-2").val();
		}
		else if( type == "Q4" ){
			data = type+":"+$("#optionSelectQ4-1").val()+","+$("#optionSelectQ4-2").val();	
		}
		else if( type == "Q5" ){
			data = type+":"+$("#optionSelectQ5-2").val();	
		}
		else if( type == "Q6" ){
			data = type+":"+$("#optionSelectQ6-1").val();	
		}
		
		validationPart1.fetchResult(data , "result"+type);
	});
};


validationPart1.fetchData = function(data, queryNum){
	$.ajax({
		type:"post",
		url:'/DataWarehouse/fetchData',
		data: data,
		success: function(result){
			alert("success Result: "+result);
			
			if( queryNum == 1 ){
				$("#optionSelectQ1").text("");
				$("#optionSelectQ1").append(validationPart1.processDataForSelect(result));
			}
			else if( queryNum == 2 ){
				$("#optionSelectQ2").text("");
				$("#optionSelectQ2").append(validationPart1.processDataForSelect(result));
			}
			else if( queryNum == 3 ){
				$("#optionSelectQ3-1").text("");
				$("#optionSelectQ3-1").append(validationPart1.processDataForSelect(result));
			}
			else if( queryNum == 4 ){
				$("#optionSelectQ3-2").text("");
				$("#optionSelectQ3-2").append(validationPart1.processDataForSelect(result));
			}
			else if( queryNum == 5 ){
				$("#optionSelectQ4-1").text("");
				$("#optionSelectQ4-1").append(validationPart1.processDataForSelect(result));
			}
			else if( queryNum == 6 ){
				$("#optionSelectQ4-2").text("");
				$("#optionSelectQ4-2").append(validationPart1.processDataForSelect(result));
			}
			else if( queryNum == 7 ){
				$("#optionSelectQ5-2").text("");
				$("#optionSelectQ5-2").append(validationPart1.processDataForSelect(result));
			}
			else if( queryNum == 8 ){
				$("#optionSelectQ6-1").text("");
				$("#optionSelectQ6-1").append(validationPart1.processDataForSelect(result));
			}
		},
		failure:function(data){
			alert("Ajax failed");
		}
	});
};


validationPart1.processDataForSelect = function(data){
	var options = "<option selected value='NA'>Select</option>";
	$.each(data.split(":"), function( index, value ) {
		if( value != "" ){
		  options += "<option value='"+value+"'>"+value+"</option>";
		}
	});
	
	return options;
};


validationPart1.fetchResult = function(data, resultId){
	queryURL = "fetchResult";
	
	if( resultId == "resultQ4" ){
		queryURL = "fetchResultQ4";
	}
	else if( resultId == "resultQ5" ){
		queryURL = "fetchResultQ5";
	}
	else if( resultId == "resultQ6" ){
		queryURL = "fetchResultQ6";
	}
	
	$.ajax({
		type:"post",
		url:'/DataWarehouse/'+queryURL,
		data: data,
		success: function(result){
			alert("success Result: "+result);
			$("#"+resultId).text("");
			$("#"+resultId).append("Query Result: "+result);
		},
		failure:function(data){
			alert("Ajax failed");
		}
	});
};


