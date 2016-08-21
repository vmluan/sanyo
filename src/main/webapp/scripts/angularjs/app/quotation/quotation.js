/**
 * Created by luan on 8/14/16.
 */


/*
var myapp = angular.module('myapp', ['ui']);
myapp.controller('quotationController', function ($scope) {

});
angular.bootstrap(document, ['myapp']);


function updateAngularJSList(result){
    var scope = angular.element($("#angularjsResult")).scope();
    console.log(scope);
    scope.$apply(function(){
        scope.list = result;
    });

}*/
var myapp = angular.module('myapp', ['ui']);
myapp.controller('quotationController', function ($scope) {
    $scope.deleteItem =  function(encounterId){
        var result = confirm('Do you want to delete this record?');
        if (result == false)
            return;
        var url = pageContext + '/quotation/'+ encounterId + '?delete';
        $.ajax({
            type : "POST",
            contentType : 'application/json',
            url : url,
            success : function(msg) {
                $("#listResult").jqxGrid('updatebounddata');
            },
            complete : function(xhr, status) {
                // $("#assignRegionButton").prop('disabled', false);
                reloadDataTable();
            }
        });
    }
    $scope.example1model = []; $scope.example1data = [ {id: 1, label: "David"}, {id: 2, label: "Jhon"}, {id: 3, label: "Danny"}];
    //define list of columns used in result table
    $scope.all_columns=[
        {
            "title": "Region",
            "type": "string",
            "checked": true
            ,"key": "region"
            ,"subKey": "regionName"
            ,"hasSubKey": true
        },
        {
            "title": "Description",
            "type": "string",
            "checked": true
            ,"key": "product"
            ,"hasSubKey": true
            ,"subKey": "productName"
        },
        {
            "title": "Unit",
            "type": "string",
            "checked": true
            ,"key": "product"
            ,"hasSubKey": true
            ,"subKey": "unit"
        },
        {
            "title": "Quantity",
            "type": "string",
            "checked": true
            ,"key": "actualQuantity"
        },
        {
            "title": "Unit Rate",
            "type": "string",
            "checked": true
            ,"key": "unitRate"
        },
        {
            "title": "Amount",
            "type": "string",
            "checked": true
            ,"key": "amount"
        },
        {
            "title": "Percent",
            "type": "string",
            "checked": true
            ,"key": "nonamePercent"
        },
        {
            "title": "Range",
            "type": "string",
            "checked": true
            ,"key": "nonameRange"
        },
        {
            "title": "Remark",
            "type": "string",
            "checked": true
            ,"key": "remark"
        },
        {
            "title": "Qty",
            "type": "string",
            "checked": true
            ,"key": "quantity"
        },
        {
            "title": "Labour",
            "type": "string",
            "checked": true
            ,"key": "labourAfterTax"
        },
        {
            "title": "Mat w/o Tax USD",
            "type": "string",
            "checked": true
            ,"key": "mat_w_o_Tax_USD"
        },
        {
            "title": "Mat w/o Tax VND",
            "type": "string",
            "checked": true
            ,"key": "mat_w_o_Tax_VND"
        },
        {
            "title": "Labour (database)",
            "type": "string",
            "checked": true
            ,"key": "product.labour"
        },
        {
            "title": "Imp Tax",
            "type": "string",
            "checked": true
            ,"key": "imp_Tax"
        },
        {
            "title": "Special con. Tax",
            "type": "string",
            "checked": true
            ,"key": "special_Con_Tax"
        },
        {
            "title": "VAT",
            "type": "string",
            "checked": true
            ,"key": "vat"
        },
        {
            "title": "Discount rate %",
            "type": "string",
            "checked": true
            ,"key": "discount_rate"
        },
        {
            "title": "Unit price after discount",
            "type": "string",
            "checked": true
            ,"key": "unit_Price_After_Discount"
        },
        {
            "title": "Allowance",
            "type": "string",
            "checked": true
            ,"key": "allowance"
        },
        {
            "title": "Unit price w Tax & profit",
            "type": "string",
            "checked": true
            ,"key": "unit_Price_W_Tax_Profit"
        },
        {
            "title": "Subcon profit",
            "type": "string",
            "checked": true
            ,"key": "subcon_Profit"
        },
        {
            "title": "Unit price w Tax – labour",
            "type": "string",
            "checked": true
            ,"key": "unit_Price_W_Tax_Labour"
        },
        {
            "title": "Cost - Mat amount USD",
            "type": "string",
            "checked": true
            ,"key": "cost_Mat_Amount_USD"
        },
        {
            "title": "Cost - Labour amount USD",
            "type": "string",
            "checked": true
            ,"key": "cost_Labour_Amount_USD"
        }
    ];
    $scope.ordered_columns = [];
    $scope.$watch('all_columns', function() {
        update_columns();
    }, true);

    var update_columns = function() {
        $scope.ordered_columns = [];
        for (var i = 0; i < $scope.all_columns.length; i++) {
            var column = $scope.all_columns[i];
            if (column.checked) {
                $scope.ordered_columns.push(column);
            }
        }
    };

    $scope.list=[];
    $scope.sortableOptionsA = {

        stop : function(e, ui) {

            var item = ui.item.scope().item;
            var fromIndex = ui.item.sortable.index;
            var sortable = ui.item.sortable.resort;
            if (sortable) {
                var newList = ui.item.sortable.resort.$modelValue;
                var regionList = new Array();
                //find toIndex
                for (x in newList) {
                    var newItem = newList[x];
                    //check if multitple regions are seleteced. If yes, prevent user to re-order.
                    if (regionList.length == 0)
                        regionList.push(newItem.region);
                    for (y in regionList) {
                        if (regionList[y].regionId == newItem.region.regionId) {
                        } else {
                            regionList.push(newItem.region);
                        }

                    }
                }
                if (regionList.length > 1) {
                    alert("Please select one Region only in order to use Drag and Drop feature correctly.")
                    return;
                }
                //parse ui to the ordered list
                var tbody =document.getElementById("resultListSortable");
                var rows = tbody.children;
                var newIndexList = new Array();

                for(var i = 0; i< rows.length; i++){
                    var rowi = rows[i];
                    if(rowi) {
                        var encounterId = rowi.getAttribute('name');
                        newIndexList.push(encounterId);
                    }
                }
                console.log(newIndexList);
                //next is to update the list with the orders in the newIndexList
                var oldList = $scope.list;
                var newList = new Array();
                for(var i=0; i<newIndexList.length; i++){
                    for(var j=0; j < oldList.length; j++){
                        var item = oldList[j];
                        var encounterID = item.encounterID;
                        if(Number(encounterID) == Number(newIndexList[i])){
                            newList.push(item);
                        }
                    }
                }
                updateAngularJSList(newList);
            }

        }
    };
    $("#saveOrdersBtn").click(function(){
        //pass current orders of the list to controller.
        var jsonData = '';
        //there is a bug of the ui component.
        //Sometime the order on GUI is different from the index in the list.
        //So to bypass this bug, we SHOULD NOT use the list
        console.log(' updated list');
        console.log($scope.list);
        for(x in $scope.list){
            var item = $scope.list[x];
            var encounterId = item.encounterID;
            var newPos = +x + +1;;
            var data = "encounterId_" + encounterId + "|newOrder_" + newPos;
            jsonData = jsonData + "," + data;
        }
        jsonData = jsonData.substring(1);
        updateEncounterOrder(jsonData);
    });

});
angular.bootstrap(document, ['myapp']);



function updateAngularJSList(result){
    var scope = angular.element($("#angularjsResult")).scope();
    scope.$apply(function(){
        scope.list = [];
        scope.list = result;
    });
}
function updateEncounterOrder(data){
    var jsonData = {params:data};
    var url = pageContext + '/quotation/updateQuotationListOrderByAngularJS';
    makeGetRequestJson(url,jsonData,handleAfterUpdateingOrder);
}
function handleAfterUpdateingOrder(result){
    // call searchBtn button click to reload result
    $("#searchBtn").click();

}
function deleteItem(encounterId){
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
}
