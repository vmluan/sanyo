/**
 * To display jqxgrid in user list page.
 */
var url = "roles/getListJson";
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
		name : 'viewuser',
		type : 'bol'
	}, {
		name : 'edituser',
		type : 'bol'
	}, {
		name : 'adduser',
		type : 'bol'
	}, {
		name : 'viewgroup',
		type : 'bol'
	}, {
		name : 'editgroup',
		type : 'bol'
	}, {
		name : 'addgroup',
		type : 'bol'
	}, {
		name : 'loginadministrator',
		type : 'bol'
	}, {
		name : 'groupid',
		type : 'string'
	} ],
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
					source : dataAdapter,
					sortable : true,
					pageable : true,
					autoheight : true,
					autoloadstate : false,
					autosavestate : false,
					columnsresize : true,
					columnsreorder : true,
					columnsresize : true,
					// rowsheight : 45,
					autorowheight : true,
					columns : [
							{
								text : '',
								datafield : 'groupid',
								width : '10%',
								cellsrenderer : function(row, column, value) {
									return '<input type ="button" value="Edit" onClick = "updateProduct('
											+ value
											+ ')"></input>'
											+ '<input type ="button" value="Delete" onClick = "deleteProduct('
											+ value + ')"></input>';
								}
							}, {
								text : '#',
								datafield : 'stt',
								width : '5%',
								columntype : 'number',
								cellsrenderer : function(row, column, value) {
									return row + 1;
								}
							}, {
								text : 'Role Name',
								datafield : 'groupName',
								width : '10%'
							}, {
								text : 'Description',
								datafield : 'description',
								align : 'right',
								cellsalign : 'right',
								cellsformat : 'c0',
								width : '20%'
							}, {
								text : 'Admin',
								datafield : 'loginadministrator',
								align : 'right',
								cellsalign : 'right',
								columntype : 'checkbox',
								width : '9%'
							}, {
								text : 'View User',
								datafield : 'viewuser',
								align : 'right',
								cellsalign : 'right',
								columntype : 'checkbox',
								width : '9%'
							}, {
								text : 'Add User',
								datafield : 'adduser',
								align : 'right',
								cellsalign : 'right',
								columntype : 'checkbox',
								width : '9%'
							}, {
								text : 'Update User',
								datafield : 'edituser',
								align : 'right',
								cellsalign : 'right',
								columntype : 'checkbox',
								width : '9%'
							}, {
								text : 'View Group',
								datafield : 'viewgroup',
								align : 'right',
								cellsalign : 'right',
								columntype : 'checkbox',
								width : '9%'
							}, {
								text : 'Add Group',
								datafield : 'addgroup',
								align : 'right',
								cellsalign : 'right',
								columntype : 'checkbox',
								width : '9%'
							}, {
								text : 'Update Group',
								datafield : 'editgroup',
								align : 'right',
								cellsalign : 'right',
								columntype : 'checkbox',
								width : '9%'
							} ]
				});
function updateProduct(userid) {
	window.location.href = '/admin/roles/' + userid + '?form';
}