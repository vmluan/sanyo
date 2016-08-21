

var urlResult = pageContext + "/quotation/getAssignedProductOfRegion";

var sourceResult = {
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
		type : 'number'
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
	}, {
		name: 'hasMakerDeleted',
		type: 'bool'
	}

	],
	sortcolumn: 'order',
	sortdirection: 'asc',
	id : 'encounterID',
	url : urlResult,
	data : {
		regionId : 0,
		locationIds: -1,
		projectId: projectId
	}
};

var dataAdapterResult = new $.jqx.dataAdapter(sourceResult, {
	downloadComplete : function(data, status, xhr) {
	},
	loadComplete : function(data) {
	},
	loadError : function(xhr, status, error) {
	}
});
// will update currency and format based on vnd or usd.
if(currency == 'VND'){
	$("#locationSum").jqxNumberInput({ width: '250px', height: '25px', symbol: 'VND', disabled: true, decimalDigits: 0, digits: 13, max: 999999999999
		,groupSeparator: ","});
	$("#regionSum").jqxNumberInput({ width: '250px', height: '25px', symbol: 'VND', disabled: true, decimalDigits: 0, digits: 13, max: 999999999999
		,groupSeparator: ","});

} else if(currency == 'JPY'){
	$("#locationSum").jqxNumberInput({ width: '250px', height: '25px', symbol: 'JPY', disabled: true, decimalDigits: 2, digits: 12, max: 999999999999
		,groupSeparator: "." });
	$("#regionSum").jqxNumberInput({ width: '250px', height: '25px', symbol: 'JPY', disabled: true, decimalDigits: 2, digits: 12, max: 999999999999
		,groupSeparator: "."});

}else{
	$("#locationSum").jqxNumberInput({ width: '250px', height: '25px', symbol: '$', disabled: true, decimalDigits: 2, digits: 12, max: 999999999999
		,groupSeparator: ","});
	$("#regionSum").jqxNumberInput({ width: '250px', height: '25px', symbol: '$', disabled: true, decimalDigits: 2, digits: 12, max: 999999999999
		,groupSeparator: ","});
}
var urlLocation = pageContext + "/quotation/getAssignedLocationsJson";
// prepare the data
var sourceLocation = {
	datatype : "json",
	datafields : [ {
		name : 'locationId',
		type : 'number'
	}, {
		name : 'locationName',
		type : 'string'
	}, {
		name : 'orderNo',
		type : 'number'
	} ],
//    sortcolumn: 'orderNo',
//    sortdirection: 'asc',
	id : 'locationId',
	url : urlLocation,
	data : {
		projectId : projectId,
		regionType: regionType
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
//get all regions of project. Then it will be used in all session later of that project in Add Quotation page.
var urlAllRegions=pageContext + "/quotation/getAllAssignedRegionsJson";
var sourceAllRegions = {
	datatype : "json",
	datafields : [ {
		name : 'regionId',
		type : 'number'
	}, {
		name : 'regionName',
		type : 'string'
	}, {
		name : 'regionDesc',
		type : 'string'
	}, {
		name : 'locationName',
		map : 'location>locationName'
	}, {
		name : 'locationId',
		map : 'location>locationId'
	} ],
	id : 'regionId',
	url : urlAllRegions,
	data : {
		projectId: projectId,
		regionType: regionType
	}
};
var dataAdapterAllRegions = new $.jqx.dataAdapter(sourceAllRegions, {autoBind : true,
	downloadComplete : function(data, status, xhr) {
	},
	loadComplete : function(data) {
	},
	loadError : function(xhr, status, error) {
	}
});

function updateRegionSearchList(locationIds){
	var records = dataAdapterAllRegions.records;
	var result = new Array();
	if(locationIds) {
		var locationArray = locationIds.split(',');
		for(var i=0; i<locationArray.length; i++){
			if(locationArray[i]=='0') {
				result =  records;
				break;
			}
			for(var j=0; j< records.length; j++){
				var record = records[j];
				if(record.locationId == locationArray[i]){
					var hasExisted = false;
					for(var k=0; k<result.length;k++){
						if(result[k].regionId == record.regionId) {
							hasExisted = true;
							break;
						}
					}
					if(!hasExisted){
						result.push(record);
					}
				}

			}
		}
	}else{
		result = []; // to remove data from Region List.
	}
	console.log(result);
	$("#listRegion").jqxComboBox({source: result});
};


// Create a jqxComboBox
$("#jqxWidgetLocation").jqxComboBox({
	// checkboxes : true,
	source : dataAdapterLocation,
	displayMember : "locationName",
	valueMember : "locationId",
	width : '100%',
	checkboxes: true,
	height : 25
});
// prepare the data for region
var urlRegion = pageContext + "/projects/getAssginedRegionsOfLocationJson";
var sourceRegion = {
	datatype : "json",
	datafields : [ {
		name : 'regionId',
		type : 'number'
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
//		sortcolumn: 'regionName',
//		sortdirection: 'asc',
	id : 'regionId',
	url : urlRegion,
	data : {
		locationId : 0,
		projectId: projectId,
		regionType: regionType
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
	height : 25,
	checkboxes: true
});

$("#listRegion").on('checkChange', function(event) {
	if (event.args) {
		var item = event.args.item;
		if (item) {
			var value = item.value;
			var label = item.label;
			var checked = item.checked;

			if (checked && label == 'All') {
				$("#listRegion").jqxComboBox('checkAll');
				var regionIDs = getCheckedRegionIds();
				updateRegionSum(regionIDs);
			} else if (!checked && label == 'All') {
				$("#listRegion").jqxComboBox('uncheckAll');
				var regionIDs = getCheckedRegionIds();
				updateRegionSum(regionIDs);
			}
			else if (!checked && label != 'All') { //uncheck a button that is not ALl
				// $("#listRegion").jqxComboBox('uncheckIndex',0);
				if(!checkedAll()){
					var regionIDs = getCheckedRegionIds();
					updateRegionSum(regionIDs);
				}
			}else if(checked && label != 'All'){ //check item that is not All
				if(!checkedAll()){ //All is not checked
					var regionIDs = getCheckedRegionIds();
					updateRegionSum(regionIDs);
				}
			}

		}


	}
});
$("#jqxWidgetLocation").on('checkChange', function (event)
{
	if (event.args) {
		var item = event.args.item;
		if(item){
			var value = item.value;
			var label = item.label;
			var checked = item.checked;

			if(checked && label =='All'){
				$("#jqxWidgetLocation").jqxComboBox('checkAll');
			}else if(!checked && label =='All')
				$("#jqxWidgetLocation").jqxComboBox('uncheckAll');
			else if(!checked && label !='All'){
				console.log(checked );
				console.log(label);
				//$("#jqxWidgetLocation").jqxComboBox('uncheckIndex',0);
			}
			var locationIDs=getCheckedLocationIds();
			if(locationIDs != ''){
				updateLocationSum(locationIDs);
				//load regions of checked locations.
/*				sourceRegion.data.locationId = locationIDs;
				dataAdapterRegion = new $.jqx.dataAdapter(sourceRegion);
				$("#listRegion").jqxComboBox({
					// checkboxes : true,
					source : dataAdapterRegion,
					displayMember : "regionName",
					valueMember : "regionId",
					width : '100%',
					height : 25,
					checkboxes: true
				});*/
				updateRegionSearchList(locationIDs);
			}else{
				$("#locationSum").val(0);
				updateRegionSearchList(null);
			}
		}
		console.log(locationIDs);

	}

});
function updateRegionSum(regionIds){
	if(regionIds == '')
		$("#regionSum").val(0);
	else{
		var urlLocationSum = pageContext + '/quotation/getRegionSum';
		$.ajax({
			type : "GET",
			data : {
				regionIds: regionIds
			},
			contentType : 'application/json',
			url : urlLocationSum,
			success : function(msg) {
				$("#regionSum").val(msg);
			},
			complete : function(xhr, status) {
			}
		})
	}
}
function updateLocationSum(locationIds){
	var urlLocationSum = pageContext + '/quotation/getLocationSum';
	$.ajax({
		type : "GET",
		data : {
			locationIds: locationIds,
			projectId: projectId
		},
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
		datafields : [
		{
			name: 'productLabel',
			type : 'string'
		}
		,{
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
			type : 'number'
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
			productGroupId : productGroupId,
			regionId: 0
		}
	};

	return sourceProducts;
}
function getProductAdapter(sourceProducts){
	var dataAdapterProducts = new $.jqx.dataAdapter(sourceProducts, {
		autoBind : true,
		downloadComplete : function(data, status, xhr) {
			//update allowance
			//update discount_rate
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

// get all ProductGroup assigned to the project
var urlAllMakerProject = pageContext + "/projects/getAllMakerProject";
var makerProjectData = {projectId : projectId, regionType: regionType};
var allMakerProjectObjs;
function handleProjectMakers(output){
	allMakerProjectObjs = output;
}
makeGetRequestJson(urlAllMakerProject,makerProjectData,handleProjectMakers);
// will write new code to update ProductGroup dropdown list by using data from allMakerProjectObjs object
function updateProductGroupDropDown(regionId){
	 // get result
	var result = [];
	return result;
}
var urlProductGroup = pageContext + "/projects/getProductGroupMakersJson";
//prepare the data
var sourceProductGroup = {
	datatype : "json",
	datafields : [ {
		name : 'groupId',
		type : 'number'
	}, {
		name : 'groupCode',
		type : 'string'
	} , {
		name : 'groupName',
		type : 'string'
	}, {
		name : 'discount',
		type : 'string'
	}, {
		name : 'allowance',
		type : 'string'
	}],
	sortcolumn: 'groupCode',
	sortdirection: 'asc',
	id : 'groupId',
	url : urlProductGroup,
	data : {
		projectId : projectId,
		regionType: regionType,
		regionId: 0
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
function checkedAll(){
	var regionIds = getCheckedRegionIds();
	if(regionIds && regionIds.indexOf(",") > 0){
		var list = regionIds.split(',');
		for(var i=0; i<list.length; i++){
			if(list[i] == 0)
				return true;
		}
	}
	return false;
}
function saveEncounter(row,isUpdate) {
	//var item = $("#listRegion").jqxComboBox('getSelectedItem');
	var regionIds = getCheckedRegionIds();
	if(regionIds && regionIds.indexOf(",") > 0){
		var list = regionIds.split(',');
		var count = 0;
		for(var i=0; i<list.length; i++){
			if(list[i] > 0)
				count++;
		}
		if(count >1)
			alert("Please select a single Region in order to add a new record.");
	}

	var items = $("#listRegion").jqxComboBox('getCheckedItems');
	var item='';
	for( var i=0; i< items.length; i++){
		if(items[i].value >0){
			item = items[i];
		}
	}
	console.log(item);
	var encounter = new Object();
	var data;
	if(isUpdate){
		//data = $('#listResult').jqxGrid('getrowdata', row);
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
	encounter.unit_Price_W_Tax_Labour = data.unit_Price_W_Tax_Labour;
	var jsonData = JSON.stringify(encounter);
	console.log(jsonData);
	var url = pageContext + '/quotation/' + projectId + '/addquotation?form';
	$.ajax({
		type : "POST",
		contentType : 'application/json',
		data : jsonData,
		url : url,
		success : function(msg) {
			if(msg == null || msg==""){
				alert('You do not have privilege on this action');
				return;
			}
			console.log(msg);
			$("#list").jqxGrid('updatebounddata');

			//$('#list').jqxGrid('addrow', null, {}, 'first');
			$("#list").jqxGrid('begincelledit', 0, "productGroupCode");
			var regionIDs=getCheckedRegionIds();
			var locationIDs=getCheckedLocationIds();
			//reloadDataTable();
//			var itemLocation = $("#jqxWidgetLocation").jqxComboBox('getSelectedItem');
			var locationIds = getCheckedLocationIds();
			updateLocationSum(locationIds);
			var regionIDs = getCheckedRegionIds();
			updateRegionSum(regionIDs);
			$("#searchBtn").click();
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
		$("#list").jqxGrid('begincelledit', 0, "test");
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
			editmode: 'click',
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
						var itemRegion = $("#listRegion").jqxComboBox('getSelectedItem');
						if(itemRegion){
							sourceProductGroup.data.regionId = itemRegion.value;
							dataAdapterProductGroup = new $.jqx.dataAdapter(sourceProductGroup);
						}
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
								if(item){
									// get item's label and value.
									var label = item.label;
									var value = item.value;
									//set value to hidden field
									$("#list").jqxGrid('setcellvalue', 0, "groupId", value);
									//update project combobox.
									var sourceProducts2 = getUrlProducts(value);
									var itemRegion = $("#listRegion").jqxComboBox('getSelectedItem');
									if(itemRegion){
										var regionId = itemRegion.value;
										sourceProducts2.data.regionId = regionId; //set region id
										dataAdapterProducts = getProductAdapter(sourceProducts2);
									}
									setDefaultCellValues(label);
								}

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
					text : 'Search by Name',
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
							displayMember : "productLabel",
							valueMember : "productID",
							promptText : "Please Choose:"
						});
						editor.on('select', function(event) {
							var args = event.args;
							if (args) {
								var index = args.index;
								var item = args.item;
								if(item){
									// get item's label and value.
									var label = item.label;
									var value = item.value;
									//set value to hidden field
									$("#list").jqxGrid('setcellvalue', 0, "productId", value);
									//update Description field
									updateProductFields(value);
								}
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
					cellsformat : 'p2',
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
					datafield : 'buttonAdd',
					width : '10%',
					cellsrenderer : function(row, column, value) {
						return '<div class="col-md-12" style="margin-left: -0px;">'
							+ '<a id="addQuotationBtn" class="btn btn-app col-md-12" style="margin-left: -0px;">'
							+ '<i class="glyphicon glyphicon-plus"></i>'
							+ '</div>';
					},
					cellbeginedit : function(row) {
						return false;
					}
				},
				{
					text : '',
					datafield : 'test',
					align : 'center',
					cellsalign : 'right',
					editable: true,
					width : '0%'
				} ]
		});

}
$('#list').on('cellclick', function (event) {
	var field = event.args.datafield;
	var index = event.args.rowindex;
	if(field == 'buttonAdd'){
		addItem(index);
	}
	// As we do not support to add an encounter for multiple Regions, Raise a message to let user know this logic
	var regionIds = getCheckedRegionIds();
	if(regionIds && regionIds.indexOf(",") > 0){
		var list = regionIds.split(',');
		var count = 0;
		for(var i=0; i<list.length; i++){
			if(list[i] > 0)
				count++;
		}
		if(count >1)
			alert("Please select a single Region in order to add a new record.");
	}
});

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
function setDefaultCellValues(productGroupCode){
	if(productGroupCode){
		var allowanceOfGroup = allowance;
		var discountRateOfGroup = discountRate;
		discountRateOfGroup = getDiscount(productGroupCode);
		allowanceOfGroup = getAllowance(productGroupCode);
		$("#list").jqxGrid('setcellvalue', 0, "discount_rate", discountRateOfGroup);
		$("#list").jqxGrid('setcellvalue', 0, "allowance", allowanceOfGroup);

	}else{
		$("#list").jqxGrid('setcellvalue', 0, "imp_Tax", impTax);
		$("#list").jqxGrid('setcellvalue', 0, "special_Con_Tax", specialCon);
		$("#list").jqxGrid('setcellvalue', 0, "vat", vat);
		$("#list").jqxGrid('setcellvalue', 0, "subcon_Profit", subcon_Profit);
	}

}
function updateQuantity(qtyManual){
	//Q'ty = Q'ty(nhập) * hao hut% bênsheet Summary(Q'ty sub-main, E-Q'ty other ,M-Q'ty)
	var indicator = 0;
	var productGroupCode = $("#list").jqxGrid('getcellvalue', 0, "productGroupCode");

	switch(productGroupCode){
		case '131':
			//sub-main
			indicator = qtySubMain;
			break;
		case  '111E-1', '111E-2','111E-3','111E-4','111E-5','121':
			//E-qty
			indicator = eQtyOther;
			break;
		case '111M-1','111M-2','111M-3','111-5','191-3','194-10','195-2','195-3':
			//Mech-qty
			indicator = mQtymQty;
			break;
		default:
			indicator = 100;
	}
	var result = (Number(qtyManual) * Number(indicator)) / Number(100);
	result = getDecimalNumber(result);
	//update quantity field.
	$("#list").jqxGrid('setcellvalue', 0, "actualQuantity", result);
	updatePriceAfterDiscount();
}
function updatePriceAfterDiscount(){
	//Unit price after discount bằng cot 12+ cot 13/tỉ giá)  = mat_w_o_Tax USD + mat_w_o_Tax_VND/tigia
	//var exchangeRate = 22000; //update to get from database later.
	var floatValue = parseFloat(exchangeRate);
	if(isNaN(floatValue)){
		alert("Exchange Rate is not set up or not valid. Please check at the Project Detail page.");
		return;
	}
	var mat_w_o_Tax_USD = $("#list").jqxGrid('getcellvalue', 0, "mat_w_o_Tax_USD"); //cot 11
	var mat_w_o_Tax_VND = $("#list").jqxGrid('getcellvalue', 0, "mat_w_o_Tax_VND"); //cot 12
	var result = Number(mat_w_o_Tax_USD) + Number(mat_w_o_Tax_VND) / Number(floatValue);
	result = getDecimalNumber(result);
	$("#list").jqxGrid('setcellvalue', 0, "unit_Price_After_Discount", result);

	updateUnitRate();
}
function updateUnitRate(){
//unit rate = cot 19 * cot 20 =  Unit price after discount  * Allowance
	var unit_Price_After_Discount = $("#list").jqxGrid('getcellvalue', 0, "unit_Price_After_Discount");
	var allowance = $("#list").jqxGrid('getcellvalue', 0, "allowance");
	var result = Number(unit_Price_After_Discount) * Number(allowance) / Number(100);
	result = getDecimalNumber(result);
	$("#list").jqxGrid('setcellvalue', 0, "unitRate", result);
	updateUnitPriceWTaxProfit();
}
function updateUnitPriceWTaxProfit() {
	//congthuc =cột 19*(1+(1+cột 16*(1+cot15))*cột 17)*cot18
	//=  Unit price after discount * (1+(1+[Special con. Tax]*(1+[Imp Tax]))*[VAT ])*[Discount rate %]
	//var result =
	var unit_Price_After_Discount = $("#list").jqxGrid('getcellvalue', 0, "unit_Price_After_Discount"); //cot 18
	//cot 15 = specialCon
	var specialCon = $("#list").jqxGrid('getcellvalue', 0, "special_Con_Tax")/100; //cot 15
	var impTax = $("#list").jqxGrid('getcellvalue', 0, "imp_Tax")/100;//cot 14
	var vat = 	$("#list").jqxGrid('getcellvalue', 0, "vat")/100; //cot 16
	var discountRate = $("#list").jqxGrid('getcellvalue', 0, "discount_rate")/100;
	unit_Price_After_Discount = Number(unit_Price_After_Discount);
	specialCon = Number(specialCon);
	impTax = Number(impTax);
	vat = Number(vat);
	discountRate = Number(discountRate);
	var result = unit_Price_After_Discount*(1+(1+specialCon*(1+impTax))*vat)*discountRate;
	result = getDecimalNumber(result);
	$("#list").jqxGrid('setcellvalue', 0, "unit_Price_W_Tax_Profit", result);
	updateUnitPriceWTaxLabour();
}
function updateUnitPriceWTaxLabour(){
// Unit price w Tax - labour =cot 13 * cot 21
	var labour = $("#list").jqxGrid('getcellvalue', 0, "labourProduct"); //cot 13
	var subcon_Profit = $("#list").jqxGrid('getcellvalue', 0, "subcon_Profit") /100;//cot 21
	var result = Number(labour) * Number(subcon_Profit);
	result = getDecimalNumber(result);
	$("#list").jqxGrid('setcellvalue', 0, "unit_Price_W_Tax_Labour", result);
	updateCostMatAmountUsd();
}
function updateCostMatAmountUsd(){
// Cost - Mat amount USD =cot 19* cot 10 = Unit price after discount * Q'ty (nhập)
	var unit_Price_After_Discount = $("#list").jqxGrid('getcellvalue', 0, "unit_Price_After_Discount"); //cot 19
	var qtyManual = $("#list").jqxGrid('getcellvalue', 0, "quantity"); //cot 10
	var result = Number(unit_Price_After_Discount) * Number(qtyManual);
	result = getDecimalNumber(result);
	$("#list").jqxGrid('setcellvalue', 0, "cost_Mat_Amount_USD", result);
	updateCostMatAmountVnd();
}
function updateCostMatAmountVnd(){
// Cost - Labour amount USD =cot 22* cot 9
	var unit_Price_W_Tax_Labour = $("#list").jqxGrid('getcellvalue', 0, "unit_Price_W_Tax_Labour"); //cot 22
	var qtyManual = $("#list").jqxGrid('getcellvalue', 0, "quantity"); //cot 9
	var result = Number(unit_Price_W_Tax_Labour) * Number(qtyManual);
	result = getDecimalNumber(result);
	$("#list").jqxGrid('setcellvalue', 0, "cost_Labour_Amount_USD", result);
	updateLabour();
}
function updateLabour(){
//labour sau thue = cot 6 * cot 23
	var unit_Price_W_Tax_Labour = $("#list").jqxGrid('getcellvalue', 0, "unit_Price_W_Tax_Labour"); //cot 23
	var quantity = $("#list").jqxGrid('getcellvalue', 0, "actualQuantity"); //cot 6
	var result = Number(unit_Price_W_Tax_Labour) * Number(quantity);
	result = getDecimalNumber(result);
	$("#list").jqxGrid('setcellvalue', 0, "labour", result);
	updateAmount();
}
function updateAmount(){
	//amount = cot6 * cot 7 = actualQty * unitRate;
	var quantity = $("#list").jqxGrid('getcellvalue', 0, "actualQuantity"); //cot 6
	var unitRate = $("#list").jqxGrid('getcellvalue', 0, "unitRate");
	var result = Number(quantity) * Number(unitRate);
	result = getDecimalNumber(result);
	$("#list").jqxGrid('setcellvalue', 0, "amount", result);
}

function updateMat_w_o_Tax_USD(){
	//it is invoked for some special product group code.
	//var result = sum(AE1 : AEn) * percent;
	var percent = $("#list").jqxGrid('getcellvalue', 0, "nonamePercent"); //cot 5
	var range = $("#list").jqxGrid('getcellvalue', 0, "nonameRange"); //cot 5
	var total = 0;
	if(range && percent){
		var url = pageContext + '/quotation/getmat_w_o_tax_usd';
		var regionIDs = getCheckedRegionIds();
		var locationIDs = getCheckedLocationIds();
		if (regionIDs && locationIDs) {
			var arrRegion = regionIDs.split(',');
			var arrLocation = locationIDs.split(',');
			if (arrRegion.length > 1 && arrLocation.length > 1) {
				alert('Please select 1 region only in order to add new quotation record.');
				return;
			}
		}

		$.ajax({
			type: "GET",
			contentType: 'application/json',
			url: url,
			data: {
				percent: percent,
				range: range,
				regionId: regionIDs,
				locationId: locationIDs
			},
			success: function (msg) {
			},
			complete: function (jqxhr, settings, exception) {
				//$("#quotation_data_table").html(jqxhr.responseText);
				var result = getDecimalNumber(jqxhr.responseText);
				$("#list").jqxGrid('setcellvalue', 0, "mat_w_o_Tax_USD", result); //update mat_w_o_tax_usd
			}
		});

		/*
		 var arrs = range.split('-');
		 var start = arrs[0];
		 var end = arrs[1];

		 for(var i=start; i<= end; i++){
		 var aEValue = $("#listResult").jqxGrid('getcellvalue', i, "cost_Mat_Amount_USD"); //cot 5
		 total = total + aEValue;
		 }
		 var result = total * percent/100;
		 $("#list").jqxGrid('setcellvalue', 0, "mat_w_o_Tax_USD", result);
		 */
	}
}
function updateItem(row){
	alert('This function is not supported yet');
	return;
	var encounterId = $('#listResult').jqxGrid('getcellvalue', row, "uid");
	saveEncounter(row, true);

}
/*function deleteItem(encounterId){
	var result = confirm('Do you want to delete this record?');
	if (result == false)
		return;
	var url = pageContext + '/quotation/'+ encounterId + '?delete';
	$.ajax({
		type : "POST",
		contentType : 'application/json',
		url : url,
		success : function(msg) {

		},
		complete : function(xhr, status) {
			// $("#assignRegionButton").prop('disabled', false);
		//	reloadDataTable();
			$("#searchBtn").click();
		}
	});
}*/
$("#searchBtn").click(function(){
	var checkedItems = $("#listRegion").jqxComboBox('getCheckedItems');
	var regionIDs=getCheckedRegionIds();
	var locationIDs=getCheckedLocationIds();
	var row1 = $('#list').jqxGrid('getrows');
	if(row1)
		$('#list').jqxGrid('updatebounddata');
	else
		loadAddQuotationGrid();

//	reloadDataTable(); // luan disable on 8/14/2016 to use angularjs instead of using datatable.
	var url = pageContext + "/quotation/getAssignedProductOfRegion";
	var jsonData = {regionId: regionIDs,
					locationIds: locationIDs};
	makeGetRequestJson(url,jsonData,updateAngularJSList);
	//updateAngularJSList is declare in /angularjs/app/quotation folder
});



function getCheckedLocationIds(){
	var checkedItemsLocation = $("#jqxWidgetLocation").jqxComboBox(
		'getCheckedItems');
	var locationIDs = '';
	$.each(checkedItemsLocation, function(index) {
		locationIDs += this.value + ",";
	});
	if (locationIDs != '') {
		locationIDs = locationIDs.substr(0, locationIDs.length - 1);
	}
	console.log(locationIDs);
	return locationIDs;
}
function getCheckedRegionIds(){
	var checkedItems = $("#listRegion").jqxComboBox('getCheckedItems');
	var regionIDs='';
	$.each(checkedItems, function (index) {
		regionIDs += this.value + ",";
	});
	if(regionIDs != ''){
		regionIDs = regionIDs.substr(0, regionIDs.length-1);
	}
	return regionIDs;
}
function getRenderUsd(){
	var result = $.fn.dataTable.render.number( ',', '.', 2, '$ ' );
	return result;
}
function getRenderVnd(){
	var result = $.fn.dataTable.render.number( ',', '.', 0, 'VND ' );
	return result;
}
function getRenderNumberBasedOnCurrency(){
	var result = getRenderUsd();
	if(currency == 'VND'){
		result = getRenderVnd();
	}else if(currency == 'JPY'){
		result = $.fn.dataTable.render.number( ',', '.', 2, 'JPY ' );
	}else{
		// USD is default
	}
	return result;
}
function getAllowance(productGroupCode){
	var records = dataAdapterProductGroup.records;
	for(var i=0; i< records.length; i++){
		if (records[i].groupCode == productGroupCode && records[i].allowance) {
			return records[i].allowance;
		}
	}
	return allowance;

}
function getDiscount(productGroupCode){
	var records = dataAdapterProductGroup.records;
	for(var i=0; i< records.length; i++){
		if (records[i].groupCode == productGroupCode && records[i].discount) {
			return records[i].discount;
		}
	}
	return discountRate;

}