var dateTimeFormat = 'dd/MM/yyyy HH.mm.ss';
var url = "/quanlyban/tablelistjson";
var sysDate = new Date();
var date = sysDate.getDate() + '/' + (sysDate.getMonth() + 1) + '/' + sysDate.getFullYear();
var sourceTables =
            {
		datatype: "json",        
		datafields: [
                    { name: 'tableNumber' },
					{ name: 'tableID' },
                    { name: 'area' },
                    { name: 'customerName' },
                    { name: 'openTime', type: 'date'},
                    { name: 'closedTime', type: 'date'},
                    { name: 'totalMoney' },
                    { name: 'status' }
                ],
                id: 'tableID',
                url: url
            };
            var dataAdapterTables = new $.jqx.dataAdapter(sourceTables, {
                downloadComplete: function (data, status, xhr) { },
                loadComplete: function (data) { },
                loadError: function (xhr, status, error) { },
				formatData: function (data) {
							$.extend(data, {
								startdate: date,
								enddate: date
							});
							return data;
						}
            });
            $("#customersGrid").jqxGrid(
            {
                width: '95%',
                height: 250,
                source: dataAdapterTables,                
                keyboardnavigation: false,
				showstatusbar: true,
                statusbarheight: 25,
                altrows: true,
                showaggregates: true,
                columns: [
                    { text: 'Bàn Số', datafield: 'tableNumber', width: '10%' },
                    { text: 'Khu', datafield: 'area', width: '10%' },
                    { text: 'Tên KH', datafield: 'customerName', width: '25%' },
					{ text: 'Tổng tiền', datafield: 'totalMoney', width: '15%', cellsformat: 'c', align: 'right', cellsalign: 'right', aggregates: [{ '<b>Tổng</b>':
                            function (aggregatedValue, currentValue, column, record) {
                                var total = currentValue;
                                return aggregatedValue + total;
                            }
                      }]  
					
					},
                    { text: 'Thời gian vào', datafield: 'openTime', width: '20%', cellsformat: dateTimeFormat,align: 'right', cellsalign: 'right'},
					{ text: 'Thời gian ra', datafield: 'closedTime', width: '20%', cellsformat: dateTimeFormat,align: 'right', cellsalign: 'right'}
                    
                ]
            });

            // Orders Grid
            // prepare the data
            var dataFields = [
                        { name: 'productName' },
                        { name: 'quantity' },
                        { name: 'productPrice'},
                        { name: 'productPrice'},
                        { name: 'encounterTime' }
                    ];
            


//            var dataAdapter = new $.jqx.dataAdapter(source);
//            dataAdapter.dataBind();


            $("#customersGrid").on('rowselect', function (event) {
                var tableID = event.args.row.tableID;
                var urlEncounter = '/quanlyban/encounterlistjson?' + 'tableid=' + tableID;
                var sourceEncounter =
                {
                	datatype: "json",
                	datafields: [
                                 { name: 'product', map:'product>productName'},
                                 { name: 'quantity' },
                                 { name: 'productPrice'},
                                 { name: 'encounterTime', type: 'date'}
                             ],
                	id: 'encounterID',
                	url: urlEncounter
                };
                var dataAdapterEncounters = new $.jqx.dataAdapter(sourceEncounter, {
                    downloadComplete: function (data, status, xhr) { },
                    loadComplete: function (data) { },
                    loadError: function (xhr, status, error) { }
                });
				
                // update data source.
                $("#ordersGrid").jqxGrid({ source: dataAdapterEncounters });
				
            });

            $("#ordersGrid").jqxGrid(
            {
                width: '95%',
                height: 250,
                keyboardnavigation: false,
                columns: [
                    { text: 'Tên SP', datafield: 'product', width: '35%' },
                    { text: 'SL', datafield: 'quantity', width: '5%',align: 'right', cellsalign: 'right' },
                    { text: 'Gia', datafield: 'productPrice',  width: '10%', align: 'right', cellsalign: 'right' },
					{ text: 'Thành Tiền', width: '10%', align: 'right', cellsalign: 'right',
					   cellsrenderer: function (row, columnfield, value, defaulthtml, columnproperties) {
								var rowdata = $("#ordersGrid").jqxGrid('getrowdata', row);
								return '<span style="margin: 4px; float: ' + columnproperties.cellsalign + '; color: #0000ff;">' + rowdata.productPrice * rowdata.quantity + '</span>';
							}
					},				
                    { text: 'Thời gian', datafield: 'encounterTime', cellsformat: dateTimeFormat, align: 'right', cellsalign: 'right' }
                ]
            });
            
			function reloadGrids(date, date2){
				dataAdapterTables = new $.jqx.dataAdapter(sourceTables,
					{
						formatData: function (data) {
							$.extend(data, {
								startdate: date,
								enddate: date2
							});
							return data;
						}
					}
				);
				$("#customersGrid").jqxGrid({ source: dataAdapterTables });
				$("#ordersGrid").jqxGrid('clear');
			}