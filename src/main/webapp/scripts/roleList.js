/**
 * To display jqxgrid in user list page.
 */
var url = pageContext + "/admin/roles/getListJson";
// prepare the data
var source = {
	datatype : "json",
	datafields : [ {
		name : 'groupName',
		type : 'string'
	}, {
		name : 'description',
		type : 'string'
	}, {
		name : 'loginadministrator',
		type : 'bol'
	}, {
		name : 'groupid',
		type : 'string'
	}
	],
	id : 'groupid',
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
					height : 500,
					theme: 'energyblue',
					source : dataAdapter,
					sortable : true,
					pageable : true,
					autoheight : true,
					autoloadstate : false,
					autosavestate : false,
					columnsresize : true,
					columnsreorder : true,
					columnsresize : true,
					rowsheight : 45,
					showpinnedcolumnbackground : false,
					altrows : true,
					autorowheight : true,
					columns : [
							{
								text : 'Action',
								align : 'center',
								datafield : 'groupid',
								width : '15%',
								cellsrenderer : function(row, column, value) {
										return '<div class="col-md-12">'
											+ '<a class="btn btn-app col-md-5" onclick="updateRole('+ value + ')">'
											+ '<i class="glyphicon glyphicon-edit"></i>'
											+ '</a>'
											+ '<a class="btn btn-app col-md-5">'
											+ '<i class="glyphicon glyphicon-remove-circle" onclick="deleteRole('+ value +  ')"' + '></i>'
											+ '</a>' + '</div>';											
								}
							},
							{
								text : '#',
								datafield : 'stt',
								width : '4%',
								align : 'center',
								columntype : 'number',
								cellsrenderer : function(row, column, value) {
									return "<div style='margin:4px;'>"
											+ (value + 1) + "</div>";
								}
							}, {
								text : 'Role Name',
								datafield : 'groupName',
								width : '14%',
								align : 'center'
							}, {
								text : 'Description',
								datafield : 'description',
								align : 'center',
								cellsalign : 'right',
								cellsformat : 'c0',
								width : '53%'
							}, {
								text : 'Admin',
								datafield : 'loginadministrator',
								align : 'center',
								cellsalign : 'right',
								columntype : 'checkbox',
								width : '14%'
							}]
				});
function updateRole(groupid) {
	window.location.href = pageContext + '/admin/roles/' + groupid + '?form';
}