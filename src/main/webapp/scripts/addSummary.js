/**
 * Created by Chuong on 12/15/2015.
 */


var urlProductGroup = pageContext + '/summary/getProductGroupRateJson/'+ projectId;
var sourceProductGroupRate = {
    datatype : "json",
    datafields : [ {
        name : 'Code',
        type : 'string'
    }, {
        name : 'MEWORKS',
        type : 'string'
    }, {
        name : 'Discount',
        type : 'float'
    }, {
        name : 'Allowance',
        type : 'float'
    }, {
        name : 'TotalME',
        type : 'float'
    }, {
        name : 'TotalLabourME',
        type : 'float'
    } ],
    sortcolumn : 'Code',
    sortdirection : 'asc',
    id : 'id',
    data : {
        productGroud : ''
    },
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
        width: 850,
        source: dataProductGroupRateAdapter,
        pageable: true,
        autoheight: true,
        sortable: true,
        altrows: true,
        enabletooltips: true,
        editable: true,
        selectionmode: 'multiplecellsadvanced',
        columns: [
            { text: 'Code', datafield: 'Code', width: 250 },
            { text: 'M&E WORKS',datafield: 'MEWORKS', cellsalign: 'right', align: 'right', width: 200 },
            { text: 'Discount', datafield: 'Discount', align: 'right', cellsalign: 'right', cellsformat: 'c2', width: 200 },
            { text: 'Allowance', datafield: 'Allowance', cellsalign: 'right', width: 100 },
            { text: 'Total M&E', columngroup: 'total', datafield: 'TotalME' },
            { text: 'Total Labour M&E', columngroup: 'total', datafield: 'TotalLabourME' }
        ],
        columngroups: [
            { text: 'Total', align: 'center', name: 'Total' }
        ]
    });

function loadProductGroupRate (){


}
