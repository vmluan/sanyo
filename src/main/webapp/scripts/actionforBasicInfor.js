/**
 * To display jqxgrid in user list page.
 */

function makeReport(id, lang) {
	var url = pageContext + '/reports/' + id + '/report';
	if(lang)
		url += '?rptLanguage='+ lang;
	window.location.href = url;
}
function updateProject(id) {
	window.location.href = pageContext + '/projects/' + id + '?form';
}
function addQuotation(projectId){
	window.location.href = pageContext + '/quotation?projectId=' + projectId;
}
function cloneProject(projectId){
	var url = pageContext + '/projects/' + projectId + '?clone';
	$.ajax({
		type : "POST",
		contentType : 'application/json',
		url : url,
		success : function(msg) {
			alert('clone successfully.');
			$("#list").jqxGrid('updatebounddata');
		},
		complete : function(xhr, status) {
			if(xhr.status==403)
				alert('You have no permission to do this action');
		}
});
	
}
function deleteProject(projectId){
    var result = confirm('Do you want to delete this record?');
    if (result == false)
		return;
	var url = pageContext + '/projects/' + projectId + '?delete';
	$.ajax({
		type : "POST",
		contentType : 'application/json',
		url : url,
		success : function(msg) {
			alert('delete successfully.');
			$("#list").jqxGrid('updatebounddata');
		},
		complete : function(xhr, status) {
			if(xhr.status==403)
				alert('You have no permission to do this action');
		}
});
	
}
function closeProject(projectId){
    var result = confirm('Do you want to close project?');
    if (result == false)
		return;
	var url = pageContext + '/projects/' + projectId + '?close';
	$.ajax({
		type : "POST",
		contentType : 'application/json',
		url : url,
		success : function(msg) {
			$("#list").jqxGrid('updatebounddata');
		},
		complete : function(xhr, status) {
			if(xhr.status==403)
				alert('You have no permission to do this action');
		}


});

	
}

function update_price(th)
{
	//alert($(th).attr("onClick"));
	bootbox.confirm("Are you sure?", function(result) {
		if(result==true)
		{
  //Example.show("Confirm result: "+result);
  			$(th).children().addClass("glyphicon-refresh");
  			//var projectID = $(th).parent().parent().parent().next().children().children().children().attr("onClick");
			var projectID = $(th).prev().attr("onClick");
			//console.log(projectID);
			projectID = projectID.split("(").pop().split(")")[0];
			//alert(projectID);
			if (projectID == "")
				return;
			var url = pageContext + '/projects/' + projectID + '?update';
			$.ajax({
				type : "POST",
				contentType : 'application/json',
				url : url,
				success : function(msg) {
					//alert(msg);
					//if(msg==false)
					//{
						$(".price_span").text("update "+msg+" encounter!");
					//}
					$("#notificationUpdatePrice").slideDown("slow");
					setTimeout(function(){
						$("#notificationUpdatePrice").slideUp("slow");
						$(th).children().removeClass("glyphicon-refresh");
					},200);
				},
				complete : function(xhr, status) {
					//alert(xhr);
					if(xhr.status==403)
						alert('You have no permission to do this action');
				}


			});
		}
	});

}
function disabledButtonUpdate(){
	setTimeout(function(){

			$(".update_price").each(function(index){
				if(StatusNeedUpdatePrice!=null)
				{
					if(StatusNeedUpdatePrice.length==0)
					{
						$(this).attr("disabled","true");
						$(this).removeAttr("onClick");
					}
					else
					{
						index = StatusNeedUpdatePrice.length - index-1;
						if(StatusNeedUpdatePrice[index]!=true)
						{
							$(this).attr("disabled","true");
							$(this).removeAttr("onClick");
						}
					}
				}
				else
				{
					$(this).attr("disabled","true");
					$(this).removeAttr("onClick");
				}
				
			});
		},0);
}
/*
jQuery.fn.onPositionChanged = function (trigger, millis) {
    if (millis == null) millis = 10;
    var o = $(this[0]); // our jquery object
    if (o.length < 1) return o;

    var lastPos = null;
    var lastOff = null;
    setInterval(function () {
        if (o == null || o.length < 1) return o; // abort if element is non existend eny more
        if (lastPos == null) lastPos = o.position();
        if (lastOff == null) lastOff = o.offset();
        var newPos = o.position();
        var newOff = o.offset();
        if (lastPos.top != newPos.top || lastPos.left != newPos.left) {
            $(this).trigger('onPositionChanged', { lastPos: lastPos, newPos: newPos });
            if (typeof (trigger) == "function") trigger(lastPos, newPos);
            lastPos = o.position();
        }
        if (lastOff.top != newOff.top || lastOff.left != newOff.left) {
            $(this).trigger('onOffsetChanged', { lastOff: lastOff, newOff: newOff});
            if (typeof (trigger) == "function") trigger(lastOff, newOff);
            lastOff= o.offset();
        }
    }, millis);

    return o;
};
//$("#jqxScrollThumbhorizontalScrollBarlist").onPositionChanged(function(){disabledButtonUpdate();});
/*setTimeout(function(){	
$(".update_price").onPositionChanged(function(){disabledButtonUpdate();});
},100);*/
//$(".bg-important").text(projectNeedUpdate);
//$(".notification").text("Có "+projectNeedUpdate+" project cần update giá mới");

/*
 page: is used to location the 'quotation' page or 'basic information' page
 		These 2 pages are required different menu
 */
function createButtonAction(value,lang,page)
{
	var lang = "'VN'";
	var result =
													'<button style="float:none;margin:6px" class="btn bg-olive margin"  onclick="makeReport('+ value +  ')"' + '>Print (En)</button>'
													+ '<button style="float:none;margin:6px" class="btn bg-olive margin"  onclick="makeReport('+ value + ',' + lang + ')"' + '>Print (VN)</button>';
	if (page == "quotation")
													result += '<button style="float:none;margin:6px" class="btn bg-olive margin" onclick="updateProject('+ value +')"' + '>Basic info</button>';
	else
													result += '<button style="float:none;margin:6px" class="btn bg-purple margin" onclick="addQuotation('+ value +  ')"' + '>Quotation</button>';

													result += '<button style="float:none;margin:6px" class="btn bg-navy margin" onclick="addMaker('+ value +  ')"' + '>Marker List</button>'
													+ '<button style="float:none;margin:6px" class="btn bg-orange margin" onclick="cloneProject('+ value +  ')"' + '>Copy</button>';
	if(projectStatus != 'closed'){
										//alert(StatusNeedUpdatePrice);
																			
										if(isAdmin == true)
											result +=	'<button style="float:none;margin:6px" class="btn bg-maroon margin" onclick="closeProject('+ value +  ')"' + '>Close</button>';
									}				
								//+ '<button class="btn bg-maroon margin col-md-2" onclick="closeProject('+ value +  ')"' + '>Close1</button>';										
									result +=	'<button style="float:none;margin:6px" class="btn btn-danger margin" onclick="deleteProject('+ value +  ')"' + '>X</button>';

	$("#evelop-buttonAction").append(result);
}
disabledButtonUpdate();
