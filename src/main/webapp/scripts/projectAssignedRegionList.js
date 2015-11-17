/**
 * To display jqxgrid in user list page.
 */
var url = pageContext + "/projects/getAssginedRegionsJson";
var source =
{
	dataType: "json",
	dataFields: [
		{ name: 'id', type: 'number' },
		{ name: 'parentId', type: 'number' },
		{ name: 'name', type: 'string' },
		{ name: 'value', type: 'string' }
	],
	hierarchy:
	{
		keyDataField: { name: 'id' },
		parentDataField: { name: 'parentId' }
	},
	id: 'id',
    sortcolumn: 'name',
    sortdirection: 'asc',	
	url : url,
	data : {
		projectId : projectId
	}					
};
var dataAdapter = new $.jqx.dataAdapter(source);
			
           // create Tree Grid
            $("#list").jqxTreeGrid(
            {
                width: '100%',
                source: dataAdapter,
                sortable: true,
				theme: 'energyblue',
                source: dataAdapter,
                sortable: true,
				selectionMode: 'singleRow',
                ready: function()
                {
                   $("#list").jqxTreeGrid('expandAll');
                },				
                columns: [
							{
								text : 'Name',
								datafield : 'name',
								align : 'center',
								width : '75%'
							},
							{
								text : 'Action',
								datafield : 'id',
								align : 'center',
								width : '25%',
								cellsrenderer : function(row, column, value) {
									if(value < 1010101010)
										return '<div class="col-md-12">'
													+'<p>'
														+ '<button type="button" class="btn bg-olive margin col-md-5"  onclick="updateRegion('+ value +  ')"' + '>Edit</button>'
														+ '<button type="button" class="btn btn-danger margin col-md-1" onclick="deleteRegion('+ value +  ')"' + '>X</button>'
													+ '</p>'
												+ '</div>'
												;
									else
										return '';
								}
							}							
                ]
            });

function updateRegion(id) {
	var url = pageContext + '/projects/regions/' + id + '?form';
	window.location.href = url;
}

function deleteRegion(id){
    var result = confirm('Do you want to delete this record?');
    if (result == false)
		return;
	var url = pageContext + '/regions/' + id + '?delete';
	$.ajax({
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