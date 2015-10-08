var url = "/admin/users/getListJson";
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
var dataAdapter = new $.jqx.dataAdapter(source, {
	downloadComplete : function(data, status, xhr) {
	},
	loadComplete : function(data) {
	},
	loadError : function(xhr, status, error) {
	}
});
// Create a jqxComboBox
$("#jqxWidget").jqxComboBox({
	checkboxes : true,
	source : dataAdapter,
	displayMember : "username",
	valueMember : "username",
	width : '100%',
	height : 25
});

var sourceRole = [ "View", "Edit" ];
$("#jqxWidgetRole").jqxDropDownList({
	height : '25',
	source : sourceRole,
	width : '100%',
	selectedIndex : 0,
	autoOpen : true,
	autoDropDownHeight : true
});
