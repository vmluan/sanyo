/**
 * To display jqxgrid in user list page.
 */
var url = pageContext + "/projects/getRevisionsJson";
// prepare the data
var source = {
	datatype : "json",
	datafields : [ {
		name : 'revisionId',
		type : 'string'
	}, {
		name : 'date',
		type : 'date'
	}, {
		name : 'revisionNo',
		type : 'string'
	} , {
		name : 'revisionDesc',
		type : 'string'
	}],
	id : 'revisionId',
    sortcolumn: 'date',
    sortdirection: 'desc',	
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
								text : 'Revision No',
								datafield : 'revisionNo',
								align : 'center',
								cellsalign : 'left',
								cellsformat : 'c0',
								width : '10%'
							}, {
								text : 'Date',
								datafield : 'date',
								align : 'center',
								cellsalign : 'left',
								cellsformat: 'MM/dd/yyyy',
								width : '20%'
							},
							{
								text : 'Description',
								datafield : 'revisionDesc',
								align : 'center',
								cellsalign : 'left',
								cellsformat : 'c0',
								width : '25%'
							} ,
							{
								text : 'Action',
								datafield : 'revisionId',
								align : 'center',
								width : '35%',
								cellsrenderer : function(row, column, value) {
									if (projectStatus2 == 'ONGOING') {
										return '<div class="col-md-12">'
											+ '<button type="button" class="btn btn-info col-md-8"  onclick="updateRevison(' + value + ')"' + '>Edit</button>'
											+ '<button type="button" class="btn btn-danger col-md-2" onclick="deleteRevison(' + value + ')"' + '>X</button>'
											+ '</div>'
											;
									} else {
										// hide the Action field
										$('#listRevision').jqxGrid('hidecolumn', 'revisionId');
										$("#listRevision").jqxGrid('setcolumnproperty', 'revisionDesc', 'width', 'auto');
										$("#listRevision").jqxGrid('setcolumnproperty', 'date', 'width', 'auto');
										$("#listRevision").jqxGrid('setcolumnproperty', 'revisionNo', 'width', 'auto');

									}
								}
							}
							]
				});
function updateRevison(id) {
	window.location.href = pageContext + '/projects/revisions/' + id + '?form';
}
function deleteRevison(id){
    var result = confirm('Do you want to delete this record?');
    if (result == false)
		return;
	var url = pageContext + '/projects/revisions/' + id + '?delete';
	$.ajax({
		type : "POST",
		contentType : 'application/json',
		url : url,
		success : function(msg) {
			alert('delete successfully.');
			$("#listRevision").jqxGrid('updatebounddata');
		},
		complete : function(xhr, status) {

		}
});
}