var getFormatedDate_YYYYMMDD = function(){
		var d = new Date();
		var month = d.getMonth()+1 +'';
		var day = d.getDate() + '';
		if(month.length == 1)
			month = "0" + month;
		if(day.length == 1)
			day = "0" + day;
		var date = (d.getFullYear())+'-'
		+ month
		+ '-' + day;
		return date;
	};