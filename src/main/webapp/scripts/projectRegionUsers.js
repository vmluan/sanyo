var url = "/admin/users/getListJson";
//prepare the data
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
$("#jqxWidget").jqxComboBox({ checkboxes: true, source: dataAdapter, displayMember: "username", valueMember: "userid", width: 200, height: 25});
