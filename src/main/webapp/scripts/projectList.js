/**
 * To display jqxgrid in user list page.
 */
var url = "projects/getListJson";
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
		name : 'createdBy',
		type : 'string'
	}, {
		name : 'lastModifiedBy',
		type : 'string'
	}, {
		name : 'projectId',
		type : 'string'
	} ],
	id : 'projectId',
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
					width : 1000,
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
					// showfilterrow : true,
					// filterable : true,
					columnsresize : true,
					rowsheight : 45,
					// autorowheight: true,
					columns : [
							{
								text : 'Action',
								align : 'center',
								datafield : 'projectId',
								width : '20%',
								cellsrenderer : function(row, column, value) {
									return '<div class="col-md-6">'
											+ '<a class="btn btn-app" onclick="updateProduct('
											+ value
											+ ')">'
											+ '<i class="glyphicon glyphicon-edit"></i>'
											+ '</a>'
											+ '<a class="btn btn-app">'
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
								text : 'Project Code',
								datafield : 'projectCode',
								align : 'center',
								width : '10%'
							}, {
								text : 'Project Name',
								datafield : 'projectName',
								align : 'center',
								cellsalign : 'right',
								cellsformat : 'c0',
								width : '30%'
							}, {
								text : 'Description',
								datafield : 'description',
								align : 'center',
								cellsalign : 'right',
								cellsformat : 'c0',
								width : '20%'
							}, {
								text : 'Client',
								datafield : 'customerName',
								align : 'center',
								width : '20%',
							} ]
				});
function updateProduct(id) {
	window.location.href = '/projects/' + id + '?form';
}