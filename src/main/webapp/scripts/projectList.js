/**
 * To display jqxgrid in user list page.
 */
var url = pageContext + "/projects/getListJson?status=" + projectStatus;
// prepare the data
var source = {
	datatype : "json",
	datafields : [ {
		name : 'projectCode',
		type : 'string'
	}, {
		name : 'projectName',
		type : 'string'
	}, {
		name : 'customerName',
		type : 'string'
	}, {
		name : 'description',
		type : 'string'
	}, {
		name : 'openTime',
		type : 'string'
	}, {
		name : 'closedTime',
		type : 'string'
	}, {
		name : 'status',
		type : 'string'
	}, {
		name : 'createdDate',
		type : 'date'
	}, {
		name : 'createdBy',
		type : 'string'
	}, {
		name : 'revision',
		map : 'revisions>0>revisionNo'
	}, {
		name : 'lastModifiedBy',
		type : 'string'
	}, {
		name : 'projectId',
		type : 'string'
	}, {
		name : 'lmodDate',
		type : 'date'
	} ],
	id : 'projectId',
    sortcolumn: 'createdDate',
    sortdirection: 'desc',	
	url : url
};
var cellsrenderer = function(row, columnfield, value, defaulthtml,
		columnproperties, rowdata) {
	if (value < 20) {
		return '<span style="margin: 4px; float: '
				+ columnproperties.cellsalign + '; color: #ff0000;">' + value
				+ '</span>';
	} else {
		return '<span style="margin: 4px; float: '
				+ columnproperties.cellsalign + '; color: #008000;">' + value
				+ '</span>';
	}
}
var dataAdapter = new $.jqx.dataAdapter(source, {
	downloadComplete : function(data, status, xhr) {
	},
	loadComplete : function(data) {
	},
	loadError : function(xhr, status, error) {
	}
});
// initialize jqxGrid
$("#list")
		.jqxGrid(
				{
					width : '100%',
					height : 400,
					theme: 'energyblue',
					source : dataAdapter,
					sortable : true,
					pageable : true,
					autoloadstate : false,
					autosavestate : false,
					columnsresize : true,
					columnsreorder : true,
					// showfilterrow : true,
					// filterable : true,
					columnsresize : true,
					//rowsheight : 200,
					autorowheight: true,
					columns : [
							{
								text : '#',
								datafield : 'stt',
								width : '5%',
								columntype : 'number',
								align : 'center',
								cellsrenderer : function(row, column, value) {
									return "<div style='margin:4px;'>"
											+ (value + 1) + "</div>";
								}
							}, {
								text : 'Project Name',
								datafield : 'projectName',
								align : 'center',
								cellsalign : 'right',
								cellsformat : 'c0',
								width : '10%'
							}, {
								text : 'Created Date',
								datafield : 'createdDate',
								align : 'center',
								width : '10%',
								cellsformat: 'MM/dd/yyyy hh:mm:ss'
							}, {
								text : 'Last Modified',
								datafield : 'lmodDate',
								align : 'center',
								width : '10%',
								cellsformat: 'MM/dd/yyyy hh:mm:ss'
							}, {
								text : 'By',
								datafield : 'lastModifiedBy',
								align : 'center',
								width : '10%',
							}, {
								text : 'Latest Revison',
								datafield : 'revision',
								align : 'center',
								width : '10%',
							},
							/*{
								
								text : 'Price Update',
								/*datafield : 'revision',*/
								//align : 'center',
								//width : '10%',
								/*cellsrenderer : function(row, column, value) {
									var result = "";
									//alert(StatusNeedUpdatePrice);
									//alert(projectStatus);

									if(projectStatus == 'ongoing' && isAdmin == true){
										disabledButtonUpdate();									
										result =
												'<div class="col-md-12" style="margin: auto;padding-top: 10px;">'
												+'<p>'
													+ '<button id="update_price" class="btn btn-olive btn-primary update_price" onclick="update_price(this)"><span class="glyphicon spinning"></span>Update</button>';
													/*'<button id="update_price" class="btn bg-olive margin col-md-2"  onclick="update_price()"' + '>Update</button>';*/
									/*}
									else result =
												'<div class="col-md-12">'
												+'<p>'
													+ '<button id="" disabled class="btn bg-olive margin col-md-2"' + '>Update</button>';
									return result;
									}*/								
							//},*/
							{
								text : 'Action',
								align : 'center',
								datafield : 'projectId',
								width : '50%',
								cellsrenderer : function(row, column, value) {
									var lang = "'VN'";
									var result = 
												'<div class="col-md-12">'
												+'<p>'
													+ '<button style="float:none;margin:6px" class="btn bg-olive margin"  onclick="makeReport('+ value +  ')"' + '>Print (En)</button>'
													+ '<button style="float:none;margin:6px" class="btn bg-olive margin"  onclick="makeReport('+ value + ',' + lang + ')"' + '>Print (VN)</button>'
													+ '<button style="float:none;margin:6px" class="btn bg-olive margin"  onclick="updateProduct('+ value +  ')"' + '>Basic Info</button>'
													+ '<button style="float:none;margin:6px" class="btn bg-purple margin" onclick="addQuotation('+ value +  ')"' + '>Quotation</button>'
													+ '<button style="float:none;margin:6px" class="btn bg-navy margin" onclick="addMaker('+ value +  ')"' + '>Marker List</button>'
													+ '<button style="float:none;margin:6px" class="btn bg-orange margin" onclick="cloneProject('+ value +  ')"' + '>Copy</button>';
									if(projectStatus != 'closed'){
										//alert(StatusNeedUpdatePrice);
										disabledButtonUpdate();										
										if(isAdmin == true)
											result += '<button data-toggle="tooltip" title="price update for project" style="float:none;margin:6px" id="update_price" class="btn btn-olive btn-primary update_price" onclick="update_price(this)"><span class="glyphicon spinning"></span>Price Update</button>';
											result +=	'<button style="float:none;margin:6px" class="btn bg-maroon margin col-md-2" onclick="closeProject('+ value +  ')"' + '>Close</button>';
									}				
								//+ '<button class="btn bg-maroon margin col-md-2" onclick="closeProject('+ value +  ')"' + '>Close1</button>';										
									result +=	'<button style="float:none;margin:6px" class="btn btn-danger margin col-md-1" onclick="deleteProject('+ value +  ')"' + '>X</button>'
												+ '</p>'
											;
									return result;
								}
							}
							]
				});
				
				//
function makeReport(id, lang) {
	var url = pageContext + '/reports/' + id + '/report';
	if(lang)
		url += '?rptLanguage='+ lang;
	window.location.href = url;
}
function updateProduct(id) {
	window.location.href = pageContext + '/projects/' + id + '?form';
}
function addQuotation(projectId, defaultTab){
	var url = pageContext + '/quotation?projectId=' + projectId ;

	if(defaultTab != undefined){
		url = url + "&defautTab=" + defautTab;
	}else{
		//defaul is Add quotation for ELEC
		url = url + "&defautTab=" + 'quotationElec';
	}
	window.location.href = url;
}
function addMaker(projectId, defaultTab){
	var url = pageContext + '/quotation?projectId=' + projectId ;

	if(defaultTab != undefined){
		url = url + "&defautTab=" + defautTab;
	}else{
		//defaul is Add quotation for ELEC
		url = url + "&defautTab=" + 'makerElec';
	}
	window.location.href = url;
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
$("#jqxScrollThumbhorizontalScrollBarlist").onPositionChanged(function(){disabledButtonUpdate();});
/*setTimeout(function(){	
$(".update_price").onPositionChanged(function(){disabledButtonUpdate();});
},100);*/
$(".bg-important").text(projectNeedUpdate);
$(".notification").text("Có "+projectNeedUpdate+" project cần update giá mới");

