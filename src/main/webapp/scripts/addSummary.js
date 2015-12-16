/**
 * Created by Chuong on 12/15/2015.
 */


var urlProductGroup = pageContext + '/summary/getProductGroupRateJson';
var sourceProductGroupRate = {
    datatype : "json",
    datafields : [ {
        name : 'Code',
        type : 'string'
    }, {
        name : 'M&E WORKS',
        type : 'string'
    }, {
        name : 'Discount',
        type : 'float'
    }, {
        name : 'Allowance',
        type : 'float'
    }, {
        name : 'Total M&E',
        type : 'float'
    }, {
        name : 'Total labour M&E',
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
var dataAdapter = new $.jqx.dataAdapter(sourceProductGroupRate, {
    downloadComplete: function (data, status, xhr) { },
    loadComplete: function (data) { },
    loadError: function (xhr, status, error) { }
});
// initialize jqxGrid
$("#productGroupRate").jqxGrid(
    {
        width: 850,
        source: dataAdapter,
        pageable: true,
        autoheight: true,
        sortable: true,
        altrows: true,
        enabletooltips: true,
        editable: true,
        selectionmode: 'multiplecellsadvanced',
        columns: [
            { text: 'Product Name', columngroup: 'ProductDetails', datafield: 'ProductName', width: 250 },
            { text: 'Quantity per Unit', columngroup: 'ProductDetails', datafield: 'QuantityPerUnit', cellsalign: 'right', align: 'right', width: 200 },
            { text: 'Unit Price', columngroup: 'ProductDetails', datafield: 'UnitPrice', align: 'right', cellsalign: 'right', cellsformat: 'c2', width: 200 },
            { text: 'Units In Stock', datafield: 'UnitsInStock', cellsalign: 'right', cellsrenderer: cellsrenderer, width: 100 },
            { text: 'Discontinued', columntype: 'checkbox', datafield: 'Discontinued' }
        ],
        columngroups: [
            { text: 'Product Details', align: 'center', name: 'ProductDetails' }
        ]
    });

function loadProductGroupRate (){


}
