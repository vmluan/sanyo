/**
 * Created by Chuong on 12/15/2015.
 */


var urlProductGroup = pageContext + '/summary/getProductGroupRateJson/'+ projectId;
var sourceProductGroupRate = {
    datatype : "json",
    datafields : [ {
        name : 'id',
        type : 'float'
    },{
        name : 'code',
        map : 'productGroup>groupCode'
    }, {
        name : 'discount',
        type : 'float'
    }, {
        name : 'allowance',
        type : 'float'
    }, {
        name : 'totalMaterial',
        type : 'totalMaterialLabour>totalMaterial'
    }, {
        name : 'totalLabor',
        type : 'totalMaterialLabour>totalLabour'
    }, {
        name : 'productGroupName',
        map : 'productGroup>groupName'
    }
    ],
    sortcolumn : 'code',
    sortdirection : 'asc',
    url : urlProductGroup
};
var dataProductGroupRateAdapter = new $.jqx.dataAdapter(sourceProductGroupRate, {
    downloadComplete: function (data, status, xhr) { },
    loadComplete: function (data) { },
    loadError: function (xhr, status, error) { }
});
// initialize jqxGrid
$("#productGroupRate").jqxGrid(
    {
        width: '100%',
        source: dataProductGroupRateAdapter,
        pageable: true,
        autoheight: true,
        sortable: true,
        altrows: true,
        enabletooltips: true,
        editable: true,
        selectionmode: 'multiplecellsadvanced',
        columns: [
            { text: 'Code', datafield: 'code', width: '20%' },
            { text: 'M&E WORKS',datafield: 'productGroupName', cellsalign: 'right', align: 'right', width: '30%' },
            { text: 'Discount', datafield: 'discount', align: 'right', cellsalign: 'right', cellsformat: 'p', width: '10%' },
            { text: 'Allowance', datafield: 'allowance', cellsalign: 'right', cellsformat: 'p', width: '10%' },
            { text: 'Total Material', datafield: 'totalMaterial', cellsalign: 'right', width: '10%' },
            { text: 'Total Labour', datafield: 'totalLabor', cellsalign: 'right', width: '10%' },
            {
                text : 'Action',
                align : 'center',
                datafield : 'buttonUpdate',
                width : '10%',
                cellsrenderer : function(row, column, value) {
                    return '<div class="col-md-12" style="margin-left: -0px;">'
                        + '<button class="btn bg-olive margin col-md-4"  onclick="updateRate('+ row +','+dataProductGroupRateAdapter.records[row].id + ')"' + '>Update</button>'
                        + '</div>';
                },
                cellbeginedit : function(row) {
                    return false;
                }
            }
        ]
        //],
        //columngroups: [
        //    { text: 'Total', align: 'center', name: 'Total' }
        //]
    });


//Update DiscountRate and Allowance
$('#list').on('cellclick', function (event) {
    var field = event.args.datafield;
    var index = event.args.rowindex;
    if(field == 'buttonAdd'){
        addItem(index);
    }
});

function updateRate(row, id){
    var productGroupRate = new Object();
    var data = $('#productGroupRate').jqxGrid('getrowdata', row);

    productGroupRate.id = id;
    productGroupRate.discount =
        data.discount;
    productGroupRate.allowance =
        data.allowance;

    var jsonData = JSON.stringify(productGroupRate);
    console.log(jsonData);
    var url = pageContext + '/summary/' + id + '/updateRate?form';
    $.ajax({
        type : "POST",
        contentType : 'application/json',
        data : jsonData,
        url : url,
        success : function(msg) {
            $("#productGroupRate").jqxGrid('updatebounddata');

            //$('#list').jqxGrid('addrow', null, {}, 'first');
            //$("#list").jqxGrid('begincelledit', 0, "desc");
            //var regionIDs=getCheckedRegionIds();
            //var locationIDs=getCheckedLocationIds();
            //showResultGrid(locationIDs, regionIDs);
            //loadDataTable();
//			var itemLocation = $("#jqxWidgetLocation").jqxComboBox('getSelectedItem');
            //var locationIds = getCheckedLocationIds();
            //updateLocationSum(locationIds);
        },
        complete : function(xhr, status) {
            // $("#assignRegionButton").prop('disabled', false);
        }
    });

}

