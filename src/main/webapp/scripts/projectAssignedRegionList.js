/**
 * To display jqxgrid in user list page.
 */
var url = "getAssginedRegionsJson";
// prepare the data
var source = {
	datatype : "json",
	datafields : [ {
		name : 'regionId',
		type : 'string'
	}, {
		name : 'regionName',
		type : 'string'
	}, {
		name : 'regionDesc',
		type : 'string'
	}, {
		name : 'userName',
		map : 'userRegionRoles>0>userName'
	}, {
		name : 'roleName',
		map : 'userRegionRoles>0>roleName'
	}, {
		name : 'locationName',
		map : 'location>locationName'
	} ],
	id : 'regionId',
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
$("#list")
		.jqxGrid(
				{
					width : '100%',
					height : 500,
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
					rowsheight : 45,
					showpinnedcolumnbackground : false,
					altrows : true,							
					// autorowheight: true,
					columns : [
							{
								text : 'Action',
								datafield : 'regionId',
								align : 'center',
								width : '25%',
								cellsrenderer : function(row, column, value) {
									return '<div class="col-md-12">'
									+'<p>'
										+ '<button class="btn bg-olive margin col-md-12"  onclick="updateItem('+ value +  ')"' + '>Update</button>'
										+ '<button class="btn btn-danger margin col-md-12" onclick="deleteItem('+ value +  ')"' + '>X</button>'
									+ '</p>'
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
								text : 'Region',
								datafield : 'regionName',
								align : 'center',
								width : '35%'
									
							} , {
								text : 'Location',
								datafield : 'locationName',
								align : 'center',
								width : '35%'
							} ]
				});
function updateItem(id) {
	window.location.href = '/projects/regions/' + id + '?form';
}

function deleteItem(id){
    var result = confirm('Do you want to delete this record?');
    if (result == false)
		return;
	var url = '/regions/' + id + '?delete';
	$.ajax({''
		type : "POST",
		contentType : 'application/json',
		url : url,
		success : function(msg) {
			alert('delete successfully.');
			$("#list").jqxGrid('updatebounddata');
		},
		complete : function(xhr, status) {

		}
});
}