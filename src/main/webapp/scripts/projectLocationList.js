/**
 * To display jqxgrid in user list page.
 */
var url = pageContext + "/projects/getLocationsJson";
// prepare the data
var source = {
	datatype : "json",
	datafields : [ {
		name : 'locationId',
		type : 'string'
	},{
		name : 'orderNo',
		type : 'number'
	}, {
		name : 'locationName',
		type : 'string'
	}, {
		name : 'locationDesc',
		type : 'string'
	} ],
	id : 'locationId',
    sortcolumn: 'orderNo',
    sortdirection: 'asc',	
	url : url,
	data : {
		projectId : projectId
	}
};
var dataAdapter = new $.jqx.dataAdapter(source, {
	downloadComplete : function(data, status, xhr) {
	},
	loadComplete : function(data) {
	},
	loadError : function(xhr, status, error) {
	}
});
// initialize jqxGrid
$("#listLocation")
		.jqxGrid(
				{
					width : '100%',
					height : 300,
					theme : 'energyblue',
					source : dataAdapter,
					sortable : true,
					pageable : true,
					autoheight : true,
					autoloadstate : false,
					autosavestate : false,
					columnsresize : true,
					columnsreorder : true,
					// showfilterrow : true,
					// filterable : true,
					columnsresize : true,
					//rowsheight : 45,
					showpinnedcolumnbackground : false,
					altrows : true,
					autorowheight: true,
					rendered: function(type)
					{
						// select all grid cells.
						var gridCells = $('#listLocation').find('.jqx-grid-cell');

						// initialize the jqxDragDrop plug-in. Set its drop target to the second Grid.
						gridCells.jqxDragDrop({
							appendTo: 'body',  dragZIndex: 99999,
							dropAction: 'none',
							initFeedback: function (feedback) {
								feedback.height(25);
							},
							dropTarget: $('#listLocation'), revert: true
						});
						gridCells.off('dragStart');
						gridCells.off('dragEnd');
						gridCells.off('dropTargetEnter');
						gridCells.off('dropTargetLeave');

						// disable revert when the dragged cell is over the second Grid.
						gridCells.on('dropTargetEnter', function () {
							gridCells.jqxDragDrop({ revert: false });
						});
						// enable revert when the dragged cell is outside the second Grid.
						gridCells.on('dropTargetLeave', function () {
							gridCells.jqxDragDrop({ revert: true });
						});
						// initialize the dragged object.
						gridCells.on('dragStart', function (event) {
							var value = $(this).text();
							var position = $.jqx.position(event.args);
							var cell = $("#listLocation").jqxGrid('getcellatposition', position.left, position.top);
							/*$(this).jqxDragDrop('data', {
								value: value
							});
							*/
							$(this).jqxDragDrop('data', cell.row);
						});
						// set the new cell value when the dragged cell is dropped over the second Grid.      
						gridCells.on('dragEnd', function (event) {
							var startRow = $(this).jqxDragDrop('data');
							var position = $.jqx.position(event.args);
							var cell = $("#listLocation").jqxGrid('getcellatposition', position.left, position.top);
							if (cell != null) {
								var targetRow = $("#listLocation").jqxGrid('getrowdata', cell.row);
								var test='';
								// move dapped row to afer the dropped row.
								//update orderNo of start row = endRow 's orderNo.
								//if startRowOder < endRowOrder then 
									//update orderNo of start row = endRow 's orderNo.
									//endOrder = endORder -1. apply for (startOrder,endOrder]
									
								//else
									//update orderNo of start row = endRow 's orderNo.
									//endOrder = endORder +1. apply for [endRow, startRow)
									
								
								var endRowOrderNo = $('#listLocation').jqxGrid('getcellvalue', cell.row, "orderNo");
								var startRowOrderNo = $('#listLocation').jqxGrid('getcellvalue', startRow, "orderNo");
								//update startRow
								if(startRow != cell.row){
									if(startRowOrderNo < endRowOrderNo){
										updateOrderNo(startRowOrderNo, endRowOrderNo, - 1 );
									}else{
										updateOrderNo(endRowOrderNo, startRowOrderNo, +1);
									}
									$("#listLocation").jqxGrid('setcellvalue', startRow, "orderNo", endRowOrderNo);
								}
								//update endRow and prior rows.
								  
								//  $("#listLocation").jqxGrid('setcellvalue', cell.row, "orderNo", currentOrderNo +1);
							}
						});
					},
					columns : [
							{
								text : '#',
								datafield : 'stt',
								width : '5%',
								columntype : 'number',
								cellsrenderer : function(row, column, value) {
									return "<div style='margin:4px;'>"
											+ (value + 1) + "</div>";
								}
							}, {
								text : 'Name',
								datafield : 'locationName',
								align : 'center',
								cellsalign : 'left',
								cellsformat : 'c0',
								width : '30%'
							}, {
								text : 'Description',
								datafield : 'locationDesc',
								align : 'center',
								cellsalign : 'left',
								cellsformat : 'c0',
								width : '30%'
							}, {
								text : '',
								datafield : 'orderNo',
								hidden : false,
								width : '10%'
							},
							{
								text : 'Action',
								datafield : 'locationId',
								align : 'center',
								width : '35%',
								cellsrenderer : function(row, column, value) {
									return '<div class="col-md-12">'
												+'<p>'
													+ '<button type="button" class="btn bg-olive margin col-md-5"  onclick="updateLocation('+ value +  ')"' + '>Edit</button>'
													+ '<button type="button" class="btn btn-danger margin col-md-1" onclick="deleteLocation('+ value +  ')"' + '>X</button>'
												+ '</p>'
											+ '</div>'
											;									
								}
							}
							]
				});
function updateLocation(id) {
	window.location.href = pageContext + '/projects/locations/' + id + '?form';
}
function deleteLocation(id){
    var result = confirm('Do you want to delete this record?');
    if (result == false)
		return;
	var url = pageContext + '/projects/locations/' + id + '?delete';
	$.ajax({
		type : "POST",
		contentType : 'application/json',
		url : url,
		success : function(msg) {
			alert('delete successfully.');
			$("#listLocation").jqxGrid('updatebounddata');
		},
		complete : function(xhr, status) {

		}
});
}

// for drag and drop
//$("#jqx-grid-cell").$.jqxDragDrop({ dropTarget: $('#listLocation') });
function updateOrderNo(start, end, increment){
	var rows = $("#listLocation").jqxGrid('getrows'); //get all rows with order as displayed on GUI
	var length = rows.length;
	//$('#listLocation').jqxGrid('getrowdatabyid', rowid);
	for(var i = start; i <= end; i++){
		var row = rows[i];
		//var currentOrderNo = $('#listLocation').jqxGrid('getcellvalue', i, "orderNo");
		//$("#listLocation").jqxGrid('setcellvalue', i, "orderNo", currentOrderNo + increment);
	}
}