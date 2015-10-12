/**
 * To display jqxgrid in user list page.
 */
var url = "/quotation/getAssignedProductOfRegion";


var source = {
	datatype : "json",
	datafields : [{
		name : 'orderNo',
		type : 'string'
	},{
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
	},{
		name : 'amount',
		type : 'string'
		
	},{
		name : 'remark',
		type : 'string'
		
	},{
		name : 'Mat_w_o_Tax_USD',
		type : 'string'
		
	},{
		name : 'Mat_w_o_Tax_VND',
		type : 'string'
		
	},{
		name : 'labour',
		type : 'string'
		
	},{
		name : 'Imp_Tax',
		type : 'string'
		
	},{
		name : 'Special_Con_Tax',
		type : 'string'
		
	},{
		name : 'Discount_rate',
		type : 'string'
		
	},{
		name : 'encounterTime',
		type : 'string'
		
	},{
		name : 'VAT',
		type : 'string'
		
	},{
		name : 'Unit_Price_After_Discount',
		type : 'string'
		
	},{
		name : 'allowance',
		type : 'string'
		
	},{
		name : 'Unit_Price_W_Tax_Profit',
		type : 'string'
		
	},{
		name : 'Subcon_Profit',
		type : 'string'
		
	},{
		name : 'Unit_Price_W_Tax_Labour',
		type : 'string'
		
	},{
		name : 'Cost_Mat_Amount_USD',
		type : 'string'
		
	},{
		name : 'Cost_Labour_Amount_USD',
		type : 'string'
		
	}],
	id : 'encounterID',
	url : url,
	addrow: function (rowid, rowdata, position, commit) {
		// synchronize with the server - send insert command
		// call commit with parameter true if the synchronization with the server is successful 
		//and with parameter false if the synchronization failed.
		// you can pass additional argument to the commit callback which represents the new ID if it is generated from a DB.
		
	   commit(true);
	},
	deleterow: function (rowid, commit) {
		// synchronize with the server - send delete command
		// call commit with parameter true if the synchronization with the server is successful 
		//and with parameter false if the synchronization failed.
		commit(true);
	},
	updaterow: function (rowid, newdata, commit) {
		// synchronize with the server - send update command
		// call commit with parameter true if the synchronization with the server is successful 
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
					theme: 'energyblue',
					rowsheight : 45,
					source : dataAdapter,
					ready: function()
					{
						$('#list').jqxGrid('addrow', null, {}, 'first');
					},					
					filterable: true,
					editable: true,					
					// autorowheight: true,
					columns : [
							{
								text : 'Location',
								//columntype: 'dropdownlist',
								datafield : 'location',
								align : 'center',
								width : '20%',
								columntype: 'combobox',
									createeditor: function (row, column, editor) {
									// assign a new data source to the combobox.
										var list = ['Location 1', 'Location 2', 'Location 3'];;
										editor.jqxComboBox({ autoDropDownHeight: true, source: list, promptText: "Please Choose:" });
									},
									// update the editor's value before saving it.
									cellvaluechanging: function (row, column, columntype, oldvalue, newvalue) {
										// return the old value, if the new value is empty.
										if (newvalue == "") return oldvalue;
									}
						
							},
							{
								text : 'Sub-Region',
								//columntype: 'dropdownlist',
								datafield : 'region',
								align : 'center',
								width : '20%',
								columntype: 'combobox',
									createeditor: function (row, column, editor) {
									// assign a new data source to the combobox.
										var list = ['Region 1', 'Region 2', 'Region 3'];;
										editor.jqxComboBox({ autoDropDownHeight: true, source: list, promptText: "Please Choose:" });
									},
									// update the editor's value before saving it.
									cellvaluechanging: function (row, column, columntype, oldvalue, newvalue) {
										// return the old value, if the new value is empty.
										if (newvalue == "") return oldvalue;
									}
						
							},{
								text : 'Search by Name',
								datafield : 'searchName',
								align : 'center',
								cellsalign : 'right',
								//cellsformat : 'c0',
								width : '20%',
								columntype: 'combobox',
								createeditor: function (row, column, editor) {
								// assign a new data source to the combobox.
									var list = ['Product 1', 'Product 2', 'Product 3'];
									editor.jqxComboBox({ autoDropDownHeight: true, source: list, promptText: "Please Choose:" });
								},
								// update the editor's value before saving it.
								cellvaluechanging: function (row, column, columntype, oldvalue, newvalue) {
									// return the old value, if the new value is empty.
									if (newvalue == "") return oldvalue;
								}								
							},{
								text : 'Search by Code',
								datafield : 'searchCode',
								align : 'center',
								cellsalign : 'right',
								//cellsformat : 'c0',
								width : '20%',
								columntype: 'combobox',
								createeditor: function (row, column, editor) {
								// assign a new data source to the combobox.
									var list = ['Code 1', 'Code 2', 'Code 3'];
									editor.jqxComboBox({ autoDropDownHeight: true, source: list, promptText: "Please Choose:" });
								},
								// update the editor's value before saving it.
								cellvaluechanging: function (row, column, columntype, oldvalue, newvalue) {
									// return the old value, if the new value is empty.
									if (newvalue == "") return oldvalue;
								}								
							},
							{
								text : 'No',
								datafield : 'orderNo',
								align : 'center',
								cellsalign : 'right',
								cellsformat : 'c0',
								width : '20%'
							} ,{
								text : 'Unit',
								datafield : 'unit',
								align : 'center',
								cellsalign : 'right',
								//cellsformat : 'c0',
								width : '20%'
							} ,{
								text : 'Quantity',
								datafield : 'quantity',
								align : 'center',
								cellsalign : 'right',
								//cellsformat : 'c0',
								width : '20%'
							} ,{
								text : 'Unit Rate',
								datafield : 'unitRate',
								align : 'center',
								cellsalign : 'right',
								//cellsformat : 'c0',
								width : '20%'
							} ,{
								text : 'Amount',
								datafield : 'amount',
								align : 'center',
								cellsalign : 'right',
								//cellsformat : 'c0',
								width : '20%'
							} ,{
								text : 'Remark',
								datafield : 'remark',
								align : 'center',
								cellsalign : 'right',
								//cellsformat : 'c0',
								width : '20%'
							} ,{
								text : 'Labour',
								datafield : 'labour',
								align : 'center',
								cellsalign : 'right',
								//cellsformat : 'c0',
								width : '20%'
							} ,{
								text : 'Mat w/o Tax USD',
								datafield : 'Mat_w_o_Tax_USD',
								align : 'center',
								cellsalign : 'right',
								//cellsformat : 'c0',
								width : '20%'
							} ,{
								text : 'Mat w/o Tax VND',
								datafield : 'Mat_w_o_Tax_VND',
								align : 'center',
								cellsalign : 'right',
								//cellsformat : 'c0',
								width : '20%'
							} ,{
								text : 'Imp Tax',
								datafield : 'Imp_Tax',
								align : 'center',
								cellsalign : 'right',
								//cellsformat : 'c0',
								width : '20%'
							} ,{
								text : 'Special con. Tax',
								datafield : 'Special_Con_Tax',
								align : 'center',
								cellsalign : 'right',
								//cellsformat : 'c0',
								width : '20%'
							} ,{
								text : 'VAT',
								datafield : 'VAT',
								align : 'center',
								cellsalign : 'right',
								//cellsformat : 'c0',
								width : '20%'
							} ,{
								text : 'Discount rate %',
								datafield : 'Discount_rate',
								align : 'center',
								cellsalign : 'right',
								//cellsformat : 'c0',
								width : '20%'
							} ,{
								text : 'Unit price after discount',
								datafield : 'Unit_Price_After_Discount',
								align : 'center',
								cellsalign : 'right',
								//cellsformat : 'c0',
								width : '20%'
							} ,{
								text : 'Allowance',
								datafield : 'allowance',
								align : 'center',
								cellsalign : 'right',
								//cellsformat : 'c0',
								width : '20%'
							} ,{
								text : 'Unit price w Tax & profit',
								datafield : 'Unit_Price_W_Tax_Profit',
								align : 'center',
								cellsalign : 'right',
								//cellsformat : 'c0',
								width : '20%'
							} ,{
								text : 'Subcon profit',
								datafield : 'subConProfit',
								align : 'center',
								cellsalign : 'right',
								//cellsformat : 'c0',
								width : '20%'
							} ,{
								text : 'Unit price w Tax – labour',
								datafield : 'Unit_Price_W_Tax_Labour',
								align : 'center',
								cellsalign : 'right',
								//cellsformat : 'c0',
								width : '20%'
							} ,{
								text : 'Cost - Mat amount USD',
								datafield : 'Cost_Mat_Amount_USD',
								align : 'center',
								cellsalign : 'right',
								//cellsformat : 'c0',
								width : '20%'
							} ,{
								text : 'Cost - Labour amount USD',
								datafield : 'Cost_Labour_Amount_USD',
								align : 'center',
								cellsalign : 'right',
								//cellsformat : 'c0',
								width : '20%'
							},
							{
								text : 'Action',
								align : 'center',
								datafield : '',
								width : '20%',
								cellsrenderer : function(row, column, value) {
									return '<div class="col-md-6">'
											+ '<a class="btn btn-app" onclick="addItem('
											+ row
											+ ')">'
											+ '<i class="glyphicon glyphicon-plus"></i>'
											+ '</div>';
								},
								  cellbeginedit: function (row) {
									   return false;
								  }								
							}
							]
				});
function addItem(row) {
	//call server action to add new row.
	saveEncounter(row);

	
}
function saveEncounter(row){
	var data = $('#list').jqxGrid('getrowdata', row);
	var encounter = new Object();
//	var regionJson = new Object();
//	regionJson.regionName = data.region;
//	encounter.regionJson = regionJson
//	encounter.orderNo = data.orderNo;
	encounter.orderNo = '1';
	encounter.encounterID = '1';
	encounter.unitRate = '1';
	encounter.quantity = '1';
	encounter.actualQuantity = '1';
	encounter.amount = '1';
	encounter.remark = '1';
	encounter.Mat_w_o_Tax_USD = '1';
	encounter.Mat_w_o_Tax_VND = '1';
	encounter.labour = '1';
	encounter.Imp_Tax = '1';
	encounter.Special_Con_Tax = '1';
	encounter.Discount_rate = '1';
	encounter.VAT = '1';
	encounter.Unit_Price_After_Discount = '1';
	encounter.allowance = '1';
	encounter.Unit_Price_W_Tax_Profit = '1';
	encounter.Cost_Mat_Amount_USD = '1';
	encounter.Cost_Labour_Amount_USD = '1';
	
	var jsonData = JSON.stringify(encounter);
	console.log(jsonData);
	//var url = '/quotation/' + projectId + '/addquotation?form';
	var url = '/quotation/1/addquotation?form';
	//var url = '/quotation/1/savequotation';
	$.ajax({
		   type: "POST",
		   contentType : 'application/json',
		   data: jsonData,
		   url: url,
		   success: function(msg){
				$("#list").jqxGrid('updatebounddata');
				
				$('#list').jqxGrid('addrow', null, {}, 'first');
				$("#list").jqxGrid('begincelledit', 0, "desc");
		   }
			,complete: function(xhr,status){
//				$("#assignRegionButton").prop('disabled', false);
		  }		   
		});
	
	
	
	
	
	
	
	
	
}