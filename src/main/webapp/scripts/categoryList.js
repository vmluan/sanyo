/**
 * To display jqxgrid in user list page.
 */
var url = pageContext + "/admin/categories/getListJson";
// prepare the data
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
//				});
$("#list").jqxTreeGrid(
        {
            //width: '100%',
			width: 850,
			theme: 'energyblue',
            source: dataAdapter,
			editable: false,
			sortable : true,			
	        columns : [
				{
					text : 'Name',
					dataField : 'name',
					width : '40%'
				},
				{
					text : 'Description',
					dataField : 'value',
					width : '40%'
				},
				{
					text : 'Action',
					align : 'center',
					datafield : 'id',
					width : '20%',
					cellsrenderer : function(row, column, value) {
						return '<div class="col-md-12">'
								+ '<p>'
								+ '<button type="button" class="btn bg-olive margin col-md-5"  onclick="updateProduct('
								+ value + ')"' + '>Edit</button>'
								+ '</p>' + '</div>';
					}
				} ]
        });
function updateProduct(userid) {
	window.location.href = pageContext + '/admin/categories/' + userid + '?form';
}