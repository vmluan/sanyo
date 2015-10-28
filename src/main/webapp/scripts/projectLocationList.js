/**
 * To display jqxgrid in user list page.
 */
var url = "/projects/getLocationsJson";
// prepare the data
var source = {
	datatype : "json",
	datafields : [ {
		name : 'locationId',
		type : 'string'
	}, {
		name : 'locationName',
		type : 'string'
	}, {
		name : 'locationDesc',
		type : 'string'
	} ],
	id : 'locationId',
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
					columns : [
							{
								text : 'Action',
								datafield : 'locationId',
								align : 'center',
								width : '25%',
								cellsrenderer : function(row, column, value) {
									return '<div class="col-md-6">'
										+ '<a class="btn btn-app col-md-2" onclick="updateLocation('+ value + ')">'
										+ '<i class="glyphicon glyphicon-edit"></i>'
										+ '</a>'
										+ '<a class="btn btn-app col-md-2">'
										+ '<i class="glyphicon glyphicon-remove-circle" onclick="deleteLocation('+ value +  ')"' + '></i>'
										+ '</a>' 
										+ '</div>';										
								}
							},
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
								width : '35%'
							}, {
								text : 'Description',
								datafield : 'locationDesc',
								align : 'center',
								cellsalign : 'left',
								cellsformat : 'c0',
								width : '35%'
							} ]
				});
function updateLocation(id) {
	window.location.href = '/projects/locations/' + id + '?form';
}
function deleteLocation(id){
    var result = confirm('Do you want to delete this record?');
    if (result == false)
		return;
	var url = '/projects/locations/' + id + '?delete';
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