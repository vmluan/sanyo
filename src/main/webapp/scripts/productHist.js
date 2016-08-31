var url = pageContext + "/products/getproductHistJson";
// prepare the data
var source =
        {
            datatype: "json",
            datafields: [
                {name: 'max_w_o_tax_usd', type: 'string'},
                {name: 'max_w_o_tax_vnd', type: 'float'},
                {name: 'labour', type: 'string'},
                {name: 'issuedDate', type: 'date'},
				{name: 'expiredDate', type: 'date'}
            ],
            id: 'productID',
            url: url,
        	data : {
        		productID : productID
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
$("#jqxgridProductHist").jqxGrid(
        {
            width: '100%',
            height: 400,
            source: dataAdapter,
            sortable: true,
            pageable: true,
            autoheight: true,
            columnsresize: true,
            autorowheight: true,
			theme: 'energyblue',
            columns: [
				{
					text : '#',
					datafield : 'stt',
					width : '5%',
					columntype : 'number',
					align : 'center',
					cellsrenderer : function(row, column, value) {
                        console.log(value);
						return "<div style='margin:4px;'>"
								+ (value + 1) + "</div>";
					}
				},
                {text: 'Max W/O Tax USD', datafield: 'max_w_o_tax_usd', width: '20%',cellsformat: 'c0',cellsalign: 'right'},
				{text: 'Max W/O Tax VND', datafield: 'max_w_o_tax_vnd', width: '20%',cellsformat: 'c0',cellsalign: 'right'},
                {text: 'Labour', datafield: 'labour', align: 'right', cellsalign: 'right', cellsformat: 'c2', width: '15%'},
                {
                    text: 'Start Date',
                    datafield: 'issuedDate',
                    align: 'right',
                    cellsalign: 'right',
                    cellsformat: 'MM/dd/yyyy',
                    width: '20%'
                },
                {
                    text: 'End Date',
                    datafield: 'expiredDate',
                    align: 'right',
                    cellsalign: 'right',
                    cellsformat: 'MM/dd/yyyy',
                    width: '20%'
                },

            ]
        });
