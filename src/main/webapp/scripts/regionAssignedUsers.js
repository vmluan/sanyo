/**
 * To display jqxgrid in user list page.
 */
var url = pageContext + "/regions/getAssginedUsersJson";
// prepare the data
var source = {
	datatype : "json",
	datafields : [ {
		name : 'id',
		type : 'string'
	}, {
		name : 'roleName',
		type : 'string'
	}, {
		name : 'userName',
		type : 'string'
	} ],
	id : 'id',
	url : url,
	data : {
		regionId : regionId
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
$("#jqxWidgetAssignedUsers")
		.jqxGrid(
				{
					width : 500,
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
					rowsheight : 45,
					// autorowheight: true,
					columns : [
							{
								text : 'Action',
								datafield : 'id',
								align : 'center',
								width : '18%',
								cellsrenderer : function(row, column, value) {
									return '<div class="col-md-6" style="margin-left: -20px;">'
											+ '<a class="btn btn-app" onclick="deleteAssignedUser('
											+ value
											+ ')">'
											+ '<i class="glyphicon glyphicon-remove-circle"></i>'
											+ '</a>' + '</div>';
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
								text : 'Assign Users',
								datafield : 'userName',
								align : 'center',
								cellsalign : 'left',
								cellsformat : 'c0',
								width : '37%'
							}, {
								text : 'Role',
								datafield : 'roleName',
								align : 'center',
								cellsalign : 'left',
								cellsformat : 'c0',
								width : '40%'
							} ]
				});
function updateProduct(id) {
	window.location.href = pageContext + '/projects/regions/' + id + '?form';
}
function deleteAssignedUser(id){
	var result = confirm('Do you want to delete this record?');
    if (result == false)
		return;
	var url = pageContext + '/regions/assignedUser/' + id + '?delete';
	$.ajax({
		type : "POST",
		contentType : 'application/json',
		url : url,
		success : function(msg) {
			alert('delete successfully.');
			$("#jqxWidgetAssignedUsers").jqxGrid('updatebounddata');
		},
		complete : function(xhr, status) {

		}
});
}