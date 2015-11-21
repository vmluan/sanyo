var urlProductGroup = pageContext + '/productgroups/getproductGroupJson';
var sourceProductGroup = {
	datatype : "json",
	datafields : [ {
		name : 'groupId',
		type : 'string'
	}, {
		name : 'groupName',
		type : 'string'
	}, {
		name : 'groupCode',
		type : 'string'
	} ],
	sortcolumn : 'groupName',
	sortdirection : 'asc',
	id : 'groupId',
	url : urlProductGroup
};
var dataAdapterProductGroup = new $.jqx.dataAdapter(sourceProductGroup, {
	autoBind : true,
	downloadComplete : function(data, status, xhr) {
	},
	loadComplete : function(data) {
	},
	loadError : function(xhr, status, error) {
	}
});
$("#jqxWidgetPG").jqxComboBox({
	autoDropDownHeight : true,
	source : dataAdapterProductGroup,
	displayMember : "groupName",
	valueMember : "groupId",
	//checkboxes: true,
	promptText : "Please Choose:"
});

$("#jqxWidgetPG").on('select', function(event) {
	$.getScript(pageContext + '/resources/scripts/productMaker.js', function(){}).done(function( s, Status ) {
		setTimeout(function(){ 
			updateMaker();
			}, 100);
	});
});	
