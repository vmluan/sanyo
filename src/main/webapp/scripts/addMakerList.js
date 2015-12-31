var urlRegion = pageContext + "/projects/getAssginedCategoriesJson";
// prepare the data
var sourceRegion = {
	datatype : "json",
	datafields : [ {
		name : 'categoryId',
		type : 'string'
	}, {
		name : 'name',
		type : 'string'
	} ],
	sortcolumn : 'name',
	sortdirection : 'asc',
	id : 'categoryId',
	url : urlRegion,
	data : {
		projectId : projectId,
		regionType : regionType
	}
};
var dataAdapterRegion = new $.jqx.dataAdapter(sourceRegion, {
	autoBind : true,
	downloadComplete : function(data, status, xhr) {
	},
	loadComplete : function(data) {
	},
	loadError : function(xhr, status, error) {
	}
});

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
	data : {
		productGroudCode : ''
	},
	url : urlMaker
};
var dataAdapterMaker = new $.jqx.dataAdapter(sourceMaker, {
	autoBind : true,
	downloadComplete : function(data, status, xhr) {
	},
	loadComplete : function(data) {
	},
	loadError : function(xhr, status, error) {
	}
});

loadAddQuotationGrid();
showResultGrid();
function addItem(row) {
	// call server action to add new row.
	var data = $('#listMaker').jqxGrid('getrowdata', row);
	saveMakerList(data);

}
function saveMakerList(data) {
console.log(data);
	//var data = $('#listMaker').jqxGrid('getrowdata', row);
	var makerList = new Object();
	if(data.id >0)
		makerList.id = data.id;
	makerList.categoryName = data.categoryName;
	makerList.productGroupName = data.productGroupName;
	makerList.modelNo = data.modelNo;
	makerList.makerName = data.makerName;
	makerList.delivery = data.delivery;
	makerList.remarks = data.remark;
	makerList.makerId = data.makerId;
	makerList.categoryId = data.categoryId;
	makerList.productGroupId = data.productGroupId;
	makerList.equivalent = data.equivalent;

	var jsonData = JSON.stringify(makerList);
	console.log(jsonData);
	var url = pageContext + '/quotation/'+projectId+'/addmakerlist?form';
	$.ajax({
		type : "POST",
		contentType : 'application/json',
		data : jsonData,
		url : url,
		success : function(msg) {
			$("#listMaker").jqxGrid('updatebounddata');
			$("#listResultMaker").jqxGrid('updatebounddata');
			// $('#listMaker').jqxGrid('addrow', null, {}, 'first');
			
			$("#listMaker").jqxGrid('begincelledit', 0, "categoryName");
		},
		complete : function(xhr, status) {
			// $("#assignRegionButton").prop('disabled', false);
		}
	});

}
function updateItem(row){
	console.log(row);
	var data = $("#listResultMaker").jqxGrid('getrowdata', row);
	var id= data.id;
	saveMakerList(data);
}
var toThemeProperty = function(className) {
	return className + " " + className + "-" + theme;
}

var groupsrenderer = function(text, group, expanded, data) {
	if ((data.groupcolumn.datafield == 'price')
			|| (data.groupcolumn.datafield == 'quantity')) {
		var number = dataAdapter.formatNumber(group,
				data.groupcolumn.cellsformat);
		var text = data.groupcolumn.text + ': ' + number;
		if (data.subItems.length > 0) {
			var aggregate = this.getcolumnaggregateddata(
					data.groupcolumn.datafield, [ 'sum' ], true, data.subItems);
		} else {
			var rows = new Array();
			var getRows = function(group, rows) {
				if (group.subGroups.length > 0) {
					for (var i = 0; i < group.subGroups.length; i++) {
						getRows(group.subGroups[i], rows);
					}
				} else {
					for (var i = 0; i < group.subItems.length; i++) {
						rows.push(group.subItems[i]);
					}
				}
			}
			getRows(data, rows);
			var aggregate = this.getcolumnaggregateddata(
					data.groupcolumn.datafield, [ 'sum' ], true, rows);
		}

		return '<div class="' + toThemeProperty('jqx-grid-groups-row')
				+ '" style="position: absolute;"><span>' + text + ', </span>'
				+ '<span class="'
				+ toThemeProperty('jqx-grid-groups-row-details') + '">'
				+ "Total" + ' (' + aggregate.sum + ')' + '</span></div>';
	} else {
		return '<div class="' + toThemeProperty('jqx-grid-groups-row')
				+ '" style="position: absolute;"><span>' + text + '</span>';
	}
}

$('#listMaker').on('bindingcomplete', function(event) {
	$('#listMaker').jqxGrid('addrow', null, {}, 'first');
	$("#listMaker").jqxGrid('setcellvalue', 0, "equivalent"," / or equivalent");
});

function loadAddQuotationGrid() {

	var source2 = {
		datatype : "json",
		datafields : [ {
			name : 'name',
			type : 'string'
		}, {
			name : 'productGroupName',
			type : 'string'
		}, {
			name : 'modelNo',
			type : 'string'
		}, {
			name : 'makerName',
			type : 'string'
		}, {
			name : 'delivery',
			type : 'string'
		}, {
			name : 'remark',
			type : 'string'

		} ],

		url : "/test"
	};
	var dataAdapter2 = new $.jqx.dataAdapter(source2, {
		downloadComplete : function(data, status, xhr) {
		},
		loadComplete : function(data) {
		},
		loadError : function(xhr, status, error) {
		}
	});
	// initialize jqxGrid
	$("#listMaker")
			.jqxGrid(
					{
						width : '100%',
						height : 100,
						theme : 'energyblue',
						rowsheight : 45,
						source : dataAdapter2,
						filterable : true,
						editable : true,
						editmode: 'click',
						ready : function() {
							$("#listMaker").jqxGrid('setcellvalue', 0, "equivalent",
									" / or equivalent");

						},
						// autorowheight: true,
						columns : [
								{
									text : 'System',
									datafield : 'categoryName',
									align : 'center',
									cellsalign : 'right',
									// cellsformat : 'c0',
									width : '15%',
									columntype : 'combobox',
									createeditor : function(row, column, editor) {
										// assign a new data source to the
										// combobox.
										editor.jqxComboBox({
											autoDropDownHeight : true,
											source : dataAdapterRegion,
											displayMember : "name",
											valueMember : "categoryId",
											promptText : "Please Choose:"
										});

										editor.on('select', function(event) {
											var args = event.args;
											if (args) {
												var index = args.index;
												var item = args.item;
												// get item's label and value.
												var label = item.label;
												var value = item.value;
												// set value to hidden field
												$("#listMaker").jqxGrid(
														'setcellvalue', 0,
														"categoryId", value);
											}
										});

									},
									geteditorvalue : function(row, cellvalue,
											editor) {
										// return the editor's value.
										var item = editor
												.jqxComboBox('getSelectedItem');
										if (item) {
											return item.label;
										}
									}
								},
								{
									text : 'Region Id',
									datafield : 'categoryId',
									width : 0,
									hidden : true
								},
								{
									text : 'Nhom Vat Tu',
									datafield : 'productGroupName',
									align : 'center',
									cellsalign : 'right',
									// cellsformat : 'c0',
									width : '15%',
									columntype : 'combobox',
									createeditor : function(row, column, editor) {
										// assign a new data source to the
										// combobox.
										// update to get productgroup list
										// later.
										editor.jqxComboBox({
											autoDropDownHeight : true,
											source : dataAdapterProductGroup,
											displayMember : "groupName",
											valueMember : "groupId",
											promptText : "Please Choose:"
										});
										editor
												.on(
														'select',
														function(event) {
															var args = event.args;
															if (args) {
																var index = args.index;
																var item = args.item;
																// get item's
																// label and
																// value.
																var label = item.label;
																var value = item.value;
																// set value to
																// hidden field
																$("#listMaker")
																		.jqxGrid(
																				'setcellvalue',
																				0,
																				"productGroupId",
																				value);
																updateMakerAdapter(label);
															}
														});
									},
									geteditorvalue : function(row, cellvalue,
											editor) {
										// return the editor's value.
										var item = editor
												.jqxComboBox('getSelectedItem');
										if (item) {
											return item.label;
										}
									}

								},
								{
									text : 'productGroupId',
									datafield : 'productGroupId',
									width : 0,
									hidden : true
								},
								{
									text : 'Model No',
									datafield : 'modelNo',
									align : 'center',
									cellsalign : 'right',
								//	cellsformat : 'c0',
									width : '15%'
								},
								{
									text : 'Maker',
									datafield : 'makerName',
									align : 'center',
									cellsalign : 'right',
									// cellsformat : 'c0',
									width : '15%',
									columntype : 'combobox',
									createeditor : function(row, column, editor) {
										// assign a new data source to the
										// combobox.
										// update to get maker list later.
										editor.jqxComboBox({
											autoDropDownHeight : true,
											source : dataAdapterMaker,
											displayMember : "name",
											valueMember : "id",
											checkboxes: true,
											promptText : "Please Choose:"
										});
										
									},
									geteditorvalue : function(row, cellvalue,
											editor) {
										// return the editor's value.
										var items = editor.jqxComboBox('getCheckedItems');
										console.log(items);
										var item = editor.jqxComboBox('getSelectedItem');
										if (items) {
											var makers = '';
											var makerIds = '';
											for(var i=0; i< items.length; i++){
												if(i==0){
													makers = items[i].label;
													makerIds = items[i].value;
												}
												else{
													makers += ', ' + items[i].label;
													makerIds += ", " + items[i].value;
												}
											}
											$("#listMaker").jqxGrid(
													'setcellvalue', 0,
													"makerId", makerIds);											
											return makers;
										}
									}

								},
								{
									text : 'makerId',
									datafield : 'makerId',
									width : 0,
									hidden : true
								},
								{
									text : '',
									datafield : 'equivalent',
									width : '15%',
									cellsalign : 'left',
								},
								{
									text : 'Delivery',
									datafield : 'delivery',
									align : 'center',
									cellsalign : 'right',
									// cellsformat : 'c0',
									width : '15%'
								},
								{
									text : 'Remarks',
									dataField : 'remark',
									align : 'center',
									cellsalign : 'right',
									// cellsformat : 'c0',
									width : '15%',
									columnType : "dropdownlist",
									createEditor : function(row, cellvalue,
											editor, cellText, width, height) {
										// construct the editor.
										var remarkList = [ "Import", "By Other", "Local manufacture", "Local / Import", "Import main component"];
										 
										editor.jqxDropDownList({
											height : '25',
											source : remarkList,
											width : '100%',
											height : '45',
											selectedIndex : 0,
											autoOpen : false,
											autoDropDownHeight : true
										});
										editor.on('select', function(event) {
											var args = event.args;
											if (args) {
												var index = args.index;
												var item = args.item;
												if(item){
													var label = item.label;
													var value = item.value;
													// set value to hidden field
													$("#listMaker").jqxGrid(
															'setcellvalue', 0,
															"remark", value);												
												}

											}
										});										
									},
									initEditor : function(row, cellvalue,
											editor, celltext, width, height) {
										// set the editor's current value.
										// The callback is called each time
										// the editor is displayed.
										editor.jqxDropDownList('selectItem',
												cellvalue);
									},
									getEditorValue : function(row, cellvalue,
											editor) {
										// return the editor's value.
										return editor.val();
									}
								},							
								{
									text : 'Action',
									align : 'center',
									datafield : 'buttonAdd',
									width : '10%',
									cellsrenderer : function(row, column, value) {
										return '<div class="col-md-12" style="margin-left: -0px;">'
												+ '<a class="btn btn-app col-md-12" style="margin-left: -0px;">'
												+ '<i class="glyphicon glyphicon-plus"></i>'
												+ '</div>';
									},
									cellbeginedit : function(row) {
										return false;
									}
								} ]
					});

}
$('#listMaker').on('cellclick', function (event) {
	var field = event.args.datafield;
	var index = event.args.rowindex;
	if(field == 'buttonAdd'){
		addItem(index);
	}
 });
var toThemeProperty = function(className) {
	return className + " " + className + "-" + theme;
}
var groupsrenderer = function(text, group, expanded, data) {
		return '<div class="' + toThemeProperty('jqx-grid-groups-row')
				+ '" style="position: absolute;"><span>' + text + '</span>';
}
function showResultGrid(categoryId) {
	/**
	 * To display jqxgrid in user list page.
	 */
	var url = pageContext + "/makers/getAssignedMakersJson";

	var source = {
		datatype : "json",
		datafields : [ {
			name : 'id',
			type : 'string'
		}, {
			name : 'productGroupName',
			map : 'productGroupMaker>productGroup>groupName'
		}, {
			name : 'productGroupId',
			map : 'productGroupMaker>productGroup>groupId'
		}, {
			name : 'makerName',
			map : 'productGroupMaker>maker>name'
		}, {
			name : 'makerId',
			map : 'productGroupMaker>maker>id'
		}, {
			name : 'categoryName',
			map : 'category>name'
		}, {
			name : 'categoryId',
			map : 'category>categoryId'
		}, {
			name : 'delivery',
			type : 'string'
		}, {
			name : 'remark',
			type : 'string'
		}, {
			name : 'modelNo',
			type : 'string'
		}, {
			name : 'createdBy',
			type : 'string'
		}, {
			name : 'createdDate',
			type : 'string'
		}, {
			name : 'equivalent',
			type : 'string'
		} 
		],
		sortcolumn : 'id',
		sortdirection : 'asc',
		id : 'id',
		url : url,
		data : {
			projectId : projectId,
			regionType : regionType
		},
		addrow : function(rowid, rowdata, position, commit) {
			commit(true);
		},
		deleterow : function(rowid, commit) {
			commit(true);
		},
		updaterow : function(rowid, newdata, commit) {
			commit(true);
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

	$("#listResultMaker")
			.jqxGrid(
					{
						width : '100%',
						height : 500,
						theme : 'energyblue',
						rowsheight : 45,
						source : dataAdapter,
						filterable : true,
						editable : true,
						pageable : true,
						// autorowheight: true,
						groupable: true,
						ready: function(){
							$("#listResultMaker").jqxGrid('expandallgroups');
						},
						columns : [
								{
									text : 'System',
									datafield : 'categoryName',
									align : 'center',
									cellsalign : 'right',
									// cellsformat : 'c0',
									width : '15%',
									columntype : 'combobox',
									createeditor : function(row, column, editor) {
										// assign a new data source to the
										// combobox.
										editor.jqxComboBox({
											autoDropDownHeight : true,
											source : dataAdapterRegion,
											displayMember : "name",
											valueMember : "categoryId",
											promptText : "Please Choose:"
										});

										editor.on('select', function(event) {
											var args = event.args;
											if (args) {
												var item = args.item;
												// get item's label and value.
												var label = item.label;
												var value = item.value;
												// set value to hidden field
												$("#listResultMaker").jqxGrid(
														'setcellvalue', getSelectedIndexOfResult(),
														"categoryId", value);
											}
										});

									},
									geteditorvalue : function(row, cellvalue,
											editor) {
										// return the editor's value.
										var item = editor
												.jqxComboBox('getSelectedItem');
										if (item) {
											return item.label;
										}
									}
								},
								{
									text : 'Category Id',
									datafield : 'categoryId',
									width : 0,
									hidden : true
								},
								{
									text : 'Nhom Vat Tu',
									datafield : 'productGroupName',
									align : 'center',
									cellsalign : 'right',
									// cellsformat : 'c0',
									width : '15%',
									columntype : 'combobox',
									createeditor : function(row, column, editor) {
										// assign a new data source to the
										// combobox.
										// update to get productgroup list
										// later.
										editor.jqxComboBox({
											autoDropDownHeight : true,
											source : dataAdapterProductGroup,
											displayMember : "groupName",
											valueMember : "groupId",
											promptText : "Please Choose:"
										});
										editor
												.on(
														'select',
														function(event) {
															var args = event.args;
															if (args) {
																var item = args.item;
																// get item's
																// label and
																// value.
																var label = item.label;
																var value = item.value;
																// set value to
																// hidden field
																$("#listResultMaker")
																		.jqxGrid(
																				'setcellvalue',
																				getSelectedIndexOfResult(),
																				"productGroupId",
																				value);
															}
														});
									},
									geteditorvalue : function(row, cellvalue,
											editor) {
										// return the editor's value.
										var item = editor
												.jqxComboBox('getSelectedItem');
										if (item) {
											return item.label;
										}
									}

								},
								{
									text : 'productGroupId',
									datafield : 'productGroupId',
									width : 0,
									hidden : true
								},
								{
									text : 'Model No',
									datafield : 'modelNo',
									align : 'center',
									cellsalign : 'right',
									//cellsformat : 'c0',
									width : '15%'
								},
								{
									text : 'Maker',
									datafield : 'makerName',
									align : 'center',
									cellsalign : 'right',
									// cellsformat : 'c0',
									width : '15%',
									columntype : 'combobox',
									createeditor : function(row, column, editor) {
										// assign a new data source to the
										// combobox.
										// update to get maker list later.
										editor.jqxComboBox({
											autoDropDownHeight : true,
											source : dataAdapterMaker,
											displayMember : "name",
											valueMember : "id",
											promptText : "Please Choose:"
										});
										editor.on('select', function(event) {
											var args = event.args;
											if (args) {
												var item = args.item;
												// get item's label and value.
												var label = item.label;
												var value = item.value;
												// set value to hidden field
												$("#listResultMaker").jqxGrid(
														'setcellvalue', getSelectedIndexOfResult(),
														"makerId", value);
											}
										});
									},
									geteditorvalue : function(row, cellvalue,
											editor) {
										// return the editor's value.
										var item = editor
												.jqxComboBox('getSelectedItem');
										if (item) {
											return item.label;
										}
									}

								},
								{
									text : 'makerId',
									datafield : 'makerId',
									width : 0,
									hidden : true
								},
								{
									text : '',
									datafield : 'equivalent',
									align : 'center',
									cellsalign : 'left',
									width : '10%'
								},								
								{
									text : 'Delivery',
									datafield : 'delivery',
									align : 'center',
									cellsalign : 'right',
									// cellsformat : 'c0',
									width : '15%'
								},
								{
									text : 'Remarks',
									dataField : 'remark',
									align : 'center',
									cellsalign : 'right',
									// cellsformat : 'c0',
									width : '15%',
									columnType : "dropdownlist",
									createEditor : function(row, cellvalue,
											editor, cellText, width, height) {
										// construct the editor.
										var remarkList = [ "Import", "By Other", "Local manufacture", "Local / Import", "Import main component"];
										 
										editor.jqxDropDownList({
											height : '25',
											source : remarkList,
											width : '15%',
											height : '45',
											selectedIndex : 0,
											autoOpen : false,
											autoDropDownHeight : true
										});
									},
									initEditor : function(row, cellvalue,
											editor, celltext, width, height) {
										// set the editor's current value.
										// The callback is called each time
										// the editor is displayed.
										editor.jqxDropDownList('selectItem',
												cellvalue);
									},
									getEditorValue : function(row, cellvalue,
											editor) {
										// return the editor's value.
										return editor.val();
									}
								},
								{
									text : 'Action',
									align : 'center',
									datafield : 'id',
									width : '30%',
									cellsrenderer : function(row, column, value) {
										return '<div class="col-md-12">'
										+'<p>'
											+ '<button class="btn bg-olive margin col-md-4"  onclick="updateItem('+ row +  ')"' + '>Update</button>'
											+ '<button class="btn btn-danger margin col-md-4" onclick="deleteItem('+ value +  ')"' +'>Delete</button>'
										+ '</p>'
									+ '</div>'
									;
									},
									cellbeginedit : function(row) {
										return false;
									}
								} ],
								groups: ['categoryName']
					});
}
function getSelectedIndexOfResult(){
	return $('#listResultMaker').jqxGrid('getselectedrowindex');
}
function updateMakerAdapter(productGroupCode){
	sourceMaker.data.productGroudCode = productGroupCode;
	dataAdapterMaker = new $.jqx.dataAdapter(sourceMaker);
	return dataAdapterMaker;
}
function deleteItem(makerId){
	var url = pageContext + '/makers/' + makerId + '?delete';
	var result = confirm('Do you want to delete this record?');
    if (result == false)
		return;
	$.ajax({
		type : "POST",
		contentType : 'application/json',
		url : url,
		success : function(msg) {
			$("#listResultMaker").jqxGrid('updatebounddata');
		},
		complete : function(xhr, status) {
		}
	});
}