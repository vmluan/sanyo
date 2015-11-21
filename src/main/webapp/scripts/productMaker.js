var item = $("#jqxWidgetPG").jqxComboBox('getSelectedItem');
var productGroupCode = item.label;
var urlMaker = pageContext + '/makers/getMakersJson';
var sourceMaker = {
	datatype : "json",
	datafields : [ {
		name : 'id',
		type : 'string'
	}, {
		name : 'name',
		type : 'string'
	}, {
		name : 'makerDesc',
		type : 'string'
	} ],
	sortcolumn : 'name',
	sortdirection : 'asc',
	id : 'id',
	url : urlMaker,
	data : {
		productGroudCode : productGroupCode
	}
};
var dataAdapterMaker = new $.jqx.dataAdapter(sourceMaker, {
	autoBind : false,
	downloadComplete : function(data, status, xhr) {
	},
	loadComplete : function(data) {
	},
	loadError : function(xhr, status, error) {
	}
});

$("#jqxWidgetMaker").jqxDropDownList({
	height : '32',
	source : dataAdapterMaker,
	width : '100%',
	selectedIndex : 0,
	displayMember : "name",
	valueMember : "id",	
	promptText : "Please Choose:"
});
