	//$(".amount").parent().pre().pre().attr("id");
	// $(".amount").parent().prev().prev().find("tbody").find("tr").each(function(){
	// 	alert($(this).find("td:nth-child(6)").text());
		
	// });
	// 

	// $(".amount").each(function(index, el) {
	// 	var total = 0;
	// 	$(this).parent().prev().prev().find("tbody").find('tr').each(function(){
	// 	var t=	$(this).find('td:nth-child(6)').text()
	// 	total += parseFloat(t);
	// 	});
	// 	$(this).text(total);
	// });
	// $(".portion").each(function(index, el) {
	// 	var total = 0;
	// 	$(this).parent().prev().prev().find("tbody").find('tr').each(function(){
	// 	var t=	$(this).find('td:nth-child(8)').text()
	// 	total += parseFloat(t);
	// 	});
	// 	$(this).text(total);
	// });
	// $(".labour").each(function(index, el) {
	// 	var total = 0;
	// 	$(this).parent().prev().prev().find("tbody").find('tr').each(function(){
	// 	var t=	$(this).find('td:nth-child(9)').text()
	// 	total += parseFloat(t);
	// 	});
	// 	$(this).text(total);
	// });
	// 	$(".total").each(function(index, el) {
	// 	var total = 0;
	// 	$(this).parent().prev().prev().find("tbody").find('tr').each(function(){
	// 	var t=	$(this).find('td:nth-child(10)').text()
	// 	total += parseFloat(t);
	// 	});
	// 	$(this).text(Math.round(total*10000000)/10000000);
	// });
	total_location("amount",6);
	total_location("portion",8);
	total_location("labour",9);
	total_location("total",10);

	total_location("amount-metrical",6);
	total_location("portion-metrical",8);
	total_location("labour-metrical",9);
	total_location("total-metrical",10);

	
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

