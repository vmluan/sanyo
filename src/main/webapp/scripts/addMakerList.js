var urlRegion = "/projects/getAssginedRegionsJson";
// prepare the data
var sourceRegion = {
	datatype : "json",
	datafields : [ {
		name : 'regionId',
		type : 'string'
	}, {
		name : 'regionName',
		type : 'string'
	}, {
		name : 'regionDesc',
		type : 'string'
	}, {
		name : 'locationName',
		map : 'location>locationName'
	} ],
	id : 'regionId',
	url : urlRegion,
	data : {
		projectId : projectId
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

function addItem(row) {
	// call server action to add new row.
	saveMakerList(row);

}
function saveMakerList(row) {
	var data = $('#list').jqxGrid('getrowdata', row);
	var makerList = new Object();
	makerList.regionName = data.regionName;
	makerList.productGroupName = data.productGroupName;
	makerList.modelNo = data.modelNo;
	makerList.makerName = data.name;
	makerList.delivery = data.delivery;
	makerList.remarks = data.remarks;
	makerList.makerId =  data.makerId;
	makerList.regionId = data.regionId;
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

			$('#list').jqxGrid('addrow', null, {}, 'first');
			$("#list").jqxGrid('begincelledit', 0, "regionName");
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
			name : 'regionName',
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
									datafield : 'regionName',
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
											displayMember : "regionName",
											valueMember : "regionId",
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
													$("#list").jqxGrid('setcellvalue', 0, "regionId", value);
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
								{ text: 'Region Id', datafield: 'regionId', width: 0, hidden: true },
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
									datafield : 'name',
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
function showResultGrid(regionId) {
	/**
	 * To display jqxgrid in user list page.
	 */
	var url = "/quotation/getAssignedProductOfRegion";

	var source = {
		datatype : "json",
		datafields : [ {
			name : 'productName',
			map : 'product>productName'
		}, {
			name : 'productCode',
			map : 'product>productCode'
		}, {
			name : 'locationId',
			map : 'location>locationId'
		}, {
			name : 'locationName',
			map : 'location>locationName'
		}, {
			name : 'orderNo',
			type : 'string'
		}, {
			name : 'unitRate',
			type : 'string'
		}, {
			name : 'quantity',
			type : 'string'
		}, {
			name : 'encounterID',
			type : 'string'
		}, {
			name : 'actualQuantity',
			type : 'string'
		}, {
			name : 'amount',
			type : 'string'

		}, {
			name : 'remark',
			type : 'string'

		}, {
			name : 'mat_w_o_Tax_USD',
			type : 'string'

		}, {
			name : 'mat_w_o_Tax_VND',
			type : 'string'

		}, {
			name : 'labour',
			type : 'string'

		}, {
			name : 'imp_Tax',
			type : 'string'

		}, {
			name : 'special_Con_Tax',
			type : 'string'

		}, {
			name : 'discount_rate',
			type : 'string'

		}, {
			name : 'encounterTime',
			type : 'string'

		}, {
			name : 'vat',
			type : 'string'

		}, {
			name : 'unit_Price_After_Discount',
			type : 'string'

		}, {
			name : 'allowance',
			type : 'string'

		}, {
			name : 'unit_Price_W_Tax_Profit',
			type : 'string'

		}, {
			name : 'subcon_Profit',
			type : 'string'

		}, {
			name : 'unit_Price_W_Tax_Labour',
			type : 'string'

		}, {
			name : 'cost_Mat_Amount_USD',
			type : 'string'

		}, {
			name : 'cost_Labour_Amount_USD',
			type : 'string'

		} ],
		id : 'encounterID',
		url : url,
		data : {
			regionId : regionId
		},
		addrow : function(rowid, rowdata, position, commit) {
			// synchronize with the server - send insert command
			// call commit with parameter true if the synchronization with the
			// server is successful
			// and with parameter false if the synchronization failed.
			// you can pass additional argument to the commit callback which
			// represents the new ID if it is generated from a DB.

			commit(true);
		},
		deleterow : function(rowid, commit) {
			// synchronize with the server - send delete command
			// call commit with parameter true if the synchronization with the
			// server is successful
			// and with parameter false if the synchronization failed.
			commit(true);
		},
		updaterow : function(rowid, newdata, commit) {
			// synchronize with the server - send update command
			// call commit with parameter true if the synchronization with the
			// server is successful
			// and with parameter false if the synchronization failed.
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

	$("#listResult")
			.jqxGrid(
					{
						width : '100%',
						height : 300,
						theme : 'energyblue',
						rowsheight : 45,
						source : dataAdapter,
						filterable : true,
						editable : true,
						groupable : true,
						groupsrenderer : groupsrenderer,
						// autorowheight: true,
						columns : [
								{
									text : 'Location',
									datafield : 'locationName',
									align : 'center',
									cellsalign : 'right',
									width : '15%'
								},
								{
									text : 'Search by Name',
									datafield : 'productName',
									align : 'center',
									cellsalign : 'right',
									// cellsformat : 'c0',
									width : '15%',
									columntype : 'combobox',
									createeditor : function(row, column, editor) {
										// assign a new data source to the combobox.
										editor.jqxComboBox({
											autoDropDownHeight : true,
											source : dataAdapterProducts,
											displayMember : "productName",
											valueMember : "productID",
											promptText : "Please Choose:"
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
									text : 'Search by Code',
									datafield : 'productCode',
									align : 'center',
									cellsalign : 'right',
									// cellsformat : 'c0',
									width : '15%',
									columntype : 'combobox',
									createeditor : function(row, column, editor) {
										// assign a new data source to the combobox.
										editor.jqxComboBox({
											autoDropDownHeight : true,
											source : dataAdapterProducts,
											displayMember : "productCode",
											valueMember : "productID",
											promptText : "Please Choose:"
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
									text : 'No',
									datafield : 'order',
									align : 'center',
									cellsalign : 'right',
									cellsformat : 'c0',
									width : '15%'
								},
								{
									text : 'Unit',
									datafield : 'unit',
									align : 'center',
									cellsalign : 'right',
									// cellsformat : 'c0',
									width : '15%'
								},
								{
									text : 'Quantity',
									datafield : 'quantity',
									align : 'center',
									cellsalign : 'right',
									// cellsformat : 'c0',
									width : '15%'
								},
								{
									text : 'Unit Rate',
									datafield : 'unitRate',
									align : 'center',
									cellsalign : 'right',
									// cellsformat : 'c0',
									width : '15%'
								},
								{
									text : 'Amount',
									datafield : 'amount',
									align : 'center',
									cellsalign : 'right',
									// cellsformat : 'c0',
									width : '15%'
								},
								{
									text : 'Remark',
									datafield : 'remark',
									align : 'center',
									cellsalign : 'right',
									// cellsformat : 'c0',
									width : '15%'
								},
								{
									text : 'Labour',
									datafield : 'labour',
									align : 'center',
									cellsalign : 'right',
									// cellsformat : 'c0',
									width : '15%'
								},
								{
									text : 'Mat w/o Tax USD',
									datafield : 'mat_w_o_Tax_USD',
									align : 'center',
									cellsalign : 'right',
									// cellsformat : 'c0',
									width : '15%'
								},
								{
									text : 'Mat w/o Tax VND',
									datafield : 'mat_w_o_Tax_VND',
									align : 'center',
									cellsalign : 'right',
									// cellsformat : 'c0',
									width : '15%'
								},
								{
									text : 'Imp Tax',
									datafield : 'imp_Tax',
									align : 'center',
									cellsalign : 'right',
									// cellsformat : 'c0',
									width : '15%'
								},
								{
									text : 'Special con. Tax',
									datafield : 'special_Con_Tax',
									align : 'center',
									cellsalign : 'right',
									// cellsformat : 'c0',
									width : '15%'
								},
								{
									text : 'VAT',
									datafield : 'vat',
									align : 'center',
									cellsalign : 'right',
									// cellsformat : 'c0',
									width : '15%'
								},
								{
									text : 'Discount rate %',
									datafield : 'discount_rate',
									align : 'center',
									cellsalign : 'right',
									// cellsformat : 'c0',
									width : '15%'
								},
								{
									text : 'Unit price after discount',
									datafield : 'unit_Price_After_Discount',
									align : 'center',
									cellsalign : 'right',
									// cellsformat : 'c0',
									width : '15%'
								},
								{
									text : 'Allowance',
									datafield : 'allowance',
									align : 'center',
									cellsalign : 'right',
									// cellsformat : 'c0',
									width : '15%'
								},
								{
									text : 'Unit price w Tax & profit',
									datafield : 'unit_Price_W_Tax_Profit',
									align : 'center',
									cellsalign : 'right',
									// cellsformat : 'c0',
									width : '15%'
								},
								{
									text : 'Subcon profit',
									datafield : 'subConProfit',
									align : 'center',
									cellsalign : 'right',
									// cellsformat : 'c0',
									width : '15%'
								},
								{
									text : 'Unit price w Tax – labour',
									datafield : 'unit_Price_W_Tax_Labour',
									align : 'center',
									cellsalign : 'right',
									// cellsformat : 'c0',
									width : '15%'
								},
								{
									text : 'Cost - Mat amount USD',
									datafield : 'cost_Mat_Amount_USD',
									align : 'center',
									cellsalign : 'right',
									// cellsformat : 'c0',
									width : '10%'
								},
								{
									text : 'Cost - Labour amount USD',
									datafield : 'cost_Labour_Amount_USD',
									align : 'center',
									cellsalign : 'right',
									// cellsformat : 'c0',
									width : '10%'
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
								} ],
						groups : [ 'locationName' ]
					});
}