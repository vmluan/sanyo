/**
 * To display jqxgrid in user list page.
 */
var url = "/projects/getRevisionsJson";
// prepare the data
var source = {
	datatype : "json",
	datafields : [ {
		name : 'revisionId',
		type : 'string'
	}, {
		name : 'date',
		type : 'string'
	}, {
		name : 'revisionNo',
		type : 'string'
	} , {
		name : 'revisionDesc',
		type : 'string'
	}],
	id : 'revisionId',
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
$("#listRevision")
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
					rowsheight : 45,
					// autorowheight: true,
					columns : [
							{
								text : 'Action',
								datafield : 'revisionId',
								align : 'center',
								width : '25%',
								cellsrenderer : function(row, column, value) {
									return '<div class="col-md-6">'
											+ '<a class="btn btn-app" style="margin-left: -10px;" onclick="updateItem('
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
								text : 'Revision No',
								datafield : 'revisionNo',
								align : 'center',
								cellsalign : 'left',
								cellsformat : 'c0',
								width : '25%'
							}, {
								text : 'Date',
								datafield : 'date',
								align : 'center',
								cellsalign : 'left',
								cellsformat : 'c0',
								width : '20%'
							},{
								text : 'Description',
								datafield : 'revisionDesc',
								align : 'center',
								cellsalign : 'left',
								cellsformat : 'c0',
								width : '25%'
							} ]
				});
function updateProduct(id) {
	window.location.href = '/projects/revisions/' + id + '?form';
}