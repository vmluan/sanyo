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
	}, {
		name : 'locationName',
		type : 'string'
	}, {
		name : 'locationDesc',
		type : 'string'
	} ],
	id : 'locationId',
    sortcolumn: 'locationName',
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
							},
							{
								text : 'Action',
								datafield : 'locationId',
								align : 'center',
								width : '35%',
								cellsrenderer : function(row, column, value) {
									return '<div class="col-md-12">'
												+'<p>'
													+ '<button class="btn bg-olive margin col-md-5"  onclick="updateLocation('+ value +  ')"' + '>Edit</button>'
													+ '<button class="btn btn-danger margin col-md-1" onclick="deleteLocation('+ value +  ')"' + '>X</button>'
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