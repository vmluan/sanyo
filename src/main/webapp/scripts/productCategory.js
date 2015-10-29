	var regions=[
		{"categoryId": 1, "categoryName": "ELECTRICAL BOQ", "categoryDesc" : "test", "parentCategoryId": null}
		,{"categoryId": 2, "categoryName": "HIGH VOLTAGE SYSTEM", "categoryDesc" : "HIGH VOLTAGE SYSTEM", "parentCategoryId": 1}
		,{"categoryId": 3, "categoryName": "LV MAIN SYSTEM", "categoryDesc" : "LV MAIN SYSTEM", "parentCategoryId": 1}
		,{"categoryId": 4, "categoryName": "SUBMAIN SYSTEM", "categoryDesc" : "SUBMAIN SYSTEM", "parentCategoryId": 1}
		,{"categoryId": 5, "categoryName": "POWER SUPPLY TO PRODUCTION", "categoryDesc" : "", "parentCategoryId": 1}
		,{"categoryId": 6, "categoryName": "EMERGENCY POWER BACK-UP GENERATOR", "categoryDesc" : "", "parentCategoryId": 1}
		,{"categoryId": 7, "categoryName": "LIGHTING SYSTEM &amp; SOCKET OUTLET", "categoryDesc" : "", "parentCategoryId": 1}
		,{"categoryId": 8, "categoryName": "ELECTRICAL FOR MECHANICAL EQUIPMENT (A.C, FAN, AIR COMP., PUMPS)", "categoryDesc" : "", "parentCategoryId": 1}
		,{"categoryId": 9, "categoryName": "TELEPHONE SYSTEM EMPTY CONDUIT", "categoryDesc" : "", "parentCategoryId": 1}
		,{"categoryId": 10, "categoryName": "LAN SYSTEM EMPTY CONDUIT", "categoryDesc" : "", "parentCategoryId": 1}
		,{"categoryId": 11, "categoryName": "PUBLIC ADDRESS SYSTEM", "categoryDesc" : "", "parentCategoryId": 1}
		,{"categoryId": 12, "categoryName": "FIRE ALARM SYSTEM", "categoryDesc" : "", "parentCategoryId": 1}
		,{"categoryId": 13, "categoryName": "LIGHTNING PROTECTION SYSTEM", "categoryDesc" : "", "parentCategoryId": 1}
		,{"categoryId": 14, "categoryName": "WATER LEAKING ALARM (FOR PLATING AREA)", "categoryDesc" : "", "parentCategoryId": 1}
		,{"categoryId": 15, "categoryName": "MECHANICAL BOQ", "categoryDesc" : "", "parentCategoryId": null}
		,{"categoryId": 16, "categoryName": "AIR CONDITIONING SYSTEM", "categoryDesc" : "", "parentCategoryId": 15}
		,{"categoryId": 17, "categoryName": "VENTILATION SYSTEM", "categoryDesc" : "", "parentCategoryId": 15}
		,{"categoryId": 18, "categoryName": "COLD WATER SUPPLY", "categoryDesc" : "", "parentCategoryId": 15}
		,{"categoryId": 19, "categoryName": "SEWAGE DRAINAGE SYSTEM", "categoryDesc" : "", "parentCategoryId": 15}
		,{"categoryId": 20, "categoryName": "SANITARY WARE", "categoryDesc" : "", "parentCategoryId": 15}
		,{"categoryId": 21, "categoryName": "FIRE FIGHTING SYSTEM", "categoryDesc" : "", "parentCategoryId": 15}
		,{"categoryId": 22, "categoryName": "AIR COMPRESSOR SYSTEM", "categoryDesc" : "", "parentCategoryId": 15}
		,{"categoryId": 23, "categoryName": "STEAM BOILER SYSTEM", "categoryDesc" : "", "parentCategoryId": 15}
		,{"categoryId": 23, "categoryName": "LOCAL PRODUCTION HEAT EXHAUST SYSTEM (Heat Treatment &amp; Plating Area )", "categoryDesc" : "", "parentCategoryId": 15}
		];
// prepare the data
var source =
{
    dataType: "json",
    dataFields: [
        { name: 'categoryId', type: 'number' },
        { name: 'parentCategoryId', type: 'number' },
        { name: 'categoryName', type: 'string' },
        { name: 'categoryDesc', type: 'string' }
    ],
    hierarchy:
    {
        keyDataField: { name: 'categoryId' },
        parentDataField: { name: 'parentCategoryId' }
    },
    id: 'categoryId',
    localData: regions
};
var dataAdapter = new $.jqx.dataAdapter(source);

$("#jqxWidgetCategory").jqxTreeGrid(
        {
            width: '100%',
			//height: 300,
			theme: 'energyblue',
            source: dataAdapter,
			//editable: true,
            sortable: true,
			hierarchicalCheckboxes: true,
			checkboxes: true,
			selectionMode: 'singleRow',
			editSettings: { saveOnPageChange: true, saveOnBlur: true, saveOnSelectionChange: true, cancelOnEsc: true, saveOnEnter: true, editSingleCell: true, editOnDoubleClick: true, editOnF2: true },
            ready: function()
            {
               $("#jqxWidgetCategory").jqxTreeGrid('lockRow', 1);
			    $("#jqxWidgetCategory").jqxTreeGrid('lockRow', 15);
            },
            columns: [
              { text: '', dataField: 'categoryName', width: '100%' }
            ]
        });