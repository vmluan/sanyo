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

    $scope.list=[];
    $scope.sortableOptionsA = {

        stop : function(e, ui) {
            var item = ui.item.scope().item;
            var fromIndex = ui.item.sortable.index;
            var sortable = ui.item.sortable.resort;
            if(sortable){
                var newList = ui.item.sortable.resort.$modelValue;
                var toIndex;
                var regionList = new Array();
                //find toIndex
                for (x in newList){
                    var newItem = newList[x];
                    if(item.encounterID == newItem.encounterID)
                        toIndex = x;
                    //check if multitple regions are seleteced. If yes, prevent user to re-order.
                    if(regionList.length ==0)
                        regionList.push(newItem.region);
                    for(y in regionList){
                        if(regionList[y].regionId == newItem.region.regionId){
                        }else{
                            regionList.push(newItem.region);
                        }

                    }
                }
                if(regionList.length >1){
                    alert("Please select one Region only in order to use Drag and Drop feature correctly.")
                    return;
                }
                console.log(regionList);
                if(fromIndex != toIndex){
                    console.log('moved', item, fromIndex, toIndex);

                    //call service to update the order from [fromIndex, toIndex];
                    var startPos, endPos;
                    if(fromIndex < toIndex){
                        startPos=fromIndex;
                        endPos=toIndex;
                    }else{
                        startPos=toIndex;
                        endPos=fromIndex;
                    }
                    var jsonData='';
                    for(i=startPos;i<=endPos;i++){
                        var changedItem = newList[i];
                        var encounterId = changedItem.encounterID;
                        var newPos = +i + +1;
                        var data = "encounterId_" + encounterId + "|newOrder_" + newPos;
                        jsonData = jsonData + "," + data;
                    }
                    jsonData = jsonData.substring(1);
                    updateEncounterOrder(jsonData);
                }

            }

        }
    };

});
/*
myapp.directive('sortable', function ($timeout) {
    return function (scope, element, attributes) {
        element.sortable({
            stop : function(event, ui){
                scope.$apply(function () {
                    console.log(element.sortable('toArray'));

                    //scope.syncOrder(element.sortable('toArray'));
                });
            }
        });
    };
});*/
angular.bootstrap(document, ['myapp']);



function updateAngularJSList(result){
    var scope = angular.element($("#angularjsResult")).scope();
    scope.$apply(function(){
        var oldList = scope.list;
        console.log(oldList);
        console.log(result);
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