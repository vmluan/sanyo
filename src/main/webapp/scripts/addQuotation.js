$("#locationSum").jqxNumberInput({ width: '250px', height: '25px', symbol: '$', disabled: true, max: 99999999});

var urlLocation = pageContext + "/quotation/getAssignedLocationsJson";
// prepare the data
var sourceLocation = {
	datatype : "json",
	datafields : [ {
		name : 'locationId',
		type : 'string'
	}, {
		name : 'locationName',
		type : 'string'
	} ],
    sortcolumn: 'locationName',
    sortdirection: 'asc',
	id : 'locationId',
	url : urlLocation,
	data : {
		projectId : projectId
	}
};
var dataAdapterLocation = new $.jqx.dataAdapter(sourceLocation, {
	autoBind : true,
	downloadComplete : function(data, status, xhr) {
	},
	loadComplete : function(data) {
	},
	loadError : function(xhr, status, error) {
	}
});
// Create a jqxComboBox
$("#jqxWidgetLocation").jqxComboBox({
	// checkboxes : true,
	source : dataAdapterLocation,
	displayMember : "locationName",
	valueMember : "locationId",
	width : '100%',
	height : 25
});
$('#jqxWidgetLocation').on('select', function(event) {
	// load region section.
	var args = event.args;
	if (args) {
		// index represents the item's index.
		var index = args.index;
		var item = args.item;
		// get item's label and value.
		var label = item.label;
		var value = item.value;
		var urlRegion = pageContext + "/projects/getAssginedRegionsOfLocationJson";
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
		    sortcolumn: 'regionName',
		    sortdirection: 'asc',
			id : 'regionId',
			url : urlRegion,
			data : {
				locationId : value
			}
		};
		var dataAdapterRegion = new $.jqx.dataAdapter(sourceRegion, {
			downloadComplete : function(data, status, xhr) {
			},
			loadComplete : function(data) {
			},
			loadError : function(xhr, status, error) {
			}
		});
		$("#listRegion").jqxComboBox({
			// checkboxes : true,
			source : dataAdapterRegion,
			displayMember : "regionName",
			valueMember : "regionId",
			width : '100%',
			height : 25
		});
		$('#listRegion').on('select', function(event) {
			var args = event.args;
				if (args) {
					var index = args.index;
					var item = args.item;
					// get item's label and value.
					var label = item.label;
					var value = item.value;
					loadAddQuotationGrid();
					showResultGrid(value);
				}
		});
	//update locationSum
	if(value)
		updateLocationSum(value);
	}

});

function updateLocationSum(locationId){
	var urlLocationSum = pageContext + '/quotation/' + locationId +'/getLocationSum';
		$.ajax({
			type : "POST",
			contentType : 'application/json',
			url : urlLocationSum,
			success : function(msg) {
				$("#locationSum").val(msg);
			},
			complete : function(xhr, status) {

			}
	});
}

function getUrlProducts(productGroupId){
	var urlProducts = pageContext + "/productgroups/getProductsOfGroupjson";
	var sourceProducts = {
			datatype : "json",
			datafields : [ {
				name : 'productName',
				type : 'string'
			}, {
				name : 'productPrice',
				type : 'float'
			}, {
				name : 'picLocation',
				type : 'string'
			}, {
				name : 'productCode',
				type : 'string'
			}, {
				name : 'productID',
				type : 'string'
			} 
			, {
				name : 'mat_w_o_Tax_USD',
				type : 'string'
			}, {
				name : 'mat_w_o_Tax_VND',
				type : 'string'
			}, {
				name : 'unit',
				type : 'string'
			}, {
				name : 'labour',
				type : 'string'
			}	
			],
		    sortcolumn: 'productName',
		    sortdirection: 'asc',
			id : 'productID',
			url : urlProducts,
			data : {
				productGroupId : productGroupId
			}
		};
	
	return sourceProducts;
}
function getProductAdapter(sourceProducts){
	var dataAdapterProducts = new $.jqx.dataAdapter(sourceProducts, {
		autoBind : true,
		downloadComplete : function(data, status, xhr) {
		},
		loadComplete : function(data) {
		},
		loadError : function(xhr, status, error) {
		}
	});
	return dataAdapterProducts;
}
// prepare the data
var sourceProducts = getUrlProducts();
var dataAdapterProducts = getProductAdapter(sourceProducts);


var urlProductGroup = pageContext + "/projects/getProductGroupMakersJson";
//prepare the data
var sourceProductGroup = {
	datatype : "json",
	datafields : [ {
		name : 'groupId',
		type : 'string'
	}, {
		name : 'groupCode',
		type : 'string'
	} , {
		name : 'groupName',
		type : 'string'
	}],
	sortcolumn: 'groupCode',
	sortdirection: 'asc',
	id : 'groupId',
	url : urlProductGroup,
	data : {
		projectId : projectId,
		regionType: regionType
	}
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

function addItem(row) {
	// call server action to add new row.
	saveEncounter(row);

}
function saveEncounter(row,isUpdate) {
	var item = $("#listRegion").jqxComboBox('getSelectedItem'); 
	
	var encounter = new Object();
	var data;
	if(isUpdate){
		data = $('#listResult').jqxGrid('getrowdata', row);
		}
	else{
		data = $('#list').jqxGrid('getrowdata', row);
		encounter.productCode = data.productCode;
		encounter.productId = data.productId;
	}
	
	encounter.regionId = item.value;
	encounter.regionName = item.label;
	
//	encounter.productName = data.productName;
	encounter.order = data.order;
	encounter.encounterID = data.encounterID;
	encounter.unitRate = data.unitRate;
	encounter.actualQuantity = data.actualQuantity;
	encounter.quantity = data.quantity;
	encounter.amount = data.amount;
	encounter.remark = data.remark;
	encounter.mat_w_o_Tax_USD = data.mat_w_o_Tax_USD;
	encounter.mat_w_o_Tax_VND = data.mat_w_o_Tax_VND;
	encounter.labour = data.labourProduct;
	encounter.labourAfterTax = data.labour;
	encounter.imp_Tax = data.imp_Tax;
	encounter.special_Con_Tax = data.special_Con_Tax;
	encounter.discount_rate = data.discount_rate;
	encounter.vat = data.vat;
	encounter.unit_Price_After_Discount = data.unit_Price_After_Discount;
	encounter.allowance = data.allowance;
	encounter.unit_Price_W_Tax_Profit = data.unit_Price_W_Tax_Profit;
	encounter.cost_Mat_Amount_USD = data.cost_Mat_Amount_USD;
	encounter.cost_Labour_Amount_USD = data.cost_Labour_Amount_USD;
	encounter.subcon_Profit = data.subcon_Profit;
	encounter.nonamePercent = data.nonamePercent;
	encounter.nonameRange = data.nonameRange;

	var jsonData = JSON.stringify(encounter);
	console.log(jsonData);
	 var url = '/quotation/' + projectId + '/addquotation?form';
//	var url = '/quotation/1/addquotation?form';
	// var url = '/quotation/1/savequotation';
	$.ajax({
		type : "POST",
		contentType : 'application/json',
		data : jsonData,
		url : url,
		success : function(msg) {
			$("#list").jqxGrid('updatebounddata');

			//$('#list').jqxGrid('addrow', null, {}, 'first');
			//$("#list").jqxGrid('begincelledit', 0, "desc");
			showResultGrid(item.value);
			var itemLocation = $("#jqxWidgetLocation").jqxComboBox('getSelectedItem');
			updateLocationSum(itemLocation.value);
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
	return '<div class="' + toThemeProperty('jqx-grid-groups-row')
			+ '" style="position: absolute;"><span>' + text + '</span>';
}

	 $('#list').on('bindingcomplete', function (event) {
		 $('#list').jqxGrid('addrow', null, {}, 'first');
		 setDefaultCellValues();
	 });
	 $('#list').on('cellvaluechanged', function (event) {
		 var args = event.args;
		 var datafield = args.datafield;
		 if(datafield == 'quantity'){
			//update Quantity field.
			var value = args.value.toString();
			updateQuantity(value);
		 }else if(datafield == 'nonamePercent' ||
				 datafield == 'nonameRange'){
			 updateMat_w_o_Tax_USD();
		 }
	 });
	 
function loadAddQuotationGrid() {

	var emptyData =[];
	var source2 = {
		datatype : "json",
		datafields : [],
		id : 'encounterID',
		localdata: emptyData
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
						ready: function()
						{
							setDefaultCellValues();
							$("#list").jqxGrid('sortby', 'order', 'asc');
							
						},						
						columns : [
									{
										text : 'Ma nhom vat tu',
										datafield : 'productGroupCode',
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
												source : dataAdapterProductGroup,
												displayMember : "groupCode",
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
														$("#list").jqxGrid('setcellvalue', 0, "groupId", value);
														//update project combobox.
														var sourceProducts2 = getUrlProducts(value);
														dataAdapterProducts = getProductAdapter(sourceProducts2);
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
								{ text: 'Group Id', datafield: 'groupId', width: 0, hidden: true },
								{
									text : 'No',
									datafield : 'order',
									align : 'center',
									cellsalign : 'right',
									cellsformat : 'n',
									width : '15%'
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
										// assign a new data source to the
										// combobox.
										editor.jqxComboBox({
											autoDropDownHeight : true,
											source : dataAdapterProducts,
											displayMember : "productCode",
											valueMember : "productID",
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
													$("#list").jqxGrid('setcellvalue', 0, "productId", value);
													//update Description field
													updateProductFields(value);
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
								{ text: 'Product Id', datafield: 'productId', width: 0, hidden: true },
						        {
									text : 'Description',
									datafield : 'productName',
									align : 'center',
									cellsalign : 'right',
									editable: false,
									width : '15%'
								},								
								{
									text : 'Unit',
									datafield : 'unit',
									align : 'center',
									cellsalign : 'right',
									editable: false,
									width : '15%'
								},
								{
									text : 'Quantity',
									datafield : 'actualQuantity',
									align : 'center',
									cellsalign : 'right',
									editable: false,
									cellsformat : 'n',
									width : '15%'
								},
								{
									text : 'Unit Rate',
									datafield : 'unitRate',
									align : 'center',
									cellsalign : 'right',
									editable: false,
									width : '15%'
								},
								{
									text : 'Amount',
									datafield : 'amount',
									align : 'center',
									cellsalign : 'right',
									editable: false,
									cellsformat : 'c2',
									width : '15%'
								},
								{
									text : 'Percent',
									datafield : 'nonamePercent',
									align : 'center',
									cellsalign : 'right',
									cellsformat : 'p2',
									width : '15%'
								},
								{
									text : 'Range',
									datafield : 'nonameRange',
									align : 'center',
									cellsalign : 'right',
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
									text : 'Qty',
									datafield : 'quantity',
									align : 'center',
									cellsalign : 'right',
									cellsformat : 'n',
									width : '15%'
								},								
								{
									text : 'Labour',
									datafield : 'labour',
									align : 'center',
									cellsalign : 'right',
									cellsformat : 'c2',
									editable: false,
									width : '15%'
								},
								{
									text : 'Mat w/o Tax USD',
									datafield : 'mat_w_o_Tax_USD',
									align : 'center',
									cellsalign : 'right',
									cellsformat : 'c2',
									editable: false,
									width : '15%'
								},
								{
									text : 'Mat w/o Tax VND',
									datafield : 'mat_w_o_Tax_VND',
									align : 'center',
									cellsalign : 'right',
									cellsformat : 'c2',
									editable: false,
									width : '15%'
								},
								{
									text : 'Labour (database)',
									datafield : 'labourProduct',
									align : 'center',
									cellsalign : 'right',
									cellsformat : 'c2',
									editable: false,
									width : '15%'
								},								
								{
									text : 'Imp Tax',
									datafield : 'imp_Tax',
									align : 'center',
									cellsalign : 'right',
									cellsformat : 'p2',
									editable: false,
									width : '15%'
								},
								{
									text : 'Special con. Tax',
									datafield : 'special_Con_Tax',
									align : 'center',
									cellsalign : 'right',
									cellsformat : 'p2',
									editable: false,
									width : '15%'
								},
								{
									text : 'VAT',
									datafield : 'vat',
									align : 'center',
									cellsalign : 'right',
									cellsformat : 'p2',
									editable: false,
									width : '15%'
								},
								{
									text : 'Discount rate %',
									datafield : 'discount_rate',
									align : 'center',
									cellsalign : 'right',
									cellsformat : 'p',
									editable: false,
									width : '15%'
								},
								{
									text : 'Unit price after discount',
									datafield : 'unit_Price_After_Discount',
									align : 'center',
									cellsalign : 'right',
									cellsformat : 'c0',
									editable: false,
									width : '15%'
								},
								{
									text : 'Allowance',
									datafield : 'allowance',
									align : 'center',
									cellsalign : 'right',
									cellsformat : 'p2',
									editable: false,
									width : '15%'
								},
								{
									text : 'Unit price w Tax & profit',
									datafield : 'unit_Price_W_Tax_Profit',
									align : 'center',
									cellsalign : 'right',
									cellsformat : 'c2',
									editable: false,
									width : '15%'
								},
								{
									text : 'Subcon profit',
									datafield : 'subcon_Profit',
									align : 'center',
									cellsalign : 'right',
									cellsformat : 'p2',
									editable: false,
									width : '15%'
								},
								{
									text : 'Unit price w Tax – labour',
									datafield : 'unit_Price_W_Tax_Labour',
									align : 'center',
									cellsalign : 'right',
									cellsformat : 'c2',
									editable: false,
									width : '15%'
								},
								{
									text : 'Cost - Mat amount USD',
									datafield : 'cost_Mat_Amount_USD',
									align : 'center',
									cellsalign : 'right',
									cellsformat : 'c2',
									editable: false,
									width : '10%'
								},
								{
									text : 'Cost - Labour amount USD',
									datafield : 'cost_Labour_Amount_USD',
									align : 'center',
									cellsalign : 'right',
									cellsformat : 'c2',
									editable: false,
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
								} ]
					});
					
}
function showResultGrid(regionId) {
	/**
	 * To display jqxgrid in user list page.
	 */
	var url = pageContext + "/quotation/getAssignedProductOfRegion";

	var source = {
		datatype : "json",
		datafields : [ {
			name : 'productName',
			map : 'product>productName'
		}, {
			name : 'productCode',
			map : 'product>productCode'
		}, {
			name : 'regionName',
			map : 'region>regionName'
		}, {
			name : 'order',
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

		}, {
			name : 'nonamePercent',
			type : 'string'

		}, {
			name : 'nonameRange',
			type : 'string'

		} , {
			name : 'unit',
			map : 'product>unit'

		}, {
			name: 'labourProduct',
			map: 'product>labour'
		}
		],
	    sortcolumn: 'order',
	    sortdirection: 'asc',
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
						height : '100%',
						theme : 'energyblue',
						rowsheight : 45,
						source : dataAdapter,
						filterable : true,
						editable : true,
						groupable : true,
					//	groupsrenderer : groupsrenderer,
						pageable : true,
						columnsresize : true,
						//autorowheight: true,
						showstatusbar: true,
						statusbarheight: 45,
						altrows: true,						
						showaggregates: true,
						columns : [
								{
									text : 'Region',
									datafield : 'regionName',
									align : 'center',
									cellsalign : 'right',
									width : '15%'
								},
								{
									text : 'No',
									datafield : 'order',
									align : 'center',
									cellsalign : 'right',
									cellsformat : 'n',
									width : '15%'
								},
						        {
									text : 'Description',
									datafield : 'productName',
									align : 'center',
									cellsalign : 'right',
									editable: false,
									width : '15%'
								},								
								{
									text : 'Unit',
									datafield : 'unit',
									align : 'center',
									cellsalign : 'right',
									editable: false,
									width : '15%'
								},
								{
									text : 'Quantity',
									datafield : 'actualQuantity',
									align : 'center',
									cellsalign : 'right',
									editable: false,
									cellsformat : 'n',
									width : '15%'
								},
								{
									text : 'Unit Rate',
									datafield : 'unitRate',
									align : 'center',
									cellsalign : 'right',
									editable: false,
									cellsformat : 'c2',
									width : '15%'
								},
								{
									text : 'Amount',
									datafield : 'amount',
									align : 'center',
									cellsalign : 'right',
									editable: false,
									cellsformat : 'c2',
									width : '15%',
									aggregates: ['sum']
								},
								{
									text : 'Percent',
									datafield : 'nonamePercent',
									align : 'center',
									cellsalign : 'right',
									cellsformat : 'p2',
									width : '15%'
								},
								{
									text : 'Range',
									datafield : 'nonameRange',
									align : 'center',
									cellsalign : 'right',
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
									text : 'Qty',
									datafield : 'quantity',
									align : 'center',
									cellsalign : 'right',
									cellsformat : 'n',
									width : '15%'
								},								
								{
									text : 'Labour',
									datafield : 'labour',
									align : 'center',
									cellsalign : 'right',
									cellsformat : 'c2',
									editable: false,
									width : '15%'
								},
								{
									text : 'Mat w/o Tax USD',
									datafield : 'mat_w_o_Tax_USD',
									align : 'center',
									cellsalign : 'right',
									cellsformat : 'c2',
									editable: false,
									width : '15%'
								},
								{
									text : 'Mat w/o Tax VND',
									datafield : 'mat_w_o_Tax_VND',
									align : 'center',
									cellsalign : 'right',
									cellsformat : 'c2',
									editable: false,
									width : '15%'
								},
								{
									text : 'Labour (database)',
									datafield : 'labourProduct',
									align : 'center',
									cellsalign : 'right',
									cellsformat : 'c2',
									editable: false,
									width : '15%'
								},								
								{
									text : 'Imp Tax',
									datafield : 'imp_Tax',
									align : 'center',
									cellsalign : 'right',
									cellsformat : 'p2',
									editable: false,
									width : '15%'
								},
								{
									text : 'Special con. Tax',
									datafield : 'special_Con_Tax',
									align : 'center',
									cellsalign : 'right',
									cellsformat : 'p2',
									editable: false,
									width : '15%'
								},
								{
									text : 'VAT',
									datafield : 'vat',
									align : 'center',
									cellsalign : 'right',
									cellsformat : 'p2',
									editable: false,
									width : '15%'
								},
								{
									text : 'Discount rate %',
									datafield : 'discount_rate',
									align : 'center',
									cellsalign : 'right',
									cellsformat : 'p2',
									editable: false,
									width : '15%'
								},
								{
									text : 'Unit price after discount',
									datafield : 'unit_Price_After_Discount',
									align : 'center',
									cellsalign : 'right',
									cellsformat : 'p2',
									editable: false,
									width : '15%'
								},
								{
									text : 'Allowance',
									datafield : 'allowance',
									align : 'center',
									cellsalign : 'right',
									cellsformat : 'p2',
									editable: false,
									width : '15%'
								},
								{
									text : 'Unit price w Tax & profit',
									datafield : 'unit_Price_W_Tax_Profit',
									align : 'center',
									cellsalign : 'right',
									cellsformat : 'c2',
									editable: false,
									width : '15%'
								},
								{
									text : 'Subcon profit',
									datafield : 'subcon_Profit',
									align : 'center',
									cellsalign : 'right',
									cellsformat : 'p2',
									editable: false,
									width : '15%'
								},
								{
									text : 'Unit price w Tax – labour',
									datafield : 'unit_Price_W_Tax_Labour',
									align : 'center',
									cellsalign : 'right',
									// cellsformat : 'c0',
									editable: false,
									width : '15%'
								},
								{
									text : 'Cost - Mat amount USD',
									datafield : 'cost_Mat_Amount_USD',
									align : 'center',
									cellsalign : 'right',
									// cellsformat : 'c0',
									editable: false,
									width : '10%'
								},
								{
									text : 'Cost - Labour amount USD',
									datafield : 'cost_Labour_Amount_USD',
									align : 'center',
									cellsalign : 'right',
									cellsformat : 'c2',
									editable: false,
									width : '10%'
								},
								{
									text : 'Action',
									align : 'center',
									datafield : 'encounterID',
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
						groups : [ 'regionName' ]
					});
}
function updateProductFields(productId){
	var records = dataAdapterProducts.records;
	for(var i=0; i< records.length; i++){
		var record = records[i];
		if(record.productID == productId){
			$("#list").jqxGrid('setcellvalue', 0, "productName", record.productName); //update name
			$("#list").jqxGrid('setcellvalue', 0, "labourProduct", record.labour); //update labour
			$("#list").jqxGrid('setcellvalue', 0, "unit", record.unit); //update unit
			$("#list").jqxGrid('setcellvalue', 0, "mat_w_o_Tax_USD", record.mat_w_o_Tax_USD); //update mat_w_o_Tax_USD
			$("#list").jqxGrid('setcellvalue', 0, "mat_w_o_Tax_VND", record.mat_w_o_Tax_VND); //update mat_w_o_Tax_VND
			return;
			}
	}
}
function setDefaultCellValues(){
	$("#list").jqxGrid('setcellvalue', 0, "imp_Tax", impTax); 
	$("#list").jqxGrid('setcellvalue', 0, "special_Con_Tax", specialCon);
	$("#list").jqxGrid('setcellvalue', 0, "vat", vat);
	$("#list").jqxGrid('setcellvalue', 0, "discount_rate", discountRate);
	$("#list").jqxGrid('setcellvalue', 0, "allowance", allowance);
	$("#list").jqxGrid('setcellvalue', 0, "subcon_Profit", subcon_Profit);
}
function updateQuantity(qtyManual){
	//Q'ty = Q'ty(nhập) * hao hut% bênsheet Summary(Q'ty sub-main, E-Q'ty other ,M-Q'ty)
	var indicator = 0;
	var productGroupCode = $("#list").jqxGrid('getcellvalue', 0, "productGroupCode");
	
	switch(productGroupCode){
		case '131', '151-HV':
			//sub-main
			indicator = qtySubMain;
			break;
		case  '111E-1', '111E-2','111E-3','111E-4','111E-5','121', '111':
			//E-qty
			indicator = eQtyOther;
			break;
		case '111M-1','111M-2','111M-3','111-5','191-3','194-10','195-2','195-3':	
			//Mech-qty
			indicator = mQtymQty;
			break;
		default:
			//sub-main
			indicator = qtySubMain;
	}
	var result = (qtyManual * indicator)/100;
	//update quantity field.
	$("#list").jqxGrid('setcellvalue', 0, "actualQuantity", result);
	updatePriceAfterDiscount();
}
function updatePriceAfterDiscount(){
	//Unit price after discount bằng cot 11+ cot 12/tỉ giá) 
	var exchangeRate = 22000; //update to get from database later.
	var mat_w_o_Tax_USD = $("#list").jqxGrid('getcellvalue', 0, "mat_w_o_Tax_USD"); //cot 11
	var mat_w_o_Tax_VND = $("#list").jqxGrid('getcellvalue', 0, "mat_w_o_Tax_VND"); //cot 12
	var result = mat_w_o_Tax_USD + mat_w_o_Tax_VND/exchangeRate;
	$("#list").jqxGrid('setcellvalue', 0, "unit_Price_After_Discount", result);
	
	updateUnitRate();
}
function updateUnitRate(){
//unit rate = cot 18 * cot 19 =  Unit price after discount  * Allowance
	var unit_Price_After_Discount = $("#list").jqxGrid('getcellvalue', 0, "unit_Price_After_Discount");
	var result = unit_Price_After_Discount * allowance/100;
	$("#list").jqxGrid('setcellvalue', 0, "unitRate", result);
	updateUnitPriceWTaxProfit();
}
function updateUnitPriceWTaxProfit() {
//Unit price w Tax & profit =cot 18*(1+(1+cot 15*(1+cot14))*cot 16)*cot17 
	//var result = 
	var unit_Price_After_Discount = $("#list").jqxGrid('getcellvalue', 0, "unit_Price_After_Discount"); //cot 18
	//cot 15 = specialCon
	var specialCon = $("#list").jqxGrid('getcellvalue', 0, "special_Con_Tax")/100; //cot 15
	var impTax = $("#list").jqxGrid('getcellvalue', 0, "imp_Tax")/100;//cot 14
	var vat = 	$("#list").jqxGrid('getcellvalue', 0, "vat")/100; //cot 16
	var discountRate = $("#list").jqxGrid('getcellvalue', 0, "discount_rate")/100;
	var result = unit_Price_After_Discount*(1+(1+specialCon*(1+impTax))*vat)*discountRate;
	
	$("#list").jqxGrid('setcellvalue', 0, "unit_Price_W_Tax_Profit", result);
	updateUnitPriceWTaxLabour();
}
function updateUnitPriceWTaxLabour(){
// Unit price w Tax - labour =cot 13 * cot 21 
	var labour = $("#list").jqxGrid('getcellvalue', 0, "labourProduct"); //cot 13
	var subcon_Profit = $("#list").jqxGrid('getcellvalue', 0, "subcon_Profit") /100;//cot 21
	var result = labour * subcon_Profit;
	$("#list").jqxGrid('setcellvalue', 0, "unit_Price_W_Tax_Labour", result);
	updateCostMatAmountUsd();
}
function updateCostMatAmountUsd(){
// Cost - Mat amount USD =cot 18* cot 9 
	var unit_Price_After_Discount = $("#list").jqxGrid('getcellvalue', 0, "unit_Price_After_Discount"); //cot 18 
	var qtyManual = $("#list").jqxGrid('getcellvalue', 0, "quantity"); //cot 9
	var result = unit_Price_After_Discount * qtyManual;
	$("#list").jqxGrid('setcellvalue', 0, "cost_Mat_Amount_USD", result);
	updateCostMatAmountVnd();
}
function updateCostMatAmountVnd(){
// Cost - Labour amount USD =cot 22* cot 9
	var unit_Price_W_Tax_Labour = $("#list").jqxGrid('getcellvalue', 0, "unit_Price_W_Tax_Labour"); //cot 22
	var qtyManual = $("#list").jqxGrid('getcellvalue', 0, "quantity"); //cot 9
	var result = unit_Price_W_Tax_Labour * qtyManual;
	$("#list").jqxGrid('setcellvalue', 0, "cost_Labour_Amount_USD", result);
	updateLabour();
}
function updateLabour(){
//labour sau thue = cot 5 * cot 22
	var unit_Price_W_Tax_Labour = $("#list").jqxGrid('getcellvalue', 0, "unit_Price_W_Tax_Labour"); //cot 22
	var quantity = $("#list").jqxGrid('getcellvalue', 0, "actualQuantity"); //cot 5
	var result = unit_Price_W_Tax_Labour * quantity;
	$("#list").jqxGrid('setcellvalue', 0, "labour", result);
	updateAmount();
}
function updateAmount(){
	//assume that amout = qty * unitRate
	var quantity = $("#list").jqxGrid('getcellvalue', 0, "actualQuantity"); //cot 5
	var unitRate = $("#list").jqxGrid('getcellvalue', 0, "unitRate");
	var result = quantity * unitRate;
	$("#list").jqxGrid('setcellvalue', 0, "amount", result);
}

function updateMat_w_o_Tax_USD(){
	//it is invoked for some special product group code.
	//var result = sum(AE1 : AEn) * percent;
	var percent = $("#list").jqxGrid('getcellvalue', 0, "nonamePercent"); //cot 5
	var range = $("#list").jqxGrid('getcellvalue', 0, "nonameRange"); //cot 5
	var total = 0;
	if(range && percent){
		var arrs = range.split('-');
		var start = arrs[0];
		var end = arrs[1];
		
		for(var i=start; i<= end; i++){
			var aEValue = $("#listResult").jqxGrid('getcellvalue', i, "cost_Mat_Amount_USD"); //cot 5
			total = total + aEValue;
		}
	}
	var result = total * percent/100;
	$("#list").jqxGrid('setcellvalue', 0, "mat_w_o_Tax_USD", result);
	
}
function updateItem(row){
	 var encounterId = $('#listResult').jqxGrid('getcellvalue', row, "uid");
	 saveEncounter(row, true);

}
function deleteItem(encounterId){
     var result = confirm('Do you want to delete this record?');
    if (result == false)
		return;
	var url = pageContext + '/quotation/'+ encounterId + '?delete';
	$.ajax({
		type : "POST",
		contentType : 'application/json',
		url : url,
		success : function(msg) {
			$("#listResult").jqxGrid('updatebounddata');
		},
		complete : function(xhr, status) {
			// $("#assignRegionButton").prop('disabled', false);
		}
	});
}