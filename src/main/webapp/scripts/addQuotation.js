var urlLocation = "/quotation/getAssignedLocationsJson";
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
	id : 'locationId',
	url : urlLocation,
	data : {
		regionId : regionId
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
	checkboxes : true,
	source : dataAdapterLocation,
	displayMember : "locationName",
	valueMember : "locationId",
	width : '100%',
	height : 25
});

var urlProducts = "/products/getproductsjson";
// prepare the data
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
	} ],
	id : 'productID',
	url : urlProducts
};
var dataAdapterProducts = new $.jqx.dataAdapter(sourceProducts, {
	autoBind : true,
	downloadComplete : function(data, status, xhr) {
	},
	loadComplete : function(data) {
	},
	loadError : function(xhr, status, error) {
	}
});

/**
 * To display jqxgrid in user list page.
 */
var url = "/quotation/getAssignedProductOfRegion";

var source = {
	datatype : "json",
	datafields : [ {
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
		name : 'vAT',
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

// initialize jqxGrid
$("#list")
		.jqxGrid(
				{
					width : '100%',
					height : 100,
					theme : 'energyblue',
					rowsheight : 45,
					source : dataAdapter,
					ready : function() {
						$('#list').jqxGrid('addrow', null, {}, 'first');
					},
					filterable : true,
					editable : true,
					// autorowheight: true,
					columns : [
							{
								text : 'Search by Name',
								datafield : 'searchName',
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
								datafield : 'searchCode',
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
								datafield : 'orderNo',
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
								datafield : 'vAT',
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
								width : '15%'
							},
							{
								text : 'Cost - Labour amount USD',
								datafield : 'cost_Labour_Amount_USD',
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
function addItem(row) {
	// call server action to add new row.
	saveEncounter(row);

}
function saveEncounter(row) {
	var rows = $("#jqxWidgetLocation").jqxComboBox('getCheckedItems');
	var locations = new Array();
	// <![CDATA[
	for (var i = 0; i < rows.length; i++) {
		var location = new Object();
		location.locationId = rows[i].value;
		location.locationName = rows[i].label;
		locations.push(location);
	}

	// ]]>

	var data = $('#list').jqxGrid('getrowdata', row);
	var encounter = new Object();
	encounter.regionId = regionId;
	encounter.locations = locations;
	encounter.productCode = data.searchCode;
	encounter.productName = data.searchName;
	encounter.orderNo = data.orderNo;
	encounter.encounterID = data.encounterID;
	encounter.unitRate = data.unitRate;
	encounter.quantity = data.quantity;
	encounter.actualQuantity = data.actualQuantity;
	encounter.amount = data.amount;
	encounter.remark = data.remark;
	encounter.mat_w_o_Tax_USD = data.mat_w_o_Tax_USD;
	encounter.mat_w_o_Tax_VND = data.mat_w_o_Tax_VND;
	encounter.labour = data.labour;
	encounter.imp_Tax = data.imp_Tax;
	encounter.special_Con_Tax = data.special_Con_Tax;
	encounter.discount_rate = data.discount_rate;
	encounter.vAT = data.vAT;
	encounter.unit_Price_After_Discount = data.unit_Price_After_Discount;
	encounter.allowance = data.allowance;
	encounter.unit_Price_W_Tax_Profit = data.unit_Price_W_Tax_Profit;
	encounter.cost_Mat_Amount_USD = data.cost_Mat_Amount_USD;
	encounter.cost_Labour_Amount_USD = data.cost_Labour_Amount_USD;

	var jsonData = JSON.stringify(encounter);
	console.log(jsonData);
	// var url = '/quotation/' + projectId + '/addquotation?form';
	var url = '/quotation/1/addquotation?form';
	// var url = '/quotation/1/savequotation';
	$.ajax({
		type : "POST",
		contentType : 'application/json',
		data : jsonData,
		url : url,
		success : function(msg) {
			$("#list").jqxGrid('updatebounddata');

			$('#list').jqxGrid('addrow', null, {}, 'first');
			$("#list").jqxGrid('begincelledit', 0, "desc");
		},
		complete : function(xhr, status) {
			// $("#assignRegionButton").prop('disabled', false);
		}
	});

}