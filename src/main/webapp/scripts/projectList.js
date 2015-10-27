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
	}, {
		name : 'lmodDate',
		type : 'date'
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
					width : '100%',
					height : 400,
					theme: 'energyblue',
					source : dataAdapter,
					sortable : true,
					pageable : true,
					autoloadstate : false,
					autosavestate : false,
					columnsresize : true,
					columnsreorder : true,
					// showfilterrow : true,
					// filterable : true,
					columnsresize : true,
					//rowsheight : 200,
					autorowheight: true,
					columns : [
							{
								text : '#',
								datafield : 'stt',
								width : '5%',
								columntype : 'number',
								align : 'center',
								cellsrenderer : function(row, column, value) {
									return "<div style='margin:4px;'>"
											+ (value + 1) + "</div>";
								}
							}, {
								text : 'Project Name',
								datafield : 'projectName',
								align : 'center',
								cellsalign : 'right',
								cellsformat : 'c0',
								width : '15%'
							}, {
								text : 'Last Modified',
								datafield : 'lmodDate',
								align : 'center',
								width : '15%',
								cellsformat: 'MM/dd/yyyy hh:mm:ss'
							}, {
								text : 'By',
								datafield : 'lastModifiedBy',
								align : 'center',
								width : '15%',
							},
							{
								text : 'Action',
								align : 'center',
								datafield : 'projectId',
								width : '50%',
								cellsrenderer : function(row, column, value) {
									return '<div class="col-md-12">'
												+'<p>'
													+ '<button class="btn bg-olive margin col-md-2"  onclick="updateProduct('+ value +  ')"' + '>Basic Info</button>'
													+ '<button class="btn bg-purple margin col-md-2" onclick="addQuotation('+ value +  ')"' + '>Pricing</button>'
													+ '<button class="btn bg-navy margin col-md-2">Marker</button>'
													+ '<button class="btn bg-orange margin col-md-1" onclick="cloneProject('+ value +  ')"' + '>Clone</button>'
													+ '<button class="btn bg-maroon margin col-md-1">Close</button>'
													+ '<button class="btn btn-danger margin col-md-1">X</button>'
												+ '</p>'
											+ '</div>'
											;
								}
							}
							]
				});
				
				//
function updateProduct(id) {
	window.location.href = '/projects/' + id + '?form';
}
function addQuotation(projectId){
	window.location.href = '/quotation?projectId=' + projectId;
}
function cloneProject(projectId){
	var url = '/projects/' + projectId + '?clone';
	$.ajax({
		type : "POST",
		contentType : 'application/json',
		url : url,
		success : function(msg) {
			alert('clone successfully.');
			$("#list").jqxGrid('updatebounddata');
		},
		complete : function(xhr, status) {

		}
});
	
}