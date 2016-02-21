
$(".LabourEdit").mouseover(function(){
	$(this).children().removeClass();
	$(this).children().addClass("editshow");
});
$(".LabourEdit").mouseleave(function(){
	$(this).children().removeClass();
	$(this).children().addClass("edithide");
});
////save summary


    	//////////////////////
$(".LabourEdit").click(function(){
	var values = $(this).text().replace("%","").replace("Edit","");
	var kq = $(this);
	bootbox.prompt({
	  title: "Enter the value",
	  value: values,
	  callback: function(result) {
	    if (result === null) {
	    } else {
	    	if(values!=result)
	      		{
	      			kq.text(result+"%");
	      			$(".LabourEdit").each(function(index){
	      				var val = parseInt($(this).text().replace("%","").replace("Edit",""));
	      				if(index===0)
	      					expenseLis.siteexpenses = val;
	      				if(index===1)
	      					expenseLis.japanese = val;
	      				if(index===2)
	      					expenseLis.engineer = val;
	      				if(index===3)
	      					expenseLis.profit = val;
	      				
	      			});
	      			
				    var data = JSON.stringify(expenseLis);
	      			console.log(data);
	      			  $.ajax({
				        type : "POST",
				        contentType : 'application/json',
				        data : data,
				        url : url,
				        success : function(msg) {
				        	//("ldkf");
				            loadMatch();
				        },
				        complete : function(xhr, status) {
				            bootbox.alert('Update complete!', function() {
							  //Example.show("Hello world callback");
							});
				        }
				    });
	      		}
	    }
	  }
	});
});
//////////////////////////

////////////////////////////////////////
	total_location("amount",6);
	total_location("portion",8);
	total_location("labour",9);
	total_location("total",10);

	total_location("amount-metrical",6);
	total_location("portion-metrical",8);
	total_location("labour-metrical",9);
	total_location("total-metrical",10);
	loadMatch();
	function loadMatch(){
		$(".Profit").text(parseFloat($(".Profit").text())-(parseFloat($(".Profit").prev().text().replace("%","").replace("Edit","")))/100*parseFloat($(".Profit").text()));
		$(".UNITRATE").each(function(){
			//alert(7/100);
			$(this).text(parseFloat($(this).next().next().next().next().next().text())-parseFloat((parseFloat($(this).next().next().next().next().text().replace("%","").replace("Edit","")))/100)*parseFloat($(this).next().next().next().next().next().text()));
		});
		$(".AMOUNT").each(function(){
			//alert(($(this).prev().text()));
			$(this).text(parseFloat($(this).prev().text())*parseFloat($(this).prev().prev().text()));
		});
	}
	function total_location(ClassName,nth){
		$("."+ClassName).each(function(index, el) {
		var total = 0;
		$(this).parent().prev().prev().find("tbody").find('tr').each(function(){
		var t=	$(this).find('td:nth-child('+nth+')').text()
		total += parseFloat(t);
		});
		$(this).text(Math.round(total*10000000)/10000000);
	});
	}
	sum_total("amount","total-amount");
	sum_total("portion","total-portion");
	sum_total("labour","total-labour");
	sum_total("total","total-total");

	sum_total("amount-metrical","total-amount-metrical");
	sum_total("portion-metrical","total-portion-metrical");
	sum_total("labour-metrical","total-labour-metrical");
	sum_total("total-metrical","total-metrical");
	function sum_total(amount,totalamount){

		var sum=0;
		$("."+amount).each(function(index, el) {
			//alert($(this).text());
			
			if(sum+"" =="NaN")
			{
				sum=0;
			}
			sum += parseFloat($(this).text());
			
		});
			$("."+totalamount).text(Math.round(sum*10000000)/10000000);
	}

