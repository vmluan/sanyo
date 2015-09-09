/**
 * To display jqxgrid in user list page.
 */
var url = "users/getListJson";
// prepare the data
var source = {
	datatype : "json",
	datafields : [ {
		name : 'username',
		type : 'string'
	}, {
		name : 'email',
		type : 'string'
	}, {
		name : 'avatar',
		type : 'string'
	}, {
		name : 'userid',
		type : 'string'
	}, {
		name : 'mobile',
		type : 'string'
	}	],
	id : 'userid',
	url : url
};
var cellsrenderer = function(row, columnfield, value, defaulthtml, columnproperties, rowdata) {
    if (value < 20) {
        return '<span style="margin: 4px; float: ' + columnproperties.cellsalign + '; color: #ff0000;">' + value + '</span>';
    }
    else {
        return '<span style="margin: 4px; float: ' + columnproperties.cellsalign + '; color: #008000;">' + value + '</span>';
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
					width : 1000,
					height : 500,
					source : dataAdapter,
					sortable : true,
					pageable : true,
					autoheight : true,
					autoloadstate : false,
					autosavestate : false,
					columnsresize : true,
					columnsreorder : true,
					//showfilterrow : true,
					//filterable : true,
					columnsresize : true,
					rowsheight : 45,
					//autorowheight: true,
					columns : [
							{
								text : '',
								datafield : 'userid',
								width : '15%',
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
								text : 'User Name',
								datafield : 'username',
								width : '10%'
							}, {
								text : 'Email',
								datafield : 'email',
								align : 'right',
								cellsalign : 'right',
								cellsformat : 'c0',
								width : '40%'
							},{
								text : 'Mobile',
								datafield : 'mobile',
								align : 'right',
								cellsalign : 'right',
								cellsformat : 'c0',
								width : '20%'
							},
							{text: 'Avatar', datafield: 'avatar', width: '10%',
								cellsrenderer: function(row, column, value) {
								
									if (value)
										return '<img width="45px" height="45px" src="../../images/profile/' + value + '"/>';
								}
							},
							]
				});
function updateProduct(userid){
	window.location.href='/admin/users/' + userid + '?form';
}				