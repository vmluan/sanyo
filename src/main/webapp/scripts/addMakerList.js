var urlRegion = "/projects/getAssginedCategoriesJson";
// prepare the data
var sourceRegion = {
	datatype : "json",
	datafields : [ {
		name : 'categoryId',
		type : 'string'
	}, {
		name : 'name',
		type : 'string'
	}],
    sortcolumn: 'name',
    sortdirection: 'asc',
	id : 'categoryId',
	url : urlRegion,
	data : {
		projectId : projectId,
		regionType: regionType
	}
};
var dataAdapterRegion = new $.jqx.dataAdapter(sourceRegion, {autoBind : true,
	downloadComplete : function(data, status, xhr) {
	},
	loadComplete : function(data) {
	},
	loadError : function(xhr, status, error) {
	}
});

var urlProductGroup = '/productgroups/getproductGroupJson';
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
		}],
	    sortcolumn: 'groupName',
	    sortdirection: 'asc',
		id : 'groupId',
		url : urlProductGroup
	};
	var dataAdapterProductGroup = new $.jqx.dataAdapter(sourceProductGroup, {autoBind : true,
		downloadComplete : function(data, status, xhr) {
		},
		loadComplete : function(data) {
		},
		loadError : function(xhr, status, error) {
		}
	});
	
	var urlMaker = '/makers/getMakersJson';
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
			}],
		    sortcolumn: 'name',
		    sortdirection: 'asc',
			id : 'id',
			url : urlMaker
		};
		var dataAdapterMaker = new $.jqx.dataAdapter(sourceMaker, {autoBind : true,
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
	saveMakerList(row);

}
function saveMakerList(row) {
	var data = $('#list').jqxGrid('getrowdata', row);
	var makerList = new Object();
	makerList.categoryName = data.categoryName;
	makerList.productGroupName = data.productGroupName;
	makerList.modelNo = data.modelNo;
	makerList.makerName = data.makerName;
	makerList.delivery = data.delivery;
	makerList.remarks = data.remarks;
	makerList.makerId =  data.makerId;
	makerList.categoryId = data.categoryId;
	makerList.productGroupId = data.productGroupId;

	var jsonData = JSON.stringify(makerList);
	console.log(jsonData);
	var url = '/quotation/1/addmakerlist?form';
	$.ajax({
		type : "POST",
		contentType : 'application/json',
		data : jsonData,
		url : url,
		success : function(msg) {
			$("#list").jqxGrid('updatebounddata');
			$("#listResult").jqxGrid('updatebounddata');
		//	$('#list').jqxGrid('addrow', null, {}, 'first');
			$("#list").jqxGrid('begincelledit', 0, "categoryName");
		},
		complete : function(xhr, status) {
			// $("#assignRegionButton").prop('disabled', false);
		}
	});

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

	 $('#list').on('bindingcomplete', function (event) {
		 $('#list').jqxGrid('addrow', null, {}, 'first');
	 });
	 
function loadAddQuotationGrid() {


	var source2 = {
		datatype : "json",
		datafields : [  {
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
			name : 'remarks',
			type : 'string'

		}],
		
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
	$("#list")
			.jqxGrid(
					{
						width : '100%',
						height : 100,
						theme : 'energyblue',
						rowsheight : 45,
						source : dataAdapter2,
						filterable : true,
						editable : true,
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
													//set value to hidden field
													$("#list").jqxGrid('setcellvalue', 0, "categoryId", value);
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
								{ text: 'Region Id', datafield: 'categoryId', width: 0, hidden: true },
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
										//update to get productgroup list later.
										editor.jqxComboBox({
											autoDropDownHeight : true,
											source : dataAdapterProductGroup,
											displayMember : "groupName",
											valueMember : "groupId",
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
													//set value to hidden field
													$("#list").jqxGrid('setcellvalue', 0, "productGroupId", value);
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
								{ text: 'productGroupId', datafield: 'productGroupId', width: 0, hidden: true },
								{
									text : 'Model No',
									datafield : 'modelNo',
									align : 'center',
									cellsalign : 'right',
									cellsformat : 'c0',
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
										//update to get maker list later.
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
													var index = args.index;
													var item = args.item;
													// get item's label and value.
													var label = item.label;
													var value = item.value;
													//set value to hidden field
													$("#list").jqxGrid('setcellvalue', 0, "makerId", value);
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
								{ text: 'makerId', datafield: 'makerId', width: 0, hidden: true },
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
									datafield : 'remarks',
									align : 'center',
									cellsalign : 'right',
									// cellsformat : 'c0',
									width : '15%'
								},
								{
									text : 'Action',
									align : 'center',
									datafield : '',
									width : '10%',
									cellsrenderer : function(row, column, value) {
										return '<div class="col-md-6">'
												+ '<a class="btn btn-app" onclick="addItem('
												+ row
												+ ')">'
												+ '<i class="glyphicon glyphicon-plus"></i>'
												+ '</div>';
									},
									cellbeginedit : function(row) {
										return false;
									}
								} ]
					});
					
}
function showResultGrid(categoryId) {
	/**
	 * To display jqxgrid in user list page.
	 */
	var url = "/makers/getAssignedMakersJson";

	var source = {
		datatype : "json",
		datafields : [ {
			name : 'id',
			type : 'string'
		}, {
			name : 'productGroupName',
			map : 'productGroup>groupName'
		}, {
			name : 'makerName',
			map : 'maker>name'
		}, {
			name : 'categoryName',
			map : 'category>name'
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
		}],
	    sortcolumn: 'id',
	    sortdirection: 'asc',
		id : 'id',
		url : url,
		data : {
			projectId : projectId,
			regionType: regionType
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

	$("#listResult").jqxGrid(
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
											//set value to hidden field
											$("#list").jqxGrid('setcellvalue', index, "categoryId", value);
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
						{ text: 'Category Id', datafield: 'categoryId', width: 0, hidden: true },
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
								//update to get productgroup list later.
								editor.jqxComboBox({
									autoDropDownHeight : true,
									source : dataAdapterProductGroup,
									displayMember : "groupName",
									valueMember : "groupId",
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
											//set value to hidden field
											$("#list").jqxGrid('setcellvalue', index, "productGroupId", value);
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
						{ text: 'productGroupId', datafield: 'productGroupId', width: 0, hidden: true },
						{
							text : 'Model No',
							datafield : 'modelNo',
							align : 'center',
							cellsalign : 'right',
							cellsformat : 'c0',
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
								//update to get maker list later.
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
											var index = args.index;
											var item = args.item;
											// get item's label and value.
											var label = item.label;
											var value = item.value;
											//set value to hidden field
											$("#list").jqxGrid('setcellvalue', index, "makerId", value);
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
						{ text: 'makerId', datafield: 'makerId', width: 0, hidden: true },
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
							datafield : 'remark',
							align : 'center',
							cellsalign : 'right',
							// cellsformat : 'c0',
							width : '15%'
						},
						{
							text : 'Action',
							align : 'center',
							datafield : '',
							width : '10%',
							cellsrenderer : function(row, column, value) {
								return '<div class="col-md-6">'
										+ '<a class="btn btn-app" onclick="updateItem('
										+ row
										+ ')">'
										+ '<i class="glyphicon glyphicon-edit"></i>'
										+ '</div>';
							},
							cellbeginedit : function(row) {
								return false;
							}
						} ]
			});
}