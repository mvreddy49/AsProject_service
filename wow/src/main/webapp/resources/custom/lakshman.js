$(document).ready(function(){
	
	$("#speciality_city").change(function(){
		var docspec=$("#speciality_doctor").val();
		var doccity=$("#speciality_city").val();
		if(docspec==""){
			alert("please Enter  spesciality");
			return false;
		}
		
		
		 gethospatals(docspec,doccity);
	});
	function gethospatals(docspec,doccity){
		$.ajax({
			url:"gethospitaldetails.ajax",
			type:"POST",
			data:{"docspec":docspec,"doccity":doccity},
			success:function(response){
				$("#clinicSelect").find('option').remove();
			console.log(response);
			$.each(response,function(index,item){
	    		$("#clinicSelect").append($("<option></option>").text(item.clinicName).val(item.clinicName));
	});
			$("#speciality_city").html(appendString);
			},
			error:function(){
				alert("Please try again..!");
			}
		});
	}
	

	
	

});
