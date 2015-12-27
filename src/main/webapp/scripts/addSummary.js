/**
 * Created by Chuong on 12/15/2015.
 */


var urlProductGroup = pageContext + '/summary/getProductGroupRateJson/'+ projectId;
var sourceProductGroupRate = {
    datatype : "json",
    datafields : [ {
        name : 'code',
        map : 'productGroup>groupCode'
    }, {
        name : 'discount',
        type : 'float'
    }, {
        name : 'allowance',
        type : 'float'
    }, {
        name : 'productGroupName',
        map : 'productGroup>groupName'
    }
    ],
    sortcolumn : 'id',
    sortdirection : 'asc',
    id : 'id',
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
        width: 550,
        source: dataProductGroupRateAdapter,
        pageable: true,
        autoheight: true,
        sortable: true,
        altrows: true,
        enabletooltips: true,
        editable: true,
        selectionmode: 'multiplecellsadvanced',
        columns: [
            { text: 'Code', datafield: 'code', width: 250 },
            { text: 'M&E WORKS',datafield: 'productGroupName', cellsalign: 'right', align: 'right', width: 200 },
            { text: 'Discount', datafield: 'discount', align: 'right', cellsalign: 'right', cellsformat: 'p', width: 200 },
            { text: 'Allowance', datafield: 'allowance', cellsalign: 'right', cellsformat: 'p', width: 100 }//,
            //{ text: 'Total M&E', columngroup: 'total', datafield: 'id' },
            //{ text: 'Total Labour M&E', columngroup: 'total', datafield: 'id' }
        ]
        //],
        //columngroups: [
        //    { text: 'Total', align: 'center', name: 'Total' }
        //]
    });

