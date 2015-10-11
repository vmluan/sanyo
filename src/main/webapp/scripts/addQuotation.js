/**
 * To display jqxgrid in user list page.
 */
var url = "/admin/users/getListJson";


var source = {
	datatype : "json",
	datafields : [{
		name : 'username',
		type : 'string'
	},{
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
	},{
		name : 'desc',
		type : 'string'
		
	} ],
	id : 'userid',
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
					height : 500,
					theme: 'energyblue',
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
								text : 'User name',
								//columntype: 'dropdownlist',
								datafield : 'username',
								align : 'center',
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
						
						},
							
							{
								text : 'Search product name/code',
								datafield : 'search',
								align : 'center',
								cellsalign : 'right',
								cellsformat : 'c0',
								width : '20%'
							},
							{
								text : 'No',
								datafield : 'no',
								align : 'center',
								cellsalign : 'right',
								cellsformat : 'c0',
								width : '20%'
							} ,{
								text : 'Unit',
								datafield : 'unit',
								align : 'center',
								cellsalign : 'right',
								cellsformat : 'c0',
								width : '20%'
							} ,{
								text : 'Quantity',
								datafield : 'quantity',
								align : 'center',
								cellsalign : 'right',
								cellsformat : 'c0',
								width : '20%'
							} ,{
								text : 'Unit Rate',
								datafield : 'unitRate',
								align : 'center',
								cellsalign : 'right',
								cellsformat : 'c0',
								width : '20%'
							} ,{
								text : 'Amount',
								datafield : 'amount',
								align : 'center',
								cellsalign : 'right',
								cellsformat : 'c0',
								width : '20%'
							} ,{
								text : 'Remark',
								datafield : 'remark',
								align : 'center',
								cellsalign : 'right',
								cellsformat : 'c0',
								width : '20%'
							} ,{
								text : 'Labour',
								datafield : 'labour',
								align : 'center',
								cellsalign : 'right',
								cellsformat : 'c0',
								width : '20%'
							} ,{
								text : 'Mat w/o Tax USD',
								datafield : 'matWoTaxUsd',
								align : 'center',
								cellsalign : 'right',
								cellsformat : 'c0',
								width : '20%'
							} ,{
								text : 'Mat w/o Tax VND',
								datafield : 'matWoTaxVnd',
								align : 'center',
								cellsalign : 'right',
								cellsformat : 'c0',
								width : '20%'
							} ,{
								text : 'Imp Tax',
								datafield : 'impTax',
								align : 'center',
								cellsalign : 'right',
								cellsformat : 'c0',
								width : '20%'
							} ,{
								text : 'Special con. Tax',
								datafield : 'specialConTax',
								align : 'center',
								cellsalign : 'right',
								cellsformat : 'c0',
								width : '20%'
							} ,{
								text : 'VAT',
								datafield : 'VAT',
								align : 'center',
								cellsalign : 'right',
								cellsformat : 'c0',
								width : '20%'
							} ,{
								text : 'Discount rate %',
								datafield : 'discountRatePer',
								align : 'center',
								cellsalign : 'right',
								cellsformat : 'c0',
								width : '20%'
							} ,{
								text : 'Unit price after discount',
								datafield : 'unitPriceAfterDiscount',
								align : 'center',
								cellsalign : 'right',
								cellsformat : 'c0',
								width : '20%'
							} ,{
								text : 'Allowance',
								datafield : 'allowance',
								align : 'center',
								cellsalign : 'right',
								cellsformat : 'c0',
								width : '20%'
							} ,{
								text : 'Unit price w Tax & profit',
								datafield : 'unitPriceWTaxProfit',
								align : 'center',
								cellsalign : 'right',
								cellsformat : 'c0',
								width : '20%'
							} ,{
								text : 'Subcon profit',
								datafield : 'subConProfit',
								align : 'center',
								cellsalign : 'right',
								cellsformat : 'c0',
								width : '20%'
							} ,{
								text : 'Unit price w Tax – labour',
								datafield : 'unitPriceWTaxSubTrLabour',
								align : 'center',
								cellsalign : 'right',
								cellsformat : 'c0',
								width : '20%'
							} ,{
								text : 'NoCost - Mat amount USD',
								datafield : 'noCostSubTrMatAmountUsd',
								align : 'center',
								cellsalign : 'right',
								cellsformat : 'c0',
								width : '20%'
							} ,{
								text : 'Cost - Labour amount USD',
								datafield : 'costLabourAmountUsd',
								align : 'center',
								cellsalign : 'right',
								cellsformat : 'c0',
								width : '20%'
							} ]
				});
function updateProduct(userid) {
	window.location.href = '/admin/users/' + userid + '?form';
}