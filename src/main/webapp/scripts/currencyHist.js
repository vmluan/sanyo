var url = pageContext + "/currency/getHistJson";
// prepare the data
var source =
        {
            datatype: "json",
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
            id: 'id',
            url: url,
    	    sortcolumn: 'startDate',
    	    sortdirection: 'asc',
        	data : {
        		currencyId : currencyId
        	}
        };
var dataAdapter = new $.jqx.dataAdapter(source, {
    downloadComplete: function(data, status, xhr) {
    },
    loadComplete: function(data) {
    },
    loadError: function(xhr, status, error) {
    }
});
// initialize jqxGrid
$("#jqxgridHist")
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
					}
					]
		});
