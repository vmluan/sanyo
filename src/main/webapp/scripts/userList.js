/**
 * To display jqxgrid in user list page.
 */
var url = pageContext + "/admin/users/getListJson";
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
	} ],
	id : 'userid',
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
					showpinnedcolumnbackground : false,
					altrows : true,					
					// autorowheight: true,
					columns : [
							{
								text : 'Action',
								align : 'center',
								datafield : 'userid',
								width : '20%',
								cellsrenderer : function(row, column, value) {
									return '<div class="col-md-6">'
											+ '<a class="btn btn-app" onclick="updateUser('
											+ value
											+ ')">'
											+ '<i class="glyphicon glyphicon-edit"></i>'
											+ '</a>'
											+ '<a class="btn btn-app" onclick="deleteUser('
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
							},
							{
								text : 'User Name',
								datafield : 'username',
								align : 'center',
								width : '10%'
							},
							{
								text : 'Email',
								datafield : 'email',
								align : 'center',
								cellsalign : 'right',
								cellsformat : 'c0',
								width : '30%'
							},
							{
								text : 'Mobile',
								datafield : 'mobile',
								align : 'center',
								cellsalign : 'right',
								cellsformat : 'c0',
								width : '20%'
							},
							{
								text : 'Avatar',
								datafield : 'avatar',
								align : 'center',
								width : '20%',
								cellsrenderer : function(row, column, value) {

									if (value) {
										return '<img width="45px" height="45px" src="../../images/profile/'
												+ value + '"/>';
									}
								}
							}, ]
				});
function updateUser(userid) {
	window.location.href = pageContext + '/admin/users/' + userid + '?form';
}
function deleteUser(userid) {
	var result = confirm('Do you want to delete this record?');
    if (result == false)
		return;
	var url = pageContext + '/admin/users/' + userid + '?delete';
	$.ajax({
		type : "POST",
		contentType : 'application/json',
		url : url,
		success : function(msg) {
			$(".delete_span").text("deleted successfully");
			//}
			$("#notificationUpdatePrice").slideDown("slow");
			setTimeout(function(){
				$("#notificationUpdatePrice").slideUp("slow");
				$(th).children().removeClass("glyphicon-refresh");
			},200);
			$("#list").jqxGrid('updatebounddata');
		},
		complete : function(xhr, status) {

		}
});
}