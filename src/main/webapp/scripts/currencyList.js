/**
 * To display jqxgrid in user list page.
 */
var url = pageContext + "/currency/getListJson";
// prepare the data
var source = {
	datatype : "json",
	datafields : [ {
		name : 'id',
		type : 'string'
	}, {
		name : 'exchangeRateValue',
		type : 'string'
	}, {
		name : 'sourceCurrencyCode',
		map : 'sourceCurrency>currencyCode'
	}, {
		name : 'sourceCurrencyName',
		map : 'sourceCurrency>currencyName'
	},{
		name : 'targetCurrencyCode',
		map : 'targetCurrency>currencyCode'
	}, {
		name : 'targetCurrencyName',
		map : 'targetCurrency>currencyName'
	},{
		name : 'startDate',
		type : 'date'
	}, {
		name : 'endDate',
		type : 'date'
	}],
	id : 'id',
	url : url
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
					height : 400,
					theme: 'energyblue',
					source : dataAdapter,
					sortable : true,
					pageable : true,
					autoloadstate : false,
					autosavestate : false,
					columnsresize : true,
					columnsreorder : true,
					// showfilterrow : true,
					// filterable : true,
					columnsresize : true,
					//rowsheight : 200,
					autorowheight: true,
					columns : [
							{
								text : '#',
								datafield : 'stt',
								width : '5%',
								columntype : 'number',
								align : 'center',
								cellsrenderer : function(row, column, value) {
									return "<div style='margin:4px;'>"
											+ (value + 1) + "</div>";
								}
							}, {
								text : 'From',
								datafield : 'sourceCurrencyName',
								align : 'center',
								cellsalign : 'right',
								width : '15%'
							}, {
								text : 'To',
								datafield : 'targetCurrencyName',
								align : 'center',
								cellsalign : 'right',
								width : '15%'
							},{
								text : 'Value',
								datafield : 'exchangeRateValue',
								align : 'center',
								cellsalign : 'right',
								cellsformat : 'c0',
								width : '15%'
							},{
								text : 'Start Date',
								datafield : 'startDate',
								align : 'center',
								width : '15%',
								cellsformat: 'MM/dd/yyyy'
							}, {
								text : 'End Date',
								datafield : 'endDate',
								align : 'center',
								width : '15%',
								cellsformat: 'MM/dd/yyyy'
							},
							{
								text : 'Action',
								align : 'center',
								datafield : 'id',
								width : '50%',
								cellsrenderer : function(row, column, value) {
									return '<div class="col-md-12">'
												+'<p>'
													+ '<button class="btn bg-olive margin col-md-2"  onclick="updateProduct('+ value +  ')"' + '>Edit</button>'
												+ '</p>'
											+ '</div>'
											;
								}
							}
							]
				});
				
				//
function updateProduct(id) {
	window.location.href = pageContext + '/currency/' + id + '?form';
}
